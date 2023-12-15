package panneauxsolaires.model.objects;

import panneauxsolaires.model.entity.*;
import panneauxsolaires.repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataPanneaux {
    double consommationEleve;
    LocalDate datePrevisions;
    Secteur secteur;
    List<Luminosite> listeLuminosite;
    List<Pointage> listePointage;

    public double getConsommationEleve() {
        return consommationEleve;
    }

    public void setConsommationEleve(double consommationEleve) {
        this.consommationEleve = consommationEleve;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public List<Luminosite> getListeLuminosite() {
        return listeLuminosite;
    }

    public List<Pointage> getListePointage() {
        return listePointage;
    }

    public LocalDate getDatePrevisions() {
        return datePrevisions;
    }
    //    setters


    public void setDatePrevisions(LocalDate datePrevisions) {
        this.datePrevisions = datePrevisions;
    }
    public void setDatePrevisions(String datePrevisions) throws Exception {
        if(datePrevisions.isEmpty()){
            throw new Exception("date nulle");
        }
        setDatePrevisions(Utils.convertToLocalDate(datePrevisions)); ;
    }

    public void setSecteur(Secteur panneaux) {
        this.secteur = panneaux;
    }
    public void setSecteur(int idPanneaux, SecteurRepository secteurRepository) throws Exception {
        Optional<Secteur> pan = secteurRepository.findById(idPanneaux);
        if(pan.isPresent()){
            setSecteur(pan.get());
        }
        else{
            throw new Exception("panneaux inexistant");
        }
    }

    public void setListeLuminosite(List<Luminosite> listeLuminosite) {
        this.listeLuminosite = listeLuminosite;
    }
    public void setListeLuminosite( LuminositeRepository luminositeRepository) throws Exception {
        if(getDatePrevisions()==null){
            throw new Exception("veuillez selectionner une date");
        }
        setListeLuminosite(luminositeRepository.findByDateLuminosite(this.getDatePrevisions()));
    }


    public void setListePointage(List<Pointage> listePointage) {
        this.listePointage = listePointage;
    }
    public void setListePointage(PointageRepository pointageRepository) {
        setListePointage(pointageRepository.listePointage(getDatePrevisions(), getSecteur().getId()));
    }

    public DataPanneaux(LocalDate datePrevisions, Secteur panneaux) {
        setDatePrevisions(datePrevisions);
        setSecteur(panneaux);

    }
    public DataPanneaux(LocalDate datePrevisions, Secteur panneaux, PointageRepository pointageRepository,LuminositeRepository luminositeRepository) throws Exception {
        setDatePrevisions(datePrevisions);
        setSecteur(panneaux);
        setListePointage(pointageRepository);
        setListeLuminosite(luminositeRepository);
    }
    public DataPanneaux(String datePrevisions, Secteur panneaux, PointageRepository pointageRepository,LuminositeRepository luminositeRepository) throws Exception {
        setDatePrevisions(datePrevisions);
        setSecteur(panneaux);
        setListePointage(pointageRepository);
        setListeLuminosite(luminositeRepository);
    }

    public DataPanneaux(LocalDate datePrevisions, Secteur panneaux, List<Luminosite> listeLuminosite, List<Pointage> listePointage) {
        setSecteur(panneaux);
        setDatePrevisions(datePrevisions);
        setListeLuminosite(listeLuminosite);
        setListePointage(listePointage);
    }


    public DataPanneaux() {
    }

//
    public List<Pointage> getPointageByLuminosite(Luminosite luminosite){
        List<Pointage> listePointage=new ArrayList<>();
        for (int i = 0; i < getListePointage().size(); i++) {
            if((luminosite.getDebutLuminosite().isAfter(getListePointage().get(i).getDebut_cours()) || luminosite.getDebutLuminosite().equals(getListePointage().get(i).getDebut_cours())) && luminosite.getDebutLuminosite().isBefore(getListePointage().get(i).getFin_cours())){
                listePointage.add(getListePointage().get(i));
            }
        }
        return listePointage;
    }
    public int getSommeEleves(Luminosite luminosite){
        List<Pointage> pointages= getPointageByLuminosite(luminosite);
        int somme = 0;
        for (int i = 0; i < pointages.size(); i++) {
            somme+=pointages.get(i).getNombreEleves();
        }
        return somme;
    }

    public List<Salle> getSalleFromData(SalleRepository salleRepository){
        return salleRepository.findBySecteur(getSecteur());
    }

    public Coupures getHeureCoupureWithOutRecharge(SalleRepository salleRepository){
//        setConsommationEleve(getConsommationEleve()-10);
        double batterie_2= getSecteur().getCapaciteBatterie()/2;
        double batterie=getSecteur().getCapaciteBatterie();
        double consommationTotale=0;
        double puissancePanneaux=0;
        double mihoatra=0;
        boolean coupure = false;
        LocalTime heureCoupure= null;
        List<DetailCoupure> detailCoupures=new ArrayList<>();
        for (int i = 0; i < getListeLuminosite().size(); i++) {
            double resteUtilisable=batterie - batterie_2;
            int nombreeleves=getSommeEleves(getListeLuminosite().get(i));
            consommationTotale= getConsommationEleve()*nombreeleves;
            puissancePanneaux= getSecteur().getCapacitePanneaux()*((double) getListeLuminosite().get(i).getNiveauLuminosite() /10);
            if(puissancePanneaux<consommationTotale){
                batterie-=(consommationTotale-puissancePanneaux);
            }

            if(batterie<batterie_2
                    && puissancePanneaux<consommationTotale
            ){
                int minutes =(int)( (puissancePanneaux+resteUtilisable)*60/consommationTotale);
                heureCoupure= Utils.addMinuteHour(getListeLuminosite().get(i).getDebutLuminosite(),minutes);
                coupure=true;
                detailCoupures.add(new DetailCoupure(consommationTotale, getListeLuminosite().get(i),puissancePanneaux,batterie_2,coupure,nombreeleves,mihoatra));

                break;
            }
            detailCoupures.add(new DetailCoupure(consommationTotale,getListeLuminosite().get(i),puissancePanneaux,batterie,coupure,nombreeleves,mihoatra));
        }
        Coupures coupures= new Coupures(getDatePrevisions(), getSalleFromData(salleRepository), detailCoupures, heureCoupure);
        coupures.setPointages(this.getListePointage());
        coupures.setConsommationParEleve(getConsommationEleve());
        coupures.setSecteur(this.getSecteur());
        return coupures;
    }

    public Coupures getHeureCoupure(SalleRepository salleRepository){
//        setConsommationEleve(getConsommationEleve()-10);
        double batterie_2= getSecteur().getCapaciteBatterie()/2;
        double batterie=getSecteur().getCapaciteBatterie();
        double consommationTotale=0;
        double puissancePanneaux=0;
        boolean coupure = false;
        double mihoatra=0;
        LocalTime heureCoupure= null;
        List<DetailCoupure> detailCoupures=new ArrayList<>();
        for (int i = 0; i < getListeLuminosite().size(); i++) {
            double resteUtilisable=batterie - batterie_2;
            int nombreeleves=getSommeEleves(getListeLuminosite().get(i));
            consommationTotale= getConsommationEleve()*nombreeleves;
            puissancePanneaux= getSecteur().getCapacitePanneaux()*((double) getListeLuminosite().get(i).getNiveauLuminosite() /10);
            if(puissancePanneaux<consommationTotale){
                batterie-=(consommationTotale-puissancePanneaux);
            }
            else if(puissancePanneaux>consommationTotale && batterie< getSecteur().getCapaciteBatterie()){
                mihoatra= puissancePanneaux-consommationTotale;
                double tokonyhiampy=getSecteur().getCapaciteBatterie()-batterie;
                if(mihoatra<tokonyhiampy){
                    batterie+=mihoatra;
                }
                else{
                    batterie+=tokonyhiampy;
                }
            }
            if(batterie<batterie_2
                    && puissancePanneaux<consommationTotale
            ){
                Long secondes= (long) (((puissancePanneaux+resteUtilisable) * 3600)/consommationTotale);
                heureCoupure=getListeLuminosite().get(i).getDebutLuminosite().plusSeconds(secondes);

                coupure=true;
                detailCoupures.add(new DetailCoupure(consommationTotale, getListeLuminosite().get(i),puissancePanneaux,batterie_2,coupure,nombreeleves,mihoatra));

                break;
            }
            detailCoupures.add(new DetailCoupure(consommationTotale,getListeLuminosite().get(i),puissancePanneaux,batterie,coupure,nombreeleves,mihoatra));
        }
        Coupures coupures= new Coupures(getDatePrevisions(), getSalleFromData(salleRepository), detailCoupures, heureCoupure);
        coupures.setPointages(this.getListePointage());
        coupures.setConsommationParEleve(getConsommationEleve());
        coupures.setSecteur(this.getSecteur());
        return coupures;
    }

//    public LocalTime getHeureCoupure(){
//        double batterie_2= getSecteur().getCapaciteBatterie()/2;
//        double batterie=getSecteur().getCapaciteBatterie();
//        double consommationTotale=0;
//        double puissancePanneaux=0;
//        LocalTime heureCoupure= null;
//        for (int i = 0; i < getListeLuminosite().size(); i++) {
//            double resteUtilisable=batterie - batterie_2;
//            consommationTotale= getConsommationEleve()*getSommeEleves(getListeLuminosite().get(i));
//            puissancePanneaux= getSecteur().getCapacitePanneaux()*((double) getListeLuminosite().get(i).getNiveauLuminosite() /10);
//            if(puissancePanneaux<consommationTotale){
//                batterie-=(consommationTotale-puissancePanneaux);
//            }
//
//            else if(puissancePanneaux>consommationTotale && batterie< getSecteur().getCapaciteBatterie()){
//                double mihoatra= puissancePanneaux-consommationTotale;
//                double tokonyhiampy=getSecteur().getCapaciteBatterie()-batterie;
//                if(mihoatra<tokonyhiampy){
//                    batterie+=mihoatra;
//                }
//                else{
//                    batterie+=tokonyhiampy;
//                }
//            }
//
//            if(batterie<batterie_2
////                    && puissancePanneaux<consommationTotale
//            ){
//                double minutes = (puissancePanneaux+resteUtilisable)*60/consommationTotale;
//                heureCoupure= Utils.getMinutes(getListeLuminosite().get(i).getDebutLuminosite(),minutes);
//
//                break;
//            }
//        }
//        return heureCoupure;
//    }

    public LocalTime getHeureCoupure(){
        double batterie_2= getSecteur().getCapaciteBatterie()/2;
        double batterie=getSecteur().getCapaciteBatterie();
        double consommationTotale=0;
        double puissancePanneaux=0;
        LocalTime heureCoupure= null;
        for (int i = 0; i < getListeLuminosite().size(); i++) {
            double resteUtilisable=batterie - batterie_2;
            consommationTotale= getConsommationEleve()*getSommeEleves(getListeLuminosite().get(i));
            puissancePanneaux= getSecteur().getCapacitePanneaux()*((double) getListeLuminosite().get(i).getNiveauLuminosite()/10);
            if(puissancePanneaux<consommationTotale){
                batterie-=(consommationTotale-puissancePanneaux);
            }
            else if(puissancePanneaux>consommationTotale && batterie< getSecteur().getCapaciteBatterie()){
                double mihoatra= puissancePanneaux-consommationTotale;
                double tokonyhiampy=getSecteur().getCapaciteBatterie()-batterie;
                if(mihoatra<tokonyhiampy){
                    batterie+=mihoatra;
                }
                else{
                    batterie+=tokonyhiampy;
                }
            }

            if(batterie<batterie_2
//                    && puissancePanneaux<consommationTotale
            ){
//                double minutes = ((puissancePanneaux+resteUtilisable)*60)/consommationTotale;
//                System.out.println("minutes "+ minutes+" "+puissancePanneaux+ " "+resteUtilisable+" "+consommationTotale);
//
//                heureCoupure= Utils.getMinutes(getListeLuminosite().get(i).getDebutLuminosite(),minutes);
                Long secondes= (long) (((puissancePanneaux+resteUtilisable) * 3600)/consommationTotale);
                heureCoupure=getListeLuminosite().get(i).getDebutLuminosite().plusSeconds(secondes);

                break;
            }
        }
        return heureCoupure;
    }

//    public double getConsommationParEleveDichotomie(CoupuresPanneaux coupuresPanneaux, double consommationDepart){
//        this.setConsommationEleve(consommationDepart);
//        LocalTime getHeureCoupure = this.getHeureCoupure();
//        if(getHeureCoupure.isAfter(coupuresPanneaux.getHeureCoupure())){
//
//        }
//        else{
//
//        }
//    }



    public double dichotomie2 ( CoupuresPanneaux coupuresPanneaux) {
        LocalTime localTime = coupuresPanneaux.getHeureCoupure();
        double intervale1 = 0;
        double intervalle2 = 200;
        double milieu = 0;
        System.out.println("Local " + localTime);

        while ((intervalle2 - intervale1) >= 0.0001) {
            milieu = (intervale1 + intervalle2) / 2;

            this.setConsommationEleve(milieu);
            LocalTime heureCoupure = this.getHeureCoupure();
            System.out.println(milieu + " " + heureCoupure + " " + intervale1 + " " + intervalle2);

            if (heureCoupure == null) {
//                continue;
                intervale1 = milieu;
            } else {
                if (localTime.isAfter(heureCoupure)) {
                    intervalle2 = milieu;
                } else if (localTime.isBefore(heureCoupure)) {
                    intervale1 = milieu;
                }
                if (heureCoupure.equals(localTime)) {
                    return milieu;
                }

            }

        }
        return milieu;
    }
//    public double dichotomie2 ( CoupuresPanneaux coupuresPanneaux){
//        LocalTime localTime=coupuresPanneaux.getHeureCoupure();
//        double intervale1= 0 ;
//        double intervalle2 = 200;
//        double milieu=0;
////        System.out.println("Local "+ localTime);
//
//        while ((intervalle2-intervale1) >= 1e-6){
//            milieu= (intervale1+intervalle2)/2;
//
//            this.setConsommationEleve(milieu);
//            LocalTime heureCoupure= this.getHeureCoupure();
////            System.out.println(milieu+" "+heureCoupure +" "+ intervale1+" "+intervalle2);
//
//            if(heureCoupure==null){
////                continue;
//                intervale1=milieu;
//            }
//            else{
//                if(localTime.isAfter(heureCoupure)){
//                    intervalle2=milieu;
//                }
//                else if(localTime.isBefore(heureCoupure)){
//                    intervale1=milieu;
//                }
//                if(heureCoupure.equals(localTime)){
//                    return milieu;
//                }
//
//            }
//
//        }
//        return milieu;
//    }

    public static double moyenneConsommation (LocalDate datePrevisions,Secteur panneaux,CoupuresPanneauxRepository coupuresPanneauxRepository, PointageRepository pointageRepository, LuminositeRepository luminositeRepository) throws Exception {
        // alaina ny liste an'ny coupures panneaux rehetra
        List<CoupuresPanneaux> coupuresPanneaux= coupuresPanneauxRepository.getCoupuresPanneauxBySecteur(panneaux);
        double sommeConsommation=0;
        int nombreDonne=0;
        // creer l'objet datapanneaux

        List<Double> moyenne = new ArrayList<>();

        for (int i = 0; i < coupuresPanneaux.size(); i++) {
            DataPanneaux dataPanneaux= new DataPanneaux(coupuresPanneaux.get(i).getDate(),panneaux, pointageRepository,luminositeRepository);
            double conso=dataPanneaux.dichotomie2(coupuresPanneaux.get(i));
            sommeConsommation+=conso;
            nombreDonne++;
            System.out.println("=> "+conso);
            moyenne.add(conso);
        }
        // calcul consommation par jours séléctionnée
        // calcul moyenne conso

        moyenne.forEach(System.out::println);
//        return sommeConsommation/nombreDonne;
            return calculateAverage(moyenne);
    }

    public static List<Pointage> getMoyenneNombreEleve(Secteur secteur,LocalDate dateChoisis, PointageRepository pointageRepository, SalleRepository salleRepository) throws Exception {
        List<Object[]> result = pointageRepository.getPointageByDatePointage(dateChoisis, secteur.getId());
        List<Pointage> pointages = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Optional<Salle> salle = salleRepository.findById((Integer) result.get(i)[3]);
            if(salle.isPresent()){
                pointages.add(new Pointage(dateChoisis, Utils.convertToTime(String.valueOf(result.get(i)[1])), Utils.convertToTime(String.valueOf(result.get(i)[2])), Integer.parseInt( String.valueOf(result.get(i)[0])), salle.get()));
            }
            else {
                throw new Exception(" salle introuvable ");
            }
        }
        return pointages;
    }

    public void setNewDataPanneaux(LocalDate datePrevisions, Secteur secteur,LuminositeRepository luminositeRepository, PointageRepository pointageRepository, SalleRepository salleRepository, CoupuresPanneauxRepository coupuresPanneauxRepository) throws Exception {
        this.setDatePrevisions(datePrevisions);
        setConsommationEleve(moyenneConsommation(datePrevisions,secteur,coupuresPanneauxRepository,pointageRepository, luminositeRepository));
        setSecteur(secteur);
        setListePointage(getMoyenneNombreEleve(secteur,datePrevisions,pointageRepository,salleRepository));
        setListeLuminosite(luminositeRepository);
    }

    public List<DataPanneaux> listFuturesPRevisions  (LocalDate datePrevisions, LuminositeRepository luminositeRepository, PointageRepository pointageRepository, SecteurRepository secteurRepository, SalleRepository salleRepository, CoupuresPanneauxRepository coupuresPanneauxRepository) throws Exception {
        List<DataPanneaux> dataPanneaux=new ArrayList<>();
        List<Secteur> listSecteur = secteurRepository.findAll();
        for (int i = 0; i < listSecteur.size(); i++) {
            DataPanneaux dataPanneaux1= new DataPanneaux();
            dataPanneaux1.setNewDataPanneaux(datePrevisions,listSecteur.get(i),luminositeRepository,pointageRepository,salleRepository,coupuresPanneauxRepository);
            dataPanneaux.add(dataPanneaux1);
        }
        return dataPanneaux;
    }

    public List<Coupures> faireUnePrevisionDeCoupure(LocalDate datePrevisions, LuminositeRepository luminositeRepository, PointageRepository pointageRepository, SecteurRepository secteurRepository,SalleRepository salleRepository, CoupuresPanneauxRepository coupuresPanneauxRepository) throws Exception {

        List<DataPanneaux> dataPanneaux=listFuturesPRevisions(datePrevisions,luminositeRepository,pointageRepository,secteurRepository,salleRepository,coupuresPanneauxRepository);
        List<Coupures> coupures = new ArrayList<>();
        for (int i = 0; i < dataPanneaux.size(); i++) {
            coupures.add(dataPanneaux.get(i).getHeureCoupure(salleRepository));
        }
        return coupures;

    }

//    ===========
    public static double calculateAverage(List<Double> liste){
        double somme = 0 ;
        for (int i = 0; i < liste.size(); i++) {
            somme +=liste.get(i);
        }
        return (somme/ liste.size());
    }




}

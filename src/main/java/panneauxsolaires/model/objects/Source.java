///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package panneauxsolaires.model.objects;
//
//import connection.Connexion;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.sql.Time;
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//
///**
// *
// * @author Antsa
// */
//public class Source {
//    private int idSource;
//    private String nom;
//    private Panneau panneau;
//    private Batterie batterie;
//    private Classe[] classes;
//    private static final double debutConsommation=200;
//    private double nbEleveMatin;
//    private double nbEleveAprem;
//
//    public int getIdSource() {
//        return idSource;
//    }
//
//    public void setIdSource(int idSource) throws Exception{
//        if(idSource<=0)
//        {
//            throw new Exception("id source doit etre positif");
//        }
//        else{
//            this.idSource = idSource;
//        }
//    }
//
//    public void setIdSource(String idSource) throws Exception{
//        try{
//            int id=Integer.valueOf(idSource);
//            this.setIdSource(id);
//        }
//        catch(Exception e)
//        {
//            throw new Exception("Id source invalide");
//        }
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//
//    public Panneau getPanneau() {
//        return panneau;
//    }
//
//    public void setPanneau(Panneau panneau) {
//        this.panneau = panneau;
//    }
//
//    public Batterie getBatterie() {
//        return batterie;
//    }
//
//    public void setBatterie(Batterie batterie) {
//        this.batterie = batterie;
//    }
//
//    public Classe[] getClasses() {
//        return classes;
//    }
//
//    public void setClasses(Classe[] classes) {
//        this.classes = classes;
//    }
//
//    public double getNbEleveMatin() {
//        return nbEleveMatin;
//    }
//
//    public void setNbEleveMatin(double nbEleve)throws Exception {
//         if(nbEleve>=0)
//        {
//            this.nbEleveMatin = nbEleve;
//        }
//        else{
//            throw new Exception("Le nombre d'eleve matin doit etre positive ou nulle");
//        }
//    }
//
//     public void setNbEleveMatin(String nbEleve)throws Exception {
//        try
//        {
//           double qtt=Double.valueOf(nbEleve);
//           this.setNbEleveMatin(qtt);
//        }
//        catch(Exception e){
//            throw new Exception("nombre d'eleve matin invalide");
//        }
//    }
//
//    public double getNbEleveAprem() {
//        return nbEleveAprem;
//    }
//
//    public void setNbEleveAprem(double nbEleve)throws Exception {
//         if(nbEleve>=0)
//        {
//            this.nbEleveAprem = nbEleve;
//        }
//        else{
//            throw new Exception("Le nombre d'eleve apres-midi doit etre positive ou nulle");
//        }
//    }
//
//     public void setNbEleveAprem(String nbEleve)throws Exception {
//        try
//        {
//           double qtt=Double.valueOf(nbEleve);
//           this.setNbEleveAprem(qtt);
//        }
//        catch(Exception e){
//            throw new Exception("nombre d'eleve aprem invalide");
//        }
//    }
//
//
//
//    public Source(int idSource,String nom, Panneau panneau, Batterie batterie, Classe[] classes) throws Exception{
//        this.setIdSource(idSource);
//        this.setNom(nom);
//        this.setPanneau(panneau);
//        this.setBatterie(batterie);
//        this.setClasses(classes);
//    }
//
//    public Source(int idSource,Connection con)throws Exception {
//        this.setIdSource(idSource);
//        this.setNom(this.getNomById(con));
//        this.setClasses(this.getClasse(con));
//        this.setPanneau(this.getPanneau(con));
//        this.setBatterie(this.getBatterie(con));
//    }
//
//    public Source() {
//    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public Resultat[] getResultat(double nbMatin,double nbAprem,double consommation,Date d,Connection con) throws Exception
//    {
//        ArrayList<Resultat> result=new ArrayList<>();
//        Resultat[] r=new Resultat[0];
//        Time coupure=Source.enleveUneHeure(Classe.debutMatin);
//        double batt=this.getBatterie().getPuissance();
//        double puissanceseuil=this.getBatterie().getPuissance()*this.getBatterie().getSeuil()/100;
//        Luminosite l=new Luminosite();
//        double puissancepanneau=0;
//        double besoin=consommation*nbMatin;
//        while(batt>puissanceseuil)
//        {
//            coupure=Source.ajoutUneHeure(coupure);
//            if(coupure.equals(Classe.finMatin))
//            {
//                coupure=Classe.debutAprem;
//            }
//            if(coupure.after(Classe.finMatin))
//            {
//                besoin=consommation*nbAprem;
//            }
//            l=Luminosite.getLuminositeHeure(d, coupure, coupure, con);
//            puissancepanneau=this.getPanneau().puissancePanneau(l);
//            Resultat re=new Resultat(coupure,besoin,l.getLuminosite(),puissancepanneau,batt,consommation);
//            result.add(re);
//            System.out.println("coupure : "+coupure+" | consommation : "+consommation+" | besoin : "+besoin+" | luminosite : "+l.getLuminosite()+" | panneau : "+puissancepanneau+" | batterie : "+batt);
//            if(puissancepanneau<=besoin)
//            {
//                double necessite=besoin-puissancepanneau;
//                if(batt-puissanceseuil>=necessite)
//                {
//                    batt-=necessite;
//                }
//                else{
//                    double reste=batt-puissanceseuil;
//                    double minute=reste*60/necessite;
//                    coupure=Source.ajoutMinute(coupure, minute);
//                    batt=puissanceseuil;
//                    Resultat res=new Resultat(coupure,besoin,l.getLuminosite(),puissancepanneau,batt,consommation);
//                    result.add(res);
//                    System.out.println("coupure : "+coupure+" | consommation : "+consommation+" | besoin : "+besoin+" | luminosite : "+l.getLuminosite()+" | panneau : "+puissancepanneau+" | batterie : "+batt);
//                }
//            }
//            else if(puissancepanneau>besoin)
//            {
//                double charge=puissancepanneau-besoin;
//                if(batt+charge<this.getBatterie().getPuissance())
//                {
//                    System.out.println(coupure+" haha"+charge);
//                    batt+=charge;
//                }
//                else{
//                    batt=this.getBatterie().getPuissance();
//                }
//            }
//            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
//        }
//        return result.toArray(r);
//    }
//
//    public Resultat getConsommation(Date d,Connection con) throws Exception
//    {
//        Resultat r=new Resultat();
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            double consommation=Source.debutConsommation;
//            Time heurecoupure=this.getHeureCoupure(d,con);
//            double nbMatin=this.getNb(d,"matin",con);
//            double nbAprem=this.getNb(d,"aprem",con);
//            //System.out.println(nbMatin+"huhu"+nbAprem);
//            Resultat[] res=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//            r=res[res.length-1];
//            double moitie=Source.debutConsommation;
//            while(!r.getHeureCoupure().equals(heurecoupure))
//            {
//                //System.out.println(this.getIdSource()+" "+consommation+" ");
//                moitie/=2;
//                if(r.getHeureCoupure().before(heurecoupure))
//                {
//                    consommation-=moitie;
//                    Resultat[] resl=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//                    r=resl[resl.length-1];
//                }
//                else{
//                    consommation+=moitie;
//                    Resultat[] resl=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//                    r=resl[resl.length-1];
//                }
//            }
//            System.out.println(r.getHeureCoupure()+" jl,vjls"+heurecoupure);
//
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return r;
//
//    }
//
//    /*public Resultat getConsommation(Date d,Connection con) throws Exception
//    {
//        Resultat r=new Resultat();
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            double consommation=Source.debutConsommation;
//            Time heurecoupure=this.getHeureCoupure(d,con);
//            double nbMatin=this.getNb(d,"matin",con);
//            double nbAprem=this.getNb(d,"aprem",con);
//            //System.out.println(nbMatin+"huhu"+nbAprem);
//            Resultat[] res=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//            r=res[res.length-1];
//            while(!r.getHeureCoupure().equals(heurecoupure))
//            {
//                //System.out.println(this.getIdSource()+" "+consommation+" ");
//                if(r.getHeureCoupure().before(heurecoupure))
//                {
//                    consommation-=0.01;
//                    Resultat[] resl=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//                    r=resl[resl.length-1];
//                }
//                else{
//                    consommation+=0.01;
//                    Resultat[] resl=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//                    r=resl[resl.length-1];
//                }
//            }
//
//
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return r;
//
//    }*/
//
//    public Resultat[] getCoupure(Date d,Connection con) throws Exception
//    {
//        Resultat[] re=new Resultat[0];
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            double nbMatin=this.getNbMoy(d,"matin",con);
//            double nbAprem=this.getNbMoy(d,"aprem",con);
//            double consommation=Source.getConsommationMoy(con);
//            //System.out.println("huhu"+consommation);
//            re=this.getResultat(nbMatin,nbAprem,consommation,d,con);
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return re;
//    }
//
//    public static ArrayList<Coupure> getAllCoupure(String date,Connection con) throws Exception
//    {
//        Date d=new Date(System.currentTimeMillis());
//        if(!date.equals(""))
//        {
//            d=Date.valueOf(date);
//        }
//        ArrayList<Coupure> result=new ArrayList<>();
//        if(Luminosite.getLuminosite(d, con).length==0)
//        {
//            throw new Exception("La meteo n'est pas encore insérée pour cette date");
//        }
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            Source[] s=Source.getAllSource(con);
//            for(Source sr : s)
//            {
//                Resultat[] re=sr.getCoupure(d, con);
//                Coupure c=new Coupure(re,sr,re[re.length-1].getHeureCoupure());
//                result.add(c);
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return result;
//    }
//    public static double getConsommationMoy(Connection con) throws Exception
//    {
//        double consommationMoy=0;
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            Source[] all=Source.getAllSource(con);
//            int nb=0;
//            for(Source s : all)
//            {
//                Date[] daty=s.getDateCoupure(con);
//                for(Date d: daty)
//                {
//                    Resultat r=s.getConsommation(d,con);
//                    consommationMoy+=r.getConsommationUnit();
//                    nb++;
//                }
//            }
//            consommationMoy/=nb;
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return consommationMoy;
//    }
//    public Time getHeureCoupure(Date d,Connection con) throws Exception
//    {
//        Time t=new Time(0);
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select heurecoupure from coupure where idsource="+this.getIdSource()+" and datecoupure='"+d+"'";
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//                t=res.getTime("heurecoupure");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return t;
//    }
//
//    public static Time ajoutUneHeure(Time t)
//    {
//        long heure=t.getTime()+3600000;
//        return new Time(heure);
//    }
//    public static Time enleveUneHeure(Time t)
//    {
//        long heure=t.getTime()-3600000;
//        return new Time(heure);
//    }
//    public static Time ajoutMinute(Time t,double minut)
//    {
//        LocalTime time=t.toLocalTime();
//        long minutes = (long) minut;
//        int seconds = (int) ((minut - minutes) * 60);
//        long newTime = time.toSecondOfDay() * 1000 + (long) (minutes * 60 * 1000)+(long) (seconds)*1000;
//
//        return Time.valueOf(LocalTime.ofSecondOfDay(newTime / 1000));
//    }
//
//    public static int getJour(Date d)throws Exception
//    {
//        try{
//            LocalDate ld=d.toLocalDate();
//            DayOfWeek dayOfWeek = ld.getDayOfWeek();
//            return dayOfWeek.getValue();
//        }
//        catch(Exception e)
//        {
//            throw e;
//        }
//    }
//
//
//    public double getNbMoy(Date d,String dj,Connection con) throws Exception {
//        double nb=0;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            for (Classe classe : this.getClasses()) {
//                nb += classe.getNbMoy(d, dj, con);
//            }
//        }
//         catch(Exception ex){
//            throw ex;
//        }
//        finally{
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        if(dj.equalsIgnoreCase("matin")) this.setNbEleveMatin(nb);
//        else this.setNbEleveAprem(nb);
//        return nb;
//    }
//
//    public double getNb(Date d,String dj,Connection con) throws Exception {
//        double nb=0;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            for (Classe classe : this.getClasses()) {
//                nb += classe.getNb(d, dj, con);
//            }
//        }
//         catch(Exception ex){
//            throw ex;
//        }
//        finally{
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return nb;
//    }
//
//    public Classe[] getClasse(Connection con) throws Exception{
//        ArrayList<Classe> classe=new ArrayList<>();
//        Classe[] kilasy;
//        kilasy = new Classe[0];
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select * from v_SourceClasse where idsource="+this.getIdSource();
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//                Classe c=new Classe(res.getInt("idclasse"),res.getString("nomclasse"),res.getInt("nbtotalplace"));
//                classe.add(c);
//            }
//            if(classe.isEmpty())
//            {
//                throw new Exception("Cette source ne comporte aucune classe");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return classe.toArray(kilasy);
//    }
//
//    public Panneau getPanneau(Connection con) throws Exception{
//        /*ArrayList<Panneau> pann=new ArrayList<Panneau>();
//        Panneau[] pan=new Panneau[0];*/
//        Panneau p=null;
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select * from v_source where idsource="+this.getIdSource();
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//               p=new Panneau(res.getInt("idpanneau"),res.getString("marquepanneau"),Unite.getById(res.getInt("idunitepanneau"), con),res.getDouble("puissancepanneau"));
//               //pann.add(p);
//            }
//            if(p==null)
//            {
//                throw new Exception("Cette source ne comporte aucun panneau");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return p;
//        //return pann.toArray(pan);
//    }
//
//     public Batterie getBatterie(Connection con) throws Exception{
//        /*ArrayList<Batterie> batt=new ArrayList<Batterie>();
//        Batterie[] bat=new Batterie[0];*/
//        Batterie b=null;
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select * from v_source where idsource="+this.getIdSource();
//            //String requete = "select * from SourceBatterie where idsource="+this.getIdSource();
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//               b=new Batterie(res.getInt("idbatterie"),res.getString("marquebatterie"),Unite.getById(res.getInt("idunitebatterie"), con),res.getDouble("puissancebatterie"));
//               //batt.add(b);
//            }
//            if(b==null)
//            {
//                throw new Exception("Cette source ne comporte aucune batterie");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return b;
//        //return batt.toArray(bat);
//    }
//
//     public static Source[] getAllSource(Connection con) throws Exception
//     {
//        ArrayList<Source> source=new ArrayList<>();
//        Source[] s;
//        s = new Source[0];
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select * from source";
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//                Source so=new Source(res.getInt("idsource"),con);
//                source.add(so);
//            } if(source.isEmpty())
//            {
//                throw new Exception("Aucune source");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return source.toArray(s);
//     }
//
//     public String getNomById(Connection con) throws Exception
//     {
//        String nom="";
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select nomsource from source where idsource="+this.getIdSource();
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//               nom=res.getString("nomsource");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return nom;
//     }
//
//     public Date[] getDateCoupure(Connection con) throws Exception
//     {
//        ArrayList<Date> date=new ArrayList<>();
//        Date[] d;
//        d = new Date[0];
//        Statement st = null;
//        ResultSet res = null;
//        int co=0;
//        if(con==null)
//        {
//            con=Connexion.getConnect("postgres");
//            co=1;
//        }
//        try{
//            String requete = "select datecoupure from coupure where idsource="+this.getIdSource();
//            st = con.createStatement();
//            res = st.executeQuery(requete);
//            while(res.next())
//            {
//               Date da=res.getDate("datecoupure");
//               date.add(da);
//            }
//             if(date.isEmpty())
//            {
//                throw new Exception("Cette source ne comporte aucune donnée de coupure");
//            }
//        }
//        catch(Exception ex){
//            throw ex;
//        }
//        finally{
//
//            if( res != null ){
//                res.close();
//            }
//            if(st != null ){
//                st.close();
//            }
//            if(co==1)
//            {
//                con.close();
//            }
//        }
//        return date.toArray(d);
//     }
//}

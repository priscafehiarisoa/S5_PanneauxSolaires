package panneauxsolaires.model.objects;

import panneauxsolaires.model.entity.Pointage;
import panneauxsolaires.model.entity.Salle;
import panneauxsolaires.model.entity.Secteur;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Coupures {
    LocalDate date;
    List<Salle> listeSalle;

    List<Pointage> pointages;
    List<DetailCoupure> detailCoupures;
    LocalTime heureCoupure;
    double consommationParEleve;
    Secteur secteur;

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public double getConsommationParEleve() {
        return consommationParEleve;
    }

    public void setConsommationParEleve(double consommationParEleve) {
        this.consommationParEleve = consommationParEleve;
    }

    public List<Pointage> getPointages() {
        return pointages;
    }

    public void setPointages(List<Pointage> pointages) {
        this.pointages = pointages;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Salle> getListeSalle() {
        return listeSalle;
    }

    public void setListeSalle(List<Salle> listeSalle) {
        this.listeSalle = listeSalle;
    }

    public List<DetailCoupure> getDetailCoupures() {
        return detailCoupures;
    }

    public void setDetailCoupures(List<DetailCoupure> detailCoupures) {
        this.detailCoupures = detailCoupures;
    }

    public LocalTime getHeureCoupure() {
        return heureCoupure;
    }

    public void setHeureCoupure(LocalTime heureCoupure) {
        this.heureCoupure = heureCoupure;
    }

    public Coupures(LocalDate date, List<Salle> listeSalle, List<DetailCoupure> detailCoupures, LocalTime heureCoupure) {
        setDate(date);
        setListeSalle(listeSalle);
        setDetailCoupures(detailCoupures);
        setHeureCoupure(heureCoupure);
    }

    public Coupures() {
    }

    @Override
    public String toString() {
        return "Coupures{" +
                ", heureCoupure=" + heureCoupure +
                ", consommation par eleve =" + consommationParEleve +
                " date=" + date +
                ",\t listeSalle=" + listeSalle +
                ",\t \t detailCoupures=" + detailCoupures +
                ",\t \t pointage=" + pointages +

                '}';
    }
}

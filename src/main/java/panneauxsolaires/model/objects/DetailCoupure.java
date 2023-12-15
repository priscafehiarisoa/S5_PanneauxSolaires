package panneauxsolaires.model.objects;

import panneauxsolaires.model.entity.Luminosite;

import java.time.LocalTime;

public class DetailCoupure {
    double consommation ;
     Luminosite luminosite;
     double puissancePanneaux;
     int nombresEleves;
     double resteBatterie;
     boolean coupure;
     double surplus;

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public double getConsommation() {
        return consommation;
    }

    public int getNombresEleves() {
        return nombresEleves;
    }

    public void setNombresEleves(int nombresEleves) {
        this.nombresEleves = nombresEleves;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public Luminosite getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(Luminosite luminosite) {
        this.luminosite = luminosite;
    }

    public double getPuissancePanneaux() {
        return puissancePanneaux;
    }

    public void setPuissancePanneaux(double puissancePanneaux) {
        this.puissancePanneaux = puissancePanneaux;
    }

    public double getResteBatterie() {
        return resteBatterie;
    }

    public void setResteBatterie(double resteBatterie) {
        this.resteBatterie = resteBatterie;
    }

    public boolean isCoupure() {
        return coupure;
    }

    public void setCoupure(boolean coupure) {
        this.coupure = coupure;
    }

    public DetailCoupure() {
    }

    public DetailCoupure(double consommation, Luminosite luminosite, double puissancePanneaux, double resteBatterie, boolean coupure, int nombresEleves,double surplus) {
        setConsommation(consommation);
        setLuminosite(luminosite);
        setPuissancePanneaux(puissancePanneaux);
        setResteBatterie(resteBatterie);
        setCoupure(coupure);
        setNombresEleves(nombresEleves);
        setSurplus(surplus);
    }

    @Override
    public String toString() {
        return "DetailCoupure{" +
                "consommation=" + consommation +
                " eleves : =" + nombresEleves +
                ", luminosite=" + luminosite +
                ", puissancePanneaux=" + puissancePanneaux +
                ", resteBatterie=" + resteBatterie +
                ", coupure=" + coupure +
                '}';
    }
}

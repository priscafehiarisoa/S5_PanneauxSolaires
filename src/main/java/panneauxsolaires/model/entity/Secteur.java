package panneauxsolaires.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Secteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomPanneaux;

    private double capaciteBatterie;
    private double capacitePanneaux;

    public String getNomPanneaux() {
        return nomPanneaux;
    }

    public void setNomPanneaux(String nomPanneaux) {
        this.nomPanneaux = nomPanneaux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCapaciteBatterie() {
        return capaciteBatterie;
    }

    public void setCapaciteBatterie(double capaciteBatterie) throws Exception {
        if(capaciteBatterie<0){
            throw new Exception("capaciteBatterie est negative ");
        }
        this.capaciteBatterie = capaciteBatterie;
    }
    public void setCapaciteBatterie(String capaciteBatterie) throws Exception {
        if(capaciteBatterie.isEmpty()){
            throw new Exception("la chaine de caractère de capaciteBatterie est vide ");
        }
        setCapaciteBatterie(Double.parseDouble(capaciteBatterie)); ;
    }

    public double getCapacitePanneaux() {
        return capacitePanneaux;
    }

    public void setCapacitePanneaux(double capacitePanneaux) throws Exception {
        if(capacitePanneaux<0){
            throw new Exception("capacitePanneaux est negative ");
        }
        this.capacitePanneaux = capacitePanneaux;
    }
    public void setCapacitePanneaux(String capacitePanneaux) throws Exception {
        if(capacitePanneaux.isEmpty()){
            throw new Exception("la chaine de caractère de capacitePanneaux est vide ");
        }
        setCapacitePanneaux(Double.parseDouble(capacitePanneaux));
    }

    public Secteur(int id,String nomPanneaux, double capaciteBatterie, double capacitePanneaux) throws Exception {
        setId(id);
        setNomPanneaux(nomPanneaux);
        setCapaciteBatterie(capaciteBatterie);
        setCapacitePanneaux(capacitePanneaux);
    }

    public Secteur(String nomPanneaux,double capaciteBatterie, double capacitePanneaux) throws Exception {
        setCapaciteBatterie(capaciteBatterie);
        setNomPanneaux(nomPanneaux);
        setCapacitePanneaux(capacitePanneaux);
    }
    public Secteur(String capaciteBatterie, String capacitePanneaux) throws Exception {
        setCapaciteBatterie(capaciteBatterie);
        setCapacitePanneaux(capacitePanneaux);
    }

    public Secteur() {
    }

    @Override
    public String toString() {
        return "Panneaux{" +
                "id=" + id +
                ", nomPanneaux='" + nomPanneaux + '\'' +
                ", capaciteBatterie=" + capaciteBatterie +
                ", capacitePanneaux=" + capacitePanneaux +
                '}';
    }
}

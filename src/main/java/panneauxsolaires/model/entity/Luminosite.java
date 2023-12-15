package panneauxsolaires.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import panneauxsolaires.model.objects.Utils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Luminosite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    LocalDate dateLuminosite;
    LocalTime debutLuminosite;
    LocalTime finLuminosite;
    int niveauLuminosite;

    public int getNiveauLuminosite() {
        return niveauLuminosite;
    }

    public void setNiveauLuminosite(int niveauLuminosite) throws Exception {
        if(niveauLuminosite<0){
            throw new Exception("niveau de luminosité négative");
        }
        else if(niveauLuminosite>10){
            throw new Exception("la limite de niveau de luminosite est de 10 ");
        }
        this.niveauLuminosite = niveauLuminosite;
    }

    public void setNiveauLuminosite(String niveauLuminosite) throws Exception {
        if(niveauLuminosite.isEmpty()){
            throw new Exception("niveau de luminosite vide");
        }
        setNiveauLuminosite(Integer.parseInt(niveauLuminosite));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateLuminosite() {
        return dateLuminosite;
    }

    public void setDateLuminosite(LocalDate dateLuminosite) {
        this.dateLuminosite = dateLuminosite;
    }
    public void setDateLuminosite(String dateLuminosite) {
        setDateLuminosite(Utils.convertToLocalDate(dateLuminosite));
    }

//    public void setDateLuminosite(String dateLuminosite) {
//        this.dateLuminosite = dateLuminosite;
//    }

    public LocalTime getDebutLuminosite() {
        return debutLuminosite;
    }

    public void setDebutLuminosite(LocalTime debutLuminosite) {
        this.debutLuminosite = debutLuminosite;
    }
    public void setDebutLuminosite(String debutLuminosite) {
        setDebutLuminosite(Utils.convertToTime(debutLuminosite));
    }

    public LocalTime getFinLuminosite() {
        return finLuminosite;
    }

    public void setFinLuminosite(LocalTime finLuminosite) {
        this.finLuminosite = finLuminosite;
    }
    public void setFinLuminosite(String finLuminosite) {
        setFinLuminosite(Utils.convertToTime(finLuminosite));
    }

    public Luminosite(int id, LocalDate dateLuminosite, LocalTime debutLuminosite, LocalTime finLuminosite,int niveauLuminosite) throws Exception {
        setId(id);
        setDateLuminosite(dateLuminosite);
        setDebutLuminosite(debutLuminosite);
        setFinLuminosite(finLuminosite);
        setNiveauLuminosite(niveauLuminosite);
    }

    public Luminosite(LocalDate dateLuminosite, LocalTime debutLuminosite, LocalTime finLuminosite,int niveauLuminosite) throws Exception {
        setDateLuminosite(dateLuminosite);
        setDebutLuminosite(debutLuminosite);
        setFinLuminosite(finLuminosite);
        setNiveauLuminosite(niveauLuminosite);
    }
    public Luminosite(String dateLuminosite, String debutLuminosite, String finLuminosite,String niveauLuminosite) throws Exception {
        setDateLuminosite(dateLuminosite);
        setDebutLuminosite(debutLuminosite);
        setFinLuminosite(finLuminosite);
        setNiveauLuminosite(niveauLuminosite);

    }

    public Luminosite() {
    }

    @Override
    public String toString() {
        return "Luminosite{" +
                "id=" + id +
                ", dateLuminosite=" + dateLuminosite +
                ", debutLuminosite=" + debutLuminosite +
                ", finLuminosite=" + finLuminosite +
                ", niveauLuminosite=" + niveauLuminosite +
                '}';
    }
}

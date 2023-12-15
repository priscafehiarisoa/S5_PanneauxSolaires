package panneauxsolaires.model.entity;

import jakarta.persistence.*;
import panneauxsolaires.model.objects.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Pointage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate datePointage;
    private LocalTime debut_cours;
    private LocalTime fin_cours;
    private int nombreEleves;
    @ManyToOne
    @JoinColumn(name = "id_salle")
    private Salle salle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(LocalDate datePointage) {
        this.datePointage = datePointage;
    }
    public void setDatePointage(String datePointage) {
        setDatePointage(Utils.convertToLocalDate(datePointage));
    }

    public LocalTime getDebut_cours() {
        return debut_cours;
    }

    public void setDebut_cours(LocalTime debut_cours) {
        this.debut_cours = debut_cours;
    }
    public void setDebut_cours(String debut_cours) {
        setDebut_cours(Utils.convertToTime(debut_cours));
    }

    public LocalTime getFin_cours() {
        return fin_cours;
    }

    public void setFin_cours(LocalTime fin_cours) {
        this.fin_cours = fin_cours;
    }
    public void setFin_cours(String fin_cours) {
        setFin_cours(Utils.convertToTime(fin_cours));
    }

    public int getNombreEleves() {
        return nombreEleves;
    }

    public void setNombreEleves(int nombreEleves) {
        this.nombreEleves = nombreEleves;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Pointage(int id, LocalDate datePointage, LocalTime debut_cours, LocalTime fin_cours, int nombreEleves, Salle salle) {
       setId(id);
        setDatePointage(datePointage);
        setDebut_cours(debut_cours);
        setFin_cours(fin_cours);
        setNombreEleves(nombreEleves);
        setSalle(salle);
    }

    public Pointage(LocalDate datePointage, LocalTime debut_cours, LocalTime fin_cours, int nombreEleves, Salle salle) {
        setDatePointage(datePointage);
        setDebut_cours(debut_cours);
        setFin_cours(fin_cours);
        setNombreEleves(nombreEleves);
        setSalle(salle);
    }

    public Pointage() {
    }

    @Override
    public String toString() {
        return "Pointage{" +
                "id=" + id +
                ", datePointage=" + datePointage +
                ", debut_cours=" + debut_cours +
                ", fin_cours=" + fin_cours +
                ", nombreEleves=" + nombreEleves +
                ", salle=" + salle +
                '}';
    }
}

package panneauxsolaires.model.entity;

import jakarta.persistence.*;
import panneauxsolaires.model.objects.Utils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class CoupuresPanneaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;
    private LocalTime heureCoupure;
    @ManyToOne
    @JoinColumn(name = "idSecteur")
    private Secteur secteur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setDate(String date) {
       setDate(Utils.convertToLocalDate(date));
    }

    public LocalTime getHeureCoupure() {
        return heureCoupure;
    }

    public void setHeureCoupure(LocalTime heureCoupure) {
        this.heureCoupure = heureCoupure;
    }
    public void setHeureCoupure(String heureCoupure) {
       setHeureCoupure(Utils.convertToTime(heureCoupure));
    }


    public CoupuresPanneaux(int id, LocalDate date, LocalTime heureCoupure) {
        setId(id);
        setDate(date);
        setHeureCoupure(heureCoupure);
    }

    public CoupuresPanneaux(LocalDate date, LocalTime heureCoupure) {
        setDate(date);
        setHeureCoupure(heureCoupure);
    }

    public CoupuresPanneaux() {
    }
}

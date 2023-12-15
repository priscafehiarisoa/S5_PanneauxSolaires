package panneauxsolaires.model.entity;

import jakarta.persistence.*;
import org.springframework.data.repository.query.Param;

@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomSalle;
    @ManyToOne
    @JoinColumn(name = "id_secteur")
    private Secteur secteur;

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur panneaux) {
        this.secteur = panneaux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Salle(int id, String nomSalle) {
        setId(id);
        setNomSalle(nomSalle);
    }

    public Salle(String nomSalle) {
        setNomSalle(nomSalle);
    }

    public Salle (){

    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nomSalle='" + nomSalle + '\'' +
                ", panneaux=" + secteur +
                '}';
    }
}

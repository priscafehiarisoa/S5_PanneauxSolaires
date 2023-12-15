package panneauxsolaires.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import panneauxsolaires.model.entity.Salle;
import panneauxsolaires.model.entity.Secteur;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle,Integer> {
    List<Salle> findBySecteur(Secteur secteur);
}

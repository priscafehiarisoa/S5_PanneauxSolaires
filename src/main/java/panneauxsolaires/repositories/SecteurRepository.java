package panneauxsolaires.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import panneauxsolaires.model.entity.Secteur;

public interface SecteurRepository extends JpaRepository<Secteur, Integer> {
}

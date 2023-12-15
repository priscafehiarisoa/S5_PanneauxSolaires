package panneauxsolaires.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import panneauxsolaires.model.entity.Luminosite;

import java.time.LocalDate;
import java.util.List;

public interface LuminositeRepository extends JpaRepository<Luminosite,Integer> {
    List<Luminosite> findByDateLuminosite(LocalDate dateLuminosite);
}

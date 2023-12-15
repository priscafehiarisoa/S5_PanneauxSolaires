package panneauxsolaires.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import panneauxsolaires.model.entity.CoupuresPanneaux;
import panneauxsolaires.model.entity.Secteur;

import java.time.LocalDate;
import java.util.List;

public interface CoupuresPanneauxRepository extends JpaRepository<CoupuresPanneaux,Integer> {

    List<CoupuresPanneaux> getCoupuresPanneauxBySecteur (Secteur secteur);

    @Query(value = "select * from coupures_panneaux   where extract(dow from date)=extract (DOW from CAST(?1 AS DATE)) and id_secteur=?2 ",nativeQuery = true)
    List<CoupuresPanneaux> getCoupuresPanneauxBydate( LocalDate date,int  idsecteur );
}

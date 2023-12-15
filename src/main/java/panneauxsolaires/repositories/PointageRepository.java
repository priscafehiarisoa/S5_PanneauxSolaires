package panneauxsolaires.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import panneauxsolaires.model.entity.Pointage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface PointageRepository extends JpaRepository<Pointage,Integer> {

    // fonction maka pointage en fonction an'ny date su panneaux
//    @Query(value = "select Pointage from Pointage p join Salle")
    @Query (value = "select p from Pointage p where p.datePointage=:datePointage" +
            " and p.salle.secteur.id=:idsecteur"
            )
    List<Pointage> listePointage(@Param("datePointage") LocalDate date
            , @Param("idsecteur") int idsecteur
    );

    @Query(value = "select ROUND(AVG(nombre_eleves), 0) as nombre_eleves, debut_cours, fin_cours,id_salle from Pointage join salle s on s.id = Pointage.id_salle join secteur s2 on s2.id = s.id_secteur  where extract(dow from date_pointage)=extract (DOW from CAST(?1 AS DATE)) and id_secteur=?2 group by debut_cours, fin_cours, id_salle" , nativeQuery = true)
    List<Object[]> getPointageByDatePointage(@Param("datePointage") LocalDate datePointage, @Param("idsecteur") int idsecteur);
}

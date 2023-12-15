package panneauxsolaires;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import panneauxsolaires.model.entity.CoupuresPanneaux;
import panneauxsolaires.model.entity.Secteur;
import panneauxsolaires.model.objects.DataPanneaux;
import panneauxsolaires.model.objects.Utils;
import panneauxsolaires.repositories.*;

import java.util.List;
import java.util.Optional;

@Configuration
public class Main {
    @Bean
    CommandLineRunner commandLineRunner (LuminositeRepository luminositeRepository,
                                         PointageRepository pointageRepository,
                                         SecteurRepository secteurRepository,
                                         SalleRepository salleRepository,
                                         CoupuresPanneauxRepository coupuresPanneauxRepository){
        return args -> {
            Optional<Secteur> panneaux= secteurRepository.findById(1);
            Secteur panneaux1= panneaux.get();

            DataPanneaux dataPanneaux = new DataPanneaux("2023-11-01",panneaux1,pointageRepository,luminositeRepository);
            dataPanneaux.setConsommationEleve(74.0972900390625);
//            System.out.println(dataPanneaux.getDatePrevisions());
//            System.out.println(dataPanneaux.getPanneaux());
//            System.out.println("====luminosite===");
//            dataPanneaux.getListeLuminosite().forEach(System.out::println);
//            System.out.println("====pointage===");
//            dataPanneaux.getListePointage().forEach(System.out::println);
//
//            System.out.println(dataPanneaux.getSommeEleves(dataPanneaux.getListeLuminosite().get(0)));
//                Coupures coupures= dataPanneaux.getHeureCoupure(salleRepository);
//
//                coupures.getDetailCoupures().forEach(System.out::println);
//
//            System.out.println(DataPanneaux.moyenneConsommation(panneaux1,coupuresPanneauxRepository,pointageRepository,luminositeRepository));
            System.out.println(dataPanneaux.getHeureCoupure());

/**
            92.55819320678711 */

            CoupuresPanneaux coupuresPanneaux = new CoupuresPanneaux(Utils.convertToLocalDate("2023-12-11"),Utils.convertToTime("15:46:00"));
//
            List<CoupuresPanneaux> coupuresPanneaux1= coupuresPanneauxRepository.findAll();
            for (int i = 0; i < coupuresPanneaux1.size(); i++) {
                dataPanneaux=new DataPanneaux(coupuresPanneaux1.get(i).getDate(),panneaux1,pointageRepository,luminositeRepository);
                System.out.println(dataPanneaux.dichotomie2(coupuresPanneaux1.get(i)));

            }


//            List<CoupuresPanneaux> listeCoupures = coupuresPanneauxRepository.findAll();
//            for (int i = 0; i < listeCoupures.size(); i++) {
//                DataPanneaux dataPa= new DataPanneaux(listeCoupures.get(i).getDate(),panneaux.get() , pointageRepository,luminositeRepository);
//                System.out.println(listeCoupures.get(i).getDate()+" "+dataPa.dichotomie2(listeCoupures.get(i)));
//            }


//            dataPanneaux.getMoyenneNombreEleve(Utils.convertToLocalDate("2023-12-13"),pointageRepository,salleRepository).forEach(System.out::println);
//                dataPanneaux.faireUnePrevisionDeCoupure(Utils.convertToLocalDate("2023-12-13"), luminositeRepository, pointageRepository, secteurRepository, salleRepository,coupuresPanneauxRepository).forEach(System.out::println);

            System.out.println(dataPanneaux.getHeureCoupure(salleRepository));
//            coupuresPanneauxRepository.getCoupuresPanneauxBydate(Utils.convertToLocalDate("2023-12-11"),1).forEach(System.out::println);
        };
    }
}

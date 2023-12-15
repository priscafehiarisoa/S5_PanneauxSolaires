package panneauxsolaires.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import panneauxsolaires.model.entity.Secteur;
import panneauxsolaires.model.objects.Coupures;
import panneauxsolaires.model.objects.DataPanneaux;
import panneauxsolaires.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    private final LuminositeRepository luminositeRepository;
    private final PointageRepository pointageRepository;
    private final SecteurRepository secteurRepository;
    private final SalleRepository salleRepository;
    private final CoupuresPanneauxRepository coupuresPanneauxRepository;

    public ViewController(LuminositeRepository luminositeRepository,
                          PointageRepository pointageRepository,
                          SecteurRepository secteurRepository,
                          SalleRepository salleRepository,
                          CoupuresPanneauxRepository coupuresPanneauxRepository) {
        this.luminositeRepository = luminositeRepository;
        this.pointageRepository = pointageRepository;
        this.secteurRepository = secteurRepository;
        this.salleRepository = salleRepository;
        this.coupuresPanneauxRepository = coupuresPanneauxRepository;
    }

    @GetMapping({"/","Cluminosite"})
    public String geLuminosite(){
        return "luminosite";
    }

    @GetMapping("/formCoupures")
    public String getFromCoupures(){
        return "coupures/formCoupures";
    }

    @PostMapping("/getCoupures")
    public String getCoupures(@RequestParam("dates")LocalDate dates, Model model){
        String redirectingView="coupures/ListCoupures";
        DataPanneaux dataPanneaux= new DataPanneaux();
        try{
            List<Coupures> coupures=dataPanneaux.faireUnePrevisionDeCoupure(dates,luminositeRepository,pointageRepository,secteurRepository,salleRepository,coupuresPanneauxRepository);
            model.addAttribute("coupures",coupures);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            redirectingView="coupures/ListCoupures";
        }
        System.out.println(dates);
        return redirectingView ;
    }

    @GetMapping("/testCoupure")
    public String getCoupure2(Model model) throws Exception {
        Optional<Secteur> panneaux= secteurRepository.findById(1);
        Secteur panneaux1= panneaux.get();
        DataPanneaux dataPanneaux = new DataPanneaux("2023-12-13",panneaux1,pointageRepository,luminositeRepository);
        dataPanneaux.setConsommationEleve(60);
        Coupures coupures= dataPanneaux.getHeureCoupure(salleRepository);
        model.addAttribute("coupures",coupures);

        return "coupures/coupure";
    }
}

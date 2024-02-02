package Harmoniz.demo.Services;

import Harmoniz.demo.DTO.AppBudgetDTO;
import Harmoniz.demo.Entities.AppBudget;
import Harmoniz.demo.Entities.AppFlux;
import Harmoniz.demo.MapperDTO.AppBudgetDTOMapper;
import Harmoniz.demo.Repositories.AppBudgetRepository;
import Harmoniz.demo.Repositories.AppFluxRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Data
public class AppBudgetService {

    private AppBudgetRepository appBudgetRepository;
    private AppFluxRepository appFluxRepository;
    private AppFluxService appFluxService;
    private AppBudgetDTOMapper appBudgetDTOMapper;

    public void enregistrerResultat(AppBudget appBudget) {

        if (appBudget.getMois() < 1 || appBudget.getMois() > 12) {
            throw new IllegalArgumentException("Le mois du budget doit être compris entre 1 et 12");
        }

        List<AppFlux> fluxPourMois = appFluxRepository.findByMois(appBudget.getMois());

        appBudget.setAppFluxList(fluxPourMois);

        appBudget.calculerResultatPourMois(appBudget.getMois());

        appBudgetRepository.save(appBudget);
    }

    public Stream<AppBudgetDTO> voirTousLesResultats() {
        return this.appBudgetRepository
                .findAll()
                .stream()
                .map(appBudgetDTOMapper);
    }
}

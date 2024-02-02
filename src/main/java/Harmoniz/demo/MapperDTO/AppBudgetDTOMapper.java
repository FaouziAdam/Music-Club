package Harmoniz.demo.MapperDTO;

import Harmoniz.demo.DTO.AppBudgetDTO;
import Harmoniz.demo.DTO.AppFluxDTO;
import Harmoniz.demo.Entities.AppBudget;
import Harmoniz.demo.Entities.AppFlux;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppBudgetDTOMapper implements Function<AppBudget, AppBudgetDTO> {
    @Override
    public AppBudgetDTO apply(AppBudget appBudget) {

        List<AppFluxDTO> appFluxDTOList = mapFluxListToDTO(appBudget.getAppFluxList());


        return new AppBudgetDTO(
                appBudget.getMois(),
                appFluxDTOList,
                appBudget.getResultat()
        );
    }
    private List<AppFluxDTO> mapFluxListToDTO(List<AppFlux> appFluxList) {
        return appFluxList.stream()
                .map(appFlux -> new AppFluxDTO(
                        appFlux.getId(),
                        appFlux.getMois(),
                        appFlux.getTypeDeFlux(),
                        appFlux.getLibelle(),
                        appFlux.getMontant()
                ))
                .collect(Collectors.toList());
    }
}

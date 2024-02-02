package Harmoniz.demo.DTO;

import java.util.List;

public record AppBudgetDTO(
        Integer mois,
        List<AppFluxDTO> appFluxList,
        Double resultat
) {
}

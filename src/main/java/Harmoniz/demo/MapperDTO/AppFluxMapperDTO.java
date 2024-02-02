package Harmoniz.demo.MapperDTO;

import Harmoniz.demo.DTO.AppFluxDTO;
import Harmoniz.demo.Entities.AppFlux;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AppFluxMapperDTO implements Function<AppFlux, AppFluxDTO> {
    @Override
    public AppFluxDTO apply(AppFlux appFlux) {
        return new AppFluxDTO(
                appFlux.getId(),
                appFlux.getMois(),
                appFlux.getTypeDeFlux(),
                appFlux.getLibelle(),
                appFlux.getMontant()
        );
    }
}

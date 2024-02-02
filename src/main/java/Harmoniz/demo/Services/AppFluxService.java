package Harmoniz.demo.Services;

import Harmoniz.demo.Entities.AppFlux;
import Harmoniz.demo.Repositories.AppFluxRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class AppFluxService {

    public AppFluxRepository appFluxRepository;

    public void creerFlux(AppFlux appFlux){
        this.appFluxRepository.save(appFlux);
    }
}

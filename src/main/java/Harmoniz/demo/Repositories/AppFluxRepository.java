package Harmoniz.demo.Repositories;

import Harmoniz.demo.Entities.AppFlux;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppFluxRepository extends JpaRepository<AppFlux, Integer> {
    List<AppFlux> findByMois(int mois);

}

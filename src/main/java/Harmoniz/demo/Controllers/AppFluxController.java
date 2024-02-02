package Harmoniz.demo.Controllers;

import Harmoniz.demo.Entities.AppFlux;
import Harmoniz.demo.Services.AppFluxService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Data
@AllArgsConstructor
@RestController
@RequestMapping(path = "flux")
public class AppFluxController {

    public AppFluxService appFluxService;

    @PostMapping(path = "creerFlux", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> creerFlux(@RequestBody AppFlux appFlux){
        this.appFluxService.creerFlux(appFlux);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

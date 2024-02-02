package Harmoniz.demo.Controllers;

import Harmoniz.demo.DTO.AppBudgetDTO;
import Harmoniz.demo.Entities.AppBudget;
import Harmoniz.demo.Services.AppBudgetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Data
@AllArgsConstructor
@RestController
@RequestMapping(path = "budget")
public class AppBudgetController {

    public AppBudgetService appBudgetService;

    @PostMapping(path = "enregistrerResultat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> enregistrerResultat(@RequestBody AppBudget appBudget){
        this.appBudgetService.enregistrerResultat(appBudget);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "resultats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stream<AppBudgetDTO>> voirTousLesResultats(){
        return ResponseEntity.ok(this.appBudgetService.voirTousLesResultats());
    }
}

package Harmoniz.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Budgets Mensuels")
public class AppBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(1)
    @Max(12)
    private int mois;

    @CreationTimestamp
    private Date dateDeCreation;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppFlux> appFluxList;

    private double resultat;

    public void calculerResultatPourMois(int mois) {
        if (appFluxList != null && !appFluxList.isEmpty()) {
            resultat = appFluxList.stream()
                    .filter(flux -> flux.getMois() == mois)
                    .mapToDouble(AppFlux::getMontant)
                    .sum();
        } else {
            resultat = 0;
        }
    }

}

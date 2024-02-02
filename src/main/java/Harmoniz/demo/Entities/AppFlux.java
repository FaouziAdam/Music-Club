package Harmoniz.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Flux")
public class AppFlux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(1)
    @Max(12)
    private int mois;

    @Enumerated(EnumType.STRING)
    private TypeDeFlux typeDeFlux;

    @CreationTimestamp
    private Date dateDeCreation;

    private String libelle;

    private double montant;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private AppBudget appBudget;

    public double getMontant() {
        if (typeDeFlux == TypeDeFlux.Charge) {
            return -montant;
        } else {
            return montant;
        }
    }

}

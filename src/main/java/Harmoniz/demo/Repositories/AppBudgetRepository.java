package Harmoniz.demo.Repositories;

import Harmoniz.demo.Entities.AppBudget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppBudgetRepository extends JpaRepository<AppBudget, Integer> {
}

package AdamFaouzi.demo.Repositories;

import AdamFaouzi.demo.Entities.AppRole;
import AdamFaouzi.demo.Entities.LabelRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
    Optional<AppRole> findByLabel(LabelRole labelRole);
}

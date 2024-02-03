package AdamFaouzi.demo.Repositories;

import AdamFaouzi.demo.Entities.AppPost;
import AdamFaouzi.demo.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppPostRepository extends JpaRepository<AppPost, Integer> {
    List<AppPost> findAllByOrderByCreationDateDesc();
    List<AppPost> findByCreationDateBetween(Date StartDate, Date endDate);

}

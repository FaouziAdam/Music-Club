package AdamFaouzi.demo.DTO;

import java.sql.Timestamp;
import java.util.Date;

public record AppPostDTO(
        Integer id,
        Integer userId,
        Date creationDate,
        String category,
        String firstName,
        String lastName,
        String title,
        String post
) {

}

package AdamFaouzi.demo.MapperDTO;

import AdamFaouzi.demo.DTO.AppPostDTO;
import AdamFaouzi.demo.Entities.AppPost;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;


import java.util.function.Function;

@Service
public class AppPostDTOMapper implements Function<AppPost, AppPostDTO> {
    @Override
    public AppPostDTO apply(AppPost appPost) {
        return new AppPostDTO(
                appPost.getId(),
                appPost.getAppUser().getId(),
                appPost.getCreationDate(),
                appPost.getCategory(),
                appPost.getAppUser().getFirstName(),
                appPost.getAppUser().getLastName(),
                appPost.getTitle(),
                appPost.getPost()
        );
    }
}

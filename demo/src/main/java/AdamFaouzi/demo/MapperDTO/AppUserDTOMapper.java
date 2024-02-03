package AdamFaouzi.demo.MapperDTO;

import AdamFaouzi.demo.DTO.AppUserDTO;
import AdamFaouzi.demo.Entities.AppUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AppUserDTOMapper implements Function<AppUser, AppUserDTO> {
    @Override
    public AppUserDTO apply(AppUser appUser) {
        return new AppUserDTO(
                appUser.getEmail(),
                appUser.getFirstName(),
                appUser.getLastName()
        );
    }
}

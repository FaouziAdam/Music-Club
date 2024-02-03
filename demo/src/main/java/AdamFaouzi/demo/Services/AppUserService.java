package AdamFaouzi.demo.Services;

import AdamFaouzi.demo.DTO.AppPostDTO;
import AdamFaouzi.demo.DTO.AppUserDTO;
import AdamFaouzi.demo.Entities.*;
import AdamFaouzi.demo.MapperDTO.AppUserDTOMapper;
import AdamFaouzi.demo.Repositories.AppRoleRepository;
import AdamFaouzi.demo.Repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    public AppUserRepository appUserRepository;
    public AppRoleRepository appRoleRepository;
    public BCryptPasswordEncoder passwordEncoder;
    public ValidationService validationService;
    public AppUserDTOMapper appUserDTOMapper;


    public void register(AppUser appUser) {
        if (!appUser.getEmail().contains("@")) {
            throw new RuntimeException("Email invalid");
        }
        if (!appUser.getEmail().contains(".")) {
            throw new RuntimeException("Email invalid");
        }

        String pwdEncode = this.passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(pwdEncode);

        AppRole userRole = appRoleRepository.findByLabel(LabelRole.USER)
                .orElseGet(() -> {
                    AppRole role = new AppRole();
                    role.setLabel(LabelRole.USER);
                    return appRoleRepository.save(role);
                });

        appUser.setRole(userRole);
        appUser = appUserRepository.save(appUser);

        this.validationService.saveValidation(appUser);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.passByCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpire())) {
            throw new RuntimeException("Your code has expired");
        }
        AppUser validatedUser = this.appUserRepository
                .findById(validation.getAppUser().getId())
                .orElseThrow(() -> new RuntimeException("Unknown user"));
        validatedUser.setActive(true);
        this.appUserRepository.save(validatedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.appUserRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No username found"));
    }

    public Stream<AppUserDTO> getAllUsers() {
        return this.appUserRepository.findAll()
                .stream()
                .map(appUserDTOMapper);
    }

    public AppUser searchUserById(int id) {
        Optional<AppUser> optionalUser = this.appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalStateException("No User has been found");
        }
    }

    public void deleteUser(int id) {
        boolean existingUser = this.appUserRepository.existsById(id);
        if (!existingUser) {
            throw new IllegalStateException("No meloman with the ID" + id + " found");
        }
        appUserRepository.deleteById(id);
    }

    public void updateUser(int id, AppUser appUser) {

        Optional<AppUser> optionalUser = this.appUserRepository.findById(id);

        if (optionalUser.isPresent()) {
            AppUser currentUser = optionalUser.get();

            if (Objects.equals(currentUser.getId(), appUser.getId())) {
                currentUser.setFirstName(appUser.getFirstName());
                currentUser.setLastName(appUser.getLastName());
                this.appUserRepository.save(currentUser);
            }
            else {
                throw new IllegalArgumentException("User ID mismatch");
            }
        }
        else {
            throw new IllegalStateException("No User has been found");
        }
    }
}

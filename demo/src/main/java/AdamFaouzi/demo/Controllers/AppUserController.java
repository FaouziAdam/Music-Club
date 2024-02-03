package AdamFaouzi.demo.Controllers;
import AdamFaouzi.demo.DTO.AppPostDTO;
import AdamFaouzi.demo.DTO.AppUserDTO;
import AdamFaouzi.demo.DTO.AuthentificationDto;
import AdamFaouzi.demo.Entities.AppUser;
import AdamFaouzi.demo.Security.JwtService;
import AdamFaouzi.demo.Services.AppUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Data
@AllArgsConstructor
@RestController
@RequestMapping
public class AppUserController {

    private AppUserService appUserService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping(path = "register")
    public void register(@RequestBody AppUser appUser){
        log.info("register");
        this.appUserService.register(appUser);
    }
    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.appUserService.activation(activation);
    }

    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDto authentificationDto){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDto.username(), authentificationDto.password())
        );
        if (authenticate.isAuthenticated()){
            return this.jwtService.generate(authentificationDto.username());
        }
        return null;
    }

    @GetMapping(path = "searchAll",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stream<AppUserDTO>> getAllUsers(){
        return ResponseEntity.ok(this.appUserService.getAllUsers());
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser searchUserById(@PathVariable int id){
        return this.appUserService.searchUserById(id);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        this.appUserService.deleteUser(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    private ResponseEntity<String> updateUser(@PathVariable int id, AppUser appUser){
        this.appUserService.updateUser(id, appUser);
        return new ResponseEntity<>("Data updated", HttpStatus.OK);
    }
}

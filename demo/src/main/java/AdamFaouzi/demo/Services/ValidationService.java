package AdamFaouzi.demo.Services;

import AdamFaouzi.demo.Entities.AppUser;
import AdamFaouzi.demo.Entities.Validation;
import AdamFaouzi.demo.Repositories.ValidationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@Data
@AllArgsConstructor
public class ValidationService {

    public ValidationRepository validationRepository;
    public MailService mailService;

    public void saveValidation(AppUser appUser) {

        Validation validation = new Validation();

        validation.setAppUser(appUser);

        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpire(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);

        this.validationRepository.save(validation);
        this.mailService.sendMail(validation);
    }

    public Validation passByCode(String code){
       return this.validationRepository.findByCode(code)
               .orElseThrow(()-> new RuntimeException("Code invalid"));
    }
}

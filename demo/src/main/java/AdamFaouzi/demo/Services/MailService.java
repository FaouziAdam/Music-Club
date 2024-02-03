package AdamFaouzi.demo.Services;

import AdamFaouzi.demo.Entities.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class MailService {

    JavaMailSender javaMailSender;

    public void sendMail(Validation validation){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("no-reply@Musicbook.do");
        message.setTo(validation.getAppUser().getEmail());
        message.setSubject("Validation code");

        String text = String.format(
                "%s, you can validate your account with the code %s. Please note that the code expires after 10 Minutes ",
                validation.getAppUser().getFirstName(),
                validation.getCode()
        );
        message.setText(text);

        javaMailSender.send(message);
    }
}

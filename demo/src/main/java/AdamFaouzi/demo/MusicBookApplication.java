package AdamFaouzi.demo;

import AdamFaouzi.demo.Entities.AppRole;
import AdamFaouzi.demo.Entities.AppUser;
import AdamFaouzi.demo.Entities.LabelRole;
import AdamFaouzi.demo.Repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@AllArgsConstructor
@SpringBootApplication
public class MusicBookApplication implements CommandLineRunner {
	AppUserRepository appUserRepository;
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MusicBookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AppUser admin = AppUser.builder()
				.active(true)
				.firstName("Admin")
				.lastName("Admin")
				.password(passwordEncoder.encode("admin"))
				.email("admin@musicclub.com")
				.role(
						AppRole.builder()
						.label(LabelRole.ADMIN)
						.build()
				)
				.build();
		Optional<AppUser> existingUser = this.appUserRepository.findByEmail("admin@musicclub.com");

		if (existingUser.isEmpty()) {
			this.appUserRepository.save(admin);
		}
	}
}

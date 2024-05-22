package bakery.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bakery.Token.Token;
import bakery.Token.TokenRepository;
import bakery.email.EmailService;
import bakery.email.EmailTemplateName;
import bakery.model.TblUser;
import bakery.repository.UserRepository;
import bakery.role.RoleRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private EmailService emailService;

	@Value("${application.mailing.frontend.activation-url}")
	private String activationUrl;

	public void register(RegistrationRequest request) throws MessagingException {
		var userRole = roleRepository.findByRoleName("USER")
				.orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
		var user = TblUser.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.userEmail(request.getUserEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.locked(false)
				.enabled(false)
				.roles(List.of(userRole))
				.build();
		userRepo.save(user);
		sendValidationEmail(user);
	}

	private void sendValidationEmail(TblUser user) throws MessagingException {
		var newToken = generateAndSaveActivationToken(user);
		
		emailService.sendEmail(
				user.getUserEmail(), 
				user.getFullName(), 
				EmailTemplateName.ACTIVATE_ACCOUNT, 
				activationUrl, 
				newToken, 
				"Account activation");
	}

	private String generateAndSaveActivationToken(TblUser user) {
		//generate token
		String generatedToken = geneateActivationCode(6);
		var token = Token.builder()
				.token(generatedToken)
				.createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15))
				.user(user)
				.build();
		tokenRepo.save(token);
		return generatedToken;
	}

	private String geneateActivationCode(int length) {
		String characters = "0123456789";
		StringBuilder codeBuilder = new StringBuilder();
		SecureRandom secureRandom = new SecureRandom();
		for(int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(characters.length());
			codeBuilder.append(characters.charAt(randomIndex));
		}
		return codeBuilder.toString();
	}

}

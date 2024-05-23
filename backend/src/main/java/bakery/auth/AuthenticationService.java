package bakery.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bakery.Token.Token;
import bakery.Token.TokenRepository;
import bakery.email.EmailService;
import bakery.email.EmailTemplateName;
import bakery.model.TblUser;
import bakery.repository.UserRepository;
import bakery.role.RoleRepository;
import bakery.security.JwtService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;

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
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		var auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUserEmail(), 
						request.getPassword())
		);
		
		var claims = new HashMap<String, Object>();
		var user = ((TblUser)auth.getPrincipal());
		claims.put("fullName", user.getFullName());
		var jwtToken = jwtService.generateToken(claims, user);
		
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Transactional
	public void activateAccount(String token) throws MessagingException {

		Token savedToken = tokenRepo.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid token"));
		if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
			sendValidationEmail(savedToken.getUser());
			
			throw new RuntimeException("Activation token has expired. A new token has been sent.");
		}
		
		var user = userRepo.findById(savedToken.getUser().getUserId())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		user.setEnabled(true);
		userRepo.save(user);
		savedToken.setValidatedAt(LocalDateTime.now());
		tokenRepo.save(savedToken);
	
	}

}

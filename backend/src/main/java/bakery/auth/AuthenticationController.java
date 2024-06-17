package bakery.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<?> register(
			@RequestBody @Valid RegistrationRequest request
			) throws MessagingException{
		authService.register(request);
		
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticatEntity (@RequestParam("userEmail") String userEmail, 
	        @RequestParam("password") String password){
		
		AuthenticationRequest request = new AuthenticationRequest(userEmail, password);
		
		return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
		
	}
	
	@GetMapping("/activate-account")
	public void confirm(@RequestParam String token) throws MessagingException {
		authService.activateAccount(token);
	}
	
	

}

package bakery;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import bakery.role.Role;
import bakery.role.RoleRepository;

@SpringBootApplication
@EnableAsync
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner (RoleRepository roleRepository) {
		return args -> {
			if(roleRepository.findByRoleName("USER").isEmpty()) {
				roleRepository.save(Role.builder().roleName("USER").build());
			}
			
		};
	}

}

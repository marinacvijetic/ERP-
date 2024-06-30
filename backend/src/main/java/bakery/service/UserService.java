package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bakery.model.TblUser;
import bakery.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	public List<TblUser> getAllUser(){
		return userRepo.findAll();
	}
	
	public TblUser getUserById(Long userId) {
		return userRepo.findById(userId).orElse(null);
	}
	
	public TblUser getUserByEmail(String userEmail) {
		return userRepo.findUserByUserEmail(userEmail).orElse(null);
	}
	
	@Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public boolean isAdmin(UserDetails userDetails) {
        TblUser user = (TblUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        return user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN"));
    }
	
	public TblUser addUser(TblUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public TblUser updateUser(Long userId, TblUser user){
		TblUser oldUser = userRepo.findById(userId).orElse(null);
		
		if(oldUser == null) {
			return null;
		}
		
		oldUser.setUserEmail(user.getUserEmail());
		oldUser.setFirstname(user.getFirstname());
		oldUser.setLastname(user.getLastname());
		oldUser.setRoles(user.getRoles());
		oldUser.setPhoneNumber(user.getPhoneNumber());
		
		return userRepo.save(oldUser);
	}
}
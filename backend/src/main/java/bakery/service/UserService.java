package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bakery.model.TblUser;
import bakery.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<TblUser> getAllUser(){
		return userRepo.findAll();
	}
	
	public TblUser getUserById(Long userId) {
		return userRepo.findById(userId).orElse(null);
	}
	
	public TblUser getUserByEmail(String userEmail) {
		return userRepo.findUserByUserEmail(userEmail).orElse(null);
	}
	
	public void deleteUserById(Long userId) {
		userRepo.deleteById(userId);
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
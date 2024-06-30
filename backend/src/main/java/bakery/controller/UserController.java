package bakery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bakery.model.TblUser;
import bakery.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public ResponseEntity<List<TblUser>> getAllUser(){
		List<TblUser> users = userService.getAllUser();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblUser> gerUserById(@PathVariable Long id){
		TblUser user = userService.getUserById(id);
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TblUser> updateUser(@PathVariable Long id, @RequestBody TblUser user){
		TblUser updatedUser = userService.updateUser(id, user);
		if(updatedUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userService.isAdmin(userDetails)) {
            userService.deleteUser(id);
        } else {
            throw new SecurityException("You do not have permission to delete this user.");
        }
    }
	
	@PostMapping("")
	public ResponseEntity<TblUser> addUser(@RequestBody TblUser user){
		TblUser newUser = userService.addUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
}

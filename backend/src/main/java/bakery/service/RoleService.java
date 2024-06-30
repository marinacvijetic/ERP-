package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.role.Role;
import bakery.role.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepo;
	
	public List<Role> getAllRoles(){
		return roleRepo.findAll();
	}
	
	public Role getRoleById(Long roleId) {
		return roleRepo.findById(roleId).get();
	}
	
	public Role addRole(Role role) {
		return roleRepo.save(role);
	}
	
	public void deleteRoleById(Long roleId) {
		roleRepo.deleteById(roleId);
	}
	
	public Role updateRole(Long roleId, Role role) {
		Role oldRole = roleRepo.findById(roleId).get();
		
		if(oldRole == null) {
			return null;
		}
		
		oldRole.setRoleName(role.getRoleName());
		
		return roleRepo.save(oldRole);
		
	}

}

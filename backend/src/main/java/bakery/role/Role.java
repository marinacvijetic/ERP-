package bakery.role;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bakery.model.TblUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
	
	@Id
	@GeneratedValue
	@Column(name="role_id")
	private Long roleId;
	
	@Column(name="role_name", unique = true)
	private String roleName;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<TblUser> user;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	

}

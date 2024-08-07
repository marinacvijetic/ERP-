package bakery.model;


import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bakery.role.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name="tbluser")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TblUser implements UserDetails, Principal{

	@Id
	@SequenceGenerator(name="TBLUSER_USERID_GENERATOR", sequenceName="user_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLUSER_USERID_GENERATOR")
	@Column(name="user_id")
	private Long userId;

	@Column(name="user_email", unique=true)
	private String userEmail;

	@Column(name="password")
	private String password;
	
	@Column(name="firstname")
	private String firstname;

	@Column(name="lastname")
	private String lastname;

	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="number_of_orders")
	private Integer numOfOrders;

	@Column(name="locked")
	private Boolean locked;

	@Column(name="enabled")
	private Boolean enabled;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<TblOrder> orders;

	
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Role> roles;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUsername() {
		return userEmail;
	}

	public void setUsername(String username) {
		this.userEmail = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getNumOfOrders() {
		return numOfOrders;
	}

	public void setNumOfOrders(Integer numOfOrders) {
		this.numOfOrders = numOfOrders;
	}

	public List<TblOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<TblOrder> orders) {
		this.orders = orders;
	}

	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

	@Override
	public String getName() {

		return userEmail;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.roles
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getRoleName()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}
	


}

package bakery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblUser;

@Repository
public interface UserRepository extends JpaRepository<TblUser, Long>{

	Optional<TblUser> findUserByUserEmail(String UserEmail);
	
	TblUser findUserByUserId(Long userId);
}

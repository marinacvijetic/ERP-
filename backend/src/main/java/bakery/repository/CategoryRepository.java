package bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblCategory;

@Repository
public interface CategoryRepository extends JpaRepository<TblCategory, Long>{

	
}

package bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblProduct;

@Repository
public interface ProductRepository extends JpaRepository<TblProduct, Long>{

}

package bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblOrderArrivalDetails;

@Repository
public interface OrderArrivalDetailsRepository extends JpaRepository<TblOrderArrivalDetails, Long>{

}

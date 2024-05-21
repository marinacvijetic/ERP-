package bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblOrderStatus;

@Repository
public interface OrderStatusRepository extends JpaRepository<TblOrderStatus, Long>{

	TblOrderStatus findByStatusId(Long statusId);
}

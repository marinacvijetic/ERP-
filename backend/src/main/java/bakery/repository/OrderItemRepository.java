package bakery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblOrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<TblOrderItem, Long>{

	List<TblOrderItem> findByOrderOrderId(Long orderId);
}

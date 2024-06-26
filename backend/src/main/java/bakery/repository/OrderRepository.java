package bakery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblOrder;

@Repository
public interface OrderRepository extends JpaRepository<TblOrder, Long> {

	List<TblOrder> findByUserUserId(Long userId);
}

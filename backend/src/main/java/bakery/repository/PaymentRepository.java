package bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bakery.model.TblPayment;

@Repository
public interface PaymentRepository extends JpaRepository<TblPayment, Long> {

}

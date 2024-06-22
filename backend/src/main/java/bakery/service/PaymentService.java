package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblPayment;
import bakery.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepo;
	
    public List<TblPayment> findAll() {
        return paymentRepo.findAll();
    }

}

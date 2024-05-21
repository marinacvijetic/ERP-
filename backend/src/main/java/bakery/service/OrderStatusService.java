package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblOrderStatus;
import bakery.repository.OrderStatusRepository;

@Service
public class OrderStatusService {

	@Autowired
	OrderStatusRepository orderStatusRepo;
	
	public List<TblOrderStatus> getAllOrderStatus(){
		return orderStatusRepo.findAll();
	}
	
	public TblOrderStatus getOrderStatusById(Long orderStatusId) {
		return orderStatusRepo.findById(orderStatusId).get();
	}
	
	public TblOrderStatus addOrderStatus(TblOrderStatus status) {
		return orderStatusRepo.save(status);
	}
	
	public void deleteOrderStatusById(Long statusId) {
		orderStatusRepo.deleteById(statusId);
	}
	
	public TblOrderStatus updateOrderStatus(Long statusId, TblOrderStatus orderStatus) {
		TblOrderStatus oldOrderStatus = orderStatusRepo.findById(statusId).get();
		
		if(oldOrderStatus == null) {
			return null;
		}
	
		oldOrderStatus.setOrderStatus(orderStatus.getOrderStatus());
		
		return orderStatusRepo.save(oldOrderStatus);
	}
}

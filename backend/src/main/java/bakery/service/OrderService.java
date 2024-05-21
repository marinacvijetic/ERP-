package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblOrder;
import bakery.model.TblOrderStatus;
import bakery.repository.OrderRepository;
import bakery.repository.OrderStatusRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private OrderStatusRepository orderStatusRepo;
	
	public List<TblOrder> getAllOrder(){
		return orderRepo.findAll();
	}
	
	public TblOrder getOrderById(Long orderId) {
		return orderRepo.findById(orderId).get();
	}
	
	public TblOrder addOrder(TblOrder order) {
		TblOrderStatus orderedStatus = orderStatusRepo.findByStatusId(6L);
		order.setStatus(orderedStatus);
		return orderRepo.save(order);
	}
	
	public void deleteOrderById(Long orderId) {
		orderRepo.deleteById(orderId);
	}
	
	public TblOrder updateOrder(Long orderId, TblOrder order) {
		TblOrder oldOrder = orderRepo.findById(orderId).get();
		
		if(oldOrder == null) {
			return null;
		}
		
		oldOrder.setOrderDate(order.getOrderDate());
		oldOrder.setPaymentMethod(order.getPaymentMethod());
		oldOrder.setShippingMethod(order.getShippingMethod());
		oldOrder.setuser(order.getuser());
		oldOrder.setArrivalDetails(order.getArrivalDetails());
		oldOrder.setStatus(order.getStatus());
		
		return orderRepo.save(oldOrder);
	}
}

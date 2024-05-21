package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblOrderItem;
import bakery.repository.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepo;
	
	public List<TblOrderItem> getAllOrderItem(){
		return orderItemRepo.findAll();
	}
	
	public TblOrderItem getOrderItemById(Long orderItemId) {
		return orderItemRepo.findById(orderItemId).get();
	}
	
	public TblOrderItem addOrderItem(TblOrderItem orderItem) {
		return orderItemRepo.save(orderItem);
	}
	
	public void deleteOrderItemById(Long orderItemId) {
		orderItemRepo.deleteById(orderItemId);
	}
	
	public TblOrderItem updateOrderItem(Long orderItemId, TblOrderItem orderItem) {
		TblOrderItem oldOrderItem = orderItemRepo.findById(orderItemId).get();
		if(oldOrderItem == null) {
			return null;
		}
		
		oldOrderItem.setOrder(orderItem.getOrder());
		oldOrderItem.setProduct(orderItem.getProduct());
		oldOrderItem.setQuantity(orderItem.getQuantity());
		
		return orderItemRepo.save(oldOrderItem);
	}
}

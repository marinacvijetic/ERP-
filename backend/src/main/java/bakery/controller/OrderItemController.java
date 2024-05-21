package bakery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bakery.model.TblOrderItem;
import bakery.service.OrderItemService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;
	
	@GetMapping("")
	public ResponseEntity<List<TblOrderItem>> getAllOrderItem(){
		List<TblOrderItem> orderItems = orderItemService.getAllOrderItem();
		
		return new ResponseEntity<>(orderItems, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblOrderItem> getOrderItemById(@PathVariable Long id){
		TblOrderItem orderItem = orderItemService.getOrderItemById(id);
		if(orderItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(orderItem, HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<TblOrderItem> addOrderItem(@RequestBody TblOrderItem orderItem){
		TblOrderItem newOrderItem = orderItemService.addOrderItem(orderItem);
		return new ResponseEntity<>(newOrderItem, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TblOrderItem> updateOrderItem(@PathVariable Long id, @RequestBody TblOrderItem orderItem){
		TblOrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItem);
		if(updatedOrderItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updatedOrderItem, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id){
		orderItemService.deleteOrderItemById(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

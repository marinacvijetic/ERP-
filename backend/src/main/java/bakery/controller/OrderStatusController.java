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

import bakery.model.TblOrderStatus;
import bakery.service.OrderStatusService;



@RestController
@RequestMapping("/orderStatus")
public class OrderStatusController {
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@GetMapping("")
	public ResponseEntity<List<TblOrderStatus>> getAllOrderStatus(){
		List<TblOrderStatus> orderStatuses = orderStatusService.getAllOrderStatus();
		
		return new ResponseEntity<>(orderStatuses,HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblOrderStatus> getOrderStatusById(@PathVariable Long id) {
		TblOrderStatus orderStatus = orderStatusService.getOrderStatusById(id);
		if(orderStatus == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(orderStatus, HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<TblOrderStatus> addOrderStatus (@RequestBody TblOrderStatus orderStatus){
		TblOrderStatus newOrderStatus = orderStatusService.addOrderStatus(orderStatus);
		return new ResponseEntity<>(newOrderStatus, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TblOrderStatus> updateOrderStatus(@PathVariable Long id, @RequestBody TblOrderStatus orderStatus){
		TblOrderStatus updatedOrderStatus = orderStatusService.updateOrderStatus(id, orderStatus);
		if(updatedOrderStatus == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updatedOrderStatus, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrderStatusById(@PathVariable Long id){
		orderStatusService.deleteOrderStatusById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
		
}
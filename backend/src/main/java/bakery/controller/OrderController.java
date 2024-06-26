package bakery.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import bakery.model.TblOrder;
import bakery.request.OrderCreateRequest;
import bakery.request.OrderItemResponse;
import bakery.request.OrderResponse;
import bakery.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("")
	public ResponseEntity<List<OrderResponse>> getAllOrder(){
		List<TblOrder> orders = orderService.getAllOrder();
		List<OrderResponse> response = orders.stream().map(x -> {
			OrderResponse res = new OrderResponse();
			res.setOrderId(x.getOrderId());
			res.setOrderDate(x.getOrderDate());
			res.setTotal(x.getTotal());
			res.setShippingMethod(x.getShippingMethod());
			res.setArrivalDetails(x.getArrivalDetails());
			res.setOrderItemsItems(x.getOrderItems().stream().map(i -> {
				OrderItemResponse itemResponse = new OrderItemResponse();
				itemResponse.setOrderItemId(i.getOrderItemId());
				itemResponse.setQuantity(i.getQuantity());
				itemResponse.setProduct(i.getProduct());
				return itemResponse;
			}).collect(Collectors.toList()));
			res.setUser(x.getUser());
			res.setStatus(x.getStatus());
			return res;
		}).collect(Collectors.toList());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblOrder> getOrderById(@PathVariable Long id){
		TblOrder order = orderService.getOrderById(id);
		if(order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(order, HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Long> addOrder(@RequestBody OrderCreateRequest request){
		TblOrder order = new TblOrder();
		order.setOrderDate(request.getOrderDate());
		order.setOrderItems(request.getOrderItems());
		order.setUser(request.getUser());
		order.setArrivalDetails(request.getOrderArrivalDetails());
		order.setShippingMethod(request.getShippingMethod());
		TblOrder newOrder = orderService.addOrder(order);
		
		return new ResponseEntity<>(newOrder.getOrderId(), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TblOrder> updateOrder(@PathVariable Long id, @RequestBody TblOrder order){
		TblOrder updatedOrder = orderService.updateOrder(id, order);
		if(updatedOrder==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
		orderService.deleteOrderById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

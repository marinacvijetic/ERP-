package bakery.request;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import bakery.model.ShippingMethod;
import bakery.model.TblOrderArrivalDetails;
import bakery.model.TblOrderItem;
import bakery.model.TblUser;

public class OrderCreateRequest {

	private Date orderDate = Date.from(Instant.now());
	private BigDecimal total;
	private List<TblOrderItem> orderItems;
	private TblUser user;
	private TblOrderArrivalDetails orderArrivalDetails;
	private ShippingMethod shippingMethod;
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public List<TblOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TblOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TblUser getUser() {
		return this.user;
	}
	public void setUser(TblUser user) {
		this.user = user;
	}
	public TblOrderArrivalDetails getOrderArrivalDetails() {
		return orderArrivalDetails;
	}
	public void setOrderArrivalDetails(TblOrderArrivalDetails orderArrivalDetails) {
		this.orderArrivalDetails = orderArrivalDetails;
	}
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	
	
}

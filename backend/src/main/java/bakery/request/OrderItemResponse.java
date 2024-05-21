package bakery.request;

import bakery.model.TblProduct;

public class OrderItemResponse {
	
	private Long orderItemId;
	
	private Integer quantity;
	
	private TblProduct product;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public TblProduct getProduct() {
		return product;
	}

	public void setProduct(TblProduct product) {
		this.product = product;
	}
	
	

}

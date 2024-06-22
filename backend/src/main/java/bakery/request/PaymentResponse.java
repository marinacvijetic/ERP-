package bakery.request;

import java.math.BigDecimal;
import java.util.Date;

import bakery.model.PaymentMethod;

public class PaymentResponse {
	
	private Long paymentId;
	private BigDecimal amount;
	private Date paymenDate;
	private PaymentMethod paymentMethod;
	private OrderResponse order;
	
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getPaymenDate() {
		return paymenDate;
	}
	public void setPaymenDate(Date paymenDate) {
		this.paymenDate = paymenDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public OrderResponse getOrder() {
		return order;
	}
	public void setOrder(OrderResponse order) {
		this.order = order;
	}
	
	

}

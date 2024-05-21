package bakery.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="tblorder")
public class TblOrder {
	@Id
	@SequenceGenerator(name="TBLORDER_ORDERID_GENERATOR", sequenceName="order_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLORDER_ORDERID_GENERATOR")
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "total")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method")
    private ShippingMethod shippingMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TblUser user;

    @ManyToOne
    @JoinColumn(name = "arrival_details_id", referencedColumnName = "arrival_details_id")
    private TblOrderArrivalDetails arrivalDetails;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private TblOrderStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<TblOrderItem> orderItems;

    public TblOrder() {
    	
    }
    
	public TblOrder(Long orderId, Date orderDate, BigDecimal total, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod, TblUser user, TblOrderArrivalDetails arrivalDetails, TblOrderStatus status,
			List<TblOrderItem> orderItems) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.total = total;
		this.paymentMethod = paymentMethod;
		this.shippingMethod = shippingMethod;
		this.user = user;
		this.arrivalDetails = arrivalDetails;
		this.status = status;
		this.orderItems = orderItems;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

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

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public TblUser getuser() {
		return user;
	}

	public void setuser(TblUser user) {
		this.user = user;
	}

	public TblOrderArrivalDetails getArrivalDetails() {
		return arrivalDetails;
	}

	public void setArrivalDetails(TblOrderArrivalDetails arrivalDetails) {
		this.arrivalDetails = arrivalDetails;
	}

	public TblOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TblOrderStatus status) {
		this.status = status;
	}

	public List<TblOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TblOrderItem> orderItems) {
		this.orderItems = orderItems;
	}


}
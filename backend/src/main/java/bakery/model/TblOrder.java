package bakery.model;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import jakarta.persistence.OneToOne;
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
    @Column(name = "shipping_method")
    private ShippingMethod shippingMethod;
    
	@OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private TblPayment payment;

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
        this.orderItems = new ArrayList<>();
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

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}
	
	public TblPayment getPayment() {
		return payment;
	}

	public void setPayment(TblPayment payment) {
		this.payment = payment;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public TblUser getUser() {
		return user;
	}

	public void setUser(TblUser user) {
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
		return this.orderItems;
	}

	public void setOrderItems(List<TblOrderItem> orderItems) {
		this.orderItems = orderItems;
		for(TblOrderItem item : orderItems) {
			item.setOrder(this);
		}
	}
	
	public TblOrderItem addOrderItem(TblOrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);
		
		return orderItem;
	}
	
	public TblOrderItem removeOrderItem(TblOrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setOrder(null);
		
		return orderItem;
	}


}

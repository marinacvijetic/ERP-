package bakery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tblorder_item")
public class TblOrderItem {
	
	@Id
	@SequenceGenerator(name="TBLORDERITEM_ITEMID_GENERATOR", sequenceName="order_item_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLORDERITEM_ITEMID_GENERATOR")
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne
    @JsonIgnoreProperties({"total", "paymentMethod", "shippingMethod", "user", "arrivalDetails", "status"})
    @JoinColumn(name = "order_id")
    private TblOrder order;

    @ManyToOne
    @JsonIgnoreProperties({"description", "productImage", "category"})
    @JoinColumn(name = "product_id")
    private TblProduct product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    public TblOrderItem() {
    	
    }

	public TblOrderItem(Long orderItemId, TblOrder order, TblProduct product, Integer quantity) {
		this.orderItemId = orderItemId;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public TblOrder getOrder() {
		return order;
	}

	public void setOrder(TblOrder order) {
		this.order = order;
	}

	public TblProduct getProduct() {
		return product;
	}

	public void setProduct(TblProduct product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


}

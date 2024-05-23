package bakery.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="tblpayment")
public class TblPayment {

	@Id
	@SequenceGenerator(name="TBLPAYMENT_PAYMENTID_GENERATOR", sequenceName="payment_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLPAYMENT_PAYMENTID_GENERATOR")
    @Column(name = "payment_id")
	private Long paymentId;
	
	private BigDecimal amount;
	
	@Temporal(TemporalType.DATE)
	@Column(name="payment_date")
	private Date paymentDate;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

	@OneToOne
	@JoinColumn(name="order_id")
	private TblOrder order;
	
	public TblPayment() {
		
	}

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

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public TblOrder getOrder() {
		return order;
	}

	public void setOrder(TblOrder order) {
		this.order = order;
	}
	
	
    
}

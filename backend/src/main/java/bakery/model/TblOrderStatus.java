package bakery.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tblorder_status")
public class TblOrderStatus {

	@Id
	@SequenceGenerator(name="TBLORDERSTATUS_STATUSID_GENERATOR", sequenceName="order_status_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLORDERSTATUS_STATUSID_GENERATOR")
    @Column(name = "status_id")
    private Long statusId;

	@Column(name="order_status", nullable=false)
	private String orderStatus;

	public TblOrderStatus() {

	}

	public TblOrderStatus(Long statusId, String orderStatus) {

		this.statusId = statusId;
		this.orderStatus = orderStatus;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}

package bakery.model;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="tblorder_arrival_details")
public class TblOrderArrivalDetails {

	@Id
	@SequenceGenerator(name="TBLORDERARRIVAL_ARRIVALDETAILSID_GENERATOR", sequenceName="order_arrival_details_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLORDERARRIVAL_ARRIVALDETAILSID_GENERATOR")
    @Column(name = "arrival_details_id")
    private Long arrivalDetailsId;

 	@Column(name = "arrival_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date arrivalDate;

    @Column(name = "arrival_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime arrivalTime;

    @Column(name = "country", nullable = false, length = 30)
    private String country;

    @Column(name = "postal_code", nullable = false, length = 30)
    private String postalCode;

    @Column(name = "city", nullable = false, length = 30)
    private String city;

    @Column(name = "street_name", nullable = false, length = 40)
    private String streetName;

    @Column(name = "street_number", nullable = false, length = 10)
    private String streetNumber;

    public TblOrderArrivalDetails() {

    }

	public TblOrderArrivalDetails(Long arrivalDetailsId, Date arrivalDate, LocalTime arrivalTime, String country,
			String postalCode, String city, String streetName, String streetNumber) {
		this.arrivalDetailsId = arrivalDetailsId;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.country = country;
		this.postalCode = postalCode;
		this.city = city;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
	}

	public Long getArrivalDetailsId() {
		return arrivalDetailsId;
	}

	public void setArrivalDetailsId(Long arrivalDetailsId) {
		this.arrivalDetailsId = arrivalDetailsId;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
}

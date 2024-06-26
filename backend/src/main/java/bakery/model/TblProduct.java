package bakery.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tblproduct")
public class TblProduct {

	@Id
	@SequenceGenerator(name="TBLPRODUCT_PRODUCTID_GENERATOR", sequenceName="product_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLPRODUCT_PRODUCTID_GENERATOR")
	@Column(name="product_id")
	private Long productId;

	@Column(name="product_name", nullable=false)
	private String productName;

	@Column(name="description", nullable=false)
	private String description;


    @Column(name = "price_per_kilogram", nullable = false)
    private BigDecimal pricePerKilogram;

    @ManyToOne(optional=false)
    @JoinColumn(name = "category_id", nullable = false)
    private TblCategory category;

    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<TblOrderItem> orderItems;

    public TblProduct() {

    }

	public TblProduct(Long productId, String productName, String description,
			BigDecimal pricePerKilogram, TblCategory category) {
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.pricePerKilogram = pricePerKilogram;
		this.category = category;

	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPricePerKilogram() {
		return pricePerKilogram;
	}

	public void setPricePerKilogram(BigDecimal pricePerKilogram) {
		this.pricePerKilogram = pricePerKilogram;
	}

	public TblCategory getCategory() {
		return category;
	}

	public void setCategory(TblCategory category) {
		this.category = category;
	}

}

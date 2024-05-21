package bakery.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tblcategory")
public class TblCategory {

	@Id
	@SequenceGenerator(name="TBLCATEGORY_CATEGORYID_GENERATOR", sequenceName="category_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLCATEGORY_CATEGORYID_GENERATOR")
	@Column(name="category_id")
	private Long categoryId;

	@Column(name="category_name", nullable=false)
	private String categoryName;

	@JsonIgnore
	@OneToMany(mappedBy="category")
	private List<TblProduct> products;

	public TblCategory() {
		
	}

	public TblCategory(Long categoryId, String categoryName, List<TblProduct> products) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.products = products;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<TblProduct> getProducts() {
		return products;
	}

	public void setProducts(List<TblProduct> products) {
		this.products = products;
	}

}

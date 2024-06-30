package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblProduct;
import bakery.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	public List<TblProduct> getAllProduct(){
		return productRepo.findAll();
	}
	
	public TblProduct getProductById(Long productId) {
		return productRepo.findById(productId).get();
	}
	
	public TblProduct addProduct(TblProduct product) {
		return productRepo.save(product);
	}
	
	public void deleteProductById(Long productId) {
		productRepo.deleteById(productId);
		
	}
	
	public TblProduct updateProduct(Long productId, TblProduct product) {
		TblProduct oldProduct = productRepo.findById(productId).get();
		if(oldProduct == null) {
			return null;
		}
		
		oldProduct.setProductName(product.getProductName());
		oldProduct.setDescription(product.getDescription());
		oldProduct.setPricePerKilogram(product.getPricePerKilogram());
		oldProduct.setCategory(product.getCategory());
		
		return productRepo.save(oldProduct);
	}
}

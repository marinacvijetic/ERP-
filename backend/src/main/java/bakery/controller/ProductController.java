package bakery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bakery.model.TblProduct;
import bakery.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public ResponseEntity<List<TblProduct>> getAllProduct(){
		List<TblProduct> products = productService.getAllProduct();
		
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblProduct> getProductById(@PathVariable Long id){
		TblProduct product = productService.getProductById(id);
		if(product==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<TblProduct> addProduct(@RequestBody TblProduct product){
		TblProduct newProduct = productService.addProduct(product);
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TblProduct> updateProduct(@PathVariable Long id, @RequestBody TblProduct product){
		TblProduct updatedProduct = productService.updaProduct(id, product);
		if(updatedProduct == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		productService.deleteProductById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

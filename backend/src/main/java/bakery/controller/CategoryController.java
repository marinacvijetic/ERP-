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

import bakery.model.TblCategory;
import bakery.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("")
	public ResponseEntity<List<TblCategory>> getAllCategory(){
		List<TblCategory> categories = categoryService.getAllCategory();
		
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblCategory> getCategoryById(@PathVariable Long id){
		TblCategory category = categoryService.getCategoryById(id);
		if(category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(category, HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<TblCategory> addCategory(@RequestBody TblCategory category){
		TblCategory newCategory = categoryService.addCategory(category);
		return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TblCategory> updateCategory(@PathVariable Long id, @RequestBody TblCategory category){
		TblCategory updatedCategory = categoryService.updateCategory(id, category);
		if(updatedCategory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

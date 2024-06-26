package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblCategory;
import bakery.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	public List<TblCategory> getAllCategory(){
		return categoryRepo.findAll();
	}
	
	public TblCategory getCategoryById(Long categoryId) {
		return categoryRepo.findById(categoryId).get();
	}
	
	public TblCategory addCategory(TblCategory category) {
		return categoryRepo.save(category);
	}
	
	public void deleteCategoryById(Long categoryId) {
		categoryRepo.deleteById(categoryId);
	}
	
	public TblCategory updateCategory(Long categoryId, TblCategory category) {
		TblCategory oldCategory = categoryRepo.findById(categoryId).get();
		
		if(oldCategory == null) {
			return null;
		}
		
		oldCategory.setCategoryName(category.getCategoryName());
		
		return categoryRepo.save(oldCategory);
		
	}
}

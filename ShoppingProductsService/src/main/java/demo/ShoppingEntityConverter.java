package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingEntityConverter {
	
	private CategoryDao categoryDao;
	
	@Autowired
	public ShoppingEntityConverter(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	
	public ProductEntity toProductEntity(ProductBoundary product) {
		ProductEntity productEntity = new ProductEntity();	
		productEntity.setId(product.getId());
		productEntity.setName(product.getName());
		productEntity.setImage(product.getImage());
		productEntity.setPrice(product.getPrice());
		productEntity.setProductsDetails(product.getProductsDetails());
		productEntity.setCategory(product.getCategory().getName());
		
		
		return productEntity;
		
	}
	public ProductBoundary fromProductEntity(ProductEntity product) {
		ProductBoundary productBoundary = new ProductBoundary();
		productBoundary.setId(product.getId());
		productBoundary.setImage(product.getImage());
		productBoundary.setName(product.getName());
		productBoundary.setPrice(product.getPrice());
		productBoundary.setProductsDetails(product.getProductsDetails());
		CategoryEntity category = this.categoryDao.findById(product.getCategory()).get();
		CategoryBoundary cb = new CategoryBoundary();
		if(category.getCategoryParent()!=null)
			cb.setParentCategory(category.getCategoryParent().getName());
		cb.setName(category.getName());
		cb.setDescription(category.getDescription());
		productBoundary.setCategory(cb);
	
			
		return productBoundary;
	}
	public CategoryEntity toCategoryEntity(CategoryBoundary category) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName(category.getName());
		categoryEntity.setDescription(category.getDescription());

		return categoryEntity;
		
	}
	public CategoryBoundary fromCategoryEntity(CategoryEntity category) {
		CategoryBoundary categoryBoundary = new CategoryBoundary();
		categoryBoundary.setName(category.getName());
		categoryBoundary.setDescription(category.getDescription());
		
		if(category.getCategoryParent()!=null)
		categoryBoundary.setParentCategory(category.getCategoryParent().getName());
		else
			categoryBoundary.setParentCategory(null);
		
		return categoryBoundary;
	}
	

}

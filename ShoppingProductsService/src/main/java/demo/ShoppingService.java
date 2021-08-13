package demo;

import java.util.List;

public interface ShoppingService {

	public ProductBoundary createProduct(ProductBoundary product);

	public ProductBoundary getProduct(String productId);

	public List<ProductBoundary> getAllProducts(String filterType, String filterVal, String sortBy, String sortOrder,
			int size, int page);

	public CategoryBoundary createCategory(CategoryBoundary category);

	public List<CategoryBoundary> getAllCategories(String sortBy, String sortOrder, int size, int page);

	public void deleteAllShopping();

	public void bindParentCategoryToChildCategory(String parentName, String string);

}

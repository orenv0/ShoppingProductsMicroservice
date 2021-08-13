package demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingController {
	private ShoppingService shoppingService;

	@Autowired
	public ShoppingController(ShoppingService shoppingService) {
		this.shoppingService = shoppingService;
	}
	
	@RequestMapping(path = "/shopping/categories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary create(@RequestBody CategoryBoundary category) {
		return this.shoppingService.createCategory(category);

	}

	
	//GET /shopping/categories?sortBy={sortArrt}&sortOrder={order}&page={page}&size={size}
	
	@RequestMapping(path = "/shopping/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary[] getAllCategories(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String sortOrder
		) {
		return this.shoppingService.getAllCategories(sortBy, sortOrder, size, page)
				.toArray(new CategoryBoundary[0]);
	}

	// POST/shopping/products
	@RequestMapping(path = "/shopping/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary create(@RequestBody ProductBoundary product) {
		return this.shoppingService.createProduct(product);

	}
	// GET /shopping/products/{productId}
	@RequestMapping(path = "/shopping/products/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary getProduct(@PathVariable("productId") String productId) {
		return this.shoppingService.getProduct(productId);
	}
	//GET /shopping/products?sortBy={sortAttr}&sortOrder={order}&page={page)&size={size}
	@RequestMapping(path = "/shopping/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary[] getAllProducts(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String sortOrder,
			@RequestParam(name = "filterType", required = false) String filterType,
			@RequestParam(name = "filterValue", required = false) String filterVal
		) {
		
		return this.shoppingService.getAllProducts(filterType,filterVal,sortBy, sortOrder, size, page)
				.toArray(new ProductBoundary[0]);
	}
	// Bind an existing Parent element to an existing child element
		@RequestMapping(path = "shopping/categories/{parentName}/children", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
		public void bindExistingParentToExistingChild(@PathVariable("parentName") String parentName,
			 @RequestBody Map<String, String> childCategory) {
			this.shoppingService.bindParentCategoryToChildCategory(parentName,
					childCategory.get("name"));
		}
	
	
	// DELETE /shopping
	@RequestMapping(path = "/shopping", method = RequestMethod.DELETE)
	public void deleteAllShopping() {
		this.shoppingService.deleteAllShopping();
	}
}
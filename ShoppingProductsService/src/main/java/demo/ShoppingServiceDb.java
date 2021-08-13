package demo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingServiceDb implements ShoppingService {

	private ShoppingEntityConverter shoppingEntityConverter;
	private ProductDao productDao;
	private CategoryDao categoryDao;

	@Autowired
	public ShoppingServiceDb(ShoppingEntityConverter shoppingEntityConverter, ProductDao productDao,
			CategoryDao categoryDao) {
		this.shoppingEntityConverter = shoppingEntityConverter;
		this.productDao = productDao;
		this.categoryDao = categoryDao;
	}

	@Override
	@Transactional
	public ProductBoundary createProduct(ProductBoundary product) {
		Optional<ProductEntity> productFromDatabase = this.productDao.findById(product.getId());
		if (productFromDatabase.isPresent())
			throw new RuntimeException("This product already exists");

		Optional<CategoryEntity> categoryFronDb = this.categoryDao.findById(product.getCategory().getName());
		if (!categoryFronDb.isPresent())
			throw new RuntimeException("No such category");
		ProductEntity entity = this.shoppingEntityConverter.toProductEntity(product);
		return this.shoppingEntityConverter.fromProductEntity(this.productDao.save(entity));
	}
	@Override
	@Transactional(readOnly = true)
	public ProductBoundary getProduct(String productId) {
		Optional<ProductEntity> productFromDatabase = this.productDao.findById(productId);
		if (!productFromDatabase.isPresent())
			throw new NotFoundException("product does not exists");
		ProductEntity product = productFromDatabase.get();
		return this.shoppingEntityConverter.fromProductEntity(product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductBoundary> getAllProducts(String filterType, String filterVal, String sortBy, String sortOrder,
			int size, int page) {

		if (filterType == null) {
			return this.productDao.findAll(PageRequest.of(page, size, Direction.fromString(sortOrder), sortBy)).stream()
					.map(this.shoppingEntityConverter::fromProductEntity).collect(Collectors.toList());
		}

		switch (filterType.toUpperCase()) {
		case "BYNAME":
			return this.productDao
					.findAllByName(filterVal, PageRequest.of(page, size, Direction.fromString(sortOrder), sortBy))
					.stream().map(this.shoppingEntityConverter::fromProductEntity).collect(Collectors.toList());

		case "BYMINPRICE":
			return this.productDao
					.findAllByPriceGreaterThan(Double.parseDouble(filterVal),
							PageRequest.of(page, size, Direction.fromString(sortOrder), sortBy))
					.stream().map(this.shoppingEntityConverter::fromProductEntity).collect(Collectors.toList());

		case "BYMAXPRICE":
			return this.productDao
					.findAllByPriceLessThan(Double.parseDouble(filterVal),
							PageRequest.of(page, size, Direction.fromString(sortOrder), sortBy))
					.stream().map(this.shoppingEntityConverter::fromProductEntity).collect(Collectors.toList());

		case "BYCATEGORYNAME":
			System.err.println("called!");
			List<ProductBoundary> list =  this.productDao
					.findAllByCategory(filterVal, PageRequest.of(page, size, Direction.fromString(sortOrder), sortBy))
					.stream().map(this.shoppingEntityConverter::fromProductEntity).collect(Collectors.toList());
			
			if(this.categoryDao.findById(filterVal).get().getChildren()==null)
				return list;
			
			for(CategoryEntity categoryEntity : this.categoryDao.findById(filterVal).get().getChildren()) {
				list.addAll(getAllProducts(filterType, categoryEntity.getName(), sortBy, sortOrder,
						size,  page));
			
			}
			
			return list;

		}
		return null;
	}
	

	@Override
	@Transactional
	public CategoryBoundary createCategory(CategoryBoundary category) {
		Optional<CategoryEntity> categoryFromDatabase = this.categoryDao.findById(category.getName());
		if (categoryFromDatabase.isPresent())
			throw new RuntimeException("This category already exists");
		CategoryEntity entity = this.shoppingEntityConverter.toCategoryEntity(category);
		return this.shoppingEntityConverter.fromCategoryEntity(this.categoryDao.save(entity));

	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryBoundary> getAllCategories(String sortBy, String sortOrder, int size, int page) {
		return this.categoryDao.findAll(PageRequest.of(page, size, Direction.fromString(sortOrder), sortBy)).stream()
				.map(this.shoppingEntityConverter::fromCategoryEntity).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteAllShopping() {
		this.categoryDao.deleteAll();
		this.productDao.deleteAll();
	}

	@Override
	public void bindParentCategoryToChildCategory(String parentName, String childName) {
		
		if (childName == null) {
			throw new RuntimeException("child name can't be null");
		}
		
		CategoryEntity parent = this.categoryDao.findById(parentName)
				.orElseThrow(() -> new NotFoundException("no category with the name: " + parentName));

		CategoryEntity child = this.categoryDao.findById(childName)
				.orElseThrow(() -> new NotFoundException("no category with the name: " + childName));

		parent.addChild(child);
		this.categoryDao.save(parent);
		
	}

}

package demo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends PagingAndSortingRepository<ProductEntity, String> {
	
	public List<ProductEntity> findAllByPriceGreaterThan(
			@Param("minPrice") double minPrice,
			Pageable pageable);
	
	public List<ProductEntity> findAllByPriceLessThan(
			@Param("maxPrice") double maxPrice,
			Pageable pageable
			);
	public List<ProductEntity> findAllByCategory(
			@Param("category") String category,
			Pageable pageable
			);
	public List<ProductEntity> findAllByName(
			@Param("name") String name,
			Pageable pageable
			);

}

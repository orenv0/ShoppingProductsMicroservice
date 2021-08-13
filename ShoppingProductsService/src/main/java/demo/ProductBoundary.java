package demo;

import java.util.Map;

public class ProductBoundary {
	private String id;
	private String name;
	private Double price;
	private String image;
	private Map<String, Object> productsDetails;
	private CategoryBoundary category;

	public ProductBoundary() {
		super();
	}

	public ProductBoundary(String id, String name, Double price, String image, Map<String, Object> productsDetails,
			CategoryBoundary category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.productsDetails = productsDetails;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Map<String, Object> getProductsDetails() {
		return productsDetails;
	}

	public void setProductsDetails(Map<String, Object> productsDetails) {
		this.productsDetails = productsDetails;
	}

	public CategoryBoundary getCategory() {
		return category;
	}

	public void setCategory(CategoryBoundary category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ProductBoundary [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image
				+ ", productsDetails=" + productsDetails + ", category=" + category + "]";
	}

}

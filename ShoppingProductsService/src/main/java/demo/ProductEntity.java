package demo;

import java.util.Map;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class ProductEntity {
	private String id;
	private String name;
	private double price;
	private String image;
	private Map<String, Object> productsDetails;
	private String category;


	public ProductEntity() {
		super();
	}

	public ProductEntity(String id, String name, double price, String image, Map<String, Object> productsDetails,
			String category,String categoryDescription) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.productsDetails = productsDetails;
		this.category = category;
	}

	@Id
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	

	@Convert(converter = MapToJsonConverter.class)
	@Lob
	public Map<String, Object> getProductsDetails() {
		return productsDetails;
	}

	public void setProductsDetails(Map<String, Object> productsDetails) {
		this.productsDetails = productsDetails;
	}
    
	public String getCategory() {
		return category;
	}
	

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ProductEntity [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image
				+ ", productsDetails=" + productsDetails + ", category=" + category + "]";
	}


}

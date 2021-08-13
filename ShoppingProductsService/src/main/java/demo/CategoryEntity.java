package demo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIES")
public class CategoryEntity {
	private String name;
	private String description;
	private CategoryEntity categoryParent;
	private Set<CategoryEntity> children;

	public CategoryEntity() {
		super();
		this.children = new HashSet<>();
	}

	public CategoryEntity(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.children = new HashSet<>();
	}

	@Id
	public String getName() {
		return name;
	}

	public void setName(String categoryName) {
		this.name = categoryName;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public CategoryEntity getCategoryParent() {
		return categoryParent;
	}

	public void setCategoryParent(CategoryEntity categoryParent) {
		this.categoryParent = categoryParent;
	}
	
	@OneToMany(mappedBy = "categoryParent", fetch = FetchType.LAZY)
	public Set<CategoryEntity> getChildren() {
		return children;
	}

	public void setChildren(Set<CategoryEntity> children) {
		this.children = children;
	}
	public void addChild (CategoryEntity child) {
		this.children.add(child);
		child.setCategoryParent(this);
	}

	@Override
	public String toString() {
		return "Category [categoryName=" + name + ", description=" + description + "]";
	}

}

package vn.iotstar.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table (name = "categories")
@NamedQuery(name="Category.findAll",query = "SELECT c FROM Category c")
public class Category implements  Serializable{
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public Category() {
		super();
	}
	@Column(name = "categoryName", columnDefinition = "NVARCHAR(255)")
	private String categoryname;
	@Column (columnDefinition= "NVARCHAR(MAX)")
	private String images;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // chủ sở hữu category
    private User owner;
	
	public Category(int id, String categoryname, String images) {
		super();
		this.id = id;
		this.categoryname = categoryname;
		this.images = images;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	 public User getOwner() { return owner; }
	    public void setOwner(User owner) { this.owner = owner; }
	

}

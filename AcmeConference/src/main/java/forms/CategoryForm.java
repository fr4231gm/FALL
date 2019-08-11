package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Category;

public class CategoryForm {

	// Atributos
	private String 		nameEs;
	private String 		nameEn;
	private int 		id;
	private int 		version;
	private Category	parentCategory;

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getNameEs() {
		return this.nameEs;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getNameEn() {
		return this.nameEn;
	}

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}
	
	@NotNull
	public Category getParentCategory(){
		return this.parentCategory;
	}

	// Setters
	public void setNameEs(final String nameEs) {
		this.nameEs = nameEs;
	}

	public void setNameEn(final String nameEn) {
		this.nameEn = nameEn;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
	
	public void setParentCategory(final Category parentCategory){
		this.parentCategory = parentCategory; 
	}

}

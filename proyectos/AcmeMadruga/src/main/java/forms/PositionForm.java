
package forms;

public class PositionForm {

	// Atributos
	//	private Map<String, String>	name;
	private String	nameEs;
	private String	nameEn;
	private String	language;
	private int		id;
	private int		version;


	//		// Getters
	//		@ElementCollection
	//		public Map<String, String> getName() {
	//			return this.name;
	//		}
	//		
	public String getNameEs() {
		return this.nameEs;

	}

	public String getNameEn() {
		return this.nameEn;
	}
	public String getLanguage() {
		return this.language;
	}

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	// Setters
	public void setNameEs(final String nameEs) {
		this.nameEs = nameEs;
	}

	public void setNameEn(final String nameEn) {
		this.nameEn = nameEn;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	//		public void setName(final Map<String, String> name) {
	//			this.name = name;
	//		}

}

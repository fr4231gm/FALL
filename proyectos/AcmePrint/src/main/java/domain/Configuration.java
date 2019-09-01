package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "language") })
public class Configuration extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private String systemName;
	private String banner;
	private String welcomeMessage;
	private String countryCode;
	private String spamWords;
	private String language;
	private Integer finderLifeSpan;
	private Integer maxFinder;
	private String makes;
	private Double vat;
	private Double fare;
	private String categories;
	private String phaseNames;
	private String materials;

	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSystemName() {
		return this.systemName;
	}

	// @URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpamWords() {
		return this.spamWords;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	@NotNull
	@Range(min = 1, max = 24)
	public Integer getFinderLifeSpan() {
		return this.finderLifeSpan;
	}

	@NotNull
	@Range(min = 10, max = 100)
	public Integer getMaxFinder() {
		return this.maxFinder;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLanguage() {
		return this.language;
	}

	@NotBlank
	public String getMakes() {
		return this.makes;
	}

	@NotNull
	@Range(min = 0, max = 1)
	public Double getVat() {
		return this.vat;
	}

	@NotNull
	public Double getFare() {
		return this.fare;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCategories() {
		return categories;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhaseNames() {
		return phaseNames;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMaterials() {
		return materials;
	}

	// Setters ----------------------------------------------------------------
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public void setSpamWords(String spamWords) {
		this.spamWords = spamWords;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setFinderLifeSpan(Integer finderLifeSpan) {
		this.finderLifeSpan = finderLifeSpan;
	}

	public void setMaxFinder(Integer maxFinder) {
		this.maxFinder = maxFinder;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setMakes(String makes) {
		this.makes = makes;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public void setPhaseNames(String phaseNames) {
		this.phaseNames = phaseNames;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}
}

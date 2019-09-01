
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String			systemName, banner, welcomeMessage, PNDefaultCountry, spamWords, positiveWords, negativeWords, defaultBrands, lan;
	private Double			VAT;
	private Integer			finderLifeSpan, maxFinder;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	public String getPNDefaultCountry() {
		return this.PNDefaultCountry;
	}

	public void setPNDefaultCountry(final String pNDefaultCountry) {
		this.PNDefaultCountry = pNDefaultCountry;
	}

	@Range(min = 0, max = 1)
	public Double getVAT() {
		return this.VAT;
	}

	public void setVAT(final Double vAT) {
		this.VAT = vAT;
	}

	@Range(min = 1, max = 24)
	public Integer getFinderLifeSpan() {
		return this.finderLifeSpan;
	}

	public void setFinderLifeSpan(final Integer finderLifeSpan) {
		this.finderLifeSpan = finderLifeSpan;
	}

	@Range(min = 10, max = 100)
	public Integer getMaxFinder() {
		return this.maxFinder;
	}

	public void setMaxFinder(final Integer maxFinder) {
		this.maxFinder = maxFinder;
	}

	public String getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWords = positiveWords;
	}

	public String getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWords = negativeWords;
	}

	public String getDefaultBrands() {
		return this.defaultBrands;
	}

	public void setDefaultBrands(final String defaultBrands) {
		this.defaultBrands = defaultBrands;
	}
	
	@NotBlank
	public String getLan() {
		return lan;
	}

	public void setLan(String lan) {
		this.lan = lan;
	}


}

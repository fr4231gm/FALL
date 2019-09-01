
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

public class ConfigurationForm extends DomainEntity {

	//Atributos----------------------------
	private String	systemName;
	private String	banner;
	private String	welcomeMessage;
	private String	countryCode;
	private String	spamWords;
	private String	positiveWords;
	private String	negativeWords;
	private String	priorities;
	private String	language;
	private Integer	finderLifeSpan;
	private Integer	maxFinder;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSystemName() {
		return this.systemName;
	}

	@URL
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

	@Range(min = 1, max = 24)
	public Integer getFinderLifeSpan() {
		return this.finderLifeSpan;
	}

	@Range(min = 10, max = 100)
	public Integer getMaxFinder() {
		return this.maxFinder;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getPositiveWords() {
		return this.positiveWords;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getNegativeWords() {
		return this.negativeWords;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getPriorities() {
		return this.priorities;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLanguage() {
		return this.language;
	}

	// Setters
	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public void setFinderLifeSpan(final Integer finderLifeSpan) {
		this.finderLifeSpan = finderLifeSpan;
	}

	public void setMaxFinder(final Integer maxFinder) {
		this.maxFinder = maxFinder;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWords = positiveWords;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWords = negativeWords;
	}

	public void setPriorities(final String priorities) {
		this.priorities = priorities;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

}

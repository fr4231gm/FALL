package forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

public class PostForm extends DomainEntity {

	// Atributos --------------------------------------------------------------
	
	//POST
	private String	ticker;
	private Date	moment;
	private String	description;
	private String	title;
	private Double	score;
	private Boolean	isDraft;
	private String	pictures;
	private String	category;
	private String	stl;
	
	//GUIDE
	private Integer	guideId;
	private Integer guideVersion;
	private Integer extruderTemp;
	private Integer hotbedTemp;
	private Double layerHeight;
	private Integer printSpeed;
	private Integer retractionSpeed;
	private String advices;
	
	//DESIGNER
	private Integer designer;
	
	// Getters ----------------------------------------------------------------
	
	public Integer getDesigner() {
		return this.designer;
	}
	
	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}
	
	@Range(min = 0, max = 10)
	public Double getScore() {
		return this.score;
	}
	
	
	public Boolean getIsDraft() {
		return this.isDraft;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCategory() {
		return this.category;
	}
	
	@URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStl() {
		return this.stl;
	}
	
	@NotNull
	public Integer getGuideId() {
		return guideId;
	}
	
	@NotNull
	public Integer getGuideVersion() {
		return guideVersion;
	}
	
	@NotNull
	@Range(min = 0, max = 400)
	public Integer getExtruderTemp() {
		return this.extruderTemp;
	}
	
	@NotNull
	@Range(min = 0, max = 200)
	public Integer getHotbedTemp() {
		return this.hotbedTemp;
	}
	
	@NotNull
	@Range(min = 0, max = 2)
	public Double getLayerHeight() {
		return this.layerHeight;
	}
	
	@NotNull
	@Range(min = 0, max = 10000)
	public Integer getPrintSpeed() {
		return this.printSpeed;
	}
	
	@NotNull
	@Range(min = 0, max = 10000)
	public Integer getRetractionSpeed() {
		return this.retractionSpeed;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAdvices() {
		return this.advices;
	}

	// Setters ----------------------------------------------------------------
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setStl(String stl) {
		this.stl = stl;
	}
	public void setGuideId(Integer guideId) {
		this.guideId = guideId;
	}

	public void setGuideVersion(Integer guideVersion) {
		this.guideVersion = guideVersion;
	}
	public void setExtruderTemp(Integer extruderTemp) {
		this.extruderTemp = extruderTemp;
	}
	public void setHotbedTemp(Integer hotbedTemp) {
		this.hotbedTemp = hotbedTemp;
	}
	public void setLayerHeight(Double layerHeight) {
		this.layerHeight = layerHeight;
	}
	public void setPrintSpeed(Integer printSpeed) {
		this.printSpeed = printSpeed;
	}
	public void setRetractionSpeed(Integer retractionSpeed) {
		this.retractionSpeed = retractionSpeed;
	}
	public void setAdvices(String advices) {
		this.advices = advices;
	}
	public void setDesigner(Integer designer) {
		this.designer = designer;
	}
}

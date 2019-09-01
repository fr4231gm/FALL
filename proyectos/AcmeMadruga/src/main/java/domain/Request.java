
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Request extends DomainEntity {

	// Atributos
	private String	status;
	private Integer	rowRequest;
	private Integer	columnRequest;
	private String	rejectReason;


	// Constructor -- vacio por defecto
	public Request() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Pattern(regexp = "^PENDING|APPROVED|REJECTED$")
	public String getStatus() {
		return this.status;
	}
	@Range(min = 0, max = 5)
	@NotNull
	public Integer getRowRequest() {
		return this.rowRequest;
	}
	@Range(min = 0, max = 100)
	@NotNull
	public Integer getColumnRequest() {
		return this.columnRequest;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getRejectReason() {
		return this.rejectReason;
	}

	// Setters
	public void setStatus(final String status) {
		this.status = status;
	}

	public void setRowRequest(final Integer rowRequest) {
		this.rowRequest = rowRequest;
	}

	public void setColumnRequest(final Integer columnRequest) {
		this.columnRequest = columnRequest;
	}

	public void setRejectReason(final String rejectReason) {
		this.rejectReason = rejectReason;
	}


	// Relaciones
	private Procession	procession;
	private Member		member;


	// Getters
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return this.procession;
	}

	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	// Setters
	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

	public void setMember(final Member member) {
		this.member = member;
	}
}

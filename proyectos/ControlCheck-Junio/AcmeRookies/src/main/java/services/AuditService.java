
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	@Autowired
	private AuditRepository	auditRepository;

	@Autowired
	private AuditorService	auditorService;


	// Constructor
	public AuditService() {
		super();
	}

	// Create
	public Audit create() {
		// Comprobamos que la persona logueada es un auditor
		Auditor principal;
		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);

		// Nuevo Audit
		final Audit result = new Audit();
		result.setAuditor(principal);
		result.setIsDraft(true);
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;

	}

	// SAVE
	public Audit save(final Audit audit) {

		Auditor principal;
		Audit res;
		principal = this.auditorService.findByPrincipal();

		Assert.notNull(audit);
		Assert.isTrue(principal == audit.getAuditor());

		if (audit.getId() == 0) {
			audit.setMoment(new Date(System.currentTimeMillis() - 1));
			res = this.auditRepository.save(audit);
		} else
			//			Assert.isTrue(audit.getIsDraft());
			res = this.auditRepository.save(audit);
		return res;
	}

	// FIND ONE
	public Audit findOne(final int auditId) {
		Audit res;
		res = this.auditRepository.findOne(auditId);
		Assert.notNull(res);
		return res;
	}

	// FIND BY PRINCIPAL
	public Collection<Audit> findByPrincipal() {
		Auditor principal;
		principal = this.auditorService.findByPrincipal();
		Collection<Audit> res;
		res = this.auditRepository.findAuditsByAuditorId(principal.getId());
		;
		return res;
	}

	// FIND BY AUDITOR
	public Collection<Audit> findAuditsByAuditorId(final int auditorId) {
		return this.auditRepository.findAuditsByAuditorId(auditorId);
	}

	// FIND BY AUDITOR AND POSITION
	public Collection<Audit> findPublishedAuditsByAuditorIdAndPositionId(final int auditorId, final int positionId) {
		return this.auditRepository.findPublishedAuditsByAuditorIdAndPositionId(auditorId, positionId);
	}

	// FIND ALL
	public Collection<Audit> findAllPublishedAudits() {
		return this.auditRepository.findAllPublishedAudits();
	}

	// FIND BY POSITION
	public Collection<Audit> findAllPublishedAuditsByPosition(final int positionId) {
		return this.auditRepository.findAllPublishedAuditsByPositionId(positionId);
	}

	// DELETE
	public void delete(final Audit audit) {

		Auditor principal;
		principal = this.auditorService.findByPrincipal();
		final Audit aux = this.findOne(audit.getId());

		Assert.notNull(aux);
		Assert.isTrue(principal == aux.getAuditor());
		Assert.isTrue(aux.getIsDraft());

		this.auditRepository.delete(aux);
	}

	public void delete(final int auditId) {

		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		final Audit audit = this.findOne(auditId);

		Assert.notNull(audit);
		Assert.isTrue(principal == audit.getAuditor());
		Assert.isTrue(audit.getIsDraft());

		this.auditRepository.delete(auditId);
	}

	public void flush() {
		this.auditRepository.flush();
	}

	public void deleteByUserDropOut(Audit audit) {
		this.auditRepository.delete(audit);
		
	}

	public Collection<Audit> findAllAuditsByPosition(int positionId) {
		return this.auditRepository.findByPosition(positionId);
	}

}

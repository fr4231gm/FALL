
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProclaimRepository;
import domain.Proclaim;

@Service
@Transactional
public class ProclaimService {

	// Managed repository -------------------------

	@Autowired
	private ProclaimRepository	proclaimRepository;

	// Supporting services -------------------------

	@Autowired
	private ChapterService		chapterService;


	// Constructors ------------------------------------

	public ProclaimService() {
		super();
	}

	// Simple CRUDs methods -----------------------------

	public Proclaim create() {

		Proclaim res;

		res = new Proclaim();
		res.setChapter(this.chapterService.findByPrincipal());
		res.setMoment(null);

		return res;
	}

	public Proclaim save(final Proclaim proclaim) {

		// An chapter must be logged
		Assert.notNull(this.chapterService.findByPrincipal());
		// Once a proclaim is published, there's no way to update
		Assert.isTrue(proclaim.getMoment() == null);
		Assert.isTrue(proclaim.getChapter() == this.chapterService.findByPrincipal());
		// Proclaim toSave
		Proclaim res;

		res = this.proclaimRepository.save(proclaim);

		return res;
	}

	public void delete(final Proclaim proclaim) {
		// An chapter must be logged
		Assert.notNull(this.chapterService.findByPrincipal());
		// Once a proclaim is published, there's no way to update
		Assert.isTrue(proclaim.getMoment() == null);

		this.proclaimRepository.delete(proclaim);
	}

	public Proclaim findOne(final int proclaimId) {
		Assert.notNull(proclaimId);
		Proclaim res;

		res = this.proclaimRepository.findOne(proclaimId);
		Assert.notNull(res);

		return res;
	}

	public Collection<Proclaim> findAll() {
		Collection<Proclaim> res;

		res = this.proclaimRepository.findAll();

		return res;
	}

	public void flush() {
		this.proclaimRepository.flush();
	}

	// Other business methods --------------------

	public Proclaim publish(final Proclaim proclaim) {
		Proclaim res;

		// An chapter must be logged
		Assert.notNull(this.chapterService.findByPrincipal());
		// Check if the proclaim to publish is belongs to chapter logged
		Assert.isTrue(proclaim.getChapter().getId() == this.chapterService.findByPrincipal().getId());
		// Once a proclaim is published, there's no way to update
		Assert.isTrue(proclaim.getMoment() == null);

		proclaim.setMoment(new Date(System.currentTimeMillis() - 1));

		res = this.proclaimRepository.save(proclaim);

		return res;
	}

	public Collection<Proclaim> findProclaimsByChapterId(final int chapterId) {
		Assert.notNull(chapterId);
		Collection<Proclaim> res;

		res = this.proclaimRepository.findProclaimsByChapterId(chapterId);
		Assert.notNull(res);

		return res;
	}

	public Proclaim findOneToFail(final int proclaimId) {
		Assert.notNull(proclaimId);
		Proclaim res;

		res = this.proclaimRepository.findOne(proclaimId);

		return res;
	}
}

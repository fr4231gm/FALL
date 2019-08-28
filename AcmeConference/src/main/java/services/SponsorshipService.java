
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;

@Transactional
@Service
public class SponsorshipService {

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	public Collection<Sponsorship> findSponsorshipsBySponsorId() {
		Collection<Sponsorship> res;

		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();

		res = this.sponsorshipRepository.findSponsorshipsBySponsorId(principal.getId());
		Assert.notNull(res);
		return res;
	}

	// FINDONE TO FAIL
	public Sponsorship findOneToFail(final int sponsorshipId) {

		// Create instance of the result
		Sponsorship res;

		// Call the repository
		res = this.sponsorshipRepository.findOne(sponsorshipId);

		// Return the result
		return res;
	}

	// SAVE
	public Sponsorship save(final Sponsorship sponsorship) {
		Sponsorship res = null;
		final Sponsor principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(sponsorship.getSponsor().getId() == principal.getId());
		res = this.sponsorshipRepository.save(sponsorship);
		return res;
	}

	public Sponsorship findOne(final int sponsorshipId) {

		return this.sponsorshipRepository.findOne(sponsorshipId);
	}

	public Sponsorship create() {

		final Sponsor principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		final Sponsorship res = new Sponsorship();

		res.setSponsor(principal);

		return res;
	}

	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getSponsor().equals(this.sponsorService.findByPrincipal()));
		this.sponsorshipRepository.delete(sponsorship);

	}

}

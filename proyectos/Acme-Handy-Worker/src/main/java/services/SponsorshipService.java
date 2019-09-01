
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private SponsorService			sponsorService;


	public Sponsorship create() {
		final Sponsorship sponsorship = new Sponsorship();
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		sponsorship.setSponsor(sponsor);
		return sponsorship;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		return this.sponsorshipRepository.save(sponsorship);
	}

	public Sponsorship findOne(final int id) {
		return this.sponsorshipRepository.findById(id);
	}

	public Collection<Sponsorship> findAll() {
		final Collection<Sponsorship> result = new ArrayList<Sponsorship>();
		result.addAll(this.sponsorshipRepository.findAll());
		return result;
	}

}


package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuoletRepository;
import domain.Actor;
import domain.Administrator;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository		quoletRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	public Quolet findOne(final int id) {
		final Quolet q = this.quoletRepository.findOne(id);
		return q;
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepository.findAll();
	}

	public Quolet create() {
		final Quolet quolet = new Quolet();
		quolet.setAdministrator(this.administratorService.findByPrincipal());
		quolet.setIsDraft(true);
		quolet.setTicker(this.generateTicker());
		return quolet;
	}

	public Quolet save(final Quolet quolet) {
		Quolet result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (principal instanceof Administrator)
			Assert.isTrue(quolet.getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		else
			Assert.isTrue(quolet.getConference().getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		final Date actual = new Date(System.currentTimeMillis() - 1);

		if (!quolet.getIsDraft())
			quolet.setPublicationMoment(actual);

		result = this.quoletRepository.save(quolet);

		return result;
	}

	private String generateTicker() {
		String res = "";

		final Date fecha = new Date(System.currentTimeMillis() - 1);
		final DateFormat dateFormat = new SimpleDateFormat("yy:MM:dd");
		final String formattedDate = dateFormat.format(fecha);

		final String alphanumeric = "0123456789";

		for (int i = 0; i < 6; i++) {
			final int randomNumber = (int) Math.floor(Math.random() * (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}

		// Adding formatted date to alphanumeric code
		res = res + ":" + formattedDate;

		return res;
	}

	public Collection<Quolet> findByAdministrator(final int id) {
		final Collection<Quolet> q = this.quoletRepository.findQuoletsByAdministratorId(id);
		return q;
	}

	public Collection<Quolet> findByConference(final int id) {
		return this.quoletRepository.findQuoletsByConferenceId(id);
	}
}

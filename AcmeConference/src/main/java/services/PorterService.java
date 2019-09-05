package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PorterRepository;
import domain.Actor;
import domain.Administrator;
import domain.Porter;

@Service
@Transactional
public class PorterService {

	@Autowired
	private PorterRepository porterRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ActorService actorService;

	public Porter findOne(final int id) {
		final Porter q = this.porterRepository.findOne(id);
		return q;
	}

	public Collection<Porter> findAll() {
		return this.porterRepository.findAll();
	}

	public Porter create() {
		final Porter porter = new Porter();
		porter.setAdministrator(this.administratorService.findByPrincipal());
		porter.setIsDraft(true);
		porter.setTicker(this.generateTicker());
		return porter;
	}

	public Porter save(final Porter porter) {
		Porter result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (principal instanceof Administrator)
			Assert.isTrue(porter.getAdministrator().getId() == this.administratorService
					.findByPrincipal().getId());
		else
			Assert.isTrue(porter.getConference().getAdministrator().getId() == this.administratorService
					.findByPrincipal().getId());
		final Date actual = new Date(System.currentTimeMillis() - 1);

		if (!porter.getIsDraft())
			porter.setPublicationMoment(actual);

		result = this.porterRepository.save(porter);

		return result;
	}

	public void delete(Porter porter) {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(this.findByAdministrator(principal.getId()).contains(
				porter));
		Assert.isTrue(porter.getIsDraft().equals(true));

		this.porterRepository.delete(porter);
	}

	private String generateTicker() {
		String res = "";

		final Date fecha = new Date(System.currentTimeMillis() - 1);
		final DateFormat dateFormat = new SimpleDateFormat("MMddyy");
		final String formattedDate = dateFormat.format(fecha);

		String words = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789_";
		final String digits = "0123456789";

		for (int i = 0; i < 2; i++) {
			final int randomNumber = (int) Math.floor(Math.random()
					* (words.length() - 1));
			res += words.charAt(randomNumber);
			;
		}
		
		for (int i = 0; i < 2; i++) {
			final int randomNumber = (int) Math.floor(Math.random()
					* (digits.length() - 1));
			res += digits.charAt(randomNumber);
			;
		}

		// Adding formatted date to alphanumeric code
		res = res + "-" + formattedDate;

		return res;
	}

	public Collection<Porter> findByAdministrator(final int id) {
		Collection<Porter> q = this.porterRepository
				.findPortersByAdministratorId(id);
		return q;
	}

	public Collection<Porter> findByConference(final int id) {
		return this.porterRepository.findPortersByConferenceId(id);
	}
}

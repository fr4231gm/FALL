
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QulpRepository;
import domain.Actor;
import domain.Administrator;
import domain.Qulp;

@Service
@Transactional
public class QulpService {

	@Autowired
	private QulpRepository			qulpRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	public Qulp findOne(final int id) {
		final Qulp q = this.qulpRepository.findOne(id);
		return q;
	}

	public Collection<Qulp> findAll() {
		return this.qulpRepository.findAll();
	}

	public Qulp create() {
		final Qulp qulp = new Qulp();
		qulp.setAdministrator(this.administratorService.findByPrincipal());
		qulp.setIsDraft(true);
		qulp.setTicker(this.generateTicker());
		return qulp;
	}

	public Qulp save(final Qulp qulp) {
		Qulp result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (principal instanceof Administrator)
			Assert.isTrue(qulp.getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		else
			Assert.isTrue(qulp.getConference().getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		final Date actual = new Date(System.currentTimeMillis() - 1);

		if (!qulp.getIsDraft())
			qulp.setPublicationMoment(actual);

		result = this.qulpRepository.save(qulp);

		return result;
	}

	public void delete(final Qulp qulp) {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(this.findByAdministrator(principal.getId()).contains(qulp));
		Assert.isTrue(qulp.getIsDraft().equals(true));

		this.qulpRepository.delete(qulp);
	}

	private String generateTicker() {
		String res = "";

		final Date fecha = new Date(System.currentTimeMillis() - 1);
		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		final String formattedDate = dateFormat.format(fecha);

		final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSUVTXYZ";

		for (int i = 0; i < 5; i++) {
			final int randomNumber = (int) Math.floor(Math.random() * (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}

		// Adding formatted date to alphanumeric code
		res = res + "-" + formattedDate;

		return res;
	}

	public Collection<Qulp> findByAdministrator(final int id) {
		final Collection<Qulp> q = this.qulpRepository.findQulpsByAdministratorId(id);
		return q;
	}

	public Collection<Qulp> findByConference(final int id) {
		return this.qulpRepository.findQulpsByConferenceId(id);
	}
}

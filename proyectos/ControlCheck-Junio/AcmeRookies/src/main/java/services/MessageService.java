
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Message;
import domain.Position;
import domain.Rookie;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------

	@Autowired
	private MessageRepository		mRepository;

	@Autowired
	private ConfigurationService	configurationService;

	// Supporting services ----------------------------

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods --------------------------------

	public Message findOne(final int id) {
		Assert.notNull(id);

		final Message m = this.mRepository.findOne(id);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == m.getSender().getId() || m.getRecipient().getId() == principal.getId());
		return m;
	}

	public Message save(final MessageForm m) {
		Assert.notNull(m);
		Assert.isTrue(m.getId() == 0);

		final Actor principal = this.actorService.findByPrincipal();
		final Message res = new Message();
		res.setSender(principal);
		res.setMoment(new Date(System.currentTimeMillis() - 1));
		res.setIsSpam(this.actorService.checkSpam(m.getSubject()) || this.actorService.checkSpam(m.getBody()) ||this.actorService.checkSpam(m.getTags()));
		res.setBody(m.getBody());
		res.setSubject(m.getSubject());
		res.setTags(m.getTags());

		final List<Actor> recipients = new ArrayList<Actor>(m.getRecipients());

		for (int i = 0; i < recipients.size(); i++) {
			final Message copied = this.createcopy(m);
			copied.setSender(principal);
			copied.setRecipient(recipients.get(i));
			this.mRepository.save(copied);
		}

		return this.mRepository.save(res);
	}

	private Message createcopy(final MessageForm m) {
		final Message res = new Message();
		res.setMoment(new Date(System.currentTimeMillis() - 1));
		res.setIsSpam(this.actorService.checkSpam(m.getSubject()));
		res.setBody(m.getBody());
		res.setSubject(m.getSubject());
		res.setTags(m.getTags());

		return res;
	}

	public void delete(final Message m) {
		Assert.notNull(m);
		final Actor principal = this.actorService.findByPrincipal();
		if (m.getRecipient() == null)
			Assert.isTrue((m.getSender().getId() == principal.getId() && m.getRecipient() == null));
		else
			Assert.isTrue(m.getRecipient().getId() == principal.getId());
		if (m.getTags().equals("DELETED"))
			this.mRepository.delete(m);
		else {
			m.setTags("DELETED");
			this.mRepository.save(m);
		}
	}

	public Collection<Message> findSended(final int actorId) {

		Collection<Message> messages;
		messages = this.mRepository.findSended(actorId);
		Assert.notNull(messages);

		return messages;
	}

	public Collection<Message> findReceived(final int actorId) {
		Collection<Message> messages;
		messages = this.mRepository.findReceived(actorId);
		Assert.notNull(messages);

		return messages;
	}

	public void notificateApplicationStatusChange(final Company company, final Rookie rookie, final String oldstatus, final String newstatus, final String title) {
		final Message m = new Message();

		m.setSender(company);
		m.setBody("your  application request to" + title + "changed his status from" + oldstatus + "to" + newstatus);
		m.setSubject("APPLICATION STATUS CHANGED");
		m.setIsSpam(false);
		m.setMoment(new Date(System.currentTimeMillis() - 1));
		m.setTags("Notification");
		m.setRecipient(rookie);
		this.mRepository.save(m);
	}

	public void notificateNewOffer(final Company company, final Rookie rookie, final Position position) {
		final Message m = new Message();

		m.setSender(company);
		m.setBody("the company " + company.getCommercialName() + "has published a new position that matches your finder, the position ticker is:" + position.getTicker());
		m.setSubject("New offer you may like");
		m.setIsSpam(false);
		m.setMoment(new Date(System.currentTimeMillis() - 1));
		m.setTags("Notification");

		final List<Actor> recipients = new ArrayList<Actor>();
		recipients.add(rookie);
		m.setRecipient(rookie);
		this.mRepository.save(m);

	}

	public void notificateRebranding(final String systemName) {
		final List<Actor> actores = new ArrayList<Actor>(this.actorService.findAll());
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		Assert.isTrue(!this.configurationService.findConfiguration().getRebrandingNotificated());
		final MessageForm m = new MessageForm();
		m.setBody("the name of the application is now" + systemName);
		m.setSubject("Rebranding Notification");
		m.setTags("Notification");
		m.setRecipients(actores);
		this.save(m);
	}
	
	public void deleteByUserDropOut(final Message message) {
		Assert.notNull(message);
		this.mRepository.delete(message);

	}

	public Collection<Message> findAllToDelete(int messageId) {
		return this.mRepository.findAllToDelete(messageId);
	}

}

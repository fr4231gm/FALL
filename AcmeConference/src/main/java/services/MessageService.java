
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
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------

	@Autowired
	private MessageRepository		mRepository;

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
		res.setBody(m.getBody());
		res.setSubject(m.getSubject());
		res.setTopic(m.getTopic());

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
		res.setBody(m.getBody());
		res.setSubject(m.getSubject());
		res.setTopic(m.getTopic());

		return res;
	}

	public void delete(final Message m) {
		Assert.notNull(m);
		final Actor principal = this.actorService.findByPrincipal();
		if (m.getRecipient() == null){
			Assert.isTrue((m.getSender().getId() == principal.getId() && m.getRecipient() == null));}
		else{
			Assert.isTrue(m.getRecipient().getId() == principal.getId());}
		this.mRepository.delete(m);
		
	}

	public Collection<Message> findSended(final int actorId) {

		Collection<Message> messages;
		messages = this.mRepository.findMessagesSent(actorId);
		Assert.notNull(messages);

		return messages;
	}

	public Collection<Message> findReceived(final int actorId) {
		Collection<Message> messages;
		messages = this.mRepository.findMessagesRecevied(actorId);
		Assert.notNull(messages);

		return messages;
	}

}


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
import domain.Submission;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services ----------------------------

	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private SubmissionService		submissionService;

	// Simple CRUD methods --------------------------------

	public Message findOne(final int id) {
		final Message m = this.messageRepository.findOne(id);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == m.getSender().getId() || m.getRecipient().getId() == principal.getId());
		return m;
	}

	public void save(final MessageForm m) {
		Assert.notNull(m);
		Assert.isTrue(m.getId() == 0);

		final Actor principal = this.actorService.findByPrincipal();


		final List<Actor> recipients = new ArrayList<Actor>(m.getRecipients());
		
		for (int i = 0; i < recipients.size(); i++) {
			final Message copied = this.createcopy(m);
			copied.setSender(principal);
			copied.setRecipient(recipients.get(i));
			copied.setIsCopy(true);
			this.messageRepository.save(copied);
		}
		
		for (int i = 0; i < recipients.size(); i++) {
			final Message copied = this.createcopy(m);
			copied.setSender(principal);
			copied.setRecipient(recipients.get(i));
			copied.setIsCopy(false);
			this.messageRepository.save(copied);
		}


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
		if (m.getRecipient().getId() == principal.getId()){
			Assert.isTrue(m.getIsCopy()); 
		}
		else{
			Assert.isTrue((m.getSender().getId() == principal.getId()) && !m.getIsCopy());
		}
		this.messageRepository.delete(m);
		
	}

	public Collection<Message> findSent(final int actorId) {

		Collection<Message> messages;
		messages = this.messageRepository.findMessagesSent(actorId);
		Assert.notNull(messages);

		return messages;
	}

	public Collection<Message> findReceived(final int actorId) {
		Collection<Message> messages;
		messages = this.messageRepository.findMessagesRecevied(actorId);
		Assert.notNull(messages);

		return messages;
	}

	public void notifySubmission(int submissionId) {
		Submission s = this.submissionService.findOne(submissionId);
		MessageForm message = new MessageForm();
		Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(s.getAuthor());
		message.setRecipients(recipients);
		message.setBody("The submission " + s.getTicker() + "has been " + s.getStatus());
		message.setTopic("NOTIFICATION");
		message.setSubject("Submission Update");
		this.save(message);
	}

}

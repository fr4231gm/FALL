
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
import domain.Box;
import domain.Brotherhood;
import domain.Configuration;
import domain.Member;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------

	@Autowired
	private MessageRepository		mRepository;

	// Supporting services ----------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	// Simple CRUD methods --------------------------------

	public Message create() {
		final Message m = new Message();
		m.setRecipients(new ArrayList<Actor>());
		m.setSender(this.actorService.findByPrincipal());

		final Box box = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "OutBox");
		final Collection<Box> boxes = new ArrayList<Box>();

		boxes.add(box);
		m.setMoment(new Date(System.currentTimeMillis() - 1));
		m.setBoxes(boxes);

		return m;
	}

	public Collection<Message> findAll() {

		return this.mRepository.findAll();
	}

	public Message findOne(final int id) {
		Assert.notNull(id);

		final Message m = this.mRepository.findOne(id);
		final Actor principal = this.actorService.findByPrincipal();
		if (principal.getId() == m.getSender().getId()) {
		} else
			for (final Actor a : m.getRecipients())
				if (a.getId() == principal.getId())
					break;
		return m;
	}

	public Message save(final Message m) {
		Assert.notNull(m);

		final Actor principal = this.actorService.findByPrincipal();
		if (m.getId() == 0) {
			final List<Actor> recipients = new ArrayList<Actor>(m.getRecipients());
			final Box outbox = this.boxService.getSystemBoxByName(principal.getId(), "OutBox");

			m.getBoxes().add(outbox);
			if (this.actorService.checkSpam(m.getSubject()) || this.actorService.checkSpam(m.getBody()))
				for (int i = 0; i < recipients.size(); i++) {
					final Actor a = recipients.get(i);
					final Box b = this.boxService.getSystemBoxByName(a.getId(), "SpamBox");
					m.getBoxes().add(b);
					//principal.setIsSuspicious(true);
					this.actorService.save(principal);
				}
			else
				for (int i = 0; i < recipients.size(); i++) {

					final Actor a = recipients.get(i);
					final Box b = this.boxService.getSystemBoxByName(a.getId(), "InBox");

					m.getBoxes().add(b);
				}
		}

		return this.mRepository.save(m);
	}

	public void delete(final Message m, final int boxId) {
		Assert.notNull(m);
		Assert.isTrue(m.getRecipients().contains(this.actorService.findByPrincipal()) || m.getSender().equals(this.actorService.findByPrincipal()));
		final Box b = this.boxService.findOne(boxId);

		final Box trash = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "TrashBox");
		if (!(b.equals(trash))) {
			this.move(m, b, trash);
			this.save(m);
		} else {
			final List<Box> boxes = new ArrayList<Box>(m.getBoxes());
			boxes.remove(this.boxService.findOne(boxId));
			m.setBoxes(boxes);
			this.save(m);
		}
		if (m.getBoxes().isEmpty())
			this.mRepository.delete(m);
	}

	public void deleteByUserDropOut(final Message m) {
		final List<Box> boxes = new ArrayList<Box>(m.getBoxes());
		for (int i = 0; i < boxes.size(); i++) {
			final Box b = boxes.get(i);
			b.getMessages().remove(m);
			this.boxService.save2(b);
		}
	}

	// Moves a m from one folder to another.
	public void move(final Message m, final Box oldBox, final Box newBox) {
		Assert.notNull(m);
		Assert.notNull(oldBox);
		Assert.notNull(newBox);
		Assert.isTrue(oldBox.getActor().getId() == newBox.getActor().getId());
		final Collection<Box> boxes = m.getBoxes();

		final Collection<Message> newList = newBox.getMessages();
		newList.add(m);
		newBox.setMessages(newList);

		final Collection<Message> oldList = oldBox.getMessages();
		oldList.remove(m);
		oldBox.setMessages(oldList);
		boxes.remove(oldBox);
		boxes.add(newBox);
		m.setBoxes(boxes);

		this.boxService.save(oldBox);
		this.boxService.save(newBox);
		this.save(m);
	}

	public void broadcastMessage(final Message m) {
		Assert.notNull(m);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		final List<Actor> recipients = new ArrayList<Actor>(this.actorService.findAll());
		m.setRecipients(recipients);
		final Collection<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < recipients.size(); i++)
			boxes.add(this.boxService.getSystemBoxByName(recipients.get(i).getId(), "InBox"));

		m.setBoxes(boxes);
		this.save(m);
	}

	public void saveNotification(final Message m) {
		Assert.notNull(m);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		final List<Actor> recipients = new ArrayList<Actor>(this.actorService.findAll());
		m.setRecipients(recipients);
		final Collection<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < recipients.size(); i++)
			boxes.add(this.boxService.getSystemBoxByName(recipients.get(i).getId(), "NotificationBox"));
		m.setBoxes(boxes);
		this.save(m);
	}

	public Collection<Message> findSended(final int id) {

		Collection<Message> messages;
		messages = this.mRepository.findSended(id);
		Assert.notNull(messages);

		return messages;
	}

	public Collection<Integer> positiveAndNegativeWords(final Message message) {
		Assert.notNull(message);
		// indexOf(...) is case sensitive
		final String text = message.getBody().concat(" " + message.getSubject());

		// The number of positive words will be stored in the element with index 0, and negative ones in the element with index 1
		final List<Integer> posAndNegWords = new ArrayList<Integer>();
		posAndNegWords.add(0);
		posAndNegWords.add(0);

		Integer countPos = 0;
		Integer countNeg = 0;
		final List<Configuration> config = new ArrayList<Configuration>(this.configurationService.findAll());
		final String posWords;
		final String negWords;
		posWords = config.get(0).getPositiveWords().concat(", " + config.get(1).getPositiveWords());
		negWords = config.get(0).getNegativeWords().concat(", " + config.get(1).getNegativeWords());
		//		for (int i = 0; i < config.size(); i++) {
		//			posWords.concat(config.get(i).getPositiveWords() + ", ");
		//			negWords.concat(config.get(i).getNegativeWords() + ", ");
		//		}

		final String[] arrayPosWords = posWords.toLowerCase().split(", ");
		final String[] arrayNegWords = negWords.toLowerCase().split(", ");

		for (final String posWord : arrayPosWords) {
			final String[] split = text.toLowerCase().split(posWord);
			countPos += split.length - 1;
		}

		for (final String negWord : arrayNegWords) {
			final String[] split = text.toLowerCase().split(negWord);
			countNeg += split.length - 1;
		}

		posAndNegWords.set(0, countPos);
		posAndNegWords.set(1, countNeg);
		return posAndNegWords;
	}

	public void notificateRequestStatusChange(final Brotherhood brotherhood, final Member member, final String oldstatus, final String newstatus, final String title) {
		final Message m = new Message();

		m.setSender(brotherhood);
		m.setBody("the request to" + title + "changed his status from" + oldstatus + "to" + newstatus);
		m.setSubject("REQUEST STATUS CHANGED");
		m.setIsSpam(false);
		m.setMoment(new Date(System.currentTimeMillis() - 1));
		m.setTags("Notification");
		m.setPriority("NEUTRAL");

		final List<Actor> recipients = new ArrayList<Actor>();
		recipients.add(member);
		m.setRecipients(recipients);
		final Collection<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < recipients.size(); i++)
			boxes.add(this.boxService.getSystemBoxByName(recipients.get(i).getId(), "NotificationBox"));
		m.setBoxes(boxes);
		this.mRepository.save(m);

	}

	public void notificateEnrolment(final Brotherhood brotherhood, final Member member) {
		final Message m = new Message();

		m.setSender(brotherhood);
		m.setBody("the enrolment request to" + brotherhood.getTitle() + "was accepted");
		m.setSubject("ENROLMENT ACCEPTED");
		m.setIsSpam(false);
		m.setMoment(new Date(System.currentTimeMillis() - 1));
		m.setTags("Notification");
		m.setPriority("NEUTRAL");

		final List<Actor> recipients = new ArrayList<Actor>();
		recipients.add(member);
		m.setRecipients(recipients);
		final Collection<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < recipients.size(); i++)
			boxes.add(this.boxService.getSystemBoxByName(recipients.get(i).getId(), "NotificationBox"));
		m.setBoxes(boxes);
		this.mRepository.save(m);

	}

	public void notificateDropOut(final Member principal, final Brotherhood brotherhood) {
		final Message m = new Message();

		m.setSender(principal);
		m.setBody("the user" + principal.getName() + "dropped out the brotherhood");
		m.setSubject("USER DROP OUT");
		m.setIsSpam(false);
		m.setMoment(new Date(System.currentTimeMillis() - 1));
		m.setTags("Notification");
		m.setPriority("NEUTRAL");

		final List<Actor> recipients = new ArrayList<Actor>();
		recipients.add(brotherhood);
		m.setRecipients(recipients);
		final Collection<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < recipients.size(); i++)
			boxes.add(this.boxService.getSystemBoxByName(recipients.get(i).getId(), "NotificationBox"));
		m.setBoxes(boxes);
		this.mRepository.save(m);

	}

	public void notificatePublication(final Brotherhood brotherhood, final String parade) {
		final Message m = new Message();
		final Collection<Member> members = this.brotherhoodService.enrolledMembers(brotherhood.getId());
		if (!members.isEmpty()) {
			m.setSender(brotherhood);
			m.setBody("We just published the parade" + parade + "!!");
			m.setSubject("NEW PARADE PUBLISHED");
			m.setIsSpam(false);
			m.setMoment(new Date(System.currentTimeMillis() - 1));
			m.setTags("Notification");
			m.setPriority("NEUTRAL");

			final List<Actor> recipients = new ArrayList<Actor>(members);
			m.setRecipients(recipients);
			final Collection<Box> boxes = new ArrayList<Box>();
			for (int i = 0; i < recipients.size(); i++)
				boxes.add(this.boxService.getSystemBoxByName(recipients.get(i).getId(), "NotificationBox"));
			m.setBoxes(boxes);
			this.mRepository.save(m);
		}
	}

}

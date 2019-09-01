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
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------

	@Autowired
	private MessageRepository mRepository;

	// Supporting services ----------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private HandyWorkerService handyworkerService;

	// Simple CRUD methods --------------------------------

	public Message create() {
		final Message m = new Message();
		m.setRecipients(new ArrayList<Actor>());
		m.setSender(this.actorService.findByPrincipal());
		Box box = boxService.getSystemBoxByName(this.actorService
				.findByPrincipal().getId(), "out box");
		Collection<Box> boxes = new ArrayList<Box>();
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
		boolean permiso = false;
		Message m = this.mRepository.findOne(id);
		Actor principal = this.actorService.findByPrincipal();
		if (principal.getId() == m.getSender().getId()) {
			permiso = true;
		} else {
			for (Actor a : m.getRecipients()) {
				if (a.getId() == principal.getId()) {
					permiso = true;
					break;
				}
			}
		}
		return m;
	}

	public Message save(final Message m) {
		Assert.notNull(m);

		Actor principal = this.actorService.findByPrincipal();
		if (m.getId() == 0) {
			List<Actor> recipients = new ArrayList<Actor>(m.getRecipients());
			Box outbox = this.boxService.getSystemBoxByName(principal.getId(),
					"Out box");
			m.getBoxes().add(outbox);
			if (this.actorService.checkSpam(m.getSubject())
					|| this.actorService.checkSpam(m.getBody())) {
				for (int i = 0; i < recipients.size(); i++) {
					Actor a = recipients.get(i);
					Box b = this.boxService.getSystemBoxByName(a.getId(),
							"Spam box");
					m.getBoxes().add(b);
					principal.setIsSuspicious(true);
					this.actorService.save(principal);
				}
			} else {
				for (int i = 0; i < recipients.size(); i++) {
					Actor a = recipients.get(i);
					Box b = this.boxService.getSystemBoxByName(a.getId(),
							"In box");
					m.getBoxes().add(b);
				}
			}
		}

		return this.mRepository.save(m);
	}

	public void delete(final Message m, int boxId) {
		System.out.println("bien");
		Assert.notNull(m);
		System.out.println("mejor");
		Assert.isTrue(m.getRecipients().contains(this.actorService.findByPrincipal()) || m.getSender().equals(this.actorService.findByPrincipal()));
		Box b = this.boxService.findOne(boxId);
		System.out.println("chupi:" + b);
		final Box trash = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Trash box");
		if (!(b.equals(trash))) {
			System.out.println("weirdo");
			this.move(m, b, trash);
			this.save(m);
		} else {
			List<Box> boxes = new ArrayList<Box>(m.getBoxes());
						System.out.println("ahhhhhhh" + boxes);
			boxes.remove(this.boxService.findOne(boxId));
			m.setBoxes(boxes);
						System.out.println("cocacolas" + boxes);
						System.out.println("sigueñaaaaaaaaaaaaaaaa" + m.getBoxes());
			this.save(m);
		}
		if (m.getBoxes().isEmpty()) {
			this.mRepository.delete(m);
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
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		final List<Actor> recipients = new ArrayList<Actor>(
				this.actorService.findAll());
		m.setRecipients(recipients);
		final Collection<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < recipients.size(); i++)
			boxes.add(this.boxService.getSystemBoxByName(recipients.get(i)
					.getId(), "In box"));
		m.setBoxes(boxes);
		this.save(m);
	}

	public void applicationStatusChange(final int customerId,
			final int handyWorkerId) {
		final Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(this.customerService.findById(customerId));
		recipients.add(this.handyworkerService.findOne(handyWorkerId));
		final Box box1 = this.boxService.getSystemBoxByName(customerId,
				"In box");
		final Box box2 = this.boxService.getSystemBoxByName(handyWorkerId,
				"In box");
		final Collection<Box> boxes = new ArrayList<Box>();
		boxes.add(box1);
		boxes.add(box2);
		final Message m = this.create();
		m.setMoment(new Date());
		m.setPriority("NEUTRAL");
		m.setRecipients(recipients);
		m.setSender(this.customerService.findById(customerId));
		m.setSubject("Application status changed");
		m.setBody("Application status changed");
		m.setIsSpam(false);
		m.setTags(null);
		m.setBoxes(boxes);
		final Message m3 = this.save(m);
		final Collection<Message> m1 = box1.getMessages();
		m1.add(m3);
		final Collection<Message> m2 = box2.getMessages();
		m2.add(m3);
		box1.setMessages(m1);
		this.boxService.save(box1);
		box2.setMessages(m2);
		this.boxService.save(box2);

	}
}

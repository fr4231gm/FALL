package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository

	@Autowired
	private BoxRepository boxRepository;

	// Supporting Services
	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageService messageService;

	// Simple CRUD methods

	public Box create() {
		final Box b = new Box();
		Actor principal = this.actorService.findByPrincipal();
		b.setMessages(new ArrayList<Message>());
		b.setActor(principal);
		b.setIsSystem(false);
		return b;
	}

	public Box findOne(final int id) {
		Assert.notNull(id);
		return this.boxRepository.findOne(id);
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box save(final Box b) {
		Assert.notNull(b);
		Box saved;
		if (b.getIsSystem()) {
			// Si la carpeta es del sistema solo se puede modificar sus mensajes
			Box bs = this.boxRepository.findOne(b.getId());
			bs.setMessages(b.getMessages());
			saved = this.boxRepository.save(bs);
		} else {
			b.setIsSystem(false);
			saved = this.boxRepository.save(b);
		}
		return saved;
	}

	public void delete(final Box b) {
		Assert.notNull(b);
		// The default folders cannot be deleted.
		Assert.isTrue(!b.getIsSystem());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == b
				.getActor().getId());
		List<Box> childboxes = new ArrayList<Box>(this.getchilds(b.getId()));
		List<Message> messages = new ArrayList<Message>(b.getMessages());

		for (int i = 0; i < childboxes.size(); i++) {
			this.delete(childboxes.get(i));
		}
		for (int j = 0; j < messages.size(); j++) {
			this.messageService.delete(messages.get(j), b.getId());
		}
		this.boxRepository.delete(b);

	}

	public Collection<Box> getchilds(int id) {
		return this.boxRepository.getchilds(id);
	}

	// Other methods

	public Collection<Box> generateDefaultFolders(Actor x) {

		final Collection<Box> systemboxes = new ArrayList<Box>();
		final String[] systemboxesnames = new String[4];
		systemboxesnames[0] = "In box";
		systemboxesnames[1] = "Out box";
		systemboxesnames[2] = "Trash box";
		systemboxesnames[3] = "Spam box";

		for (int i = 0; i < 4; i++) {
			final Box b = new Box();
			b.setMessages(new ArrayList<Message>());
			b.setActor(x);
			b.setName(systemboxesnames[i]);
			b.setIsSystem(true);
			b.setActor(x);
			Box saved = this.boxRepository.save(b);
			systemboxes.add(saved);
		}
		this.boxRepository.flush();
		this.actorService.flush();
		return systemboxes;
	}

	public Box getSystemBoxByName(final int id, final String name) {
		Assert.notNull(name);
		return this.boxRepository.getSystemBoxByName(id, name);
	}

	public Collection<Box> findBoxesByActor(int id) {
		Collection<Box> boxes = this.boxRepository.findByActor(id);
		return boxes;
	}

	public Collection<Box> findBoxesByPrincipal() {
		Collection<Box> boxes = this.boxRepository
				.findByActor(this.actorService.findByPrincipal().getId());
		return boxes;
	}

	public void flush() {
		this.boxRepository.flush();

	}

	public Collection<Box> findRootBoxesByPrincipal() {
		Collection<Box> boxes = this.boxRepository
				.findRootBoxesByActor(this.actorService.findByPrincipal()
						.getId());
		return boxes;
	}

}

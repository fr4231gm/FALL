
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository	noteRepository;
	
	@Autowired
	private ActorService					actorService;
	
	@Autowired
	private HandyWorkerService				handyworkerService;
	
	@Autowired
	private CustomerService					customerService;
	
	@Autowired
	private RefereeService				refereeService;

	// Simple CRUD methods ----------------------------------------------------

	public Note create() {
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Customer || principal instanceof Referee || principal instanceof HandyWorker);
		Note note = null;
		note = new Note();
		note.setMoment(new Date(System.currentTimeMillis() - 1000));
		
		return note;
	}

	public Collection<Note> findAll() {
		Collection<Note> result = new ArrayList<Note>(this.noteRepository.findAll());
		return result;
	}

	public Note findOne(final int id) {
		return this.noteRepository.findOne(id);
	}

	public Note save(final Note note) {
		Assert.notNull(note);
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Customer || principal instanceof Referee || principal instanceof HandyWorker);
		if(principal instanceof Customer){
			Assert.notNull(note.getCustomerComments());
			if(this.actorService.checkSpam(note.getCustomerComments())){
				Customer suspicious = (Customer) principal;
				suspicious.setIsSuspicious(true);
				this.customerService.save(suspicious);
			}
		}
		if(principal instanceof HandyWorker){
			Assert.notNull(note.getHandyworkerComments());
			if(this.actorService.checkSpam(note.getHandyworkerComments())){
				HandyWorker suspicious = (HandyWorker) principal;
				suspicious.setIsSuspicious(true);
				this.handyworkerService.saveHandyWorker(suspicious);
			}
		}
		if(principal instanceof Referee){
			Assert.notNull(note.getRefereeComments());
			if(this.actorService.checkSpam(note.getRefereeComments())){
				Referee suspicious = (Referee) principal;
				suspicious.setIsSuspicious(true);
				this.refereeService.save(suspicious);
			}
		}

		return this.noteRepository.save(note);
	}	

	public void delete(final int id) {
		this.noteRepository.delete(id);
	}
	
	public void flush() {
		this.noteRepository.flush();
	}
	

}

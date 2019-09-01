package services;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;


import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Service under test ------------------------------------------
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private ActorService actorService;
	
	
	// Tests -------------------------------------------------------
	
	@Test
	public void testSaveNoteCustomer() {
		Note note, saved;
		super.authenticate("customer1");
		Collection<Note> notes;
		note = noteService.create();
		note.setCustomerComments("este comentario no es spam");
		saved = noteService.save(note);
		notes = noteService.findAll();
		Assert.isTrue(!this.actorService.findByPrincipal().getIsSuspicious());
		Assert.isTrue(notes.contains(saved));
	}
	
	@Test
	public void testSaveNoteSuspiciousCustomer() {
		Note note, saved;
		super.authenticate("customer1");
		Collection<Note> notes;
		note = noteService.create();
		note.setCustomerComments("este comentario es spam porque contiene la palabra 'nigeria'");
		saved = noteService.save(note);
		notes = noteService.findAll();
		Assert.isTrue(this.actorService.findByPrincipal().getIsSuspicious());
		Assert.isTrue(notes.contains(saved));
	}
	
	@Test
	public void testSaveNoteReferee() {
		Note note, saved;
		super.authenticate("referee1");
		Collection<Note> notes;
		note = noteService.create();
		note.setRefereeComments("este comentario no es spam");
		saved = noteService.save(note);
		notes = noteService.findAll();
		Assert.isTrue(!this.actorService.findByPrincipal().getIsSuspicious());
		Assert.isTrue(notes.contains(saved));
	}
	
	@Test
	public void testSaveNoteSuspiciousReferee() {
		Note note, saved;
		super.authenticate("referee1");
		Collection<Note> notes;
		note = noteService.create();
		note.setRefereeComments("este comentario es spam porque contiene la palabra 'nigeria'");
		saved = noteService.save(note);
		notes = noteService.findAll();
		Assert.isTrue(this.actorService.findByPrincipal().getIsSuspicious());
		Assert.isTrue(notes.contains(saved));
	}

	@Test
    public void testDeleteNote() {
        final Note note, saved;
		super.authenticate("customer1");
		Collection<Note> notes;
		note = noteService.create();
		note.setCustomerComments("este comentario no es spam");
		saved = noteService.save(note);
		notes = noteService.findAll();
		Assert.isTrue(notes.contains(saved));
		noteService.delete(saved.getId());
		notes = noteService.findAll();
		Assert.isTrue(!notes.contains(saved));
	}
}

package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import utilities.AbstractTest;
import domain.Note;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ------------------------------------------
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private NoteService			noteService;

	// Tests -------------------------------------------------------

	@Test
	public void testCheckSpam() {
		//Para probar este método publicamos contenido calificado como spam autenticados como customer 1 y comprobamos si se convierte en sospechoso
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
	public void testSave() {
		final Actor actor, saved;
		final Collection<Actor> actors;
		actor = this.actorService.create();
		actor.setAddress("Pepito Grillo nº 20");
		actor.setEmail("micorreo@gmail.com");
		actor.setIsSuspicious(false);
		actor.setMiddleName("middleNameActor1");
		actor.setName("actor1");
		actor.setPhoneNumber("654654654");
		actor.setPhoto("https://www.google.es");
		actor.setSurname("SurnameActor1");
		actor.getUserAccount().setUsername("actor111");
		actor.getUserAccount().setPassword("actor111");
		saved = this.actorService.save(actor);
	}
	
}

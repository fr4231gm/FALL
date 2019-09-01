
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	MessageService		messageService;

	@Autowired
	ActorService		actorService;

	@Autowired
	UserAccountService	uaService;

	@Autowired
	BoxService			boxService;


	//Requirement: 19.3
	// An actor who is authenticated must be able to:
	// send a message

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear unmessage sin subject

	// Analysis of sentence coverage total: Covered 17.1% 126/737 total instructions
	// Analysis of sentence coverage save(): Covered 69.3% 70/101 total instructions
	// Analysis of sentence coverage createCopy(): Covered 100.0% 33/33 total instructions
	// Analysis of sentence coverage findSended(): Covered 100.0% 9/9 total instructions

	// Analysis of data coverage: 50%

	@Test
	public void CreateEditMessageTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 
			{
				"customer3", "Subject", "body", "tag", "administrator0", null
			}, //Crear un mmessage

			//TESTS NEGATIVOS:
			{
				"customer3", "", "body", "tag", "administrator0", ConstraintViolationException.class
			}, //Intentar crear un message sin cuerpo

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.CreateEditMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditMessageTemplate(final String username, final String subject, final String body, final String tags, final String recipient, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Creamos el Data y le setteamos sus atributos
			final Message message = this.messageService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setTags(tags);
			final List<Actor> recipients = new ArrayList<Actor>();
			recipients.add(this.actorService.findByUserAccount(this.uaService.findByUsername(username)));
			message.setRecipients(recipients);

			//Lo guardamos y hacemos flush
			final Message saved = this.messageService.save(message);
			final Actor principal = this.actorService.findByPrincipal();
			//Comprobamos que el actor ya no tiene ese mensaje
			Assert.isTrue(this.messageService.findSended(principal.getId()).contains(saved));

			//Hacemos log out para la siguiente iteraciï¿½n
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requirement: 19.3
	// An actor who is authenticated must be able to:
	// delete a message

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un mensaje que no es mio

	// Analysis of sentence coverage total: Covered 25% 184/737 total instructions
	// Analysis of sentence coverage delete(): Covered 66.2% 51/77 total instructions
	// Analysis of sentence coverage findOne(): Covered 93.9% 46/49 total instructions
	// Analysis of sentence coverage findReceived(): Covered 100.0% 9/9 total instructions
	// Analysis of sentence coverage save(): Covered 14.9% 15/101 total instructions
	// Analysis of sentence coverage move(): Covered 98.5% 65/66 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void DeleteMessageTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 

			{
				"provider4", "message10", "box122", null
			}, //Borrar mi message

			//TESTS NEGATIVOS:
			{
				"designer2", "message1", "box35", IllegalArgumentException.class
			}, //Intentar borrar un mensaje que no es mio

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.DeleteMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
	}

	protected void DeleteMessageTemplate(final String username, final String message, final String box, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el mensaje
			final Message m = this.messageService.findOne(this.getEntityId(message));
			this.messageService.delete(m, this.getEntityId(box));

			//Comprobamos que se ha mandado a la trashbox
			Assert.isTrue(this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "TrashBox").getMessages().contains(m));

			this.boxService.flush();
			this.messageService.flush();

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

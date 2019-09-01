
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
import forms.MessageForm;

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


	//Requierement:
	// 23 An actor who is authenticated must be able to:
	// 2. send a message

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear unmessage sin subject

	// Analysis of sentence coverage total: Covered 36.4% 144/252 total instructions
	// Analysis of sentence coverage saveMessage(): Covered 98.0% 99/101 total instructions
	// Analysis of sentence coverage createCopy(): Covered 100.0% 33/33 total instructions
	// Analysis of sentence coverage findSended(): Covered 100.0% 9/9 total instructions

	// Analysis of data coverage: 50%

	@Test
	public void CreateEditMessageTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 
			{
				"rookie3", "Subject", "body", "tag", "admin", null
			}, //Crear un mmessage

			//TESTS NEGATIVOS:
			{
				"rookie3", "", "body", "tag", "admin", ConstraintViolationException.class
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
			final MessageForm message = new MessageForm();
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

	//An actor authenticated as Rookie must be able to:
	// 23 An actor who is authenticated must be able to:
	// 2. delete a message

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un mensaje que no es mio

	// Analysis of sentence coverage total: Covered 18.4% 73/396 total instructions
	// Analysis of sentence coverage delete(): Covered 55.8% 29/52 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 32/32 total instructions
	// Analysis of sentence coverage findReceived(): Covered 100.0% 9/9 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void DeleteMessageTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 

			{
				"rookie1", "message3", null
			}, //Borrar mi message

			//TESTS NEGATIVOS:
			{
				"rookie2", "message3", IllegalArgumentException.class
			}, //Intentar borrar un mensaje que no es mio

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.DeleteMessageRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void DeleteMessageRedordTemplate(final String username, final String message, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el mensaje
			final Message m = this.messageService.findOne(this.getEntityId(message));

			this.messageService.delete(m);

			//Comprobamos que se ha borrado
			Assert.isTrue(!this.messageService.findReceived(this.actorService.findByPrincipal().getId()).contains(m));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

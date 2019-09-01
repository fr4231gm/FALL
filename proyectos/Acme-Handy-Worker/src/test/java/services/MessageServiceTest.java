
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private BoxService	boxService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		final Message message;
		final Actor sender;
		Box box;

		super.authenticate("customer1");

		message = this.messageService.create();
		sender = this.actorService.findByPrincipal();
		box = this.boxService.getSystemBoxByName(sender.getId(), "out box");

		Assert.notNull(message.getMoment());
		Assert.isTrue(message.getSender().equals(sender));
		Assert.isTrue(message.getBoxes().contains(box));

	}

	@Test
	public void testBroadCastMessage() {
		int numMessagesBefore, numMessagesAfter;
		Message message;
		super.authenticate("admin");
		numMessagesBefore = this.messageService.findAll().size();
		message = this.messageService.create();
		message.setSubject("Test");
		message.setBody("Test");
		message.setPriority("NEUTRAL");
		this.messageService.broadcastMessage(message);
		numMessagesAfter = this.messageService.findAll().size();
		Assert.isTrue(numMessagesAfter == numMessagesBefore +1);
	}
	
	}

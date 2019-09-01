
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
import domain.Application;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private MessageService		messageService;

	// Tests -------------------------------------------------------

	@Test
	public void testSaveApplication() {
		final Application a, saved;
		final Collection<Application> all;
		final int nmessagesbefore, nmessagesafter;
		nmessagesbefore = this.messageService.findAll().size();
		FixUpTask f = fixUpTaskService.findAll().iterator().next();
		super.authenticate("handyworker1");
		a = this.applicationService.create();
		a.setPrice(100.0);
		a.setComments("Soy un comentario.");
		a.setFixUpTask(f);
		saved = this.applicationService.save(a);
		this.applicationService.flush();
		all = this.applicationService.findAll();
		Assert.isTrue(all.contains(saved));
		Application s = saved;
		s.setStatus("REJECTED");
		this.applicationService.notify(s.getFixUpTask().getCustomer(), s.getHandyWorker());
		this.applicationService.save(s);
		nmessagesafter = this.messageService.findAll().size();
		Assert.isTrue(nmessagesafter == nmessagesbefore + 1);
	}
}

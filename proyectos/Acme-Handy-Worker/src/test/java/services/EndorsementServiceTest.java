
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
import domain.Customer;
import domain.Endorsement;
import domain.Endorser;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Tests -------------------------------------------------------

	@Test
	public void testSave() {

		final Endorsement e, saved;
		final Collection<Endorsement> ee;
		super.authenticate("customer1");
		final Customer c = this.customerService.findByPrincipal();
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();

		e = this.endorsementService.create();
		e.setComments("jejeje");
		e.setCreator(c);
		e.setEndorsed((Endorser)h);

		saved = this.endorsementService.save(e);
		ee = this.endorsementService.findAll();

		Assert.isTrue(ee.contains(saved));

	}

	@Test
	public void testDelete() {

		final Endorsement e, saved;
		Collection<Endorsement> ee;
		super.authenticate("customer1");
		final Customer c = this.customerService.findByPrincipal();
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();

		e = this.endorsementService.create();
		e.setComments("jejeje");
		e.setCreator(c);
		e.setEndorsed(h);

		saved = this.endorsementService.save(e);
		ee = this.endorsementService.findAll();
		this.endorsementService.delete(saved);
		ee = this.endorsementService.findAll();

		Assert.isTrue(!ee.contains(saved));

	}

}

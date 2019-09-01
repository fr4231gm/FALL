
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
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyServiceWorkerTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveHandyWorker() {
		final HandyWorker handyWorker, saved;
		final Collection<HandyWorker> all;
		handyWorker = this.handyWorkerService.create();
		handyWorker.setName("Alberto");
		handyWorker.setSurname("Toledo");
		handyWorker.setEmail("albtolmay@alum.us.es");
		handyWorker.setMake("Alberto");
		handyWorker.getUserAccount().setUsername("handyworkertest");
		handyWorker.getUserAccount().setPassword("handyworkertest");
		saved = this.handyWorkerService.saveHandyWorker(handyWorker);
		all = this.handyWorkerService.findAll();
		Assert.isTrue(all.contains(saved));

	}

}

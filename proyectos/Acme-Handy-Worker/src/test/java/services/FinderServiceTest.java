
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private FinderService		finderService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveFinder() {
		final Finder f, saved;
		final Collection<Finder> all = new ArrayList<Finder>();
		super.authenticate("handyworker1");
		final HandyWorker principal = this.handyWorkerService.findByPrincipal();
		f = principal.getFinder();
		f.setKeyWord("nanithefrick");
		saved = this.finderService.saveFinder(f);
		all.addAll(this.finderService.findAll());
		Assert.isTrue(all.contains(saved));
	}

}

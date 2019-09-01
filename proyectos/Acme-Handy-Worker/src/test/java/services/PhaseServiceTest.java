
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.HandyWorker;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private PhaseService	phaseService;

	// Other Services ------------------------------------------
	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Tests -------------------------------------------------------

	@Test
	public void testSavePhase() {
		final Phase f, saved;
		Collection<Phase> all = new ArrayList<Phase>();
		super.authenticate("handyworker1");
		f = this.phaseService.create();
		f.setTitle("Título");
		f.setDescription("Descripción");
		//Fechas end posterior a start
		Application a = new Application();
		a.setStatus("ACCEPTED");
		HandyWorker principal = this.handyWorkerService.findByPrincipal();
		f.setApplication(principal.getApplications().iterator().next());

		final Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2019, 01, 01, 00, 00);
		Date startDate = new Date();
		startDate = startDateCalendar.getTime();
		f.setStart(startDate);

		final Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2019, 07, 01, 00, 00);
		Date endDate = new Date();
		endDate = endDateCalendar.getTime();
		f.setEnd(endDate);

		saved = this.phaseService.save(f);
		all = this.phaseService.findAll();
		Assert.isTrue(all.contains(saved));
	}

	@Test
	public void testDeletePhase() {
		final Collection<Phase> all = new ArrayList<Phase>();
		all.addAll(this.phaseService.findAll());
		final Phase f = all.iterator().next();
		this.phaseService.delete(f);
		final Collection<Phase> all2 = new ArrayList<Phase>();
		all2.addAll(this.phaseService.findAll());
		Assert.isTrue(!all2.contains(f));
	}

}

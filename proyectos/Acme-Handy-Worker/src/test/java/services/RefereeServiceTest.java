package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Service under test ------------------------------------------
	
	@Autowired
	private RefereeService refereeService;
	
	// Tests -------------------------------------------------------
	
	@Test
	public void testSaveReferee() {
		Referee referee, saved;
		Collection<Referee> referees;
		super.authenticate("admin");
		referee = refereeService.create();
		referee.setName("testName");
		referee.setEmail("test@mail.com");
		referee.setSurname("testSurname");
		saved = refereeService.save(referee);
		referees = refereeService.findAll();
		Assert.isTrue(referees.contains(saved));
	}
	
}
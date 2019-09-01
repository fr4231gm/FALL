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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Service under test ------------------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	// Tests -------------------------------------------------------
	
	@Test
	public void testSaveAdministrator() {
		Administrator administrator, saved;
		Collection<Administrator> administrators;
		super.authenticate("admin");
		administrator = administratorService.create();
		administrator.setName("testName");
		administrator.setEmail("test@mail.com");
		administrator.setSurname("testSurname");
		saved = administratorService.save(administrator);
		administrators = administratorService.findAll();
		Assert.isTrue(administrators.contains(saved));
	}
	
}
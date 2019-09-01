
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
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveConfiguration() {
		final Configuration configuration, saved;
		final Collection<Configuration> configurations;
		super.authenticate("admin");
		configuration = this.configurationService.create();
		//... Initialise the Configuration...
		configuration.setBanner("https://www.google.es");
		configuration.setDefaultBrands("Visa /n MasterCard");
		configuration.setFinderLifeSpan(8);
		configuration.setMaxFinder(25);
		configuration.setPNDefaultCountry("+34");
		configuration.setPositiveWords("hola /n adios");
		configuration.setNegativeWords("hola /n adios");
		configuration.setSpamWords("spam1 /n spam2");
		configuration.setSystemName("Acme-test");
		configuration.setVAT(0.21);
		configuration.setWelcomeMessage("welcome Jhon Doe");
		saved = this.configurationService.save(configuration);
		configurations = this.configurationService.findAll();
		Assert.isTrue(configurations.contains(saved));
	}
	
	@Test
	public void testDeleteConfiguration() {
		final Configuration configuration, saved;
		final Collection<Configuration> configurations;
		super.authenticate("admin");
		configuration = this.configurationService.create();
		//... Initialise the Configuration...
		configuration.setBanner("https://www.google.es");
		configuration.setDefaultBrands("Visa /n MasterCard");
		configuration.setFinderLifeSpan(8);
		configuration.setMaxFinder(25);
		configuration.setPNDefaultCountry("+34");
		configuration.setPositiveWords("hola /n adios");
		configuration.setNegativeWords("hola /n adios");
		configuration.setSpamWords("spam1 /n spam2");
		configuration.setSystemName("Acme-test");
		configuration.setVAT(0.21);
		configuration.setWelcomeMessage("welcome Jhon Doe");
		saved = this.configurationService.save(configuration);
		this.configurationService.delete(saved);
		configurations = this.configurationService.findAll();
		Assert.isTrue(!configurations.contains(saved));
	}

}

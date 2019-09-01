
package services;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private SectionService	sectionService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveSection() {
		final Section section, saved;
		final Collection<Section> sections;

		section = this.sectionService.create();

		//... Initialise the Section...
		section.setIndice(1);
		section.setPictureUrl(Arrays.asList("http://picture1.com", "http://picture2.com"));
		section.setText("El texto se ha inicializado");
		section.setTitle("Titulo para section");

		saved = this.sectionService.save(section);
		sections = this.sectionService.findAll();
		Assert.isTrue(sections.contains(saved));
	}

	//	public void testFindByTutorial(){
	//		final Collection<Section> sections;
	//		
	//		sections = this.sectionService.findAll();
	//		
	//	}

}

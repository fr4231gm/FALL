
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private PersonalRecordService	personalRecordService;

	// Tests ----------------------------------------------

	@Test
	public void testSave() {
		super.authenticate("handyWorker1");
		Curriculum curriculum, saved, curriculumRetrieved;
		PersonalRecord pr = this.personalRecordService.create();
		pr.setEmail("testing@mail.com");
		pr.setFullName("Testing Name");
		pr.setLinkedInURL("httpe://www.google.es");
		pr.setPhoto("https://www.google.es");
		PersonalRecord savedpr = this.personalRecordService.save(pr);
		HandyWorker principal = this.handyWorkerService.findByPrincipal();
		curriculum = principal.getCurriculum();
		curriculum.setPersonalRecord(savedpr);
		saved = this.curriculumService.save(curriculum);
		curriculumRetrieved = this.curriculumService.findOne(saved.getId());
		Assert.notNull(curriculumRetrieved);
	}

}

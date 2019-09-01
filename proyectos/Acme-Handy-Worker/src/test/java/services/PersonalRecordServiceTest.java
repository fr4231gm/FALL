
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
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
public class PersonalRecordServiceTest extends AbstractTest {

	// Service under test ---------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;
	
	@Autowired
	private CurriculumService		curriculumService;


	// Tests ----------------------------------------------------

	@Test
	public void TestCreate() {
		PersonalRecord personalRecord = null;

		super.authenticate("handyWorker1");
		personalRecord = this.personalRecordService.create();

		Assert.notNull(personalRecord);

	}

	@Test
	public void TestSave() {
		HandyWorker principal;
		PersonalRecord personalRecord, saved;
		Curriculum c;

		super.authenticate("handyWorker1");
		principal = this.handyWorkerService.findByPrincipal();
		personalRecord = this.personalRecordService.create();
		personalRecord.setEmail("test@gmail.com");
		personalRecord.setFullName("Test save");
		personalRecord.setLinkedInURL("https://www.link.com");
		personalRecord.setPhoto("http://www.photo.com");
		saved = this.personalRecordService.save(personalRecord);
		c = principal.getCurriculum();
		c.setPersonalRecord(saved);
		this.curriculumService.save(c);
		Assert.isTrue(principal.getCurriculum().getPersonalRecord().equals(saved));

	}

	@Test
	public void TestDelete() {
		HandyWorker handyWorker1;
		PersonalRecord personalRecord, saved ;

		super.authenticate("handyWorker1");
		handyWorker1 = this.handyWorkerService.findByPrincipal();
		personalRecord = this.personalRecordService.create();
		personalRecord.setEmail("test@gmail.com");
		personalRecord.setFullName("Test save");
		personalRecord.setLinkedInURL("https://www.link.com");
		personalRecord.setPhoto("http://www.photo.com");
		saved = this.personalRecordService.save(personalRecord);
		this.personalRecordService.delete(saved);
		Assert.isNull(handyWorker1.getCurriculum().getPersonalRecord());

	}

}

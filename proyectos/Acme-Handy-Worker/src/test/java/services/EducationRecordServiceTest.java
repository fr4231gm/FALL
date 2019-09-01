
package services;

import java.util.Collection;
import java.util.Date;

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
import domain.EducationRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// Service under test ---------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;
	
	@Autowired
	private CurriculumService		curriculumService;


	// Tests ----------------------------------------------------

	@Test
	public void TestCreate() {
		EducationRecord educationRecord = null;

		super.authenticate("handyWorker1");
		educationRecord = this.educationRecordService.create();

		Assert.notNull(educationRecord);

	}

	@Test
	public void TestSave() {
		HandyWorker principal;
		EducationRecord educationRecord, saved;
		Curriculum c;

		super.authenticate("handyWorker1");
		principal = this.handyWorkerService.findByPrincipal();
		educationRecord = this.educationRecordService.create();
		educationRecord.setDiplomaTitle("diplomatest");
		educationRecord.setInstitution("Test");
		educationRecord.setComments("Test");
		educationRecord.setAttachmentLink("https://www.link.com");
		educationRecord.setStartStudying(new Date(System.currentTimeMillis() - 1));
		saved = this.educationRecordService.save(educationRecord);
		c = principal.getCurriculum();
		Collection<EducationRecord> all = c.getEducationRecords();
		all.add(saved);
		c.setEducationRecords(all);
		this.curriculumService.save(c);
		Assert.isTrue(principal.getCurriculum().getEducationRecords().contains(saved));

	}

	@Test
	public void TestDelete() {
		HandyWorker handyWorker1;
		EducationRecord educationRecord, saved ;
		super.authenticate("handyWorker1");
		handyWorker1 = this.handyWorkerService.findByPrincipal();
		educationRecord = this.educationRecordService.create();
		educationRecord.setDiplomaTitle("diplomatest");
		educationRecord.setInstitution("Test");
		educationRecord.setComments("Test");
		educationRecord.setAttachmentLink("https://www.link.com");
		educationRecord.setStartStudying(new Date(System.currentTimeMillis() - 1));
		saved = this.educationRecordService.save(educationRecord);
		this.educationRecordService.delete(saved);
		Assert.isTrue(!handyWorker1.getCurriculum().getEducationRecords().contains(saved));

	}

}


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
import domain.ProfessionalRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// Service under test ---------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;
	
	@Autowired
	private CurriculumService		curriculumService;


	// Tests ----------------------------------------------------

	@Test
	public void TestCreate() {
		ProfessionalRecord professionalRecord = null;

		super.authenticate("handyWorker1");
		professionalRecord = this.professionalRecordService.create();

		Assert.notNull(professionalRecord);

	}

	@Test
	public void TestSave() {
		HandyWorker principal;
		ProfessionalRecord professionalRecord, saved;
		Curriculum c;

		super.authenticate("handyWorker1");
		principal = this.handyWorkerService.findByPrincipal();
		professionalRecord = this.professionalRecordService.create();
		professionalRecord.setCompanyName("test");
		professionalRecord.setComments("Test");
		professionalRecord.setRole("Tester");
		professionalRecord.setAttachmentLink("https://www.link.com");
		professionalRecord.setStartWorking(new Date(System.currentTimeMillis() - 1));
		saved = this.professionalRecordService.save(professionalRecord);
		c = principal.getCurriculum();
		Collection<ProfessionalRecord> all = c.getProfessionalRecords();
		all.add(saved);
		c.setProfessionalRecords(all);
		this.curriculumService.save(c);
		Assert.isTrue(principal.getCurriculum().getProfessionalRecords().contains(saved));

	}

	@Test
	public void TestDelete() {
		HandyWorker handyWorker1;
		ProfessionalRecord professionalRecord, saved ;
		super.authenticate("handyWorker1");
		handyWorker1 = this.handyWorkerService.findByPrincipal();
		professionalRecord = this.professionalRecordService.create();
		professionalRecord.setCompanyName("test");
		professionalRecord.setComments("Test");
		professionalRecord.setRole("Tester");
		professionalRecord.setAttachmentLink("https://www.link.com");
		professionalRecord.setStartWorking(new Date(System.currentTimeMillis() - 1));
		saved = this.professionalRecordService.save(professionalRecord);
		this.professionalRecordService.delete(saved);
		Assert.isTrue(!handyWorker1.getCurriculum().getProfessionalRecords().contains(saved));

	}

}

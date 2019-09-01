
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
import domain.Curriculum;
import domain.HandyWorker;
import domain.EndorserRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	// Service under test ---------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;
	
	@Autowired
	private CurriculumService		curriculumService;


	// Tests ----------------------------------------------------

	@Test
	public void TestCreate() {
		EndorserRecord endorserRecord = null;

		super.authenticate("handyWorker1");
		endorserRecord = this.endorserRecordService.create();

		Assert.notNull(endorserRecord);

	}

	@Test
	public void TestSave() {
		HandyWorker principal;
		EndorserRecord endorserRecord, saved;
		Curriculum c;

		super.authenticate("handyWorker1");
		principal = this.handyWorkerService.findByPrincipal();
		endorserRecord = this.endorserRecordService.create();
		endorserRecord.setEmail("test@mail.com");
		endorserRecord.setFullName("Test");
		endorserRecord.setComments("Test");
		endorserRecord.setLinkedInUrl("https://www.link.com");
		saved = this.endorserRecordService.save(endorserRecord);
		c = principal.getCurriculum();
		Collection<EndorserRecord> all = c.getEndorserRecords();
		all.add(saved);
		c.setEndorserRecords(all);
		this.curriculumService.save(c);
		Assert.isTrue(principal.getCurriculum().getEndorserRecords().contains(saved));

	}

	@Test
	public void TestDelete() {
		HandyWorker handyWorker1;
		EndorserRecord endorserRecord, saved ;
		super.authenticate("handyWorker1");
		handyWorker1 = this.handyWorkerService.findByPrincipal();
		endorserRecord = this.endorserRecordService.create();
		endorserRecord.setEmail("test@mail.com");
		endorserRecord.setFullName("Test");
		endorserRecord.setComments("Test");
		endorserRecord.setLinkedInUrl("https://www.link.com");
		saved = this.endorserRecordService.save(endorserRecord);
		this.endorserRecordService.delete(saved);
		Assert.isTrue(!handyWorker1.getCurriculum().getEndorserRecords().contains(saved));
	}

}

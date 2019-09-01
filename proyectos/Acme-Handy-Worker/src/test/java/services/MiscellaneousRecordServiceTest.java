
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
import domain.MiscellaneousRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// Service under test ---------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;
	
	@Autowired
	private CurriculumService		curriculumService;


	// Tests ----------------------------------------------------

	@Test
	public void TestCreate() {
		MiscellaneousRecord miscellaneousRecord = null;

		super.authenticate("handyWorker1");
		miscellaneousRecord = this.miscellaneousRecordService.create();

		Assert.notNull(miscellaneousRecord);

	}

	@Test
	public void TestSave() {
		HandyWorker principal;
		MiscellaneousRecord miscellaneousRecord, saved;
		Curriculum c;

		super.authenticate("handyWorker1");
		principal = this.handyWorkerService.findByPrincipal();
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("test");
		miscellaneousRecord.setComments("Test");
		miscellaneousRecord.setAttachmentLink("https://www.link.com");
		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
		c = principal.getCurriculum();
		Collection<MiscellaneousRecord> all = c.getMiscellaneousRecords();
		all.add(saved);
		c.setMiscellaneousRecords(all);
		this.curriculumService.save(c);
		Assert.isTrue(principal.getCurriculum().getMiscellaneousRecords().contains(saved));

	}

	@Test
	public void TestDelete() {
		HandyWorker handyWorker1;
		MiscellaneousRecord miscellaneousRecord, saved ;
		super.authenticate("handyWorker1");
		handyWorker1 = this.handyWorkerService.findByPrincipal();
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("test");
		miscellaneousRecord.setComments("Test");
		miscellaneousRecord.setAttachmentLink("https://www.link.com");
		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
		this.miscellaneousRecordService.delete(saved);
		Assert.isTrue(!handyWorker1.getCurriculum().getMiscellaneousRecords().contains(saved));

	}

}

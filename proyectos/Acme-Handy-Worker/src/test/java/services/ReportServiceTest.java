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
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
public class ReportServiceTest extends AbstractTest {

	// Service under test ------------------------------------------
	
	@Autowired
	private ReportService reportService;
	
	
	@Autowired
	private ComplaintService complaintService;
	
	// Tests -------------------------------------------------------
	
	@Test
	public void testSaveReport() {
		Report report, saved;
		Collection<Report> reports;
		super.authenticate("customer1");
		report = reportService.create();
		report.setComplaint(this.complaintService.findAllComplaints().iterator().next());
		report.setAttachments("some attachments to test");
		report.setDescription("this is only a test");
		saved = reportService.save(report);
		reports = reportService.findAll();
		Assert.isTrue(reports.contains(saved));
	}
	
	@Test
    public void testDeleteTutorial() {
        Report report, saved;
		Collection<Report> reports;
		super.authenticate("customer1");
		report = reportService.create();
		report.setComplaint(this.complaintService.findAllComplaints().iterator().next());
		report.setAttachments("some attachments to test");
		report.setDescription("this is only a test");
		saved = reportService.save(report);
		reports = reportService.findAll();
		Assert.isTrue(reports.contains(saved));
        this.reportService.delete(saved.getId());
        Assert.isNull(this.reportService.findOne(report.getId()));
	}	

}
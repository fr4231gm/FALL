
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
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private CustomerService		customerServcie;
	
	@Autowired
	private FixUpTaskService	fixUpTaskServcie;

	@Test
	public void testSaveComplaint() {

		final Complaint complaint, saved;
		final Collection<Complaint> complaints;
		final Customer c;
		FixUpTask f;
		super.authenticate("customer1");
		c = this.customerServcie.findByPrincipal();
		f = c.getFixUpTasks().iterator().next();
		complaint = this.complaintService.create();
		complaint.setAttachments("Soyunmojino");
		complaint.setDescription("La vida se me va");

		complaint.setFixUpTask(f);
		Collection<Complaint> x = f.getComplaints();
		x.add(complaint);
		f.setComplaints(x);
		this.fixUpTaskServcie.saveFixUpTask(f);
		saved = this.complaintService.saveComplaint(complaint);
		complaints = this.complaintService.findAllComplaints();
		Assert.isTrue(complaints.contains(saved));
	}

}

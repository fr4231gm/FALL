
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;
	
	@Autowired
	private CategoryService		categoryService;
	
	@Autowired
	private WarrantyService		warrantyService;


	@Test
	public void Save() {
		final FixUpTask f, saved;
		Collection<FixUpTask> ff;
		super.authenticate("customer1");
		final Customer c = this.customerService.findByPrincipal();
		Category category = this.categoryService.findAll().iterator().next();
		Warranty warranty = this.warrantyService.findAllWarranties().iterator().next();
		f = this.fixUpTaskService.create();
		f.setAddress("casa de tu madre");
		f.setApplications(null);
		f.setCategory(category);
		
		f.setComplaints(null);
		f.setCustomer(c);
		f.setDescription("aiudaaaaaaaaa2");
		f.setEndDate(new Date(10 / 10 / 2100));
		f.setStartDate(new Date(10 / 10 / 2090));
		f.setWarranty(warranty);

		saved = this.fixUpTaskService.saveFixUpTask(f);
		ff = this.fixUpTaskService.findAll();

		Assert.isTrue(ff.contains(saved));
	}

	// TODO las fixUpTasks solo se borran si no tienen ni appliations ni complaints
/*	@Test
	public void delete() {
		final FixUpTask f = this.fixUpTaskService.findAll().iterator().next();
		Collection<FixUpTask> ff;
		this.fixUpTaskService.deleteFixUpTask(f);
		ff = this.fixUpTaskService.findAll();
		Assert.isTrue(!ff.contains(f));
	}*/

}

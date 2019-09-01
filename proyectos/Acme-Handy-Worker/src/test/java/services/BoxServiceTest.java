
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
import domain.Box;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	@Autowired
	private BoxService	boxService;
	
	@Autowired
	private CustomerService	customerService;


	// Tests -------------------------------------------------------
	@Test
	public void testSaveBox() {
		final Box parentbox, childbox, saved, saved2;
		Collection<Box> boxes;
		super.authenticate("customer1");
		parentbox = this.boxService.create();
		parentbox.setName("parentbox-test");
		saved = this.boxService.save(parentbox);
		boxes = this.boxService.findAll();
		Assert.isTrue(boxes.contains(saved));
		childbox = this.boxService.create();
		childbox.setName("childbox-test");
		childbox.setParentBox(saved);
		saved2 = this.boxService.save(childbox);
		boxes = this.boxService.findAll();
		Assert.isTrue(boxes.contains(saved2));
	}
	
	@Test
	public void testDeleteBox() {
		final Box parentbox, childbox, saved, saved2;
		Collection<Box> boxes;
		super.authenticate("customer1");
		parentbox = this.boxService.create();
		parentbox.setName("parentbox-test");
		saved = this.boxService.save(parentbox);
		childbox = this.boxService.create();
		childbox.setName("childbox-test");
		childbox.setParentBox(saved);
		saved2 = this.boxService.save(childbox);
		this.boxService.delete(saved);
		boxes = this.boxService.findAll();
		Assert.isTrue(!(boxes.contains(saved)&&(boxes.contains(saved2))));
	}
	
	@Test
	public void testDefaultFolders() {
		final Customer customer, saved;
		customer = this.customerService.create();
		customer.setName("Alberto");
		customer.setSurname("Toledo");
		customer.setEmail("albtolmay@alum.us.es");
		customer.getUserAccount().setUsername("customer111");
		customer.getUserAccount().setPassword("customer111");
		saved = this.customerService.save(customer);
		Assert.isTrue(this.boxService.findBoxesByActor(saved.getId()).size()==4);
	}

}


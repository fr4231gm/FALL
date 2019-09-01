
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
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService	customerService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveCustomer() {
		final Customer customer, saved;
		final Collection<Customer> all;
		customer = this.customerService.create();
		customer.setName("Alberto");
		customer.setSurname("Toledo");
		customer.setEmail("albtolmay@alum.us.es");
		saved = this.customerService.save(customer);
		all = this.customerService.findAll();
		Assert.isTrue(all.contains(saved));

	}

}


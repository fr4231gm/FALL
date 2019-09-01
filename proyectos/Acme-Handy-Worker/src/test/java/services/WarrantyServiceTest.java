
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private WarrantyService	warrantyService;

	// Tests -------------------------------------------------------

	@Test
	public void testSaveWarranty() {
		final Warranty warranty, saved;
		Collection<Warranty> warranties;
		super.authenticate("admin");
		warranties = this.warrantyService.findAll();
		warranty = this.warrantyService.create();
		//... Initialise the Warranty...
		warranty.setLaws("testing Warranty");
		warranty.setTitle("testing Warranty");
		warranty.setTerms("testing Warranty");
		saved = this.warrantyService.save(warranty);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(warranties.contains(saved));
	}

	@Test
	public void testDeleteWarranty() {
		final Warranty warranty, saved;
		Collection<Warranty> warranties;
		super.authenticate("admin");
		warranties = this.warrantyService.findAll();
		warranty = this.warrantyService.create();
		//... Initialise the Warranty...
		warranty.setLaws("testing Warranty");
		warranty.setTitle("testing Warranty");
		warranty.setTerms("testing Warranty");
		saved = this.warrantyService.save(warranty);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(warranties.contains(saved));
		this.warrantyService.delete(saved);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(!warranties.contains(saved));

	}

}

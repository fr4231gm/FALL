
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
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveCreditCard() {
		final CreditCard c, saved;
		final Collection<CreditCard> cc;

		c = this.creditCardService.create();
		c.setBrandName("VISA");
		c.setCVV(987);
		c.setExpirationMonth(04);
		c.setExpirationYear(21);
		c.setHolderName("Jesucristo");
		c.setNumber("5555555555554444");

		saved = this.creditCardService.save(c);
		cc = this.creditCardService.findAll();
		Assert.isTrue(cc.contains(saved));
	}

}

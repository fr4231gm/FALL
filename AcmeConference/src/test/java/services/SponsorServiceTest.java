/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import forms.SponsorForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SponsorService sponsorService;

	// Tests ------------------------------------------------------------------

	@Test
	public void registerSponsorTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "middleName", "surname",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "sponsorbalboa",
						"sponsorbalboa", 123, 1, 22, "jose",
						"4111111111111111", "AMEX", null },

				// TEST NEGATIVO:
				{ "name", "middleName", "surname",
						"asdfgsasdf", "123456789",
						"email@gmail.com", "address", "sponsorbalboa",
						"sponsorbalboa", 123, 1, 22, "jose",
						"4111111111111111", "AMEX",
						null },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerSponsorTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(Integer) testingData[i][9], (Integer) testingData[i][10],
					(Integer) testingData[i][11], (String) testingData[i][12],
					(String) testingData[i][13], (String) testingData[i][14],
					(Class<?>) testingData[i][15]);
			this.rollbackTransaction();
		}
	}

	protected void registerSponsorTemplate(final String name,
			String middleName, final String surname, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final String password, final Integer cVV,
			final Integer expirationMonth, final Integer expirationYear,
			final String holder, final String number, final String make,
			final Class<?> expected) {

		Class<?> caught;
		Sponsor saved;

		caught = null;

		try {
			super.authenticate(null);
			
			final Sponsor sponsor = this.sponsorService.create(username,
					password);
			final CreditCard c = new CreditCard();
			sponsor.setName(name);
			sponsor.setMiddleName(middleName);
			sponsor.setSurname(surname);
			sponsor.setAddress(address);
			sponsor.setEmail(email);
			sponsor.setPhoto(photo);
			sponsor.setPhoneNumber(phoneNumber);
			c.setCVV(cVV);
			c.setExpirationMonth(expirationMonth);
			c.setExpirationYear(expirationYear);
			c.setHolder(holder);
			c.setNumber(number);
			c.setMake(make);

			saved = this.sponsorService.save(sponsor);

			Assert.isTrue(saved.getId() != 0);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

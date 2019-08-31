package services;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Reviewer;


@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReviewerServiceTest extends AbstractTest {


	@Autowired
	private ReviewerService 	reviewerService;


	@Test
	public void createEditReviewerTestDrive() {
		final Object testingData[][] = {
		//atributo0: String(name)  atributo1: String(middleName)  atributo2: String(surname)  atributo3: String(photo)  atributo4: String(email)  atributo5: String(address)  atributo7: String(phoneNumber) atributo8: String(keywords)  atributo9: String(userName) atributo10: String(password)  
				// TEST POSITIVO:
				{"name test", "", "surname test", "", "email@mail.com", "", "", "test, words, buzz", "testUserName", "testPassword", null },
				// TEST NEGATIVO 1: Nombre en blanco:
				{"", "", "surname test", "", "email@mail.com", "", "", "test, words, buzz", "testUserName", "testPassword", ConstraintViolationException.class },
				// TEST NEGATIVO 2: Apellido en blanco:
				{"name test", "", "", "", "email@mail.com", "", "", "test, words, buzz", "testUserName", "testPassword", ConstraintViolationException.class },
				// TEST NEGATIVO 3: Email en blanco:
				{"name test", "", "surname test", "", "", "", "", "test, words, buzz", "reviewer1", "testPassword",DataIntegrityViolationException.class },
				// TEST NEGATIVO 4: Nombre de usuario existente:
				{"name test", "", "surname test", "", "email@mail.com", "", "", "test, words, buzz", "reviewer1", "testPassword", DataIntegrityViolationException.class },
				// TEST NEGATIVO 5: introducir código html en el nombre:
				{"<script>alert('hello world')</script>", "", "surname test", "", "email@mail.com", "", "", "test, words, buzz", "testUserName", "testPassWord", ConstraintViolationException.class },
		};
		
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.CreateEditReviewerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
			this.rollbackTransaction();
		}
	}
	
	protected void CreateEditReviewerTemplate(final String name,  final String middleName,  final String surname,  final String photo,  final String email,  final String address, final String phoneNumber,  final String keywords, final String userName, final String password, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Reviewer reviewer = this.reviewerService.create(userName, password);

			reviewer.setName(name);
			reviewer.setMiddleName(middleName);
			reviewer.setSurname(surname);
			reviewer.setAddress(address);
			reviewer.setEmail(email);
			reviewer.setPhoto(photo);
			reviewer.setKeywords(keywords);
			reviewer.setPhoneNumber(phoneNumber);

			this.reviewerService.save(reviewer);

			this.reviewerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}





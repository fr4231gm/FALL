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
import domain.Author;


@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuthorServiceTest extends AbstractTest {


	@Autowired
	private AuthorService 	authorService;


	@Test
	public void createEditAuthorTestDrive() {
		final Object testingData[][] = {
		//atributo0: String(name)  atributo1: String(middleName)  atributo2: String(surname)  atributo3: String(photo)  atributo4: String(email)  atributo5: String(address)  atributo7: String(phoneNumber)  atributo8: String(userName) atributo9: String(password)  
				// TEST POSITIVO:
				{"name test", "", "surname test", "", "email@mail.com", "", "", "testUserName", "testPassword", null },
				// TEST NEGATIVO 1: Nombre en blanco:
				{"", "", "surname test", "", "email@mail.com", "", "", "testUserName", "testPassword", ConstraintViolationException.class },
				// TEST NEGATIVO 2: Apellido en blanco:
				{"name test", "", "", "", "email@mail.com", "", "", "testUserName", "testPassword", ConstraintViolationException.class },
				// TEST NEGATIVO 3: Email en blanco:
				{"name test", "", "surname test", "", "", "", "", "author1", "testPassword",DataIntegrityViolationException.class },
				// TEST NEGATIVO 4: Nombre de usuario existente:
				{"name test", "", "surname test", "", "email@mail.com", "", "", "author1", "testPassword", DataIntegrityViolationException.class },
				// TEST NEGATIVO 5: introducir código html en el nombre:
				{"<script>alert('hello world')</script>", "", "surname test", "", "email@mail.com", "", "", "testUserName", "testPassWord", ConstraintViolationException.class },
		};
		
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.CreateEditAuthorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8],  (Class<?>) testingData[i][9]);
			this.rollbackTransaction();
		}
	}
	
	protected void CreateEditAuthorTemplate(final String name,  final String middleName,  final String surname,  final String photo,  final String email,  final String address, final String phoneNumber,  final String userName, final String password, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Author author = this.authorService.create(userName, password);

			author.setName(name);
			author.setMiddleName(middleName);
			author.setSurname(surname);
			author.setAddress(address);
			author.setEmail(email);
			author.setPhoto(photo);
			author.setPhoneNumber(phoneNumber);

			this.authorService.save(author);

			this.authorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}





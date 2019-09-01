package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Area;
import domain.Chapter;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChapterServiceTest extends AbstractTest {

	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private AreaService areaService;
	
	//Requierement: 7. An actor who is not authenticated must be able to:
	//1. Register to the system as a chapter.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar registrarse con el title vacio
	//Intentar registrarse con el name vacio
	//Intentar registrarse con el email vacio
	//Intentar registrarse con el surname vacio
	// Intenta registrarse con el title a null
		
	//Analysis of sentence coverage: Covered 100%
		
	//Analysis of data coverage: 370 /370 total instructions

	@Test
	public void registerChapterTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "title", "address", "email@gmail.com", "http://www.google.es/photo.jpg",
						"123456789", "middleName", "name", "surname",
						"Chapter8", "Chapter8", null }, // Registrarse
														// correctamente

				// TEST NEGATIVO:
				 {"","address","email@gmail.com","http://www.google.es/photo.jpg",
				 "123456789","middleName","name","surname",
				 "Chapter8","Chapter8", ConstraintViolationException.class},
				 //Intentar registrarse con el title vacio

				// TEST NEGATIVO:
				 {"title","address","email@gmail.com","http://www.google.es/photo.jpg",
				 "123456789","middleName","","surname",
				 "Chapter8","Chapter8", ConstraintViolationException.class},
				 //Intentar registrarse con el name vacio

				// TEST NEGATIVO:
				 {"title","address","","http://www.google.es/photo.jpg",
				 "123456789","middleName","name","surname",
				 "Chapter8","Chapter8", ConstraintViolationException.class},
				 //Intentar registrarse con el email vacio

				// TEST NEGATIVO:
				 {"title","address","email@gmail.com","http://www.google.es/photo.jpg",
				 "123456789","middleName","name","",
				 "Chapter8","Chapter8", ConstraintViolationException.class},
				 //Intentar registrarse con el surname vacio

				// TEST NEGATIVO:
				{ null, "address", "email@gmail.com", "http://www.google.es/photo.jpg", "123456789",
						"middleName", "name", "surname", "Chapter8",
						"Chapter8", ConstraintViolationException.class } 
				 // Intenta registrarse con el title a null
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerChapterTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (Class<?>) testingData[i][10]);
			this.rollbackTransaction();
		}
	}

	protected void registerChapterTemplate(final String title,
			final String address, final String email, final String photo,
			final String phoneNumber, final String middleName,
			final String name, final String surname, final String username,
			final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			Chapter chapter = this.chapterService.create(username, password);
			chapter.setTitle(title);
			chapter.setAddress(address);
			chapter.setEmail(email);
			chapter.setPhoto(photo);
			chapter.setPhoneNumber(phoneNumber);
			chapter.setMiddleName(middleName);
			chapter.setName(name);
			chapter.setSurname(surname);

			this.chapterService.save(chapter);
			
			this.chapterService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-14.1 An actor who is not authenticated must be able to: 
	//List the chapters that are registered in the system

	//In the case of negative tests, the business rule that is intended to be broken: No procede
			
	//Analysis of sentence coverage: Covered 100%
			
	//Analysis of data coverage: 29 /29 total instructions
	
	@Test
	public void listChaptersTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ null } // Listar los chapters
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listChaptersTemplate((Class<?>) testingData[i][0]);
			this.rollbackTransaction();
		}
	}

	protected void listChaptersTemplate(final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente

			this.chapterService.findAll();

			this.chapterService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-2.1 An actor who is authenticated as a chapter must be able to:
	//Self-assign an area to co-ordinate

	//In the case of negative tests, the business rule that is intended to be broken:
	//Intentar autoasignarse un Area siendo una Brotherhood
				
	//Analysis of sentence coverage: Covered 100%
				
	//Analysis of data coverage: 80 / 80 total instructions	
	
	@Test
		public void selfassignAreaTestDriver() {

			final Object testingData[][] = {

			// TEST POSITIVO:
			{super.getEntityId("chapter1"), super.getEntityId("area1"), null}, // Autoasiganarse un area correctamente
			
			//TEST NEGATIVO:
			{super.getEntityId("brotherhood1"), super.getEntityId("area1"), IllegalArgumentException.class} //Intentar autoasignarse
																											//un Area siendo una Brotherhood
			};

			for (int i = 0; i < testingData.length; i++) {
				this.startTransaction();
				this.selfassignAreaTemplate((int) testingData[i][0], (int) testingData[i][1],(Class<?>) testingData[i][2]);
				this.rollbackTransaction();
			}
		}

		protected void selfassignAreaTemplate(
				final int chapterId, final int areaId, 
				final Class<?> expected) {
			Class<?> caught;

			caught = null;

			try {
				// Nos autenticamos como el usuario correspondiente
				super.authenticate(this.chapterService.findOne(chapterId).getUserAccount().getUsername());

				Area area = this.areaService.findOne(areaId);
				
				area.setChapter(this.chapterService.findOne(chapterId));
				
				this.areaService.save(area);

				this.chapterService.flush();

				unauthenticate();
				
			} catch (final Throwable oops) {
				caught = oops.getClass();
			}

			this.checkExceptions(expected, caught);
		}

}

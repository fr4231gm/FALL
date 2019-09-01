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
import domain.Curricula;
import domain.PersonalData;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	CurriculaService curriculaService;

	@Autowired
	PersonalDataService personalDataService;

	@Autowired
	HackerService hackerService;
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the curricula of every hacker that he or she can display.
	//3. An actor who is authenticated as a hacker must be able to:
	//1. Manage their curricula, which includes listing, displaying, creating and updating its datas.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un curricula si ya tiene 1 creado
	//Intentar crear un curricula como company
	//Intentar crear un curricula como admin
	//Intentar crear un curricula como sponsor
	//Intentar crear un curricula como chapter
	//Intentar crear un Curricula cuyo personal Data no tenga descripción
	//Intentar crear un Curricula cuyo personal Data no tenga título
	
	// Analysis of sentence coverage total: Covered 20.3% 78/385 total instructions
	// Analysis of sentence coverage create(): Covered 100% 15/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findCopiedById(): Covered 100% 7/7 total instructions
	// Analysis of sentence coverage save(): Covered 100% 37/37 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 40%
	
	@Test
	public void CreateEditCurriculaTestDriver() {
		// UC19-Crear y editar curricula
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "hacker3", "fullName1", "statement1", "662130562", "https://www.github.com", "https://www.linkedin.com", null },// Crear un Curricula

				// TESTS NEGATIVOS:
				
				{ "company1","fullName", "statement", "662130562", "https://www.github.com", "https://www.linkedin.com", IllegalArgumentException.class }, 	// Intentar crear
																							// un curricula
																							// como company
				
				{ "admin","fullName", "statement", "662130562", "https://www.github.com", "https://www.linkedin.com", IllegalArgumentException.class },		// Intentar crear
																							// un curricula
																							// como admin

				
				{ "hacker3", "fullName", "", "662130562", "https://www.github.com", "https://www.linkedin.com", ConstraintViolationException.class },		// Intentar 
																							//Crear un Curricula
				 																			// cuyo personal Data no tenga statement
				
				
				{ "hacker3", "", "statement", "662130562", "https://www.github.com", "https://www.linkedin.com", ConstraintViolationException.class } 	// Intentar 
																	 					  	//Crear un Curricula
																	 					  	// cuyo personal Data no tenga título
				
		};

		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
		
			this.CreateCurriculaTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],(String) testingData[i][4],(String) testingData[i][5],
					(Class<?>) testingData[i][6]);
			this.rollbackTransaction();
		}
	}

	protected void CreateCurriculaTemplate(final String username, final String fullName, final String statement, final String phoneNumber,  final String gitHubLink,  final String linkedInLink,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Curricula curricula = this.curriculaService.create();
			PersonalData personalData = this.personalDataService
					.create();

			personalData.setFullName(fullName);
			personalData.setStatement(statement);
			personalData.setGitHubLink(gitHubLink);
			personalData.setLinkedInLink(linkedInLink);
			personalData.setPhoneNumber(phoneNumber);

			curricula.setPersonalData(personalData);
			
			//UPDATING
			Curricula saved = this.curriculaService.save(curricula);
			this.curriculaService.flush();
			this.personalDataService.flush();
			
			saved.getPersonalData().setStatement("changedStatement");
			
			this.curriculaService.save(saved);
			this.curriculaService.flush();
			
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement:
	// An actor who is authenticated as a hacker must be able to:
	// Delete their curricula

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un curricula que no es del actor logeado
	//Intentar borrar un curricula como company
	
	// Analysis of sentence coverage total: Covered 7.5% 29/385 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage delete(): Covered 18.5% 5/27 total instructions	
	// Analysis of data coverage: 100%

	
	@Test
	public void DeleteCurriculaTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:

	

		// TESTS NEGATIVOS:
		 {"hacker1", "curricula2", IllegalArgumentException.class}, // Intentar borrar un curricula que no es mio

		{"admin", "curricula1",IllegalArgumentException.class}, //Intentar borrar un curricula como admin
		 
		{ "hacker3", "curricula7", DataIntegrityViolationException.class}, // Borrar un curricula copia

		};

		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.DeleteCurriculaTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
	}}

	protected void DeleteCurriculaTemplate(final String username, final String curriculaId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		
		try {
			super.authenticate(username);
			this.curriculaService.delete(this.curriculaService.findOne(this.getEntityId(curriculaId)));
			this.curriculaService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

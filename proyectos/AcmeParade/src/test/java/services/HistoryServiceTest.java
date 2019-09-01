package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.History;
import domain.InceptionRecord;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HistoryServiceTest extends AbstractTest {

	@Autowired
	HistoryService historyService;

	@Autowired
	InceptionRecordService inceptionRecordService;

	@Autowired
	BrotherhoodService brotherhoodService;
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the history of every brotherhood that he or she can display.
	//3. An actor who is authenticated as a brotherhood must be able to:
	//1. Manage their history, which includes listing, displaying, creating and updating its records.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un history si ya tiene 1 creado
	//Intentar crear un history como member
	//Intentar crear un history como admin
	//Intentar crear un history como sponsor
	//Intentar crear un history como chapter
	//Intentar crear un History cuyo inception Record no tenga descripción
	//Intentar crear un History cuyo inception Record no tenga título
	
	//Analysis of sentence coverage: Covered 100%
			
	//Analysis of data coverage: 244 / 244 total instructions
	
	@Test
	public void CreateEditHistoryTestDriver() {
		// UC19-Crear y editar history
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "brotherhood3", "title1", "description1", "", null },// Crear un History

				// TESTS NEGATIVOS:
				{ "brotherhood1","title", "description", "", IllegalArgumentException.class },  // Intentar
																								// crear un
																								// history
																								// si ya
																								// tiene 1
																								// creado

				{ "member1","title", "description", "", IllegalArgumentException.class }, 	// Intentar crear
																							// un history
																							// como member
				
				{ "admin","title", "description", "", IllegalArgumentException.class },		// Intentar crear
																							// un history
																							// como admin
				
				{ "sponsor1","title", "description", "", IllegalArgumentException.class },  // Intentar crear
																							// un history
																							// como sponsor
				
				{ "chapter1","title", "description", "", IllegalArgumentException.class },  // Intentar crear
																							// un history
																							// como chapter
				
				{ "brotherhood3", "title", "", "", ConstraintViolationException.class },		// Intentar 
																							//Crear un History
				 																			// cuyo inception Record no tenga descripción
				
				
				{ "brotherhood3", "", "description", "", ConstraintViolationException.class } 	// Intentar 
																	 					  	//Crear un History
																	 					  	// cuyo inception Record no tenga título
				
		};

		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
		
			this.CreateHistoryTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],
					(Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
	}

	protected void CreateHistoryTemplate(final String username, final String title, final String description, final String pictures,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			History history = this.historyService.create();
			InceptionRecord inceptionRecord = this.inceptionRecordService
					.create();

			inceptionRecord.setTitle(title);
			inceptionRecord.setDescription(description);
			inceptionRecord.setPictures(pictures);

			history.setInceptionRecord(inceptionRecord);
			
			//UPDATING
			History saved = this.historyService.save(history);
			this.historyService.flush();
			
			saved.getInceptionRecord().setDescription("chanfedDescription");
			
			this.historyService.save(saved);
			this.historyService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the history of every brotherhood that he or she can display.
	//3. An actor who is authenticated as a brotherhood must be able to:
	//1. Manage their history, which includes listing, displaying, creating and updating its records.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un history que no es del actor logeado
	//Intentar borrar un history como member
	
		
	//Analysis of sentence coverage: Covered 100%
				
	//Analysis of data coverage: 83 / 83 total instructions
	
	@Test
	public void DeleteHistoryTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:

		{ "brotherhood1", "history1", null }, // Borrar mi history

		// TESTS NEGATIVOS:
		 {"brotherhood1", "history2", IllegalArgumentException.class}, // Intentar borrar un history que no es mio

		{"admin", "history1",IllegalArgumentException.class} //Intentar borrar un history como member

		};

		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.DeleteHistoryTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
	}}

	protected void DeleteHistoryTemplate(final String username, final String historyId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		
		try {
			super.authenticate(username);
			this.historyService.delete(this.historyService.findOne(this.getEntityId(historyId)));
			this.historyService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

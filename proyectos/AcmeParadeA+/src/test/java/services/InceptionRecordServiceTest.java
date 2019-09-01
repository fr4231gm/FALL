package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.InceptionRecord;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class InceptionRecordServiceTest extends AbstractTest{
	
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
	//Intentar crear un inception record sin descripci�n
	//Intentar crear un inception record con descripcion en blanco
	//Intentar crear un inception record con titulo en blanco	
	//Intentar crear un inception record sin titulo
	//Intentar crear un inception record sin haber creado antes una history
	//Intentar crear un inception record estando logeado como administrator
	
	//Analysis of sentence coverage: Covered 100%
					
	//Analysis of data coverage: 211 / 211 total instructions
	
	@Test
	public void CreateEditInceptionTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"brotherhood3","title","description","https://www.google.es", null}, //Crear un inception record
			 
				//TESTS NEGATIVOS:
				{"brotherhood3","title", null, "",  ConstraintViolationException.class}, //Intentar crear un inception record sin descripci�n
				
				{"brotherhood3","title", "", "https://www.google.es", ConstraintViolationException.class}, //Intentar crear un inception record con descripcion en blanco
				
				{"brotherhood3","", "description", "https://www.google.es", ConstraintViolationException.class}, //Intentar crear un inception record con titulo en blanco
				
				{"brotherhood3", null, "description", "https://www.google.es", ConstraintViolationException.class}, //Intentar crear un inception record sin titulo
				
				{"brotherhood1", "title", "description", "https://www.google.es", IllegalArgumentException.class}, //Intentar crear un inception record sin haber creado antes una history
			 
				{"administrator1","title","description", "https://www.google.es, https://www.yahoo.es", IllegalArgumentException.class} //Intentar crear un inception record si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditInceptionTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditInceptionTemplate(final String username, final String title, final String description, final String pictures, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Creamos el Record y le setteamos sus atributos
			InceptionRecord inceptionRecord = this.inceptionRecordService.create();
			inceptionRecord.setTitle(title);
			inceptionRecord.setDescription(description);
			inceptionRecord.setPictures(pictures);
			
			//Lo guardamos y hacemos flush
			InceptionRecord saved = this.inceptionRecordService.save(inceptionRecord);
			this.inceptionRecordService.flush();
			
			//Comprobamos que el history del brotherhood ahora tiene ese record
			Assert.isTrue(this.historyService.findByPrincipal().getInceptionRecord() == saved);
			
			//Update
			this.inceptionRecordService.save(saved);
			this.inceptionRecordService.flush();
			
			//Hacemos log out para la siguiente iteraci�n
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}

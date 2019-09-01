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

import domain.MiscellaneousRecord;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest{
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	MiscellaneousRecordService miscellaneousRecordService;
	
	@Autowired
	BrotherhoodService brotherhoodService;
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the history of every brotherhood that he or she can display.
	//3. An actor who is authenticated as a brotherhood must be able to:
	//1. Manage their history, which includes listing, displaying, creating and updating its records.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un miscellaneous record sin descripción
	//Intentar crear un miscellaneous record con descripcion en blanco
	//Intentar crear un miscellaneous record con titulo en blanco
	//Intentar crear un miscellaneous record sin titulo
	//Intentar crear un miscellaneous record sin haber creado antes una history
	//Intentar crear un miscellaneous record si soy un admin
			
	//Analysis of sentence coverage: Covered 100%
									
	//Analysis of data coverage: Covered 42 / 42 total instructions
	
	@Test
	public void CreateEditMiscellaneousTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"brotherhood1","title","description", null }, //Crear un miscellaneous record
			 
				//TESTS NEGATIVOS:
				{"brotherhood1","title", null,  ConstraintViolationException.class}, //Intentar crear un miscellaneous record sin descripción
				
				{"brotherhood1","title", "",  ConstraintViolationException.class}, //Intentar crear un miscellaneous record con descripcion en blanco
				
				{"brotherhood1","", "description",  ConstraintViolationException.class}, //Intentar crear un miscellaneous record con titulo en blanco
				
				{"brotherhood1", null, "description",  ConstraintViolationException.class}, //Intentar crear un miscellaneous record sin titulo
				
				{"brotherhood3", "title", "description",  IllegalArgumentException.class}, //Intentar crear un miscellaneous record sin haber creado antes una history
			 
				{"admin","title","description", IllegalArgumentException.class} //Intentar crear un miscellaneous record si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditMiscellaneousTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditMiscellaneousTemplate(final String username, final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Creamos el Record y le setteamos sus atributos
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setDescription(description);
			
			//Lo guardamos y hacemos flush
			MiscellaneousRecord saved = this.miscellaneousRecordService.save(miscellaneousRecord);
			this.miscellaneousRecordService.flush();
			
			//Comprobamos que el history del brotherhood ahora tiene ese record
			Assert.isTrue(this.historyService.findByPrincipal().getMiscellaneousRecord().contains(saved));
			
			//UPDATE
			this.miscellaneousRecordService.save(saved);
			this.miscellaneousRecordService.flush();
			
			//Hacemos log out para la siguiente iteración
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
	//Intentar borrar un record como administrador
	//Intentar borrar un record que no es mio
			
	//Analysis of sentence coverage: Covered 95.2%
										
	//Analysis of data coverage: Covered 40 / 42 total instructions
	
	@Test
	public void DeleteMiscellaneousRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"brotherhood1", "miscellaneousRecord1", null }, //Borrar mi record
			 
				//TESTS NEGATIVOS:
				{"admin", "miscellaneousRecord1", IllegalArgumentException.class}, //Intentar borrar un record como administrador
			 
				{"brotherhood2", "miscellaneousRecord1",IllegalArgumentException.class}, //Intentar borrar un record que no es mio
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteMiscellaneousRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteMiscellaneousRedordTemplate(final String username, final String miscellaneousRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el record y hacemos flush
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(this.getEntityId(miscellaneousRecordId));
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			this.miscellaneousRecordService.flush();
			
			//Comprobamos que el history del brotherhood ya no tiene ese record
			Assert.isTrue(!this.historyService.findByPrincipal().getMiscellaneousRecord().contains(miscellaneousRecord));
			
			//Hacemos log out para la siguiente iteración
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

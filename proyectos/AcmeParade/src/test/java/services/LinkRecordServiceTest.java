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
import domain.LinkRecord;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class LinkRecordServiceTest extends AbstractTest{
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	LinkRecordService linkRecordService;
	
	@Autowired
	BrotherhoodService brotherhoodService;
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the history of every brotherhood that he or she can display.
	//3. An actor who is authenticated as a brotherhood must be able to:
	//1. Manage their history, which includes listing, displaying, creating and updating its records.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un link record sin descripción
	//Intentar crear un link record con descripcion en blanco
	//Intentar crear un link record con titulo en blanco
	//Intentar crear un link record sin titulo
	//Intentar crear un link record y linkarlo consigo mismo
	//Intentar crear un link record sin haber creado antes una history
	//Intentar crear un link record si soy un admin
	//Crear un link record con codigo malicioso en el título
	//Crear un link record con codigo malicioso en la descripcion
		
	//Analysis of sentence coverage: Covered 95.2%
								
	//Analysis of data coverage: Covered 40 /42 total instructions
	
	@Test
	public void CreateEditLinkTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"brotherhood1","title","description", "brotherhood2", null }, //Crear un link record
			 
				//TESTS NEGATIVOS:
				{"brotherhood1","title", null, "brotherhood2",  ConstraintViolationException.class}, //Intentar crear un link record sin descripción
				
				{"brotherhood1","title", "", "brotherhood2",  ConstraintViolationException.class}, //Intentar crear un link record con descripcion en blanco
				
				{"brotherhood1","", "description", "brotherhood2",  ConstraintViolationException.class}, //Intentar crear un link record con titulo en blanco
				
				{"brotherhood1", null, "description", "brotherhood2",  ConstraintViolationException.class}, //Intentar crear un link record sin titulo
				
				{"brotherhood1", "title", "description", "brotherhood1",  IllegalArgumentException.class}, //Intentar crear un link record y linkarlo consigo mismo
				
				{"brotherhood3", "title", "description", "brotherhood2",  IllegalArgumentException.class}, //Intentar crear un link record sin haber creado antes una history
			 
				{"admin","title","description","brotherhood2",  IllegalArgumentException.class}, //Intentar crear un link record si soy un admin
				
				{"brotherhood1","<script>alert('piu')</script>","description", "brotherhood2", ConstraintViolationException.class}, //Crear un link record con codigo malicioso en el título
			
				{"brotherhood1","title","<p>alert('descripción maliciosa')</p>", "brotherhood2", ConstraintViolationException.class} //Crear un link record con codigo malicioso en la descripcion
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditLinkTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],(String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditLinkTemplate(final String username, final String title, final String description, final String linkedBrotherhood, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Creamos el Record y le setteamos sus atributos
			LinkRecord linkRecord = this.linkRecordService.create();
			linkRecord.setTitle(title);
			linkRecord.setDescription(description);
			linkRecord.setBrotherhood(this.brotherhoodService.findOne(this.getEntityId(linkedBrotherhood)));
			
			//Lo guardamos y hacemos flush
			LinkRecord saved = this.linkRecordService.save(linkRecord);
			this.linkRecordService.flush();
			
			//Comprobamos que el history del brotherhood ahora tiene ese record
			Assert.isTrue(this.historyService.findByPrincipal().getLinkRecord().contains(saved));
			
			//Update
			this.linkRecordService.save(saved);
			this.linkRecordService.flush();
			
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
	
	//Analysis of sentence coverage: Covered 97.1%
									
	//Analysis of data coverage: Covered 33 / 34 total instructions
	
	@Test
	public void DeleteLinkRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"brotherhood1", "linkRecord1", null }, //Borrar mi record
			 
				//TESTS NEGATIVOS:
				{"admin", "linkRecord1", IllegalArgumentException.class}, //Intentar borrar un record como administrador
			 
				{"brotherhood2", "linkRecord1",IllegalArgumentException.class}, //Intentar borrar un record que no es mio
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteLinkRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	protected void DeleteLinkRedordTemplate(final String username, final String linkRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el record y hacemos flush
			LinkRecord linkRecord = this.linkRecordService.findOne(this.getEntityId(linkRecordId));
			this.linkRecordService.delete(linkRecord);
			this.linkRecordService.flush();
			
			//Comprobamos que el history del brotherhood ya no tiene ese record
			Assert.isTrue(!this.historyService.findByPrincipal().getLinkRecord().contains(linkRecord));
			
			//Hacemos log out para la siguiente iteración
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

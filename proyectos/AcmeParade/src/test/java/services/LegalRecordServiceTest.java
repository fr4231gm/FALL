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
import domain.LegalRecord;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class LegalRecordServiceTest extends AbstractTest{
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	LegalRecordService legalRecordService;
	
	@Autowired
	BrotherhoodService brotherhoodService;
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the history of every brotherhood that he or she can display.
	//3. An actor who is authenticated as a brotherhood must be able to:
	//1. Manage their history, which includes listing, displaying, creating and updating its records.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un legal record sin VATNumber
	//Intentar crear un legal record sin Legal Name
	//Intentar crear un legal record sin descripción
	//Intentar crear un legal record con descripcion en blanco
	//Intentar crear un legal record con titulo en blanco
	//Intentar crear un legal record sin titulo
	//Intentar crear un legal record sin haber creado antes una history
	//Intentar crear un legal record si soy un admin
	
	//Analysis of sentence coverage: Covered 100%
						
	//Analysis of data coverage: 345 /345 total instructions
	
	@Test
	public void CreateEditLegalTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"brotherhood1","title","description","legal Name", "VAT", "applicable law1, applicable law2, applicable law 3", null }, //Crear un legal record
				
				//TESTS NEGATIVOS:
				{"brotherhood1","title","description","legal Name", "", "applicable law1, applicable law2, applicable law 3", ConstraintViolationException.class}, //Intentar crear un legal record sin VATNumber
				 
				{"brotherhood1","title","description","", "VAT", "applicable law1, applicable law2, applicable law 3", ConstraintViolationException.class}, //Intentar crear un legal record sin Legal Name
				
				{"brotherhood1","title","legal Name", "VAT", "applicable law1, applicable law2, applicable law 3", null,  ConstraintViolationException.class}, //Intentar crear un legal record sin descripción
				
				{"brotherhood1","title", "","legal Name", "VAT", "applicable law1, applicable law2, applicable law 3",  ConstraintViolationException.class}, //Intentar crear un legal record con descripcion en blanco
				
				{"brotherhood1","", "description","legal Name", "VAT", "applicable law1, applicable law2, applicable law 3",  ConstraintViolationException.class}, //Intentar crear un legal record con titulo en blanco
				
				{"brotherhood1", null, "description", "legal Name", "VAT", "applicable law1, applicable law2, applicable law 3", ConstraintViolationException.class}, //Intentar crear un legal record sin titulo
				
				{"brotherhood3", "title", "description", "legal Name", "VAT", "applicable law1, applicable law2, applicable law 3", IllegalArgumentException.class}, //Intentar crear un legal record sin haber creado antes una history
			 
				{"admin","title","description", "legal Name", "VAT", "applicable law1, applicable law2, applicable law 3", IllegalArgumentException.class} //Intentar crear un legal record si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditLegalTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],(String) testingData[i][4],(String) testingData[i][5],(Class<?>) testingData[i][6]);
			this.rollbackTransaction();
		}
			
	}

	protected void CreateEditLegalTemplate(final String username, final String title, final String description, final String legalName, final String vatNumber, final String applicableLaws, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Creamos el Record y le setteamos sus atributos
			LegalRecord legalRecord = this.legalRecordService.create();
			legalRecord.setTitle(title);
			legalRecord.setDescription(description);
			legalRecord.setApplicableLaws(applicableLaws);
			legalRecord.setVatNumber(vatNumber);
			legalRecord.setLegalName(legalName);
			
			//Lo guardamos y hacemos flush
			LegalRecord saved = this.legalRecordService.save(legalRecord);
			this.legalRecordService.flush();
			
			//Comprobamos que el history del brotherhood ahora tiene ese record
			Assert.isTrue(this.historyService.findByPrincipal().getLegalRecord().contains(saved));
			
			//Update
			this.legalRecordService.save(saved);
			this.legalRecordService.flush();
			
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
	//Intentar borrar un record que no es del actor logeado
	
	//Analysis of sentence coverage: Covered 100%
							
	//Analysis of data coverage: 83 /83 total instructions
	
	@Test
	public void DeleteLegalRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"brotherhood1", "legalRecord1", null }, //Borrar mi record
			 
				//TESTS NEGATIVOS:
				{"admin", "legalRecord1", IllegalArgumentException.class}, //Intentar borrar un record como administrador
			 
				{"brotherhood2", "legalRecord1",IllegalArgumentException.class}, //Intentar borrar un record que no es mio
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteLegalRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteLegalRedordTemplate(final String username, final String legalRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el record y hacemos flush
			LegalRecord legalRecord = this.legalRecordService.findOne(this.getEntityId(legalRecordId));
			this.legalRecordService.delete(legalRecord);
			this.legalRecordService.flush();
			
			//Comprobamos que el history del brotherhood ya no tiene ese record
			Assert.isTrue(!this.historyService.findByPrincipal().getLegalRecord().contains(legalRecord));
			
			//Hacemos log out para la siguiente iteración
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

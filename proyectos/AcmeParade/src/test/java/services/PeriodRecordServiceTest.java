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
import domain.PeriodRecord;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class PeriodRecordServiceTest extends AbstractTest{
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	PeriodRecordService periodRecordService;
	
	@Autowired
	BrotherhoodService brotherhoodService;
	
	//Requierement: 2. An actor who is not authenticated must be able to:
	//1. Display the history of every brotherhood that he or she can display.
	//3. An actor who is authenticated as a brotherhood must be able to:
	//1. Manage their history, which includes listing, displaying, creating and updating its records.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un period record sin descripción
	//Intentar crear un period record con descripcion en blanco
	//Intentar crear un period record con titulo en blanco
	//Intentar crear un period record sin titulo
	//Intentar crear un period con una url no válida
	//Intentar crear un period record sin haber creado antes una history
	//Intentar crear un period record si soy un admin
	//Intentar crear un period record con año de inicio mayor que año de fin
	//Intentar crear un period record con año de inicio anterior al establishment date	
	
	//Analysis of sentence coverage: Covered 100%
							
	//Analysis of data coverage: Covered 51 / 51 total instructions
	
	@Test
	public void CreateEditPeriodTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"brotherhood1","title","description", "https://www.google.es, https://www.yahoo.es", 2019, 2020, null }, //Crear un period record
				
				//TESTS NEGATIVOS:
				{"brotherhood1","title", null, "", 2019, 2020,  ConstraintViolationException.class}, //Intentar crear un period record sin descripción
				
				{"brotherhood1","title", "", "", 2019, 2020, ConstraintViolationException.class}, //Intentar crear un period record con descripcion en blanco
				
				{"brotherhood1","", "description", "", 2019, 2020, ConstraintViolationException.class}, //Intentar crear un period record con titulo en blanco
				
				{"brotherhood1", null, "description", "", 2019, 2020, ConstraintViolationException.class}, //Intentar crear un period record sin titulo
				
				{"brotherhood1", "title", "description", "HoLaSoYuNaURLquenovaleparanada", 2019, 2020, IllegalArgumentException.class}, //Intentar crear un period con una url no válida
				
				{"brotherhood3", "title", "description", "", 2019, 2020, IllegalArgumentException.class}, //Intentar crear un period record sin haber creado antes una history
			 
				{"admin","title","description", "", 2019, 2020, IllegalArgumentException.class}, //Intentar crear un period record si soy un admin
			
				{"brotherhood1","title", "description", "", 2020, 2019, IllegalArgumentException.class}, //Intentar crear un period record con año de inicio mayor que año de fin
				
				{"brotherhood1","title", "description", "", 1200, 2020, IllegalArgumentException.class} //Intentar crear un period record con año de inicio anterior al establishment date
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditPeriodTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],(String) testingData[i][3],(Integer) testingData[i][4],(Integer) testingData[i][5], (Class<?>) testingData[i][6]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditPeriodTemplate(final String username, final String title, final String description, final String pictures, final Integer startYear, final Integer endYear, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Creamos el Record y le setteamos sus atributos
			PeriodRecord periodRecord = this.periodRecordService.create();
			periodRecord.setTitle(title);
			periodRecord.setDescription(description);
			periodRecord.setPictures(pictures);
			periodRecord.setStartYear(startYear);
			periodRecord.setEndYear(endYear);
			
			//Lo guardamos y hacemos flush
			PeriodRecord saved = this.periodRecordService.save(periodRecord);
			this.periodRecordService.flush();
			
			//Comprobamos que el history del brotherhood ahora tiene ese record
			Assert.isTrue(this.historyService.findByPrincipal().getPeriodRecord().contains(saved));
			
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
								
	//Analysis of data coverage: Covered 40 /42 total instructions
	
	@Test
	public void DeletePeriodRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"brotherhood1", "periodRecord1", null }, //Borrar mi record
			 
				//TESTS NEGATIVOS:
				{"admin", "periodRecord1", IllegalArgumentException.class}, //Intentar borrar un record como administrador
			 
				{"brotherhood2", "periodRecord1",IllegalArgumentException.class}, //Intentar borrar un record que no es mio
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeletePeriodRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeletePeriodRedordTemplate(final String username, final String periodRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el record y hacemos flush
			PeriodRecord periodRecord = this.periodRecordService.findOne(this.getEntityId(periodRecordId));
			this.periodRecordService.delete(periodRecord);
			this.periodRecordService.flush();
			
			//Comprobamos que el history del brotherhood ya no tiene ese record
			Assert.isTrue(!this.historyService.findByPrincipal().getPeriodRecord().contains(periodRecord));
			
			//Hacemos log out para la siguiente iteración
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

import domain.Curricula;
import domain.PositionData;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class PositionDataServiceTest extends AbstractTest{
	
	@Autowired
	CurriculaService curriculaService;
	
	@Autowired
	PositionDataService positionDataService;
	
	@Autowired
	RookieService rookieService;
	
	//Requierement:
	//3. An actor who is authenticated as a rookie must be able to:
	//1. Manage their curricula, which includes listing, displaying, creating and updating its position datas.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un position data sin attachment
	//Intentar crear un position data con attachment en blanco
	//Intentar crear un position data con title en blanco
	//Intentar crear un position data sin title
	//Intentar crear un position data si soy un admin
	
	// Analysis of sentence coverage total: Covered 29.5% 74/251 total instructions
	// Analysis of sentence coverage create(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage save(): Covered 100% 53/53 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 100%
			
	
	@Test
	public void CreateEditPositionTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"rookie3","title","description", new Date(), new Date(), null }, //Crear un position data
				
				{"rookie3","title","description", new Date(), null, null }, //Crear un position data
			 
				//TESTS NEGATIVOS:
				{"rookie3","title", null, new Date(), new Date(),  ConstraintViolationException.class}, //Intentar crear un position data sin description
				
				{"rookie3","title", "", new Date(), new Date(),  ConstraintViolationException.class}, //Intentar crear un position data con description en blanco
				
				{"rookie3","", "description", new Date(), new Date(),  ConstraintViolationException.class}, //Intentar crear un position data con titulo en blanco
				
				{"rookie3", null, "description", new Date(), new Date(), ConstraintViolationException.class}, //Intentar crear un position data sin titulo
				
				{"rookie3","title","description", null, new Date(), ConstraintViolationException.class}, //Crear un position data sin fecha de inicio
			 
				{"admin","title","description", new Date(), new Date(), IllegalArgumentException.class} //Intentar crear un position data si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditPositionTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4], (Class<?>) testingData[i][5]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditPositionTemplate(final String username, final String title, final String description, final Date startDate, final Date endDate, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Curricula c = this.curriculaService.findByPrincipal().iterator().next();
			
			//Creamos el Data y le setteamos sus atributos
			PositionData positionData = this.positionDataService.create();
			positionData.setTitle(title);
			positionData.setDescription(description);
			positionData.setStartDate(startDate);
			positionData.setEndDate(endDate);
			
			//Lo guardamos y hacemos flush
			PositionData saved = this.positionDataService.save(positionData, c.getId());
			this.positionDataService.flush();
			
			//Comprobamos que el curricula del rookie ahora tiene ese data
			Assert.isTrue(this.curriculaService.findOne(c.getId()).getPositionData().contains(saved));
			
			//UPDATE
			this.positionDataService.save(saved, c.getId());
			this.positionDataService.flush();
			
			//Hacemos log out para la siguiente iteraci�n
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: 2. An actor authenticated as Rookie must be able to:
	//1. Display the curricula of every rookie that he or she can display.
	//3. An actor who is authenticated as a rookie must be able to:
	//1. Delete their position data

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un data como administrador
	//Intentar borrar un data que no es mio
	
	// Analysis of sentence coverage total: Covered 21.1% 53/251 total instructions
	// Analysis of sentence coverage delete(): Covered 97.4% 37/38 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 100%
			
	
	@Test
	public void DeletePositionDataTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"rookie3", "positionData1", null }, //Borrar mi data
			 
				//TESTS NEGATIVOS:
				{"admin", "positionData1", IllegalArgumentException.class}, //Intentar borrar un data como administrador
			 
				// {"rookie2", "positionData1",IllegalArgumentException.class}, //Intentar borrar un data que no es mio
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.DeletePositionRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void DeletePositionRedordTemplate(final String username, final String positionDataId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el data y hacemos flush
			PositionData positionData = this.positionDataService.findOne(this.getEntityId(positionDataId));
			
			Curricula c = this.curriculaService.findByPositionDataId(positionData.getId());
			this.positionDataService.delete(positionData);
			this.positionDataService.flush();
			
			//Comprobamos que el curricula del rookie ya no tiene ese data
			Assert.isTrue(!this.curriculaService.findOne(c.getId()).getPositionData().contains(positionData));
			
			//Hacemos log out para la siguiente iteraci�n
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

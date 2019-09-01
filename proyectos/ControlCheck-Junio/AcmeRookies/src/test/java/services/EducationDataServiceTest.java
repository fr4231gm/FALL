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
import domain.EducationData;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class EducationDataServiceTest extends AbstractTest{
	
	@Autowired
	CurriculaService curriculaService;
	
	@Autowired
	EducationDataService educationDataService;
	
	@Autowired
	RookieService rookieService;
	
	//Requierement:
	//3. An actor who is authenticated as a rookie must be able to:
	//1. Manage their curricula, which includes listing, displaying, creating and updating its education datas.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un education data sin attachment
	//Intentar crear un education data con attachment en blanco
	//Intentar crear un education data con degree en blanco
	//Intentar crear un education data sin degree
	//Intentar crear un education data si soy un admin
	
	// Analysis of sentence coverage total: Covered 24.7% 64/259 total instructions
	// Analysis of sentence coverage save(): Covered 81.1% 43/53 total instructions
	// Analysis of sentence coverage create(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 66,66%
			
	
	@Test
	public void CreateEditEducationTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"rookie3","degree","institution", 8.5, new Date(), new Date(), null }, //Crear un education data
			 
				//TESTS NEGATIVOS:
				{"rookie3","degree", null, 5.8, new Date(), new Date(),  ConstraintViolationException.class}, //Intentar crear un education data sin institution
				
				{"rookie3","degree", "", 8.9, new Date(), new Date(),  ConstraintViolationException.class}, //Intentar crear un education data con institution en blanco
				
				{"rookie3","", "institution", 3.2, new Date(), new Date(),  ConstraintViolationException.class}, //Intentar crear un education data con titulo en blanco
				
				{"rookie3", null, "institution", 8.3, new Date(), new Date(), ConstraintViolationException.class}, //Intentar crear un education data sin titulo
			 
				{"admin","degree","institution", 8.0, new Date(), new Date(), IllegalArgumentException.class} //Intentar crear un education data si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditEducationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditEducationTemplate(final String username, final String degree, final String institution, final Double mark, final Date startDate, final Date endDate, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Curricula c = this.curriculaService.findByPrincipal().iterator().next();
			
			//Creamos el Data y le setteamos sus atributos
			EducationData educationData = this.educationDataService.create();
			educationData.setDegree(degree);
			educationData.setInstitution(institution);
			educationData.setMark(mark);
			educationData.setStartDate(startDate);
			educationData.setEndDate(endDate);
			
			//Lo guardamos y hacemos flush
			EducationData saved = this.educationDataService.save(educationData, c.getId());
			this.educationDataService.flush();
			
			//Comprobamos que el curricula del rookie ahora tiene ese data
			Assert.isTrue(this.curriculaService.findOne(c.getId()).getEducationData().contains(saved));
			
			//UPDATE
			this.educationDataService.save(saved, c.getId());
			this.educationDataService.flush();
			
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
	//1. Delete their education data

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un data como administrador
	//Intentar borrar un data que no es mio
	
	// Analysis of sentence coverage total: Covered 20.5% 53/259 total instructions
	// Analysis of sentence coverage delete(): Covered 97.4% 37/38 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 100%
			
	
	@Test
	public void DeleteEducationDataTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"rookie3", "educationData1", null }, //Borrar mi data
			 
				//TESTS NEGATIVOS:
				{"admin", "educationData1", IllegalArgumentException.class}, //Intentar borrar un data como administrador
			 
				// {"rookie2", "educationData1",IllegalArgumentException.class}, //Intentar borrar un data que no es mio
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.DeleteEducationRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void DeleteEducationRedordTemplate(final String username, final String educationDataId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el data y hacemos flush
			EducationData educationData = this.educationDataService.findOne(this.getEntityId(educationDataId));
			
			Curricula c = this.curriculaService.findByEducationDataId(educationData.getId());
			this.educationDataService.delete(educationData);
			this.educationDataService.flush();
			
			//Comprobamos que el curricula del rookie ya no tiene ese data
			Assert.isTrue(!this.curriculaService.findOne(c.getId()).getEducationData().contains(educationData));
			
			//Hacemos log out para la siguiente iteraci�n
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

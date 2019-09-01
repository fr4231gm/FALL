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

import domain.Curricula;
import domain.MiscellaneousData;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest{
	
	@Autowired
	CurriculaService curriculaService;
	
	@Autowired
	MiscellaneousDataService miscellaneousDataService;
	
	@Autowired
	HackerService hackerService;
	
	//Requierement:
	//3. An actor who is authenticated as a hacker must be able to:
	//1. Manage their curricula, which includes listing, displaying, creating and updating its miscellaneous datas.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un miscellaneous data sin attachment
	//Intentar crear un miscellaneous data con attachment en blanco
	//Intentar crear un miscellaneous data con text en blanco
	//Intentar crear un miscellaneous data sin text
	//Intentar crear un miscellaneous data si soy un admin
	
	// Analysis of sentence coverage total: Covered 27.2% 64/235 total instructions
	// Analysis of sentence coverage save(): Covered 81.1% 43/53 total instructions
	// Analysis of sentence coverage create(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 100%
			
	
	@Test
	public void CreateEditMiscellaneousTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"hacker3","text","attachments", null }, //Crear un miscellaneous data
			 
				//TESTS NEGATIVOS:
				{"hacker3","text", null,  ConstraintViolationException.class}, //Intentar crear un miscellaneous data sin attachments
				
				{"hacker3","text", "",  ConstraintViolationException.class}, //Intentar crear un miscellaneous data con attachments en blanco
				
				{"hacker3","", "attachments",  ConstraintViolationException.class}, //Intentar crear un miscellaneous data con titulo en blanco
				
				{"hacker3", null, "attachments",  ConstraintViolationException.class}, //Intentar crear un miscellaneous data sin titulo
			 
				{"admin","text","attachments", IllegalArgumentException.class} //Intentar crear un miscellaneous data si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditMiscellaneousTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
			
	}
	
	protected void CreateEditMiscellaneousTemplate(final String username, final String text, final String attachments, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Curricula c = this.curriculaService.findByPrincipal().iterator().next();
			
			//Creamos el Data y le setteamos sus atributos
			MiscellaneousData miscellaneousData = this.miscellaneousDataService.create();
			miscellaneousData.setText(text);
			miscellaneousData.setAttachments(attachments);
			
			//Lo guardamos y hacemos flush
			MiscellaneousData saved = this.miscellaneousDataService.save(miscellaneousData, c.getId());
			this.miscellaneousDataService.flush();
			
			//Comprobamos que el curricula del hacker ahora tiene ese data
			Assert.isTrue(this.curriculaService.findOne(c.getId()).getMiscellaneousData().contains(saved));
			
			//UPDATE
			this.miscellaneousDataService.save(saved, c.getId());
			this.miscellaneousDataService.flush();
			
			//Hacemos log out para la siguiente iteración
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: 2. An actor authenticated as Hacker must be able to:
	//1. Display the curricula of every hacker that he or she can display.
	//3. An actor who is authenticated as a hacker must be able to:
	//1. Delete their miscellaneous data

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un data como administrador
	//Intentar borrar un data que no es mio
	
	// Analysis of sentence coverage total: Covered 22.6% 53/235 total instructions
	// Analysis of sentence coverage delete(): Covered 97.4% 37/38 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 100%
			
	
	@Test
	public void DeleteMiscellaneousDataTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"hacker3", "miscellaneousData1", null }, //Borrar mi data
			 
				//TESTS NEGATIVOS:
				{"admin", "miscellaneousData1", IllegalArgumentException.class}, //Intentar borrar un data como administrador
			 
				// {"hacker2", "miscellaneousData1",IllegalArgumentException.class}, //Intentar borrar un data que no es mio
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.DeleteMiscellaneousRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void DeleteMiscellaneousRedordTemplate(final String username, final String miscellaneousDataId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			
			//Intentamos borrar el data y hacemos flush
			MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(this.getEntityId(miscellaneousDataId));
			
			Curricula c = this.curriculaService.findByMiscellaneousDataId(miscellaneousData.getId());
			this.miscellaneousDataService.delete(miscellaneousData);
			this.miscellaneousDataService.flush();
			
			//Comprobamos que el curricula del hacker ya no tiene ese data
			Assert.isTrue(!this.curriculaService.findOne(c.getId()).getMiscellaneousData().contains(miscellaneousData));
			
			//Hacemos log out para la siguiente iteración
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}

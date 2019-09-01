
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Proclaim;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProclaimServiceTest extends AbstractTest {

	@Autowired
	private ProclaimService	proclaimService;


	//Requierement: 14. An actor who is not authenticated must be able to:
	//2. Browse the proclaims of the chapters.
	
	//In the case of negative tests, the business rule that is intended to be broken: 
	//Crear una proclama con texto vacío		
	//Crear una proclama con texto nulo
	//Crear una proclama con texto mayor a 250
	
	//Analysis of sentence coverage: Covered 83.3%
											
	//Analysis of data coverage: Covered 96 /96 total instructions
	
	@Test
	public void createProclaimTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO: Crear una proclama con texto no vacío
			{
				"chapter1", "text", null
			},

			// TEST NEGATIVO: Crear una proclama con texto vacío
			{
				"chapter1", "", ConstraintViolationException.class
			},
			// TEST NEGATIVO: Crear una proclama con texto nulo
			{
				"chapter1", null, ConstraintViolationException.class
			},
			// TEST NEGATIVO: Crear una proclama con texto mayor a 250
			{
				"chapter1",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In suscipit mauris eu fermentum lobortis. Maecenas aliquam purus id tellus commodo pulvinar. Fusce ultrices quam ipsum, in ullamcorper sapien suscipit vitae. Phasellus accumsan odio et posuere. Me pasé",
				ConstraintViolationException.class
			}

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.createProclaimTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	//Requierement: 14. An actor who is not authenticated must be able to:
		//2. Browse the proclaims of the chapters.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Editar una proclama que no me pertenece	
	//Editar una proclama con momento no nulo
	//Editar una proclama con texto superior a 250
		
	//Analysis of sentence coverage: Covered 100%
											
	//Analysis of data coverage: Covered 135 /135 total instructions
	
	@Test
	public void editProclaimTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO: Editar una proclama mía con momento nulo
			{
				"chapter2", super.getEntityId("proclaim2"), "textNuevo", null
			},

			// TEST NEGATIVO: Editar una proclama que no me pertenece
			{
				"chapter1", super.getEntityId("proclaim2"), "textoNuevo", IllegalArgumentException.class
			},
			// TEST NEGATIVO: Editar una proclama con momento no nulo
			{
				"chapter1", super.getEntityId("proclaim1"), "textoNuevo", IllegalArgumentException.class
			},
			// TEST NEGATIVO: Editar una proclama con texto superior a 250
			{
				"chapter2",
				super.getEntityId("proclaim2"),
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In suscipit mauris eu fermentum lobortis. Maecenas aliquam purus id tellus commodo pulvinar. Fusce ultrices quam ipsum, in ullamcorper sapien suscipit vitae. Phasellus accumsan odio et posuere. Me pasé",
				ConstraintViolationException.class
			}

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editProclaimTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		this.rollbackTransaction();
	}
	
	//Requierement: 14. An actor who is not authenticated must be able to:
		//2. Browse the proclaims of the chapters.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Editar una proclama que no me pertenece	
	//Editar una proclama con momento no nulo
	//Editar una proclama con texto superior a 250
			
	//Analysis of sentence coverage: Covered 100%
												
	//Analysis of data coverage: Covered 93 /93 total instructions
	
	@Test
	public void publishProclaimTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO: Publicar una proclama mía con momento nulo
			{
				"chapter2", super.getEntityId("proclaim2"), null
			},
			// TEST NEGATIVO: Publicar una proclama que no me pertenece 
			{
				"chapter1", super.getEntityId("proclaim2"), IllegalArgumentException.class
			},
			// TEST NEGATIVO: Publicar una proclama mía con momento no nulo
			{
				"chapter1", super.getEntityId("proclaim1"), IllegalArgumentException.class
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.publishProclaimTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	//Requierement: 14. An actor who is not authenticated must be able to:
	//2. Browse the proclaims of the chapters.
	
	//In the case of negative tests, the business rule that is intended to be broken: 
	//Borrar una proclama que no me pertenece 
	//Borrar una proclama mía con momento no nulo
				
	//Analysis of sentence coverage: Covered 100%
													
	//Analysis of data coverage: Covered 93 /93 total instructions
	
	@Test
	public void deleteProclaimTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO: Borrar una proclama mía con momento nulo
			{
				"chapter2", super.getEntityId("proclaim2"), null
			},
			// TEST NEGATIVO: Borrar una proclama que no me pertenece 
			{
				"chapter1", super.getEntityId("proclaim2"), IllegalArgumentException.class
			},
			// TEST NEGATIVO: Borrar una proclama mía con momento no nulo
			{
				"chapter1", super.getEntityId("proclaim1"), IllegalArgumentException.class
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.deleteProclaimTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	//Requierement: 14. An actor who is not authenticated must be able to:
	//2. Browse the proclaims of the chapters.
		
	//In the case of negative tests, the business rule that is intended to be broken: No procede
					
	//Analysis of sentence coverage: Covered 100%
														
	//Analysis of data coverage: Covered 93 /93 total instructions

	@Test
	public void listProclaimsByChapterTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO: Borrar una proclama mía con momento nulo
			{
				super.getEntityId("chapter1"), null
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.listProclaimsByChapterTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
		this.rollbackTransaction();
	}

	// Templates ------------------------------------------------------------------------

	protected void createProclaimTemplate(final String username, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Proclaim proclaim = this.proclaimService.create();

			proclaim.setText(text);
			this.proclaimService.save(proclaim);
			this.proclaimService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editProclaimTemplate(final String username, final int proclaimId, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Proclaim proclaim = this.proclaimService.findOne(proclaimId);

			proclaim.setText(text);
			this.proclaimService.save(proclaim);
			this.proclaimService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void publishProclaimTemplate(final String username, final int proclaimId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.proclaimService.publish(this.proclaimService.findOne(proclaimId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void deleteProclaimTemplate(final String username, final int proclaimId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.proclaimService.delete(this.proclaimService.findOne(proclaimId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void listProclaimsByChapterTemplate(final int chapterId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(null);
			this.proclaimService.findProclaimsByChapterId(chapterId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}

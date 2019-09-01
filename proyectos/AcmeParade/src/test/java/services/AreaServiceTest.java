
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AreaServiceTest extends AbstractTest {

	@Autowired
	private AreaService	areaService;

	//Requirement: 14. An actor who is not authenticated must be able to:
	//1. Navigate to the areas that those chapters co-ordinates.
	
	//In the case of negative tests, the business rule that is intended to be broken: No procede
	
	//Analysis of sentence coverage: Covered 100%
	
	//Analysis of data coverage: Covered 55 / 55 total instructions
	
	@Test
	public void displayAreaByChapterIdTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO:
			{
				super.getEntityId("chapter1"), null
			} //Muestra el area correctamente

			
		//Intenta mostrar el area de un chapter que no tiene area
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayAreaByChapterIdTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void displayAreaByChapterIdTemplate(final int chapterId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.areaService.findAreaByChapterId(chapterId);

			this.areaService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

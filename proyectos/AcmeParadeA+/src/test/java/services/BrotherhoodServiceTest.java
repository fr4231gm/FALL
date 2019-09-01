package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodServiceTest extends AbstractTest {
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	//Requirement: 14. An actor who is not authenticated must be able to:
	//1. Navigate to the brotherhoods that have settle in those areas.
	
	//In the case of negative tests, the business rule that is intended to be broken: No procede
	
	//Analysis of sentence coverage: Covered 100%
	
	//Analysis of data coverage: Covered 52 / 52 total instructions
	
	@Test
	public void listBrotherhoodsByChaptersAreaTestDriver(){
		
		final Object testingData[][] = {
				
			//TEST POSITIVO:
			{super.getEntityId("area1"), null}, //Muestra los brotherhood correctamente
		
		};
		
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listBrotherhoodsByChaptersAreaTemplate((int) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction(); 
		}
	}
	
	protected void listBrotherhoodsByChaptersAreaTemplate(final int areaId,
			final Class<?> expected){
		Class<?> caught;
		
		caught = null;
		
		try{
			
			this.brotherhoodService.findBrotherhoodsByArea(areaId);
		
			this.brotherhoodService.flush();
			
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

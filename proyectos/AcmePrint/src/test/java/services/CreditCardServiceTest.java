package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardServiceTest extends AbstractTest {
	
	@Autowired
	private CreditCardService creditCardService;
	
	// Requirements: 21.3 and 23.2
	// Analysis of sentence coverage total: Covered x% x/ total instructions
	// Analysis of data coverage: x%

	//Show CreditCard
	// Analysis of sentence coverage total: Covered 12.3% 56/455 total instructions
	// Analysis of sentence coverage checkActor(): Covered 100% 34/34 total instructions
	// Analysis of sentence coverage findCreditCardByActor(): Covered 100% 15/15 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 66.6%
	@Test
	public void showCreditCardTestDriver(){
		final Object testingData[][] = {
				//TEST POSITIVO:
				{"provider1",null},
				//TEST POSITIVO:
				{"customer1",null},
				//TEST NEGATIVO:
				{"designer1",IllegalArgumentException.class},
				//TEST NEGATIVO:
				{"company1",IllegalArgumentException.class}
		};
		for(int i=0; i< testingData.length; i++){
			this.startTransaction();
			this.showCreditCardTemplate ((String) testingData[i][0],(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}
	
	protected void showCreditCardTemplate (final String username, final Class<?> expected){
		Class<?> caught;
		
		caught = null;
		
		try{
			super.authenticate(username);
			int aux = this.getEntityId(username);
			
			this.creditCardService.findCreditCardByActorId(aux);
			this.creditCardService.flush();
			
			unauthenticate();
		}catch(final Throwable Oops){
			caught = Oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	// Requirements: 21.3 and 23.2
	//Create CreditCard
	// Analysis of sentence coverage total: Covered 17.4% 79/455 total instructions
	// Analysis of sentence coverage checkActor(): Covered 100% 34/34 total instructions
	// Analysis of sentence coverage create(): Covered 100% 17/17 total instructions
	// Analysis of sentence coverage save(): Covered 67.7% 21/31 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 52.37%
	
	@Test
	public void createCreditCardTestDriver(){
		final Object testingData[][] = {
				//TEST POSITIVO:
				{"customer7","Pablo","VISA","4024813453580367", 01,27,589, null}, 
				
				//TEST NEGATIVO:
				{"company1","Matthew","VISA","4024813453580367",01 ,27,589, IllegalArgumentException.class},
		
				//TEST NEGATIVO:
				{"customer1","Matthew","VISA","4024813453580367",01 ,27,589, IllegalArgumentException.class}
		}; 
				
		for(int i=0; i<testingData.length; i++){
					this.startTransaction();
					this.createCreditCardTemplate((String)testingData[i][0],(String) testingData[i][1],(String) testingData[i][2] ,(String) testingData[i][3],(Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],(Class<?>) testingData [i][7]);
					this.rollbackTransaction();
				}
		
	}
	
	private void createCreditCardTemplate(String username, String holder,
			String make, String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV, Class<?> expected) {
		Class<?> caught = null;
		try{
			super.authenticate(username);
			final CreditCard cc = this.creditCardService.create();
		
			cc.setHolder(holder);
			cc.setNumber(number);
			cc.setMake(make);
			cc.setExpirationMonth(expirationMonth);
			cc.setExpirationYear(expirationYear);
			cc.setCVV(CVV);
			
			this.creditCardService.save(cc);
			this.creditCardService.flush();
			this.unauthenticate();
		}catch(Throwable Oops){
			caught = Oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	}
	
	// Requirements: 21.3 and 23.2
	//Edit CreditCard
	// Analysis of sentence coverage total: Covered 18.9% 86/455 total instructions
	// Analysis of sentence coverage checkActor(): Covered 100% 34/34 total instructions
	// Analysis of sentence coverage save(): Covered 96.8% 30/31 total instructions
	// Analysis of sentence coverage findCreditCardByActor(): Covered 100% 15/15 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 53.37%

	@Test
	public void editCreditCardTestDriver(){
		final Object testingData[][] = {
				//TEST POSITIVO:
				{"customer1","Pablo","VISA","4024813453580367", 01,27,589, null}, 
				
				//TEST NEGATIVO:
				{"company1","Matthew","VISA","4024813453580367",01 ,27,589, IllegalArgumentException.class},
				
		
				//TEST NEGATIVO:
				{"company1","Matthew","VISA","4024813453580367",null ,27,589, IllegalArgumentException.class}
				}; 
						
		for(int i=0; i<testingData.length; i++){
					this.startTransaction();
					this.editCreditCardTemplate((String)testingData[i][0],(String) testingData[i][1],(String) testingData[i][2] ,(String) testingData[i][3],(Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],(Class<?>) testingData [i][7]);
					this.rollbackTransaction();
				}
		
	}
	
	private void editCreditCardTemplate(String username,String holder,
			String make, String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV, Class<?> expected) {
		Class<?> caught = null;
		try{
			super.authenticate(username);
			final CreditCard cc = this.creditCardService.findCreditCardByActorId(this.getEntityId(username));
		
			cc.setHolder(holder);
			cc.setNumber(number);
			cc.setMake(make);
			cc.setExpirationMonth(expirationMonth);
			cc.setExpirationYear(expirationYear);
			cc.setCVV(CVV);
			
			this.creditCardService.save(cc);
			this.creditCardService.flush();
			this.unauthenticate();
		}catch(Throwable Oops){
			caught = Oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	}
	
	// Requirements: 21.3 and 23.2
	//Delete CreditCard
	// Analysis of sentence coverage total: Covered 27.9% 127/455 total instructions
	// Analysis of sentence coverage checkActor(): Covered 94.1% 32/34 total instructions
	// Analysis of sentence coverage checkCustomer(): Covered 94.1% 32/34 total instructions
	// Analysis of sentence coverage findCreditCardByActor(): Covered 100% 15/15 total instructions
	// Analysis of sentence coverage delete(): Covered 74.0% 54/73 total instructions
	
	// Analysis of data coverage: 66.6%
	@Test
	public void deleteCreditCardTestDrive(){
		final Object testingData[][] = {
		//TEST POSITIVO:
		{"customer6",  null}, 
		//TEST Positivo:
		{"provider1", null}, 
		//TEST NEGATIVO:
		{"customer1", IllegalArgumentException.class}
		};
		
		for(int i=0; i<testingData.length; i++){
			this.startTransaction();
			this.deleteCreditCardTemplate((String) testingData[i][0],  (Class<?>)testingData[i][1]);
			this.rollbackTransaction();
		}
	}
	protected void deleteCreditCardTemplate(final String username,  Class<?> expected){
			Class<?> caught;
			
			caught = null;
			try{
				super.authenticate(username);
				this.creditCardService.delete();
				this.unauthenticate();
			}catch(final Throwable oops){
				caught = oops.getClass();
			}
			this.checkExceptions(expected, caught);
		}
	}




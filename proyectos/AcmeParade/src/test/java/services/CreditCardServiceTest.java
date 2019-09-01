package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
	
	//Requierement: 16. An actor who is authenticated as a sponsor must be able to:
	//1. Manage his or her sponsorships which have credit cards

	//In the case of negative tests, the business rule that is intended to be broken:
	//Mostrar CreditCard de un actor que no es Sponsor
					
	//Analysis of sentence coverage: Covered 100%
					
	//Analysis of data coverage: 74 / 74 total instructions
	
	@Test
	public void listCreditCardBySponsorIdTestDriver(){
		
		final Object testingData[][] = {
		
		//TEST POSITIVO:
		{"sponsor1", null}, //Mostrar CreditCard del Sponsor1
		
		//TEST POSITIVO:
		{"sponsor2", null}, //Mostrar CreditCard del Sponsor2
		
		//TEST NEGATIVO:
		{"brotherhood1",IllegalArgumentException.class}, //Mostrar CreditCard del Brotherhood1
		
		//TEST NEGATIVO;
		{"chapter1", IllegalArgumentException.class} // Mostrar CreditCard del Chapter1
		};
	
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listCreditCardBySponsorIdTemplate((String) testingData[i][0],(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}
	
	//Requierement: 16. An actor who is authenticated as a sponsor must be able to:
	//1. Manage his or her sponsorships which have credit cards

	//In the case of negative tests, the business rule that is intended to be broken:
	//Crear una CreditCard que no sea de un Sponsor
	//Crear una creditcatd con Month a null
						
	//Analysis of sentence coverage: Covered 100%
						
	//Analysis of data coverage: 177 / 177 total instructions
	
	@Test
	public void createCreditCardTestDrive(){

		final Object testingData[][] = {
		//TEST POSITIVO:
		{"sponsor1","Pepe","VISA","4024813453580367", 01,27,589, null}, //Crea una CreditCard
		
		//TEST NEGATIVO:
		{"brotherhood1","Matthew","VISA","4024813453580367",01 ,27,589, IllegalArgumentException.class}, //Crear una CreditCard que no sea de un Sponsor
		
		//TEST NEGATIVO:
		{"sponsor1","Luisa","VISA","4024813453580367",null ,27,589, ConstraintViolationException.class} // Crear una creditCard con Month a null
		};
		for(int i=0; i<testingData.length; i++){
			this.startTransaction();
			this.createCreditCardTemplate((String)testingData[i][0],(String) testingData[i][1],(String) testingData[i][2] ,(String) testingData[i][3],(Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],(Class<?>) testingData [i][7]);
			this.rollbackTransaction();
		}
	}
	
	//Requierement: 16. An actor who is authenticated as a sponsor must be able to:
	//1. Manage his or her sponsorships which have credit cards

	//In the case of negative tests, the business rule that is intended to be broken:
	//No puede borrar una creditcard que tenga asociado sponsorships
	//No se puede borrar una creditcard que no sea del actor logeado
	
	//Analysis of sentence coverage: Covered 100%
							
	//Analysis of data coverage: Covered 98 / 98 total instructions
	
	@Test
	public void deleteCreditCardTestDrive(){
		final Object testingData[][] = {
		//TEST POSITIVO:
		{"sponsor1", this.creditCardService.findOne(this.getEntityId("creditCard3")), null}, // Eliminar una Credit Card
				
		//TEST NEGATIVO:
		{"sponsor2", this.creditCardService.findOne(this.getEntityId("creditCard2")), IllegalArgumentException.class}, // No puede borrar una creditcard que tenga asociado sponsorships
		
		//TEST NEGATIVO:
		{"sponsor2", this.creditCardService.findOne(this.getEntityId("creditCard3")), IllegalArgumentException.class} // No puedes borrar una creditcard que no sea tuya.
		};
		
		for(int i=0; i<testingData.length; i++){
			this.startTransaction();
			this.deleteCreditCardTemplate((String) testingData[i][0], (CreditCard) testingData[i][1] , (Class<?>)testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	//Templates---------------------------------------------------------------------------------
	
	protected void listCreditCardBySponsorIdTemplate(final String username, final Class<?> expected ){
		Class<?> caught;
		
		caught = null;
		
		try{
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			int aux = this.getEntityId(username);
			
			this.creditCardService.findCreditCardsBySponsorId(aux);
			this.creditCardService.flush();
			
			unauthenticate();
		}catch (final Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void createCreditCardTemplate(final String username, final String holder, final String make, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer CVV , Class<?> expected){
		Class<?> caught;
		
		caught = null;
		try{
			super.authenticate(username);
			final CreditCard creditCard = this.creditCardService.create();
			
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCVV(CVV);
			
			this.creditCardService.save(creditCard);
			this.creditCardService.flush();
			this.unauthenticate();
		}catch(final Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void deleteCreditCardTemplate(final String username, final CreditCard creditCard, Class<?> expected){
		Class<?> caught;
		
		caught = null;
		try{
			super.authenticate(username);
			this.creditCardService.delete(creditCard);
			this.unauthenticate();
		}catch(final Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}

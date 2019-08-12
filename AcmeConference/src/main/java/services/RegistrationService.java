package services;

import java.util.Arrays;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.RegistrationRepository;
import domain.Author;
import domain.CreditCard;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	// Repositorios
	@Autowired
	private RegistrationRepository registrationRepository;

	// Servicios
	@Autowired
	private AuthorService authorService;

	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
	private ConfigurationService configurationService;

	// Constructors ------------------------------------

	public RegistrationService() {
		super();
	}

	public Registration create(final int conferenceId) {

		Assert.notNull(conferenceId);
		Registration result;
		Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Registration();
		result.setAuthor(principal);
		result.setConference(this.conferenceService.findOne(conferenceId));
		final CreditCard c = new CreditCard();
		result.setCreditCard(c);

		Assert.notNull(result.getConference());

		return result;
	}

	public Registration save(final Registration registration) {
		Assert.notNull(registration);
		
		Registration res = null;
		Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.isTrue(registration.getAuthor().getId() == principal.getId());
		
		if (principal instanceof Author) {
			Assert.isTrue(registration.getAuthor().getId() == principal
					.getId());
		}
			
			// Calling the repository
			res = this.registrationRepository.save(registration);

		return res;
	}

	public Registration findOne(final int registrationId) {

		final Registration reg = this.registrationRepository
				.findOne(registrationId);

		return reg;
	}

	public Collection<Registration> findAll() {
		Collection<Registration> result;

		result = this.registrationRepository.findAll();

		return result;

	}

	public Collection<Registration> findRegistrationsByAuthorId(
			final int authorId) {

		Collection<Registration> res;

		res = this.registrationRepository.findRegistrationsByAuthorId(authorId);

		return res;
	}

	public Collection<Registration> findRegistrationsByConferenceId(
			final int conferenceId) {

		Collection<Registration> res;

		res = this.registrationRepository.findRegistrationsByConferenceId(conferenceId);

		return res;
	}
	
	public void checkCreditCard(CreditCard creditCard, BindingResult binding){
		try{
		LocalDate date = new LocalDate();
		Integer actualYear 	= date.getYearOfCentury();
		Integer actualMonth = date.getMonthOfYear();
		Integer ccYear      = creditCard.getExpirationYear();
		Integer ccMonth     = creditCard.getExpirationMonth();
        boolean numeric 	= creditCard.getNumber().matches("-?\\d+(\\.\\d+)?");
        String[] makes 		= this.configurationService.findConfiguration().getMake().split(",");
        
        //Comprobamos el año
		if (ccYear < actualYear){
			binding.rejectValue("creditCard.expirationYear", "creditCard.expired");
		}
		 //Comprobamos el mes si el año coincide
		else if(ccYear == actualYear && ccMonth < actualMonth){
			binding.rejectValue("creditCard.expirationMonth", "creditCard.expired");
		}
		
		//Comprobamos que el número de tarjeta es numérico
		if(!numeric){
			binding.rejectValue("creditCard.number", "creditCard.not.numeric");
		}
		
		//Compobamos que la marca está en la lista de marcas
		if(!Arrays.asList(makes).contains(creditCard.getMake())){
			binding.rejectValue("creditCard.make", "creditCard.invalid.make");
		}

		} catch (Throwable oops){
			binding.rejectValue("creditCard.holder", "creditCard.error");
		}
		
	}
	

}

package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Sponsor;
import forms.SponsorForm;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorRepository sponsorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UserAccountService 	userAccountService;
	
	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------

	public SponsorService() {
		super();
	}

	// CREATE
	public Sponsor create(final String username, final String password) {

		final Sponsor res = new Sponsor();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		final List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);

		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		ua.setUsername(username);
		ua.setPassword(password);

		res.setUserAccount(ua);
		return res;
	}

	// SAVE
	public Sponsor save(final Sponsor sponsor) {

		Sponsor res;
		Sponsor principal;
		Assert.notNull(sponsor);

		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(
					passwordEncoder.encodePassword(sponsor.getUserAccount()
							.getPassword(), null));

		} else {

			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == sponsor
					.getUserAccount());
		}

		res = this.sponsorRepository.save(sponsor);

		return res;
	}

	// FIND ONE
	public Sponsor findOne(final int sponsorId) {

		Sponsor res;

		res = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Sponsor> findAll() {

		Collection<Sponsor> res;

		res = this.sponsorRepository.findAll();

		return res;
	}

	// OTHER METHODS ----------------------------------------------------------

	public Sponsor findByPrincipal() {

		Sponsor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findSponsorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Sponsor findSponsorByUserAccount(final int userAccountId) {

		Assert.isTrue(userAccountId != 0);

		Sponsor res;

		res = this.sponsorRepository.findSponsorByUserAccountId(userAccountId);

		Assert.notNull(res);

		return res;
	}

	public Sponsor findOneTrimmedByPrincipal() {

		Sponsor result;
		final Sponsor principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Sponsor();
		result.setName(principal.getName());
		result.setMiddleName(principal.getMiddleName());
		result.setSurname(principal.getSurname());
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setCreditCard(principal.getCreditCard());

		result.setId(principal.getId());

		return result;
	}

	public Sponsor reconstruct(final SponsorForm sponsorForm, final BindingResult binding) {

			// initialize variables
			final Pattern patron = Pattern.compile("^([0-9]+)$");
			final Matcher encaja = patron.matcher(sponsorForm.getPhoneNumber());
			Sponsor sponsor;

			if (encaja.find())
				sponsorForm.setPhoneNumber(configurationService.findConfiguration()
						.getCountryCode() + sponsorForm.getPhoneNumber());

			// Creating a new Author
			sponsor = this.create(sponsorForm.getUsername(), sponsorForm.getPassword());

			// Actor Atributes
			sponsor.setAddress(sponsorForm.getAddress());
			sponsor.setEmail(sponsorForm.getEmail());
			sponsor.setPhoto(sponsorForm.getPhoto());
			sponsor.setPhoneNumber(sponsorForm.getPhoneNumber());
			sponsor.setMiddleName(sponsorForm.getMiddleName());
			sponsor.setName(sponsorForm.getName());
			sponsor.setSurname(sponsorForm.getSurname());
			sponsor.setCreditCard(sponsorForm.getCreditCard());

			// Validating the form
			this.validator.validate(sponsorForm, binding);

			// Checking that the username is not taken
			if (this.userAccountService.findByUsername(sponsorForm.getUsername()) != null)
				binding.rejectValue("username", "actor.username.taken");

			// Checking that the passwords are the same
			if (!sponsorForm.getPassword().equals(sponsorForm.getPasswordConfirmation()))
				binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

			// Checking that the terms are accepted
			if (!sponsorForm.getCheckTerms())
				binding.rejectValue("checkTerms", "actor.uncheck");

			// Checking that the email match the pattern
			if (!(sponsorForm.getEmail()
					.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
					&& sponsorForm.getEmail().length() > 0)
				binding.rejectValue("email", "actor.email.check");
			
			// Checking that the creditCard is a valid one
			this.checkCreditCard(sponsor.getCreditCard(), binding);

			return sponsor;
		
	}

	public Sponsor construct(final Sponsor sponsor) {

		final Sponsor res = new Sponsor();

		res.setId(sponsor.getId());
		res.setName(sponsor.getName());
		res.setMiddleName(sponsor.getMiddleName());
		res.setSurname(sponsor.getSurname());
		res.setPhoto(sponsor.getPhoto());
		res.setEmail(sponsor.getEmail());
		res.setPhoneNumber(sponsor.getPhoneNumber());
		res.setAddress(sponsor.getAddress());

		return res;
	}

	// Reconstruct the full object from a trimmed one
	public Sponsor reconstruct(final Sponsor sponsor,
			final BindingResult binding) {
		Sponsor result;
		Sponsor aux;

		aux = this.findOne(sponsor.getId());
		result = new Sponsor();

		result.setMiddleName(sponsor.getMiddleName());
		result.setAddress(sponsor.getAddress());
		result.setEmail(sponsor.getEmail());
		result.setName(sponsor.getName());
		result.setPhoneNumber(sponsor.getPhoneNumber());
		result.setPhoto(sponsor.getPhoto());
		result.setSurname(sponsor.getSurname());

		result.setId(sponsor.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(sponsor.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& sponsor.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");
		
		return result;
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

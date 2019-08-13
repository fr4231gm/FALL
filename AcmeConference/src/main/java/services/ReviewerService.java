package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Reviewer;
import domain.Submission;
import forms.ReviewerForm;

@Service
@Transactional
public class ReviewerService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private ReviewerRepository reviewerRepository;
	

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UserAccountService 	userAccountService;
	
	@Autowired
	private Validator validator;



	// Simple CRUDs methods ---------------------------------------------------
	// CREATE
	public Reviewer create(final String username, final String password) {

		final Reviewer res = new Reviewer();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REVIEWER);
		final List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);

		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		final Collection<Submission> submissiones = new ArrayList<Submission>();
		res.setSubmissions(submissiones);
		
		ua.setUsername(username);
		ua.setPassword(password);

		res.setUserAccount(ua);
		return res;
	}

	public Reviewer save(Reviewer reviewer){
		Reviewer result, principal;
		Assert.notNull(reviewer);

		// Si el reviewer no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (reviewer.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			reviewer.getUserAccount()
					.setPassword(passwordEncoder.encodePassword(reviewer.getUserAccount().getPassword(), null));
		} else{
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == reviewer.getUserAccount());
		}
		
		// Guardamos en la bbdd
		result = this.reviewerRepository.save(reviewer);
		
		return result;
	}

	public Reviewer findOne(final int reviewerId) {
		Reviewer result;
		result = this.reviewerRepository.findOne(reviewerId);
		return result;

	}

	// Devuelve todos los reviewer de la bbdd
	public Collection<Reviewer> findAll() {
		Collection<Reviewer> result;
		result = this.reviewerRepository.findAll();
		return result;
	}

	// Metodo que devuelve el reviewer logueado en el sistema
	public Reviewer findByPrincipal() {
		Reviewer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findReviewerByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un reviewer a traves del ID de su cuenta de usuario
	// Servira para el metodo findByPrincipal()
	public Reviewer findReviewerByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Reviewer result;

		result = this.reviewerRepository.findReviewerByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}
	
	public Collection<Reviewer> findReviewersBySubmission(int submissionId){ 
		Collection<Reviewer> res; 
 
		res = this.reviewerRepository.findReviewersBySubmission(submissionId); 
 
		return res; 
	} 
	
	public void checkPrincipal() {
		Reviewer principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
	}

	public Reviewer reconstruct(ReviewerForm reviewerForm, BindingResult binding) {
		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(reviewerForm.getPhoneNumber());
		Reviewer reviewer;

		if (encaja.find())
			reviewerForm.setPhoneNumber(configurationService.findConfiguration()
					.getCountryCode() + reviewerForm.getPhoneNumber());

		// Creating a new Author
		reviewer = this.create(reviewerForm.getUsername(), reviewerForm.getPassword());

		// Actor Atributes
		reviewer.setAddress(reviewerForm.getAddress());
		reviewer.setEmail(reviewerForm.getEmail());
		reviewer.setPhoto(reviewerForm.getPhoto());
		reviewer.setPhoneNumber(reviewerForm.getPhoneNumber());
		reviewer.setMiddleName(reviewerForm.getMiddleName());
		reviewer.setName(reviewerForm.getName());
		reviewer.setSurname(reviewerForm.getSurname());
		reviewer.setKeywords(reviewerForm.getKeywords());

		// Validating the form
		this.validator.validate(reviewerForm, binding);

		// Checking that the username is not taken
		if (this.userAccountService.findByUsername(reviewerForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Checking that the passwords are the same
		if (!reviewerForm.getPassword().equals(reviewerForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Checking that the terms are accepted
		if (!reviewerForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Checking that the email match the pattern
		if (!(reviewerForm.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& reviewerForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");
		
		return reviewer;
	}

}

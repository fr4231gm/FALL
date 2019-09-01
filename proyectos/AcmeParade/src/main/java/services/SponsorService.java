
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

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
import domain.Sponsor;
import forms.SponsorForm;

@Service
@Transactional
public class SponsorService {

	// Managed repository --------------------
	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services -------------------
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private Validator				validator;


	// CRUDS Methods ----------------------

	public Sponsor create(final String username, final String password) {
		// Nuevo sponsor
		final Sponsor result = new Sponsor();

		// Creamos sus authorities para definir su rol en el sistema
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);

		// Creamos su cuenta de usuario y le establecemos sus authorities
		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		// Por defecto su cuenta de usuario esta activada
		ua.setEnabled(true);

		// El nombre de usuario es el que pasamos por parametro
		ua.setUsername(username);
		ua.setPassword(password);

		// Finalmente le establecemos al sponsor su cuenta de usuario
		result.setUserAccount(ua);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Sponsor result;
		Sponsor principal;
		Assert.notNull(sponsor);

		// Si el sponsor no esta en la base de datos (es una creacion, no una
		// actualizacion)
		// codificamos la contraseña de su cuenta de usuario
		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(passwordEncoder.encodePassword(sponsor.getUserAccount().getPassword(), null));

		} else {
			// Comprobamos que la persona logueda es un sponsor
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == sponsor.getUserAccount());
		}
		// Guardamos en la bbdd
		result = this.sponsorRepository.save(sponsor);
		if (sponsor.getId() == 0)
			this.boxService.generateDefaultFolders(result);
		return result;
	}

	// Devuelve todos los sponsor de la bbdd
	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(final int sponsorId) {

		Sponsor result;
		result = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.sponsorRepository.flush();
	}

	// Ancilliary methods ------------------------------------------------

	// Metodo que devuelve el sponsor logueado en el sistema

	public Sponsor findByPrincipal() {

		Sponsor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findSponsorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	// Metodo que devuelve un sponsor a traves del ID de su cuenta de usuario
	// Servira para el metodo findByPrincipal()

	public Sponsor findSponsorByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Sponsor result;

		result = this.sponsorRepository.findSponsorByUserAccount(userAccountId);
		Assert.notNull(result);

		return result;

	}

	//-------------------------------------------------------------------------------

	public Sponsor reconstruct(final SponsorForm form, final BindingResult binding) {
		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(form.getPhoneNumber());
		Sponsor sponsor;

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			form.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + form.getPhoneNumber());

		// Creating a new Sponsor
		sponsor = this.create(form.getUsername(), form.getPassword());

		// Actor Atributes
		sponsor.setAddress(form.getAddress());
		sponsor.setEmail(form.getEmail());
		sponsor.setPhoto(form.getPhoto());
		sponsor.setPhoneNumber(form.getPhoneNumber());
		sponsor.setMiddleName(form.getMiddleName());
		sponsor.setName(form.getName());
		sponsor.setSurname(form.getSurname());

		// Validating the form
		this.validator.validate(form, binding);

		// Checking that the username is not taken
		if (this.userAccountService.findByUserName(form.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Checking that the passwords are the same
		if (!form.getPassword().equals(form.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Checking that the terms are accepted
		if (!form.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Checking that the email match the pattern
		if (!(form.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && form.getEmail().length() > 0)
			binding.rejectValue("email", "sponsor.email.check");
		return sponsor;
	}

	public Sponsor findOneTrimmedByPrincipal() {
		// Initialize variables
		Sponsor result;
		final Sponsor principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Sponsor();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setMiddleName(principal.getMiddleName());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());

		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Sponsor reconstruct(final Sponsor sponsor, final BindingResult binding) {
		Sponsor result;
		Sponsor aux;

		aux = this.findOne(sponsor.getId());
		result = new Sponsor();

		result.setAddress(sponsor.getAddress());
		result.setEmail(sponsor.getEmail());
		result.setMiddleName(sponsor.getMiddleName());
		result.setName(sponsor.getName());
		result.setPhoneNumber(sponsor.getPhoneNumber());
		result.setPhoto(sponsor.getPhoto());
		result.setSurname(sponsor.getSurname());

		result.setId(sponsor.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(sponsor.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && sponsor.getEmail().length() > 0)
			binding.rejectValue("email", "sponsor.email.check");

		return result;
	}
}


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

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Finder;
import domain.Hacker;
import forms.HackerForm;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository		hackerRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;
	
	@Autowired
	private FinderService 			finderService;

	@Autowired
	private CreditCardService 		creditCardService;
	// CREATE
	// ---------------------------------------------------------------------------
	public Hacker create(final String username, final String password) {

		final Hacker res = new Hacker();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		final List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);

		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		ua.setEnabled(true);

		ua.setUsername(username);
		ua.setPassword(password);

		res.setUserAccount(ua);

		return res;
	}

	// SAVE
	public Hacker save(final Hacker hacker) {

		Hacker res;
		Hacker principal;
		Assert.notNull(hacker);

		// Si el id del hacker es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (hacker.getId() == 0) {
			
			Finder finder = this.finderService.create();
			Finder savedFinder = this.finderService.saveFinder(finder);
			hacker.setFinder(savedFinder);
			
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			hacker.getUserAccount().setPassword(passwordEncoder.encodePassword(hacker.getUserAccount().getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una hacker
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == hacker.getUserAccount());
		}

		// En cualquiera de los casos se guarda el hacker en la BBDD
		res = this.hackerRepository.save(hacker);

		return res;
	}
	
	public Hacker saveFirst(final HackerForm form,BindingResult binding) {
		Hacker hacker = this.reconstruct(form, binding);
		CreditCard creditCard = this.creditCardService.reconstruct(form, binding);

		Hacker res;
		Assert.notNull(hacker);
		if (hacker.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			hacker.getUserAccount().setPassword(passwordEncoder.encodePassword(hacker.getUserAccount().getPassword(), null));
		}
		// Guardamos primero el hacker y luego la tarjeta
		res = this.hackerRepository.save(hacker);
		creditCard.setActor(res);
		this.creditCardService.save(creditCard);

		return res;
	}

	// FIND ONE
	public Hacker findOne(final int hackerId) {

		// Declaracion de variables
		Hacker res;

		// Obtenemos el Hacker con la id indicada y comprobamos
		// que existe un Hacker con esa id
		res = this.hackerRepository.findOne(hackerId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Hacker> findAll() {

		// Declaracion de variables
		Collection<Hacker> res;

		// Obtenemos el conjunto de Hacker
		res = this.hackerRepository.findAll();

		return res;
	}

	// OTHER METHODS
	// --------------------------------------------------------------

	public Hacker findByPrincipal() {

		// Declaracion de variables
		Hacker res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Hacker a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findHackerByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Hacker findHackerByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Hacker res;

		// Obtenemos la Hacker a traves de la query creada en el repositorio
		res = this.hackerRepository.findHackerByUserAccount(userAccountId);

		// Comprobamos que nos ha devuelto una Hacker
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: HackerForm --> Hacker
	// Reconstruir una Hacker a partir de un hackerForm para registrarlo
	public Hacker reconstruct(final HackerForm hackerForm, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(hackerForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			hackerForm.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + hackerForm.getPhoneNumber());

		// Declarar variables
		Hacker hacker;

		// Crear un nuevo hacker
		hacker = this.create(hackerForm.getUsername(), hackerForm.getPassword());

		// Atributos de Actor
		hacker.setAddress(hackerForm.getAddress());
		hacker.setEmail(hackerForm.getEmail());
		hacker.setPhoto(hackerForm.getPhoto());
		hacker.setPhoneNumber(hackerForm.getPhoneNumber());
		hacker.setName(hackerForm.getName());
		hacker.setSurname(hackerForm.getSurname());
		hacker.setVatNumber(hackerForm.getVatNumber());

		// Validar formulario
		this.validator.validate(hackerForm, binding);

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(hackerForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!hackerForm.getPassword().equals(hackerForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!hackerForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(hackerForm.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && hackerForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return hacker;
	}

	public Hacker findOneTrimmedByPrincipal() {
		// Initialize variables
		Hacker result;
		final Hacker principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Hacker();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());
		result.setVatNumber(principal.getVatNumber());

		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Hacker reconstruct(final Hacker hacker, final BindingResult binding) {
		Hacker result;
		Hacker aux;

		aux = this.findOne(hacker.getId());
		result = new Hacker();

		result.setAddress(hacker.getAddress());
		result.setEmail(hacker.getEmail());
		result.setName(hacker.getName());
		result.setPhoneNumber(hacker.getPhoneNumber());
		result.setPhoto(hacker.getPhoto());
		result.setSurname(hacker.getSurname());
		result.setVatNumber(hacker.getVatNumber());

		result.setId(hacker.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		//result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(hacker.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && hacker.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.hackerRepository.flush();
	}
}

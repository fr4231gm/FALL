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

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Provider;
import forms.ProviderForm;

@Service
@Transactional
public class ProviderService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private ProviderRepository 		providerRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private Validator 				validator;
	
	@Autowired
	private UserAccountService 		userAccountService;

	@Autowired
	private ConfigurationService 	configurationService;
	
	@Autowired
	private BoxService 				boxService;
	
	// Simple CRUD methods -----------------------------------------------------

	// CREATE
	public Provider create(final String username, final String password) {

		final Provider res = new Provider();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
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
	public Provider save(final Provider provider) {

		Provider res;
		Provider principal;
		Assert.notNull(provider);

		// Si el id del provider es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (provider.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			provider.getUserAccount().setPassword(
					passwordEncoder.encodePassword(provider.getUserAccount()
							.getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una provider
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == provider
					.getUserAccount());
		}

		// En cualquiera de los casos se guarda el provider en la BBDD
		res = this.providerRepository.save(provider);

		if (provider.getId() == 0)
			this.boxService.generateDefaultFolders(res);
		
		return res;
	}

	// FIND ONE
	public Provider findOne(final int providerId) {

		// Declaracion de variables
		Provider res;

		// Obtenemos el Provider con la id indicada y comprobamos
		// que existe un Provider con esa id
		res = this.providerRepository.findOne(providerId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Provider> findAll() {

		// Declaracion de variables
		Collection<Provider> res;

		// Obtenemos el conjunto de Provider
		res = this.providerRepository.findAll();

		return res;
	}

	// OTHER METHODS ----------------------------------------------------------

	public Provider findByPrincipal() {

		// Declaracion de variables
		Provider res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Provider a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findProviderByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Provider findProviderByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Provider res;

		// Obtenemos la Provider a traves de la query creada en el repositorio
		res = this.providerRepository
				.findProviderByUserAccountId(userAccountId);

		// Comprobamos que nos ha devuelto una Provider
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: ProviderForm --> Provider
	// Reconstruir una provider a partir de un providerForm para registrarlo
	public Provider reconstruct(final ProviderForm providerForm,
			final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(providerForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			providerForm.setPhoneNumber(this.configurationService
					.findConfiguration().getCountryCode()
					+ providerForm.getPhoneNumber());

		// Declarar variables
		Provider provider;

		// Crear un nuevo Provider
		provider = this.create(providerForm.getUsername(),
				providerForm.getPassword());

		// Atributos de Actor
		provider.setAddress(providerForm.getAddress());
		provider.setEmail(providerForm.getEmail());
		provider.setPhoto(providerForm.getPhoto());
		provider.setPhoneNumber(providerForm.getPhoneNumber());
		provider.setName(providerForm.getName());
		provider.setSurname(providerForm.getSurname());
		provider.setVatNumber(providerForm.getVatNumber());
		provider.setMiddleName(providerForm.getMiddleName());
		
		// Atributos de Provider
		provider.setMake(providerForm.getMake());
		
		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(providerForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!providerForm.getPassword().equals(
				providerForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!providerForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(providerForm.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& providerForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return provider;
	}

	public Provider construct(final Provider provider) {

		final Provider res = new Provider();

		res.setId(provider.getId());
		res.setName(provider.getName());
		res.setSurname(provider.getSurname());
		res.setPhoto(provider.getPhoto());
		res.setEmail(provider.getEmail());
		res.setPhoneNumber(provider.getPhoneNumber());
		res.setAddress(provider.getAddress());
		res.setVatNumber(provider.getVatNumber());
		res.setMiddleName(provider.getMiddleName());
		res.setMake(provider.getMake());
		
		return res;
	}

	public Provider findOneTrimmedByPrincipal() {
		// Initialize variables
		Provider result;
		final Provider principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Provider();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());
		result.setVatNumber(principal.getVatNumber());
		result.setMiddleName(principal.getMiddleName());
		result.setMake(principal.getMake());
		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Provider reconstruct(final Provider provider,
			final BindingResult binding) {
		Provider result;
		Provider aux;

		aux = this.findOne(provider.getId());
		result = new Provider();

		result.setAddress(provider.getAddress());
		result.setEmail(provider.getEmail());
		result.setName(provider.getName());
		result.setPhoneNumber(provider.getPhoneNumber());
		result.setPhoto(provider.getPhoto());
		result.setSurname(provider.getSurname());
		result.setVatNumber(provider.getVatNumber());
		result.setMiddleName(provider.getMiddleName());
		result.setMake(provider.getMake());
		result.setId(provider.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(provider.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& provider.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.providerRepository.flush();
	}
}

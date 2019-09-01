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

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository customerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private Validator validator;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private BoxService boxService;

	// Simple CRUD methods ----------------------------------------------------

	// CREATE
	public Customer create(final String username, final String password) {

		final Customer res = new Customer();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
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
	public Customer save(final Customer customer) {

		Customer res;
		Customer principal;
		Assert.notNull(customer);

		// Si el id del customer es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (customer.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			customer.getUserAccount().setPassword(
					passwordEncoder.encodePassword(customer.getUserAccount()
							.getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una customer
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == customer
					.getUserAccount());
		}

		// En cualquiera de los casos se guarda el customer en la BBDD
		res = this.customerRepository.save(customer);

		if (customer.getId() == 0)
			this.boxService.generateDefaultFolders(res);
		
		return res;
	}

	// FIND ONE
	public Customer findOne(final int customerId) {

		// Declaracion de variables
		Customer res;

		// Obtenemos el Customer con la id indicada y comprobamos
		// que existe un Customer con esa id
		res = this.customerRepository.findOne(customerId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Customer> findAll() {

		// Declaracion de variables
		Collection<Customer> res;

		// Obtenemos el conjunto de Customer
		res = this.customerRepository.findAll();

		return res;
	}

	// OTHER METHODS ----------------------------------------------------------

	public Customer findByPrincipal() {

		// Declaracion de variables
		Customer res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Customer a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findCustomerByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Customer findCustomerByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Customer res;

		// Obtenemos la Customer a traves de la query creada en el repositorio
		res = this.customerRepository
				.findCustomerByUserAccountId(userAccountId);

		// Comprobamos que nos ha devuelto una Customer
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: CustomerForm --> Customer
	// Reconstruir una Customer a partir de un customerForm para registrarlo
	public Customer reconstruct(final CustomerForm customerForm,
			final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(customerForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			customerForm.setPhoneNumber(this.configurationService
					.findConfiguration().getCountryCode()
					+ customerForm.getPhoneNumber());

		// Declarar variables
		Customer customer;

		// Crear un nuevo customer
		customer = this.create(customerForm.getUsername(),
				customerForm.getPassword());

		// Atributos de Actor
		customer.setAddress(customerForm.getAddress());
		customer.setEmail(customerForm.getEmail());
		customer.setPhoto(customerForm.getPhoto());
		customer.setPhoneNumber(customerForm.getPhoneNumber());
		customer.setName(customerForm.getName());
		customer.setSurname(customerForm.getSurname());
		customer.setVatNumber(customerForm.getVatNumber());
		customer.setMiddleName(customerForm.getMiddleName());
		
		// Validar formulario
		//this.validator.validate(customerForm, binding);

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(customerForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!customerForm.getPassword().equals(
				customerForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!customerForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(customerForm.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& customerForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return customer;
	}

	public Customer findOneTrimmedByPrincipal() {
		// Initialize variables
		Customer result;
		final Customer principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Customer();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());
		result.setVatNumber(principal.getVatNumber());
		result.setMiddleName(principal.getMiddleName());
		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Customer reconstruct(final Customer customer,
			final BindingResult binding) {
		Customer result;
		Customer aux;

		aux = this.findOne(customer.getId());
		result = new Customer();

		result.setAddress(customer.getAddress());
		result.setEmail(customer.getEmail());
		result.setName(customer.getName());
		result.setPhoneNumber(customer.getPhoneNumber());
		result.setPhoto(customer.getPhoto());
		result.setSurname(customer.getSurname());
		result.setVatNumber(customer.getVatNumber());
		result.setMiddleName(customer.getMiddleName());
		result.setId(customer.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		// result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(customer.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& customer.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.customerRepository.flush();
	}

}

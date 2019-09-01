
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

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.Finder;
import domain.Inventory;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CompanyRepository		companyRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private FinderService			finderService;
	
	@Autowired
	private InventoryService		inventoryService;
	
	@Autowired
	private BoxService				boxService;

	// Constructors -----------------------------------------------------------

	public CompanyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// CREATE
	public Company create(final String username, final String password) {

		final Company res = new Company();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
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
	public Company save(final Company company) {

		Company res;
		Company principal;
		Assert.notNull(company);

		// Si el id del Company es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (company.getId() == 0) {
			final Finder finder = this.finderService.create();
			final Finder savedFinder = this.finderService.saveFinder(finder);
			company.setFinder(savedFinder);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			company.getUserAccount().setPassword(passwordEncoder.encodePassword(company.getUserAccount().getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una Company
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == company.getUserAccount());
		}

		// En cualquiera de los casos se guarda el Company en la BBDD

		res = this.companyRepository.save(company);
		
		if (company.getId() == 0)
			this.boxService.generateDefaultFolders(res);
		return res;
	}

	// FIND ONE
	public Company findOne(final int companyId) {

		// Declaracion de variables
		Company res;

		// Obtenemos la Company con la id indicada y comprobamos
		// que existe una Company con esa id
		res = this.companyRepository.findOne(companyId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Company> findAll() {

		// Declaracion de variables
		Collection<Company> res;

		// Obtenemos el conjunto de Company
		res = this.companyRepository.findAll();

		return res;
	}

	// OTHER METHODS ----------------------------------------------------------

	public Company findByPrincipal() {

		// Declaracion de variables
		Company res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Company a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findCompanyByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Company findCompanyByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Company res;

		// Obtenemos la Company a traves de la query creada en el repositorio
		res = this.companyRepository.findCompanyByUserAccountId(userAccountId);

		// Comprobamos que nos ha devuelto una Company
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: CompanyForm --> Company
	// Reconstruir una company a partir de un companyForm para registrarlo
	public Company reconstruct(final CompanyForm companyForm, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(companyForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			companyForm.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + companyForm.getPhoneNumber());

		// Declarar variables
		Company company;

		// Crear un nuevo Company
		company = this.create(companyForm.getUsername(), companyForm.getPassword());

		// Atributos de Actor
		company.setAddress(companyForm.getAddress());
		company.setEmail(companyForm.getEmail());
		company.setPhoto(companyForm.getPhoto());
		company.setPhoneNumber(companyForm.getPhoneNumber());
		company.setName(companyForm.getName());
		company.setSurname(companyForm.getSurname());
		company.setVatNumber(companyForm.getVatNumber());
		company.setMiddleName(companyForm.getMiddleName());

		// Atributos de Company
		company.setCommercialName(companyForm.getCommercialName());

		// Validar formulario
		//this.validator.validate(companyForm, binding);

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(companyForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!companyForm.getPassword().equals(companyForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!companyForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(companyForm.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && companyForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return company;
	}

	public Company construct(final Company company) {

		final Company res = new Company();

		res.setId(company.getId());
		res.setName(company.getName());
		res.setCommercialName(company.getCommercialName());
		res.setSurname(company.getSurname());
		res.setPhoto(company.getPhoto());
		res.setEmail(company.getEmail());
		res.setPhoneNumber(company.getPhoneNumber());
		res.setAddress(company.getAddress());
		res.setVatNumber(company.getVatNumber());
		res.setMiddleName(company.getMiddleName());

		return res;
	}

	public Company findOneTrimmedByPrincipal() {
		// Initialize variables
		Company result;
		final Company principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Company();
		result.setCommercialName(principal.getCommercialName());
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
	public Company reconstruct(final Company company, final BindingResult binding) {
		Company result;
		Company aux;

		aux = this.findOne(company.getId());
		result = new Company();

		result.setCommercialName(company.getCommercialName());
		result.setAddress(company.getAddress());
		result.setEmail(company.getEmail());
		result.setName(company.getName());
		result.setPhoneNumber(company.getPhoneNumber());
		result.setPhoto(company.getPhoto());
		result.setSurname(company.getSurname());
		result.setVatNumber(company.getVatNumber());
		result.setMiddleName(company.getMiddleName());

		result.setId(company.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(company.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && company.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.companyRepository.flush();
	}
	
	public Collection<Company> findCompaniesAvailableToAttendOrders(){
		
		Collection<Company> res = new ArrayList<>();
		Collection<Inventory> inventories = this.inventoryService.findAll();
		
		for(Inventory i: inventories){
			if(this.inventoryService.haveMaterialActivePrinterAndMaterialNameSpool(i) == true){
				res.add(i.getCompany());
			}
		}
		return res;
	}

}

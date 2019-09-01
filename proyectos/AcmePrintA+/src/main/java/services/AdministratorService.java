
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

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Comment;
import domain.Company;
import domain.Customer;
import domain.Designer;
import domain.Provider;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService			loginService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CommentService			commentService;
	
	@Autowired
	private BoxService				boxService;


	// Simple CRUDs methods ---------------------------------------------------

	public Administrator create(final String username, final String password) {
		// Comprobamos que la persona logueada es un admnistrador
		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		// Nuevo administrador
		final Administrator result = new Administrator();

		// Creamos sus authorities para definir su rol en el sistema
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
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

		// Finalmente le establecemos al administador su cuenta de usuario
		result.setUserAccount(ua);

		return result;
	}

	public Administrator save(final Administrator admin) {
		Administrator result, principal;
		Assert.notNull(admin);

		// Comprobamos que la persona logueda es un admin
		// Solo un admin crea otros admin
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		// Si el administrador no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (admin.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

			admin.getUserAccount().setPassword(passwordEncoder.encodePassword(admin.getUserAccount().getPassword(), null));
		} else
			Assert.isTrue(principal.getUserAccount() == admin.getUserAccount());
		// Guardamos en la bbdd
		result = this.administratorRepository.save(admin);
		
		if (admin.getId() == 0)
			this.boxService.generateDefaultFolders(result);
		
		
		return result;
	}

	// Método para encontrar un administrador a traves de su ID
	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;

	}

	// Devuelve todos los administradores de la bbdd
	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Metodo que devuelve el administrador logueado en el sistema
	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findAdministratorByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un administrador a traves del ID de su cuenta de
	// usuario
	// Servira para el metodo findByPrincipal()
	public Administrator findAdministratorByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Administrator result;

		result = this.administratorRepository.findAdministratorByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public void changeBan(final UserAccount ua) {
		Administrator principal;

		principal = this.findByPrincipal();
		Assert.isTrue(this.actorService.findByUserAccount(ua).getIsSpammer());
		Assert.notNull(principal);
		Assert.notNull(ua);

		ua.setEnabled(!ua.isEnabled());
		this.loginService.update(ua);
	}

	public void checkPrincipal() {
		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
	}

	// AdministratorForm methods ----------------------------------------------

	// Reconstruct a administrator from a administratorForm to register him
	public Administrator reconstruct(final AdministratorForm form, final BindingResult binding) {
		// initialize variables

		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(form.getPhoneNumber());
		Administrator administrator;

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			form.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + form.getPhoneNumber());

		// Creating a new Administrator
		administrator = this.create(form.getUsername(), form.getPassword());

		// Actor Atributes

		administrator.setAddress(form.getAddress());
		administrator.setEmail(form.getEmail());
		administrator.setPhoto(form.getPhoto());
		administrator.setPhoneNumber(form.getPhoneNumber());
		administrator.setVatNumber(form.getVatNumber());
		administrator.setName(form.getName());
		administrator.setSurname(form.getSurname());
		administrator.setMiddleName(form.getMiddleName());

		// Checking that the username is not taken
		if (this.userAccountService.findByUsername(form.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Checking that the passwords are the same
		if (!form.getPassword().equals(form.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Checking that the terms are accepted
		if (!form.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Checking that the email match the pattern
		if (!(form.getEmail().matches("[A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]|[A-Za-z_.]+[\\w]+@|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@+[\\>]")) && form.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		// Checking that the new VatNumber match the pattern
		if (!(form.getVatNumber().matches("(ES-?)?([0-9A-Z][0-9]{7}[A-Z])|([A-Z][0-9]{7}[0-9A-Z])")))
			binding.rejectValue("vatNumber", "administrator.vatNumber.check");

		return administrator;
	}

	public Administrator findOneTrimmedByPrincipal() {
		// Initialize variables
		Administrator result;

		final Administrator principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Administrator();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setVatNumber(principal.getVatNumber());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());
		result.setMiddleName(principal.getMiddleName());

		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Administrator reconstruct(final Administrator administrator, final BindingResult binding) {

		Administrator result;
		Administrator aux;

		aux = this.findOne(administrator.getId());
		result = new Administrator();

		result.setAddress(administrator.getAddress());
		result.setEmail(administrator.getEmail());
		result.setVatNumber(administrator.getVatNumber());
		result.setName(administrator.getName());
		result.setPhoneNumber(administrator.getPhoneNumber());
		result.setPhoto(administrator.getPhoto());
		result.setSurname(administrator.getSurname());
		result.setMiddleName(administrator.getMiddleName());

		result.setId(administrator.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(administrator.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && administrator.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		// Checking that the new VatNumber match the pattern
		if (!(administrator.getVatNumber().matches("(ES-?)?([0-9A-Z][0-9]{7}[A-Z])|([A-Z][0-9]{7}[0-9A-Z])")))
			binding.rejectValue("vatNumber", "administrator.vatNumber.check");

		return result;
	}

	public void flush() {

		this.administratorRepository.flush();
	}

	/*************************************************************************/

	// Launch a process that flags the actors of the system as spammers

	// Find Spammer Actors to flag as spammers
	public void flagSpammers() {
		this.checkPrincipal();
		Collection<Actor> aux = new ArrayList<>();
		aux = this.administratorRepository.findSpammersToFlag();
		if (!aux.isEmpty())
			for (final Actor a : aux) {
				a.setIsSpammer(true);
				this.actorService.update(a);
			}
	}

	public Double score(final int designerId) {
		Collection<Comment> comments = new ArrayList<>();
		Double score = 0.0;
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);

		comments = this.commentService.findCommentsByDesignerId(designerId);
		if (!comments.isEmpty()) {
			Integer taman = comments.size();
			for (final Comment c : comments)
				if (c.getScore() != null)
					score = score + c.getScore();
				else
					taman = taman - 1;
			score = score / taman;
		} else
			score = -1.0;

		return score;
	}

	/************************ DASHBOARD *************************************/

	// C - level --------------------------------------------------------------

	// The average, the minimum, the maximum, and the standard deviation of the
	// number of orders per customer.
	public Collection<Double> findAvgMinMaxStddevOrdersPerCustomer() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevOrdersPerCustomer();
		return res;
	}

	// The average, the minimum, the maximum, and the standard deviation of the
	// number of applications per order.
	public Collection<Double> findAvgMinMaxStddevApplicationsPerOrder() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevApplicationsPerOrder();
		return res;
	}

	// The average, the minimum, the maximum, and the standard deviation of the
	// price offered in the applications.
	public Collection<Double> findAvgMinMaxStddevOfferedPriceOfApplications() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevOfferedPriceOfApplications();
		return res;
	}

	// The ratio of pending applications.
	public Double findRatioApplicationsByStatusPending() {
		this.checkPrincipal();
		final Double res = this.administratorRepository.findRatioApplicationsByStatusPending();
		return res;
	}

	// The ratio of accepted applications.
	public Double findRatioApplicationsByStatusAccepted() {
		this.checkPrincipal();
		final Double res = this.administratorRepository.findRatioApplicationsByStatusAccepted();
		return res;
	}

	// The ratio of rejected applications.
	public Double findRatioApplicationsByStatusRejected() {
		this.checkPrincipal();
		final Double res = this.administratorRepository.findRatioApplicationsByStatusRejected();
		return res;
	}

	// The listing of customers who have published at least 10% more orders than
	// the average, ordered by number of applications.
	public List<Customer> findActiveCustomers() {
		this.checkPrincipal();
		List<Customer> aux = new ArrayList<>();
		List<Customer> res = new ArrayList<>();
		aux = this.administratorRepository.findActiveCustomers();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Customer unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The listing of companies who have got accepted at least 10% more
	// applications than the average, ordered by number of applications.
	public List<Company> findActiveCompanies() {
		this.checkPrincipal();
		List<Company> aux = new ArrayList<>();
		List<Company> res = new ArrayList<>();
		aux = this.administratorRepository.findActiveCompanies();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Company unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// B-level ----------------------------------------------------------------

	// The minimum, the maximum, the average, and the standard deviation of the
	// number of sponsorships per post.
	public Collection<Double> findAvgMinMaxStddevSponsorshipsPerPost() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevSponsorshipsPerPost();
		return res;
	}

	// The minimum, the maximum, the average, and the standard deviation of the
	// number of printers per company.
	public Collection<Double> findMinMaxAvgStddevPrintersPerCompany() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findMinMaxAvgStddevPrintersPerCompany();
		return res;
	}

	// The ratio of published orders whose pieces are in a printer spooler.
	public Double findRatioOnPrinterSpoolerVsPublished() {
		this.checkPrincipal();
		final Double onPrinterSpooler = this.administratorRepository.findOrdersOnPrinterSpooler();
		final Double published = this.administratorRepository.findOrdersPublished();
		Double res = 0.0;
		try {
			res = onPrinterSpooler / published;
		} catch (final ArithmeticException a) {
			res = 0.0;
		}
		return res;
	}

	// The ratio of post with and without sponsorships.
	public Double findRatioPostWithAndWithoutSponsorships() {
		this.checkPrincipal();
		final Double res = this.administratorRepository.findRatioPostWithAndWithoutSponsorships();
		return res;
	}

	// The top-five designers in terms of number of posts.
	public List<Designer> findTop5DesignersByPostsPublished() {
		this.checkPrincipal();
		List<Designer> aux = new ArrayList<>();
		List<Designer> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5DesignersByPostsPublished();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Designer unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The top-five designers in terms of score.
	public List<Designer> findTop5DesignersByScore() {
		this.checkPrincipal();
		List<Designer> aux = new ArrayList<>();
		List<Designer> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5DesignersByScore();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Designer unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The top-five designers in terms of sponsorships received.
	public List<Designer> findTop5DesignersBySponsorshipsReceived() {
		this.checkPrincipal();
		List<Designer> aux = new ArrayList<>();
		List<Designer> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5DesignersBySponsorshipsReceived();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Designer unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The top-five companies in terms of number of printers.
	public List<Company> findCompaniesWithMorePrinters() {
		this.checkPrincipal();
		List<Company> aux = new ArrayList<>();
		List<Company> res = new ArrayList<>();
		aux = this.administratorRepository.findCompaniesWithMorePrinters();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Company unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The top-five provider in terms of sponsorships.
	public List<Provider> findTop5Providers() {
		this.checkPrincipal();
		List<Provider> aux = new ArrayList<>();
		List<Provider> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5Providers();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Provider unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// A-level ----------------------------------------------------------------

	// The customers that place 10% more orders than the average.
	public List<Customer> findTop5Customers() {
		this.checkPrincipal();
		List<Customer> aux = new ArrayList<>();
		List<Customer> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5Customers();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Customer unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The top-five companies in terms of print spoolers.
	public List<Company> findCompaniesWithMoreSpools() {
		this.checkPrincipal();
		List<Company> aux = new ArrayList<>();
		List<Company> res = new ArrayList<>();
		aux = this.administratorRepository.findCompaniesWithMoreSpools();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Company unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// The top-five companies in terms of invoices.
	public List<Company> findTop5ActiveCompanies() {
		this.checkPrincipal();
		List<Company> aux = new ArrayList<>();
		List<Company> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5ActiveCompanies();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Company unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	/************************* END DASHBOARD *******************************/

}

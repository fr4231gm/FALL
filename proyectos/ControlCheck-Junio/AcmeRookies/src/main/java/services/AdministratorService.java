
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
import domain.Audit;
import domain.Company;
import domain.CreditCard;
import domain.Position;
import domain.Provider;
import domain.Rookie;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services -----------------------------
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
	private CreditCardService		creditCardService;

	@Autowired
	private AuditService			auditService;


	// Constructors ------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUDs methods

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

		return result;
	}

	public Administrator saveFirst(final Administrator result, final CreditCard creditCard) {
		Administrator principal;
		Assert.notNull(result);

		// Comprobamos que la persona logueda es un admin
		// Solo un admin crea otros admin
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		// Si el administrador no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (result.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

			result.getUserAccount().setPassword(passwordEncoder.encodePassword(result.getUserAccount().getPassword(), null));
		}
		// Guardamos en la bbdd
		final Administrator res = this.administratorRepository.save(result);
		creditCard.setActor(res);
		this.creditCardService.save(creditCard);

		return res;
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

		result.setId(administrator.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		// result.setPolarity(aux.getPolarity());

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

	/**************************** ACME-HACKER-RANK *****************************/

	// DASHBOARD --------------------------------------------------------------

	// C-level ----------------------------------------------------------------

	// C1: The average, the minimum, the maximum, and the standard deviation of
	// the number of positions per company.
	public Collection<Double> findAvgMinMaxStddevPositionsPerCompany() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevPositionsPerCompany();
		return res;

	}

	// C2: The average, the minimum, the maximum, and the standard deviation of
	// the number of applications per rookie.
	public Collection<Double> findAvgMinMaxStddevApplicationsPerRookie() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevApplicationsPerRookie();
		return res;
	}

	// C3: The companies that have offered more positions.
	public List<Company> findTop5PublishableCompanies() {
		this.checkPrincipal();
		List<Company> aux = new ArrayList<>();
		List<Company> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5PublishableCompanies();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Company unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// C4: The rookies who have made more applications.
	public List<Rookie> findTop5ApplyRookies() {
		this.checkPrincipal();
		List<Rookie> aux = new ArrayList<>();
		List<Rookie> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5ApplyRookies();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Rookie unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// C5: The average, the minimum, the maximum, and the standard deviation of
	// the salaries offered.
	public Collection<Double> findAvgMinMaxStddevSalaryOffered() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevSalaryOffered();
		return res;
	}

	// C6-1: The best and the worst position in terms of salary.
	// BEST
	public Collection<Position> findBestPositionBySalary() {
		this.checkPrincipal();
		List<Position> aux = new ArrayList<>();
		List<Position> res = new ArrayList<>();
		aux = this.administratorRepository.findBestPositionBySalary();
		if (aux.size() >= 2)
			res = aux.subList(0, 1);
		else
			for (int i = 0; i < aux.size(); i++) {
				final Position unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// C6-2: The best and the worst position in terms of salary.
	// WORST
	public Collection<Position> findWorstPositionBySalary() {
		this.checkPrincipal();
		List<Position> aux = new ArrayList<>();
		List<Position> res = new ArrayList<>();
		aux = this.administratorRepository.findWorstPositionBySalary();
		if (aux.size() >= 2)
			res = aux.subList(0, 1);
		else
			for (int i = 0; i < aux.size(); i++) {
				final Position unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// B1: The minimum, the maximum, the average, and the standard deviation of
	// the number of curricula per rookie.
	public Collection<Double> findMinMaxAvgStddevCurriculaPerRookie() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findMinMaxAvgStddevCurriculaPerRookie();
		return res;
	}

	// B2: The minimum, the maximum, the average, and the standard deviation of
	// the number of results in the finders.
	public Collection<Double> findMinMaxAvgStdevFindersResults() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findMinMaxAvgStdevFindersResults();
		return res;
	}

	// B3: The ratio of empty versus non-empty finders.
	public Double findRatioEmptyVsNotEmptyFinders() {
		this.checkPrincipal();
		final Double res = this.administratorRepository.findRatioEmptyVsNotEmptyFinders();
		return res;
	}

	/******************************* ACME-ROOKIE *******************************/
	// C1: The average, the minimum, the maximum, and the standard deviation of
	// the audit score of the positions stored in the system.

	public Collection<Double> findAvgMinMaxStddevAuditsScore() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevAuditsScore();
		return res;
	}

	// C2: The average, the minimum, the maximum, and the standard deviation of
	// the audit score of the companies that are registered in the system.
	@Query("select  " + "avg (1.0* c.score), " + "min (1.0* c.score), " + "max (1.0* c.score), " + "stddev (1.0* c.score) " + "from Company c")
	public Collection<Double> findAvgMinMaxStddevCompanyScore() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevCompanyScore();
		return res;
	}

	// C3: The companies with the highest audit score
	// We considered a Top3 companies with the highest audit score
	public List<Company> findTop5Companies() {
		this.checkPrincipal();
		List<Company> aux = new ArrayList<>();
		List<Company> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5Companies();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Company unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// C4: The average salary offered by the positions that have the highest
	// average audit score.
	public List<Double> findTopSalaryPositions() {
		this.checkPrincipal();
		List<Double> aux = new ArrayList<>();
		List<Double> res = new ArrayList<>();
		aux = this.administratorRepository.findTopSalaryPositions();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Double unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}

	// B1: The minimum, the maximum, the average, and the standard deviation of
	// the number of items per provider.
	public Collection<Double> findMinMaxAvgStddevItemsPerProvider() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findMinMaxAvgStddevItemsPerProvider();
		return res;
	}

	// B2: The top-5 providers in terms of total number of items provided
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

	// A1: The average, the minimum, the maximum, and the standard deviation of
	// the number of sponsorships per provider.
	// Note: We consider active sponsorships
	public Collection<Double> findAvgMinMaxStddevSponsorshipsPerProvider() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevSponsorshipsPerProvider();
		return res;
	}

	// A2: The average, the minimum, the maximum, and the standard deviation of
	// the number of sponsorships per position.
	// Note: We consider active sponsorships
	public Collection<Double> findAvgMinMaxStddevSponsorshipsPerPosition() {
		this.checkPrincipal();
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStddevSponsorshipsPerPosition();
		return res;
	}

	// A3: The providers who have a number of sponsorships that is at least 10%
	// above the average number of sponsorships per provider.
	public Collection<Provider> findActiveProviders() {
		this.checkPrincipal();
		Collection<Provider> res = new ArrayList<>();
		res = this.administratorRepository.findActiveProviders();
		return res;
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

	// POLARITY

	public Double polarity(final int companyId) {
		Collection<Audit> audits = new ArrayList<>();
		Double polarity = 0.0;
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);

		audits = this.auditService.findAllPublishedAuditsByPosition(companyId);
		if (!audits.isEmpty()) {
			for (final Audit a : audits)
				polarity = polarity + a.getScore();
			polarity = polarity / audits.size();
		} else
			polarity = -1.0;

		return polarity;
	}

}


package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.Parade;
import domain.Position;
import domain.Sponsor;
import domain.Sponsorship;
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
	private ParadeService			paradeService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;


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
		// Por defecto su cuenta de usuario está activada
		ua.setEnabled(true);

		// El nombre de usuario es el que pasamos por par�metro
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

		// Si el administrador no est� en la base de datos (es una creaci�n, no
		// una actualizacion)
		// codificamos la contrase�a de su cuenta de usuario
		if (admin.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

			admin.getUserAccount().setPassword(passwordEncoder.encodePassword(admin.getUserAccount().getPassword(), null));
		} else
			// Si est� editando, comprobamos que el usuario loggeado es el due�o
			// de la cuenta

			Assert.isTrue(principal.getUserAccount() == admin.getUserAccount());

		// Guardamos en la bbdd
		result = this.administratorRepository.save(admin);
		if (admin.getId() == 0)
			this.boxService.generateDefaultFolders(result);
		return result;
	}

	// M�todo para encontrar un administrador a trav�s de su ID
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

		res = this.findAdministratorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un administrador a traves del ID de su cuenta de
	// usuario
	// Servira para el metodo findByPrincipal()
	public Administrator findAdministratorByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Administrator result;

		result = this.administratorRepository.findAdministratorByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public void changeBan(final UserAccount ua) {
		Administrator principal;

		principal = this.findByPrincipal();
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
	// DASHBOARD ---------------------------------------------------------------

	// C1: Return the average, the minimum, the maximum,
	// and the standard deviation of the number of members per brotherhood
	public Collection<Double> findMemberPerBrotherhoodStats() {

		Collection<Double> res = new ArrayList<>();

		res = this.administratorRepository.findMemberPerBrotherhoodStats();

		return res;
	}

	// C2: Return the largest brotherhood.
	public List<Brotherhood> findLargestBrotherhoods() {

		List<Brotherhood> aux = new ArrayList<>();
		List<Brotherhood> res = new ArrayList<>();

		aux = this.administratorRepository.findLargestBrotherhoods();
		if (aux.size() >= 3)
			res = aux.subList(0, 3);
		else
			for (int i = 0; i < aux.size() - 1; i++)
				res.add(i, aux.get(i));
		return res;
	}

	// C3: Return the smallest brotherhood.
	public List<Brotherhood> findSmallestBrotherhoods() {

		List<Brotherhood> aux = new ArrayList<>();
		List<Brotherhood> res = new ArrayList<>();

		aux = this.administratorRepository.findSmallestBrotherhoods();
		if (aux.size() >= 3)
			res = aux.subList(0, 3);
		else
			for (int i = 0; i < aux.size() - 1; i++)
				res.add(i, aux.get(i));
		return res;
	}

	// C4-1: Return the ratio of requests to march in a parade,
	// grouped by their status.APPROVED
	public Double findRatioRequestsByStatusApproved() {

		Double res = 0.;
		res = this.administratorRepository.findRatioRequestsByStatusApproved();
		return res;
	}

	// C4-2: Return the ratio of requests to march in a parade,
	// grouped by their status.REJECTED
	public Double findRatioRequestsByStatusRejected() {

		Double res = 0.;
		res = this.administratorRepository.findRatioRequestsByStatusRejected();

		return res;
	}

	// C4-3: Return the ratio of requests to march in a parade,
	// grouped by their status.PENDING
	public Double findRatioRequestsByStatusPending() {

		Double res;

		res = this.administratorRepository.findRatioRequestsByStatusPending();

		return res;
	}

	// C4-4: The ratio of requests to march grouped by status.APPROVED
	public Double findRatioRequestsByStatusApproved(final int paradeId) {

		Double res;

		res = this.administratorRepository.findRatioRequestsByStatusApproved(paradeId);

		return res;
	}

	// C4-5: The ratio of requests to march grouped by status.REJECTED
	public Double findRatioRequestsByStatusRejected(final int paradeId) {

		Double res;

		res = this.administratorRepository.findRatioRequestsByStatusRejected(paradeId);

		return res;
	}

	// C4-4: The ratio of requests to march grouped by status.PENDING
	public Double findRatioRequestsByStatusPending(final int paradeId) {

		Double res;

		res = this.administratorRepository.findRatioRequestsByStatusPending(paradeId);

		return res;
	}

	// C5: Return the parades that are going to be organised in 30 days or
	// less.
	public Collection<Parade> findFollowingParades(final Date start, final Date end) {

		Collection<Parade> res = new ArrayList<>();
		res = this.administratorRepository.findFollowingParades(start, end);

		return res;
	}

	// C6: The listing of members who have got at least 10% the maximum number
	// of request to march accepted.
	public Collection<Member> findMembersApproved() {

		Collection<Member> res = new ArrayList<>();
		res = this.administratorRepository.findMembersApproved();

		return res;
	}

	// C7: A histogram of positions.
	public Collection<Position> findPositionsHistogram() {

		Collection<Position> res = new ArrayList<>();
		res = this.administratorRepository.findPositionsHistogram();

		return res;

	}

	// B1-1: The ratio of the number of brotherhoods per area.
	public Double findRatioBrotherhoodsPerArea() {
		final Double res = this.administratorRepository.findRatioBrotherhoodsPerArea();
		return res;
	}

	// B1-2: The count of the number of brotherhoods per area.
	public Collection<Integer> findCountBrotherhoodsPerArea() {
		Collection<Integer> res = new ArrayList<>();

		res = this.administratorRepository.findCountBrotherhoodsPerArea();

		return res;
	}

	// B1-3: The min of the number of brotherhoods per area.
	// Just call findCountBrotherhoodsPerArea and get the last value.
	public Double findMinBrotherhoodsPerArea() {

		Double res;

		res = this.administratorRepository.findMinBrotherhoodsPerArea();

		return res;
	}

	// B1-4: The max of the number of brotherhoods per area.
	// Just call findCountBrotherhoodsPerArea and get the first value.
	public Double findMaxBrotherhoodsPerArea() {

		Double res;

		res = this.administratorRepository.findMaxBrotherhoodsPerArea();

		return res;
	}

	// B1-5: The avg of the number of brotherhoods per area.
	// Just call findRatioBrotherhoodsPerArea.
	public Double findAvgBrotherhoodsPerArea() {
		Double res;
		res = this.administratorRepository.findAvgBrotherhoodsPerArea();
		return res;
	}

	// B1-6: The standard deviation of the number of brotherhoods per area.
	public Double findStdevBrotherhoodsPerArea() {
		Double res;
		res = this.administratorRepository.findStddevBrotherhoodsPerArea();
		return res;
	}

	// B1: The ratio, the count, the minimum, the maximum, the average,
	// and the standard deviation of the number of brotherhoods per area.
	public Collection<Double> findRatioMinMaxAvgDesvBrotherhoodsPerArea() {
		final Collection<Double> res = new ArrayList<>();

		res.add(this.findRatioBrotherhoodsPerArea());
		res.add(this.findMinBrotherhoodsPerArea());
		res.add(this.findMaxBrotherhoodsPerArea());
		res.add(this.findAvgBrotherhoodsPerArea());
		res.add(this.findStdevBrotherhoodsPerArea());

		return res;
	}

	// B2: The minimum, the maximum, the average, and the standard deviation of
	// the number of results in the finders.
	public Collection<Double> findMinMaxAvgStdevFindersResults() {

		Collection<Double> res = new ArrayList<>();

		res = this.administratorRepository.findMinMaxAvgStdevFindersResults();

		return res;
	}

	// B3: The ratio of empty versus non-empty finders.
	public Double findRatioEmptyVsNotEmptyFinders() {
		final Double res = this.administratorRepository.findRatioEmptyVsNotEmptyFinders();
		return res;
	}

	// A+1: The ratio of spammers and not spammers.
	public Double findSpammersRatio() {
		final Double res = this.administratorRepository.findSpammersRatio();
		return res;
	}

	// A+2: The average polarity.
	public Double findPolarityAverage() {
		final Double res = this.administratorRepository.findPolarityAverage();
		return res;
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
		administrator.setMiddleName(form.getMiddleName());
		administrator.setName(form.getName());
		administrator.setSurname(form.getSurname());

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
		if (!(form.getEmail().matches("[A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]|[A-Za-z_.]+[\\w]+@|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@+[\\>]")) && form.getEmail().length() > 0)

			binding.rejectValue("email", "member.email.check");
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
		result.setMiddleName(principal.getMiddleName());
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
		result.setMiddleName(administrator.getMiddleName());
		result.setName(administrator.getName());
		result.setPhoneNumber(administrator.getPhoneNumber());
		result.setPhoto(administrator.getPhoto());
		result.setSurname(administrator.getSurname());

		result.setId(administrator.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(administrator.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && administrator.getEmail().length() > 0)

			binding.rejectValue("email", "member.email.check");

		return result;
	}

	public Collection<List<Double>> getParadeAndRatiosPendingApprovedRejected() {

		Collection<Parade> parades = new ArrayList<>();

		parades = this.paradeService.findAll();

		final Collection<List<Double>> res = new ArrayList<>();

		for (final Parade p : parades) {
			final ArrayList<Double> array = new ArrayList<>();
			array.add(new Double(p.getId()));
			array.add(this.findRatioRequestsByStatusPending(p.getId()));
			array.add(this.findRatioRequestsByStatusApproved(p.getId()));
			array.add(this.findRatioRequestsByStatusRejected(p.getId()));
			res.add(array);
		}

		return res;
	}

	// Score computing method
	public Double polarity(final Actor actor) {
		// Checking parameter
		Assert.notNull(actor);

		final List<Integer> l = new ArrayList<Integer>(this.actorService.countTotalPositiveAndNegativeWordsPerActor(actor));
		final Double p = (double) l.get(0);
		final Double n = (double) l.get(1);

		final Double score = (p - n) / (p + n);
		return score;

	}

	/***************************** ACME-PARADE ********************************/
	/********************************* queries ********************************/

	// C1:The average, the minimum, the maximum, and the standard deviation
	// of the number of records per history.
	public Collection<Double> findAvgMinMaxStdevRecordsPerHistory() {
		Collection<Double> res = new ArrayList<>();

		res = this.administratorRepository.findAvgMinMaxStdevRecordsPerHistory();

		return res;
	}

	// C2: The brotherhood with the largest history.
	// We consider the brotherhood whose history has the max number of records.
	public Brotherhood findLargestBrotherhood() {
		Brotherhood res;
		List<Brotherhood> aux = new ArrayList<>();
		aux = this.administratorRepository.findLargestBrotherhood();
		if (!aux.isEmpty())
			res = aux.get(0);
		else
			res = null;

		return res;
	}

	// C3: The brotherhoods whose history is larger than the average
	public List<Brotherhood> findBrotherhoodsLargestHistory() {
		List<Brotherhood> aux = new ArrayList<>();
		List<Brotherhood> res = new ArrayList<>();

		aux = this.administratorRepository.findBrotherhoodsLargestHistory();
		if (aux.size() >= 3)
			res = aux.subList(0, 3);
		else if (!aux.isEmpty() && (aux.size() > 1))
			for (int i = 0; i < aux.size() - 1; i++)
				res.add(i, aux.get(i));
		else if (!aux.isEmpty() && aux.size() == 1)
			res.add(0, aux.get(0));
		else
			res = new ArrayList<Brotherhood>();

		return res;
	}

	// B1: The ratio of areas that are not co-ordinated by any chapters.
	public Double findRatioAreasNotCoordinated() {
		Double res = 0.;
		res = this.administratorRepository.findRatioAreasNotCoordinated();
		return res;
	}

	// B2: The average, the minimum, the maximum, and the standard deviation of
	// the
	// number of parades co-ordinated by the chapters
	public Collection<Double> findAvgMinMaxStdevParadesPerChapters() {
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStdevParadesPerChapters();
		return res;
	}

	// B3: The chapters that co-ordinate at least 10% more parades than the
	// average.
	public Collection<Chapter> findActiveChapters() {
		Collection<Chapter> res = new ArrayList<>();
		res = this.administratorRepository.findActiveChapters();
		return res;
	}

	// B4: The ratio of parades in draft mode versus parades in final mode.
	public Double findRatioDraftVsFinalModeParades() {
		Double res = 0.;
		res = this.administratorRepository.findRatioDraftVsFinalModeParades();
		return res;
	}

	// B5: The ratio of parades in final mode grouped by status.
	public Collection<Double> findRatioParadesByStatus() {
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findRatioParadesByStatus();
		return res;
	}

	// A1: The ratio of active sponsorships.
	public Double findRatioActiveSponsorships() {
		Double res = 0.;
		res = this.administratorRepository.findRatioActiveSponsorships();
		return res;
	}

	// A2: The average, the minimum, the maximum, and the standard deviation of
	// active sponsorships per sponsor.
	public Collection<Double> findAvgMinMaxStdevSponsorshipsPerSponsor() {
		Collection<Double> res = new ArrayList<>();
		res = this.administratorRepository.findAvgMinMaxStdevSponsorshipsPerSponsor();
		return res;
	}

	// A3: The top-5 sponsors in terms of number of active sponsorships.
	public List<Sponsor> findTop5Sponsors() {
		List<Sponsor> aux = new ArrayList<>();
		List<Sponsor> res = new ArrayList<>();
		aux = this.administratorRepository.findTop5Sponsors();
		if (aux.size() >= 5)
			res = aux.subList(0, 5);
		else
			for (int i = 0; i < aux.size() - 1; i++) {
				final Sponsor unica = aux.get(i);
				res.add(unica);
			}
		return res;
	}
}

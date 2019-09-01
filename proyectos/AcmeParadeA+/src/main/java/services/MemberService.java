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

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Enrolment;
import domain.Finder;
import domain.Member;
import domain.Request;
import domain.SocialProfile;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	// Managed repository -----------------

	@Autowired
	private MemberRepository memberRepository;

	// Supporting services ----------------
	@Autowired
	private FinderService finderService;

	@Autowired
	private EnrolmentService enrolmentService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private SocialProfileService socialProfileService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private Validator validator;

	// CRUDS Methods ----------------------
	public Member create(String username, String password) {
		// Nuevo member
		final Member result = new Member();


		// Creamos sus authorities para definir su rol en el sistema
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MEMBER);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);

		// Creamos su cuenta de usuario y le establecemos sus authorities
		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		// Por defecto su cuenta de usuario esta activada
		ua.setEnabled(true);

		// El nombre de usuario es el que pasamos por parámetro
		ua.setUsername(username);
		ua.setPassword(password);

		// Finalmente le establecemos al member su cuenta de usuario
		result.setUserAccount(ua);

		return result;
	}

	public Member save(final Member member) {
		Member result;
		Member principal;
		Assert.notNull(member);

		// Si el member no esta en la base de datos (es una creacion, no una
		// actualizacion)
		// codificamos la contraseña de su cuenta de usuario
		if (member.getId() == 0) {
			Finder finder = this.finderService.create();
			Finder savedFinder = this.finderService.saveFinder(finder);
			member.setFinder(savedFinder);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			member.getUserAccount().setPassword(
					passwordEncoder.encodePassword(member.getUserAccount()
							.getPassword(), null));
			
		} else {
			// Comprobamos que la persona logueda es un member
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == member.getUserAccount());
		}
		// Guardamos en la bbdd
		result = this.memberRepository.save(member);
		if (member.getId() == 0) {
			this.boxService.generateDefaultFolders(result);
		}
		return result;
	}

	// Devuelve todos los member de la bbdd
	public Collection<Member> findAll() {
		Collection<Member> result;

		result = this.memberRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Member findOne(final int memberId) {

		Member result;
		result = this.memberRepository.findOne(memberId);
		Assert.notNull(result);

		return result;
	}

	// Other methods

	// Metodo que devuelve el member logueado en el sistema
	public Member findByPrincipal() {
		Member res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findMemberByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un member a traves del ID de su cuenta de usuario
	// Servira para el metodo findByPrincipal()
	public Member findMemberByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Member result;

		result = this.memberRepository.findMemberByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}

	// Devuelve todos los miembros de una hermandad concreta
	public Collection<Member> findMembersByBrotherhood(final int brotherhoodId) {
		final Collection<Member> result;

		result = this.memberRepository
				.findMembersByBrotherhoodId(brotherhoodId);

		return result;
	}

	// Reconstruct a member from a memberForm to register him
	public Member reconstruct(MemberForm form, BindingResult binding) {
		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(form.getPhoneNumber());
		Member member;

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			form.setPhoneNumber(configurationService.findConfiguration()
					.getCountryCode() + form.getPhoneNumber());

		// Creating a new Member
		member = this.create(form.getUsername(), form.getPassword());

		// Actor Atributes
		member.setAddress(form.getAddress());
		member.setEmail(form.getEmail());
		member.setPhoto(form.getPhoto());
		member.setPhoneNumber(form.getPhoneNumber());
		member.setMiddleName(form.getMiddleName());
		member.setName(form.getName());
		member.setSurname(form.getSurname());

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
		if (!(form.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& form.getEmail().length() > 0)
			binding.rejectValue("email", "member.email.check");
		return member;
	}

	public Member findOneTrimmedByPrincipal() {
		// Initialize variables
		Member result;
		Member principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Member();
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
	public Member reconstruct(Member member, BindingResult binding) {
		Member result;
		Member aux;

		aux = this.findOne(member.getId());
		result = new Member();

		result.setAddress(member.getAddress());
		result.setEmail(member.getEmail());
		result.setMiddleName(member.getMiddleName());
		result.setName(member.getName());
		result.setPhoneNumber(member.getPhoneNumber());
		result.setPhoto(member.getPhoto());
		result.setSurname(member.getSurname());

		result.setId(member.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		result.setPolarity(aux.getPolarity());
		result.setFinder(aux.getFinder());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(member.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& member.getEmail().length() > 0)
			binding.rejectValue("email", "member.email.check");

		return result;
	}

	public void deleteByUserDropOut(int memberId) {
		this.memberRepository.delete(memberId);
	}

	public String deleteUserAccount() {
		// Initialize variables
		String exportedData;
		Member principal;
		Finder finderToDelete;
		List<Enrolment> enrolmentsToDelete;
		List<Request> requestsToDelete;
		List<SocialProfile> socialProfilesToDelete;
		UserAccount userAccountToDelete;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		finderToDelete = principal.getFinder();
		enrolmentsToDelete = new ArrayList<Enrolment>(
				this.enrolmentService.findAllEnrolmentsByPrincipal());
		requestsToDelete = new ArrayList<Request>(
				this.requestService.findByPrincipal());
		socialProfilesToDelete = new ArrayList<SocialProfile>(
				this.socialProfileService.findByActor(principal.getId()));
		userAccountToDelete = principal.getUserAccount();

		exportedData = ("Username: " + userAccountToDelete.getUsername());

		// DELETING ENROLMENTS
		exportedData = exportedData + "ENROLMENTS:";
		for (int i = 0; i < enrolmentsToDelete.size(); i++) {
			exportedData = exportedData
					+ ("Enrolment to:"
							+ enrolmentsToDelete.get(i).getBrotherhood()
									.getTitle() + " Position:"
							+ enrolmentsToDelete.get(i).getPosition()
							+ " Moment:" + enrolmentsToDelete.get(i)
							.getMoment());
			if (enrolmentsToDelete.get(i).getDropOutMoment() != null) {
				exportedData = exportedData + "DropOutMoment"
						+ enrolmentsToDelete.get(i).getDropOutMoment();
			}
			exportedData = exportedData + "\r\n";
			this.enrolmentService
					.deleteByUserDropOut(enrolmentsToDelete.get(i));
		}

		// DELETING REQUESTS
		exportedData = exportedData + "REQUESTS: ";
		for (int i = 0; i < requestsToDelete.size(); i++) {
			exportedData = exportedData + "Request to:"
					+ requestsToDelete.get(i).getParade().getTicker()
					+ " Row:" + requestsToDelete.get(i).getRowRequest()
					+ "Column" + requestsToDelete.get(i).getColumnRequest()
					+ "Status: " + requestsToDelete.get(i).getRejectReason();
			if (requestsToDelete.get(i).getRejectReason() != null) {
				exportedData = exportedData + "Reject Reason"
						+ requestsToDelete.get(i).getRejectReason();
			}
			exportedData = exportedData + "\r\n";
			this.requestService.deleteByUserDropOut(requestsToDelete.get(i));
		}

		// DELETING SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfilesToDelete.size(); i++) {
			exportedData = exportedData + "Link"
					+ socialProfilesToDelete.get(i).getLink()
					+ " Social Network:"
					+ socialProfilesToDelete.get(i).getSocialNetwork() + "Nick"
					+ socialProfilesToDelete.get(i).getNick() + "\r\n";
			this.socialProfileService
					.deleteByUserDropOut(socialProfilesToDelete.get(i));
		}

		// DELETING BOXES AND MESSAGES
		this.boxService.deleteByUserDropOut(principal);

		this.deleteByUserDropOut(principal.getId());

		// DELETING FINDER
		exportedData = exportedData + "FINDER: ";
		exportedData = exportedData + "Parades:"
				+ finderToDelete.getParades() + "Keyword: "
				+ finderToDelete.getKeyWord() + "Area: "
				+ finderToDelete.getArea() + "StartDate: "
				+ finderToDelete.getStartDate() + "EndDate: "
				+ finderToDelete.getEndDate();
		this.finderService.deleteByUserDropOut(finderToDelete);

		return exportedData;
	}

	public String exportData() {
		// Initialize variables
		String exportedData;
		Member principal;
		Finder finder;
		List<Enrolment> enrolments;
		List<Request> requests;
		List<SocialProfile> socialProfiles;
		UserAccount userAccount;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		finder = principal.getFinder();
		enrolments = new ArrayList<Enrolment>(
				this.enrolmentService.findAllEnrolmentsByPrincipal());
		requests = new ArrayList<Request>(this.requestService.findByPrincipal());
		socialProfiles = new ArrayList<SocialProfile>(
				this.socialProfileService.findByActor(principal.getId()));
		userAccount = principal.getUserAccount();

		exportedData = ("Username: " + userAccount.getUsername());

		// ENROLMENTS
		exportedData = exportedData + "ENROLMENTS:";
		for (int i = 0; i < enrolments.size(); i++) {
			exportedData = exportedData
					+ ("Enrolment to:"
							+ enrolments.get(i).getBrotherhood().getTitle()
							+ " Position:" + enrolments.get(i).getPosition().getName()
							+ " Moment:" + enrolments.get(i).getMoment());
			if (enrolments.get(i).getDropOutMoment() != null) {
				exportedData = exportedData + "DropOutMoment"
						+ enrolments.get(i).getDropOutMoment();
			}
			exportedData = exportedData + "\r\n";
		}

		// REQUESTS
		exportedData = exportedData + "REQUESTS: ";
		for (int i = 0; i < requests.size(); i++) {
			exportedData = exportedData + "Request to:"
					+ requests.get(i).getParade().getTicker() + " Row:"
					+ requests.get(i).getRowRequest() + "Column"
					+ requests.get(i).getColumnRequest() + "Status: "
					+ requests.get(i).getRejectReason();
			if (requests.get(i).getRejectReason() != null) {
				exportedData = exportedData + "Reject Reason"
						+ requests.get(i).getRejectReason();
			}
			exportedData = exportedData + "\r\n";

		}

		// SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfiles.size(); i++) {
			exportedData = exportedData + "Link"
					+ socialProfiles.get(i).getLink() + " Social Network:"
					+ socialProfiles.get(i).getSocialNetwork() + "Nick"
					+ socialProfiles.get(i).getNick() + "\r\n";

		}

		// FINDER
		exportedData = exportedData + "FINDER: ";
		exportedData = exportedData + "Parades:" + finder.getParades()
				+ "Keyword: " + finder.getKeyWord() + "Area: "
				+ finder.getArea() + "StartDate: " + finder.getStartDate()
				+ "EndDate: " + finder.getEndDate();

		return exportedData;

	}
}

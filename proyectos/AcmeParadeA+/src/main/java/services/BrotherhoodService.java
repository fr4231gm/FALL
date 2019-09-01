
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

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Area;
import domain.Brotherhood;
import domain.Member;
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	// Suporting services
	@Autowired
	private MemberService			memberService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private BoxService				boxService;


	// CRUDS Methods ----------------------
	public Brotherhood create(final String username, final String password) {
		// Nueva brotherhood
		final Brotherhood result = new Brotherhood();

		// Creamos sus authorities para definir su rol en el sistema
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
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

		// Se inicializan las relaciones de brotherhood
		result.setArea(new Area());

		// Finalmente le establecemos al brotherhood su cuenta de usuario
		result.setUserAccount(ua);

		return result;
	}

	// SAVE
	public Brotherhood save(final Brotherhood brotherhood) {
		Brotherhood result;
		Brotherhood principal;
		Assert.notNull(brotherhood);

		// Si la hermandad no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (brotherhood.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			brotherhood.getUserAccount().setPassword(passwordEncoder.encodePassword(brotherhood.getUserAccount().getPassword(), null));
		} else {
			// Comprobamos que la persona logueda es una hermandad
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == brotherhood.getUserAccount());
		}
		// Guardamos en la bbdd
		result = this.brotherhoodRepository.save(brotherhood);
		if (brotherhood.getId() == 0)
			this.boxService.generateDefaultFolders(result);
		return result;
	}

	public Brotherhood findOne(final int brotherhoodId) {

		Brotherhood result;
		result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> res;
		res = this.brotherhoodRepository.findAll();
		return res;
	}

	// Other bussiness methods ------------------------------------------------

	// Metodo que devuelve el administrador logueado en el sistema
	public Brotherhood findByPrincipal() {
		Brotherhood res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findBrotherhoodByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un administrador a traves del ID de su cuenta de
	// usuario
	// Servira para el metodo findByPrincipal()
	public Brotherhood findBrotherhoodByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Brotherhood result;

		result = this.brotherhoodRepository.findBrotherhoodByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}

	// Metodo que devuelve una lista Brotherhood en las que el miembro logueado
	// no esta inscrito
	public Collection<Brotherhood> findBrotherhoodsWhithoutEnrolmentByPrincipal() {
		// Inicio las variables
		Collection<Brotherhood> res;
		Member principal;

		// Member logged
		principal = this.memberService.findByPrincipal();

		// Obtengo todas las brotherhoods
		res = this.brotherhoodRepository.findBrotherhoodsNotEnrolledByMember(principal.getId());

		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve una lista Brotherhood en las que el miembro logueado
	// esta inscrito
	public Collection<Brotherhood> findBrotherhoodsEnrolledByPrincipal() {
		// Inicio las variables
		Collection<Brotherhood> res;
		Member principal;

		// Member logged
		principal = this.memberService.findByPrincipal();

		// Obtengo todas las brotherhoods
		res = this.brotherhoodRepository.findBrotherhoodsEnrolledByMember(principal.getId());

		Assert.notNull(res);

		return res;
	}

	// Reconstruct: BrotherhoodForm --> Brotherhood
	// Reconstruct a brotherhood from a brotherhoodForm to register it
	public Brotherhood reconstruct(final BrotherhoodForm form, final BindingResult binding) {
		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(form.getPhoneNumber());
		Brotherhood brotherhood;

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			form.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + form.getPhoneNumber());

		// Creating a new Member
		brotherhood = this.create(form.getUsername(), form.getPassword());

		// Actor Atributes
		brotherhood.setAddress(form.getAddress());
		brotherhood.setEmail(form.getEmail());
		brotherhood.setPhoto(form.getPhoto());
		brotherhood.setPhoneNumber(form.getPhoneNumber());
		brotherhood.setMiddleName(form.getMiddleName());
		brotherhood.setName(form.getName());
		brotherhood.setSurname(form.getSurname());

		// Brotherhood Atributes
		brotherhood.setTitle(form.getTitle());
		brotherhood.setEstablishmentDate(form.getEstablishmentDate());
		brotherhood.setPictures(form.getPictures());
		brotherhood.setArea(form.getArea());

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

		if (form.getArea() == null)
			binding.rejectValue("area", "brotherhood.area.check");

		// Checking that the email match the pattern
		if (!(form.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && form.getEmail().length() > 0)
			binding.rejectValue("email", "member.email.check");

		// Checking that all the pictures URL are valid
		if (this.checkPictures(form.getPictures()))
			binding.rejectValue("pictures", "brotherhood.pictures.invalid");
		return brotherhood;
	}

	// Return true if at least one of the pictures is not an URL
	private boolean checkPictures(final String pictures) {
		boolean res = false;
		pictures.replace(" ", "");
		final String[] aux = pictures.split("\r\n");
		for (int i = 0; i < aux.length; i++)
			if (!(aux[i].matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) && aux[i].length() > 0)
				res = true;
		return res;
	}

	// Returns a trimmed Brotherhood
	public Brotherhood construct(final Brotherhood brotherhood) {

		final Brotherhood res = new Brotherhood();

		res.setId(brotherhood.getId());
		res.setName(brotherhood.getName());
		res.setMiddleName(brotherhood.getMiddleName());
		res.setSurname(brotherhood.getSurname());
		res.setPhoto(brotherhood.getPhoto());
		res.setEmail(brotherhood.getEmail());
		res.setPhoneNumber(brotherhood.getPhoneNumber());
		res.setAddress(brotherhood.getAddress());
		res.setArea(brotherhood.getArea());
		res.setTitle(brotherhood.getTitle());
		res.setPictures(brotherhood.getPictures());
		res.setEstablishmentDate(brotherhood.getEstablishmentDate());

		return res;
	}

	// Returns a trimmed Brotherhood By principal
	public Brotherhood findOneTrimmedByPrincipal() {
		// Initialize variables
		Brotherhood result;
		final Brotherhood principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Brotherhood();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setMiddleName(principal.getMiddleName());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());
		result.setArea(principal.getArea());
		result.setTitle(principal.getTitle());
		result.setPictures(principal.getPictures());
		result.setEstablishmentDate(principal.getEstablishmentDate());

		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult binding) {
		Brotherhood result;
		Brotherhood aux;

		aux = this.findOne(brotherhood.getId());
		result = new Brotherhood();

		result.setAddress(brotherhood.getAddress());
		result.setEmail(brotherhood.getEmail());
		result.setMiddleName(brotherhood.getMiddleName());
		result.setName(brotherhood.getName());
		result.setPhoneNumber(brotherhood.getPhoneNumber());
		result.setPhoto(brotherhood.getPhoto());
		result.setSurname(brotherhood.getSurname());

		result.setArea(brotherhood.getArea());
		result.setTitle(brotherhood.getTitle());
		result.setPictures(brotherhood.getPictures());
		result.setEstablishmentDate(brotherhood.getEstablishmentDate());

		result.setId(brotherhood.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(brotherhood.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && brotherhood.getEmail().length() > 0)
			binding.rejectValue("email", "member.email.check");
		if (brotherhood.getArea() == null)
			binding.rejectValue("area", "brotherhood.area.check");
		// Checking that all the pictures URL are valid
		if (this.checkPictures(brotherhood.getPictures()))
			binding.rejectValue("pictures", "brotherhood.pictures.invalid");
		return result;
	}

	public Collection<Member> enrolledMembers(final int id) {
		return this.brotherhoodRepository.findEnrolledMembers(id);
	}

	public Collection<Brotherhood> findBrotherhoodsByArea(final int areaId) {
		Assert.notNull(areaId);
		// Inicio las variables
		Collection<Brotherhood> res;

		// Obtengo las brotherhoods del area concreta
		res = this.brotherhoodRepository.findBrotherhoodsByArea(areaId);

		return res;
	}

	public void flush() {

		this.brotherhoodRepository.flush();
	}

}

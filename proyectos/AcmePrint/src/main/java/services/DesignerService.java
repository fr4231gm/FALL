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

import repositories.DesignerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Designer;
import forms.DesignerForm;

@Service
@Transactional
public class DesignerService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private DesignerRepository designerRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private Validator validator;
	
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private BoxService boxService;
	
	// Simple CRUD methods -----------------------------------------------------

	// CREATE
	public Designer create(final String username, final String password) {

		final Designer res = new Designer();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.DESIGNER);
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
	public Designer save(final Designer designer) {

		Designer res;
		Designer principal;
		Assert.notNull(designer);

		// Si el id del designer es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (designer.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			designer.getUserAccount().setPassword(
					passwordEncoder.encodePassword(designer.getUserAccount()
							.getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una designer
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == designer
					.getUserAccount());
		}

		// En cualquiera de los casos se guarda el designer en la BBDD
		res = this.designerRepository.save(designer);

		if (designer.getId() == 0)
			this.boxService.generateDefaultFolders(res);
		
		return res;
	}

	// FIND ONE
	public Designer findOne(final int designerId) {

		// Declaracion de variables
		Designer res;

		// Obtenemos el Designer con la id indicada y comprobamos
		// que existe un Designer con esa id
		res = this.designerRepository.findOne(designerId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Designer> findAll() {

		// Declaracion de variables
		Collection<Designer> res;

		// Obtenemos el conjunto de Designer
		res = this.designerRepository.findAll();

		return res;
	}

	// OTHER METHODS ----------------------------------------------------------

	public Designer findByPrincipal() {

		// Declaracion de variables
		Designer res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Designer a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findDesignerByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Designer findDesignerByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Designer res;

		// Obtenemos la Designer a traves de la query creada en el repositorio
		res = this.designerRepository
				.findDesignerByUserAccountId(userAccountId);

		// Comprobamos que nos ha devuelto una Designer
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: DesignerForm --> Designer
	// Reconstruir una designer a partir de un designerForm para registrarlo
	public Designer reconstruct(final DesignerForm designerForm,
			final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(designerForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			designerForm.setPhoneNumber(this.configurationService
					.findConfiguration().getCountryCode()
					+ designerForm.getPhoneNumber());

		// Declarar variables
		Designer designer;

		// Crear un nuevo Designer
		designer = this.create(designerForm.getUsername(),
				designerForm.getPassword());

		// Atributos de Actor
		designer.setAddress(designerForm.getAddress());
		designer.setEmail(designerForm.getEmail());
		designer.setPhoto(designerForm.getPhoto());
		designer.setPhoneNumber(designerForm.getPhoneNumber());
		designer.setName(designerForm.getName());
		designer.setSurname(designerForm.getSurname());
		designer.setVatNumber(designerForm.getVatNumber());
		designer.setMiddleName(designerForm.getMiddleName());
		
		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(designerForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!designerForm.getPassword().equals(
				designerForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!designerForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(designerForm.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& designerForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return designer;
	}

	public Designer construct(final Designer designer) {

		final Designer res = new Designer();

		res.setId(designer.getId());
		res.setName(designer.getName());
		res.setSurname(designer.getSurname());
		res.setPhoto(designer.getPhoto());
		res.setEmail(designer.getEmail());
		res.setPhoneNumber(designer.getPhoneNumber());
		res.setAddress(designer.getAddress());
		res.setVatNumber(designer.getVatNumber());
		res.setMiddleName(designer.getMiddleName());
		
		return res;
	}

	public Designer findOneTrimmedByPrincipal() {
		// Initialize variables
		Designer result;
		final Designer principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Designer();
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
	public Designer reconstruct(final Designer designer,
			final BindingResult binding) {
		Designer result;
		Designer aux;

		aux = this.findOne(designer.getId());
		result = new Designer();

		result.setAddress(designer.getAddress());
		result.setEmail(designer.getEmail());
		result.setName(designer.getName());
		result.setPhoneNumber(designer.getPhoneNumber());
		result.setPhoto(designer.getPhoto());
		result.setSurname(designer.getSurname());
		result.setVatNumber(designer.getVatNumber());
		result.setMiddleName(designer.getMiddleName());
		
		result.setId(designer.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(designer.getEmail()
				.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
				&& designer.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.designerRepository.flush();
	}
}

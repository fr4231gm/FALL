
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

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chapter;
import forms.ChapterForm;

@Service
@Transactional
public class ChapterService {

	// Managed repository ------------------

	@Autowired
	private ChapterRepository		chapterRepository;

	// Supporting services

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private BoxService				boxService;


	// CRUDS Methods ---------------------
	// CREATE
	public Chapter create(final String username, final String password) {

		// Declaramos nuevo Chapter
		final Chapter res = new Chapter();

		// Declaramos authorities para definir su rol en el sistema
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CHAPTER);
		final List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);

		// Crear cuenta de usuario del Chapter y establecer authorities
		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		// Por defecto la cuenta de usuario est� activada
		ua.setEnabled(true);

		// Pasamos el nombre de usuario por par�metros
		ua.setUsername(username);
		ua.setPassword(password);

		// Se establece la cuenta de usuario al Chapter
		res.setUserAccount(ua);

		return res;
	}

	// SAVE
	public Chapter save(final Chapter chapter) {

		// Declaracion de variables
		Chapter res;
		Chapter principal;
		Assert.notNull(chapter);

		// Si el id del Chapter es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (chapter.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			chapter.getUserAccount().setPassword(passwordEncoder.encodePassword(chapter.getUserAccount().getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una Chapter
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == chapter.getUserAccount());
		}

		// En cualquiera de los casos se guarda el chapter en la BBDD
		res = this.chapterRepository.save(chapter);
		if (chapter.getId() == 0)
			this.boxService.generateDefaultFolders(res);

		return res;
	}

	// FIND ONE
	public Chapter findOne(final int chapterId) {

		// Declaracion de variables
		Chapter res;

		// Obtenemos el Chapter con la id indicada y comprobamos
		// que existe un chapter con esa id
		res = this.chapterRepository.findOne(chapterId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Chapter> findAll() {

		// Declaracion de variables
		Collection<Chapter> res;

		// Obtenemos el conjunto de Chapters
		res = this.chapterRepository.findAll();

		return res;
	}

	// OTHER METHODS ------------------------------------

	// Metodo que devuelve el Chapter logeado en el sistema
	public Chapter findByPrincipal() {

		// Declaracion de variables
		Chapter res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Chapter a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findChapterByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un chapter a traves del ID de su cuenta de usuario
	// Servira para el metodo findByPrincipal()
	public Chapter findChapterByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Chapter res;

		// Obtenemos el Chapter a traves de la query creada en el repositorio
		res = this.chapterRepository.findChapterByUserAccount(userAccountId);

		// Comprobamos que nos ha devuelto un Chapter
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: ChapterForm --> Chapter
	// Reconstruir un chapter a partir de un chapterForm para registrarlo
	public Chapter reconstruct(final ChapterForm chapterForm, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(chapterForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			chapterForm.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + chapterForm.getPhoneNumber());

		// Declarar variables
		Chapter chapter;

		// Crear un nuevo Chapter
		chapter = this.create(chapterForm.getUsername(), chapterForm.getPassword());

		// Atributos de Actor
		chapter.setAddress(chapterForm.getAddress());
		chapter.setEmail(chapterForm.getEmail());
		chapter.setPhoto(chapterForm.getPhoto());
		chapter.setPhoneNumber(chapterForm.getPhoneNumber());
		chapter.setMiddleName(chapterForm.getMiddleName());
		chapter.setName(chapterForm.getName());
		chapter.setSurname(chapterForm.getSurname());

		// Atributos de Chapter
		chapter.setTitle(chapterForm.getTitle());

		// Validar formulario
		this.validator.validate(chapterForm, binding);

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUserName(chapterForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrase�as coinciden
		if (!chapterForm.getPassword().equals(chapterForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!chapterForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(chapterForm.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && chapterForm.getEmail().length() > 0)
			binding.rejectValue("email", "chapter.email.check");

		return chapter;
	}

	public Chapter findOneTrimmedByPrincipal() {
		// Initialize variables
		Chapter result;
		final Chapter principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Chapter();
		result.setTitle(principal.getTitle());
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
	public Chapter reconstruct(final Chapter chapter, final BindingResult binding) {
		Chapter result;
		Chapter aux;

		aux = this.findOne(chapter.getId());
		result = new Chapter();

		result.setTitle(chapter.getTitle());
		result.setAddress(chapter.getAddress());
		result.setEmail(chapter.getEmail());
		result.setMiddleName(chapter.getMiddleName());
		result.setName(chapter.getName());
		result.setPhoneNumber(chapter.getPhoneNumber());
		result.setPhoto(chapter.getPhoto());
		result.setSurname(chapter.getSurname());

		result.setId(chapter.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(chapter.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && chapter.getEmail().length() > 0)
			binding.rejectValue("email", "chapter.email.check");

		return result;
	}

	public void flush() {
		this.chapterRepository.flush();
	}

}

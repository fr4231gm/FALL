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

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Author;
import forms.ActorForm;

@Service
@Transactional
public class AuthorService {

	// Managed repository -----------------------------------------------------

		@Autowired
		private AuthorRepository authorRepository;
		
		@Autowired
		private Validator validator;

		@Autowired
		private ConfigurationService configurationService;
		
		@Autowired
		private UserAccountService userAccountService;

		// Simple CRUDs methods ---------------------------------------------------
		public Author create(String username, String password) {
			// Nueva author
			final Author result = new Author();

			// Creamos sus authorities para definir su rol en el sistema
			final Authority authority = new Authority();
			authority.setAuthority(Authority.AUTHOR);
			final List<Authority> authorities = new ArrayList<Authority>();
			authorities.add(authority);

			// Creamos su cuenta de usuario y le establecemos sus authorities
			final UserAccount ua = new UserAccount();
			ua.setAuthorities(authorities);
	

			// El nombre de usuario es el que pasamos por parametro
			ua.setUsername(username);
			ua.setPassword(password);


			// Finalmente le establecemos al author su cuenta de usuario
			result.setUserAccount(ua);

			return result;
		}
		
		public Author save(Author author){
			Author result;
			Assert.notNull(author);

			// Si el author no esta en la base de datos (es una creacion, no
			// una actualizacion)
			// codificamos la contrasena de su cuenta de usuario
			if (author.getId() == 0) {
				final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

				author.getUserAccount()
						.setPassword(passwordEncoder.encodePassword(author.getUserAccount().getPassword(), null));
			} else {
				Author principal = this.findByPrincipal();
				Assert.notNull(principal);
				Assert.isTrue(principal.getUserAccount() == author.getUserAccount());
			}
			// Guardamos en la bbdd
			result = this.authorRepository.save(author);
			
			return result;
		}

		public Author findOne(final int authorId) {
			Author result;

			result = this.authorRepository.findOne(authorId);

			return result;

		}

		// Devuelve todos los autores de la bbdd
		public Collection<Author> findAll() {
			Collection<Author> result;

			result = this.authorRepository.findAll();

			return result;
		}

		// Metodo que devuelve el author logueado en el sistema
		public Author findByPrincipal() {
			Author res;
			UserAccount userAccount;

			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);

			res = this.findAuthorByUserAccountId(userAccount.getId());
			Assert.notNull(res);

			return res;
		}

		// Metodo que devuelve un administrador a traves del ID de su cuenta de
		// usuario
		// Servira para el metodo findByPrincipal()
		public Author findAuthorByUserAccountId(final int userAccountId) {
			Assert.isTrue(userAccountId != 0);

			Author result;

			result = this.authorRepository
					.findAuthorByUserAccount(userAccountId);

			Assert.notNull(result);

			return result;
		}

		public void checkPrincipal() {
			Author principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
		}

		public Author reconstruct(final ActorForm actorForm, final BindingResult binding) {

			// initialize variables
			final Pattern patron = Pattern.compile("^([0-9]+)$");
			final Matcher encaja = patron.matcher(actorForm.getPhoneNumber());
			Author author;

			if (encaja.find())
				actorForm.setPhoneNumber(configurationService.findConfiguration()
						.getCountryCode() + actorForm.getPhoneNumber());

			// Creating a new Author
			author = this.create(actorForm.getUsername(), actorForm.getPassword());

			// Actor Atributes
			author.setAddress(actorForm.getAddress());
			author.setEmail(actorForm.getEmail());
			author.setPhoto(actorForm.getPhoto());
			author.setPhoneNumber(actorForm.getPhoneNumber());
			author.setMiddleName(actorForm.getMiddleName());
			author.setName(actorForm.getName());
			author.setSurname(actorForm.getSurname());

			// Validating the form
			this.validator.validate(actorForm, binding);

			// Checking that the username is not taken
			if (this.userAccountService.findByUsername(actorForm.getUsername()) != null)
				binding.rejectValue("username", "actor.username.taken");

			// Checking that the passwords are the same
			if (!actorForm.getPassword().equals(actorForm.getPasswordConfirmation()))
				binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

			// Checking that the terms are accepted
			if (!actorForm.getCheckTerms())
				binding.rejectValue("checkTerms", "actor.uncheck");

			// Checking that the email match the pattern
			if (!(actorForm.getEmail()
					.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
					&& actorForm.getEmail().length() > 0)
				binding.rejectValue("email", "actor.email.check");


			return author;
		
	}

		public void flush() {
			this.authorRepository.flush();
			
		}
		
}

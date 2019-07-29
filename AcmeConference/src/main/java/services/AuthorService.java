package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	// Managed repository -----------------------------------------------------

		@Autowired
		private AuthorRepository authorRepository;

		// Simple CRUDs methods ---------------------------------------------------
		
		public Author save(Author author){
			Author result, principal;
			Assert.notNull(author);

			// Comprobamos que la persona logueda es un admin
			// Solo un admin crea otros admin
			principal = this.findByPrincipal();
			Assert.notNull(principal);

			// Si el administrador no esta en la base de datos (es una creacion, no
			// una actualizacion)
			// codificamos la contrasena de su cuenta de usuario
			if (author.getId() == 0) {
				final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

				author.getUserAccount()
						.setPassword(passwordEncoder.encodePassword(author.getUserAccount().getPassword(), null));
			} else
				Assert.isTrue(principal.getUserAccount() == author.getUserAccount());
			// Guardamos en la bbdd
			result = this.authorRepository.save(author);
			
			return result;
		}

		public Author findOne(final int authorId) {
			Author result;

			result = this.authorRepository.findOne(authorId);

			return result;

		}

		// Devuelve todos los administradores de la bbdd
		public Collection<Author> findAll() {
			Collection<Author> result;

			result = this.authorRepository.findAll();

			return result;
		}

		// Metodo que devuelve el administrador logueado en el sistema
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
}

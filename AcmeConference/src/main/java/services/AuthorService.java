package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Author;

import repositories.AuthorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AuthorService {

	// Repositorios
	@Autowired
	private AuthorRepository authorRepository;

	public Author findByPrincipal() {

		// Declaracion de variables
		Author res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Author a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findAuthorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Author findAuthorByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Author res;

		// Obtenemos la Author a traves de la query creada en el repositorio
		res = this.authorRepository.findAuthorByUserAccount(userAccountId);

		// Comprobamos que nos ha devuelto una Author
		Assert.notNull(res);

		return res;
	}

}

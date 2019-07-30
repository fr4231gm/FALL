
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services ----------------------------------------------------
	


	// Simple CRUDs methods ---------------------------------------------------

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

			admin.getUserAccount()
					.setPassword(passwordEncoder.encodePassword(admin.getUserAccount().getPassword(), null));
		} else
			Assert.isTrue(principal.getUserAccount() == admin.getUserAccount());
		// Guardamos en la bbdd
		result = this.administratorRepository.save(admin);

		return result;
	}

	// M�todo para encontrar un administrador a traves de su ID
	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;

	}

	// Devuelve todos los administradores de la bbdd
	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

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

	public void checkPrincipal() {
		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
	}

	public void flush() {

		this.administratorRepository.flush();
	}

}

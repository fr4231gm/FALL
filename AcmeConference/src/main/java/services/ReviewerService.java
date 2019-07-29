package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Reviewer;

@Service
@Transactional
public class ReviewerService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private ReviewerRepository reviewerRepository;
	
	// Simple CRUDs methods ---------------------------------------------------
	
	public Reviewer save(Reviewer reviewer){
		Reviewer result, principal;
		Assert.notNull(reviewer);

		// Comprobamos que la persona logueda es un admin
		// Solo un admin crea otros admin
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		// Si el administrador no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (reviewer.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

			reviewer.getUserAccount()
					.setPassword(passwordEncoder.encodePassword(reviewer.getUserAccount().getPassword(), null));
		} else
			Assert.isTrue(principal.getUserAccount() == reviewer.getUserAccount());
		// Guardamos en la bbdd
		result = this.reviewerRepository.save(reviewer);
		
		return result;
	}

	
	public Reviewer findOne(final int reviewerId) {
		Reviewer result;

		result = this.reviewerRepository.findOne(reviewerId);

		return result;

	}

	// Devuelve todos los administradores de la bbdd
	public Collection<Reviewer> findAll() {
		Collection<Reviewer> result;

		result = this.reviewerRepository.findAll();

		return result;
	}

	// Metodo que devuelve el administrador logueado en el sistema
	public Reviewer findByPrincipal() {
		Reviewer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findReviewerByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un administrador a traves del ID de su cuenta de
	// usuario
	// Servira para el metodo findByPrincipal()
	public Reviewer findReviewerByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Reviewer result;

		result = this.reviewerRepository.findReviewerByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {
		Reviewer principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
	}

}

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorRepository sponsorRepository;

	// Simple CRUDs methods ---------------------------------------------------
	
	public Sponsor save(Sponsor sponsor, CreditCard creditCard){
		Sponsor result, principal;
		Assert.notNull(sponsor);

		// Comprobamos que la persona logueda es un admin
		// Solo un admin crea otros admin
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		// Si el administrador no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

			sponsor.getUserAccount()
					.setPassword(passwordEncoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
		} else
			Assert.isTrue(principal.getUserAccount() == sponsor.getUserAccount());
		// Guardamos en la bbdd
		result = this.sponsorRepository.save(sponsor);
		// TODO:
		//creditCard.setActor(res);
		//this.creditCardService.save(creditCard);
		
		return result;
	}


	public Sponsor findOne(final int sponsorId) {
		Sponsor result;

		result = this.sponsorRepository.findOne(sponsorId);

		return result;

	}

	// Devuelve todos los administradores de la bbdd
	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();

		return result;
	}

	// Metodo que devuelve el administrador logueado en el sistema
	public Sponsor findByPrincipal() {
		Sponsor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findSponsorByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un administrador a traves del ID de su cuenta de
	// usuario
	// Servira para el metodo findByPrincipal()
	public Sponsor findSponsorByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Sponsor result;

		result = this.sponsorRepository
				.findSponsorByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {
		Sponsor principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
	}
}

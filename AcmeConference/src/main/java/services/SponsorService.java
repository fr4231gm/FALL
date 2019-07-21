package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Sponsor;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorRepository sponsorRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public SponsorService() {
		super();
	}

	// CREATE
	public Sponsor create(final String username, final String password) {

		final Sponsor res = new Sponsor();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		final List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);

		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		ua.setUsername(username);
		ua.setPassword(password);

		res.setUserAccount(ua);
		return res;
	}

	// SAVE
	public Sponsor save(final Sponsor sponsor) {

		Sponsor res;
		Sponsor principal;
		Assert.notNull(sponsor);

		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(
					passwordEncoder.encodePassword(sponsor.getUserAccount()
							.getPassword(), null));

		} else {

			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == sponsor
					.getUserAccount());
		}

		res = this.sponsorRepository.save(sponsor);

		return res;
	}

	// FIND ONE
	public Sponsor findOne(final int sponsorId) {

		Sponsor res;

		res = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Sponsor> findAll() {

		Collection<Sponsor> res;

		res = this.sponsorRepository.findAll();

		return res;
	}

	// OTHER METHODS ----------------------------------------------------------

	public Sponsor findByPrincipal() {

		Sponsor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findSponsorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Sponsor findSponsorByUserAccount(final int userAccountId) {

		Assert.isTrue(userAccountId != 0);

		Sponsor res;

		res = this.sponsorRepository.findSponsorByUserAccountId(userAccountId);

		Assert.notNull(res);

		return res;
	}

	public Sponsor findOneTrimmedByPrincipal() {
	
		Sponsor result;
		final Sponsor principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Sponsor();
		result.setName(principal.getName());
		result.setMiddleName(principal.getMiddleName());
		result.setSurname(principal.getSurname());
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setScore(principal.getScore());
		result.setCreditCard(principal.getCreditCard());

		result.setId(principal.getId());

		return result;
	}

}


package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository	sponsorRepository;
	@Autowired
	private BoxService			boxService;
	@Autowired
	private UserAccountService	userAccountService;

	public Sponsor create() {
		final Sponsor sponsor = new Sponsor();
		final Collection<Sponsorship> sp = new ArrayList<Sponsorship>();
		UserAccount ua = this.userAccountService.create("SPONSOR");
		sponsor.setUserAccount(ua);
		sponsor.setSponsorships(sp);
		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		boolean isNew = false;
		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(
					passwordEncoder.encodePassword(sponsor
							.getUserAccount().getPassword(), null));
			isNew = true;
			
		}
		Sponsor saved = this.sponsorRepository.save(sponsor);
		if(isNew){
			this.boxService.generateDefaultFolders(saved);
		}
		return saved;
	}

	public Sponsor findByPrincipal() {
		Sponsor result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			result = null;
		else
			result = this.sponsorRepository.finByUserAccountId(userAccount.getId());
		
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result = new ArrayList<Sponsor>();
		result = this.sponsorRepository.findAll();
		return result;
	}

}

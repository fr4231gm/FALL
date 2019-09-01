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
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;
import domain.Sponsorship;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	// Managed repository

	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private BoxService boxService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private HandyWorkerService handyworkerService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private RefereeService refereeService;
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private UserAccountService userAccountService;

	// Simple CRUD Methods --------------------------------

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int id) {
		Assert.notNull(id);
		return this.actorRepository.findOne(id);
	}

	// Other business methods ----------------------

	public Actor findByPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();

		final Actor a = this.actorRepository.findByUserAccountId(userAccount
				.getId());
		return a;
	}

	public Actor create() {
		final Actor Actor = new Actor();
		UserAccount ua = this.userAccountService.create("Actor");
		Actor.setUserAccount(ua);
		return Actor;
	}

	public boolean checkSpam(final String s) {
		boolean isSpam = false;
		List<Configuration> c1 = new ArrayList<Configuration>(
				this.configurationService.findAll());
		for (int i = 0; i < c1.size(); i++) {
			Configuration c = c1.get(i);
			String y = c.getSpamWords().replaceAll(" ", "");
			y = y.toLowerCase();
			String aux = s.toLowerCase();
			String[] spam = y.split("/n");
			for (final String e : spam) {
				if (aux.contains(e)) {
					isSpam = true;
				}
			}
		}
		return isSpam;
	}

	public Collection<Actor> findAllSuspiciousActors() {
		return this.actorRepository.findAllSuspiciousActors();
	}

	public Actor save(Actor a) {
		Assert.notNull(a);

        return this.actorRepository.save(a);
    }


	public void flush() {
		this.actorRepository.flush();

	}

	public void save(ActorForm actorForm) {
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		UserAccount ua;
		UserAccount account;
        final Pattern patron = Pattern.compile("^([0-9]+)$");
        final Matcher encaja = patron.matcher(actorForm.getPhoneNumber());
        if (encaja.find()){
        	actorForm.setPhoneNumber(configurationService.findConfiguration().getPNDefaultCountry() + actorForm.getPhoneNumber());}
		switch (actorForm.getRole()) {
		case "Customer":
			final Customer customer = this.customerService.create();
			customer.setName(actorForm.getName());
			customer.setSurname(actorForm.getSurname());
			customer.setAddress(actorForm.getAddress());
			customer.setEmail(actorForm.getEmail());
			customer.setPhoto(actorForm.getPhoto());
			customer.setPhoneNumber(actorForm.getPhoneNumber());
			customer.setMiddleName(actorForm.getMiddleName());
			customer.setId(actorForm.getId());
			customer.setVersion(actorForm.getVersion());
			auth.setAuthority("CUSTOMER");
			authorities.add(auth);
			ua = this.userAccountService.create("CUSTOMER");
			ua.setUsername(actorForm.getUsername());
			ua.setPassword(actorForm.getPassword());
			ua.setAuthorities(authorities);
			account = this.userAccountService.save(ua);
			customer.setUserAccount(account);
			customer.setIsSuspicious(actorForm.getIsSuspicious());
			this.customerService.save(customer);
			break;
		case "Handyworker":
			final HandyWorker hw = this.handyworkerService.create();
			hw.setName(actorForm.getName());
			hw.setSurname(actorForm.getSurname());
			hw.setAddress(actorForm.getAddress());
			hw.setEmail(actorForm.getEmail());
			hw.setPhoto(actorForm.getPhoto());
			hw.setPhoneNumber(actorForm.getPhoneNumber());
			hw.setMiddleName(actorForm.getMiddleName());
			hw.setId(actorForm.getId());
			hw.setVersion(actorForm.getVersion());
			if (actorForm.getMake() == null) {
				hw.setMake(actorForm.getName());
			} else {
				hw.setMake(actorForm.getMake());
			}
			auth.setAuthority("HANDYWORKER");
			authorities.add(auth);
			ua = this.userAccountService.create("HANDYWORKER");
			ua.setUsername(actorForm.getUsername());
			ua.setPassword(actorForm.getPassword());
			ua.setAuthorities(authorities);
			account = this.userAccountService.save(ua);
			hw.setUserAccount(account);
			hw.setIsSuspicious(actorForm.getIsSuspicious());
			this.handyworkerService.saveHandyWorker(hw);
			break;
		case "Sponsor":
			final Sponsor sponsor = this.sponsorService.create();
			sponsor.setName(actorForm.getName());
			sponsor.setSurname(actorForm.getSurname());
			sponsor.setAddress(actorForm.getAddress());
			sponsor.setEmail(actorForm.getEmail());
			sponsor.setPhoto(actorForm.getPhoto());
			sponsor.setPhoneNumber(actorForm.getPhoneNumber());
			sponsor.setMiddleName(actorForm.getMiddleName());
			sponsor.setId(actorForm.getId());
			sponsor.setVersion(actorForm.getVersion());
			auth.setAuthority("SPONSOR");
			authorities.add(auth);
			ua = this.userAccountService.create("SPONSOR");
			ua.setUsername(actorForm.getUsername());
			ua.setPassword(actorForm.getPassword());
			ua.setAuthorities(authorities);
			account = this.userAccountService.save(ua);
			sponsor.setUserAccount(account);
			sponsor.setIsSuspicious(actorForm.getIsSuspicious());
			this.sponsorService.save(sponsor);
			break;
		case "Referee":
			final Referee referee = this.refereeService.create();
			referee.setName(actorForm.getName());
			referee.setSurname(actorForm.getSurname());
			referee.setAddress(actorForm.getAddress());
			referee.setEmail(actorForm.getEmail());
			referee.setPhoto(actorForm.getPhoto());
			referee.setPhoneNumber(actorForm.getPhoneNumber());
			referee.setMiddleName(actorForm.getMiddleName());
			referee.setId(actorForm.getId());
			referee.setVersion(actorForm.getVersion());
			auth.setAuthority("REFEREE");
			authorities.add(auth);
			ua = this.userAccountService.create("REFEREE");
			ua.setUsername(actorForm.getUsername());
			ua.setPassword(actorForm.getPassword());
			ua.setAuthorities(authorities);
			account = this.userAccountService.save(ua);
			referee.setUserAccount(account);
			referee.setIsSuspicious(actorForm.getIsSuspicious());
			this.refereeService.save(referee);
			break;
		case "Admin":
			final Administrator admin = this.adminService.create();
			admin.setName(actorForm.getName());
			admin.setSurname(actorForm.getSurname());
			admin.setAddress(actorForm.getAddress());
			admin.setEmail(actorForm.getEmail());
			admin.setPhoto(actorForm.getPhoto());
			admin.setPhoneNumber(actorForm.getPhoneNumber());
			admin.setMiddleName(actorForm.getMiddleName());
			admin.setId(actorForm.getId());
			admin.setVersion(actorForm.getVersion());
			auth.setAuthority("ADMIN");
			authorities.add(auth);
			ua = this.userAccountService.create("ADMIN");
			ua.setUsername(actorForm.getUsername());
			ua.setPassword(actorForm.getPassword());
			ua.setAuthorities(authorities);
			account = this.userAccountService.save(ua);
			admin.setUserAccount(account);
			admin.setIsSuspicious(actorForm.getIsSuspicious());
			this.adminService.save(admin);
			break;
		}
	}
}

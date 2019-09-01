
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

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Audit;
import domain.Auditor;
import domain.CreditCard;
import domain.Message;
import domain.Wolem;
import domain.SocialProfile;
import forms.AuditorForm;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository		auditorRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private MessageService			messageService;
	
	@Autowired
	private AuditService			auditService;
	
	@Autowired
	private WolemService			wolemService;
	
	@Autowired
	private SocialProfileService	socialProfileService;


	//Constructor
	public AuditorService() {
		super();
	}

	//Create
	public Auditor create(final String username, final String password) {
		//Comprobamos que la persona logueada es un administrador
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		//Nuevo Auditor
		final Auditor result = new Auditor();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);

		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);
		ua.setEnabled(true);

		ua.setUsername(username);
		ua.setPassword(password);

		result.setUserAccount(ua);

		return result;
	}

	//Save
	public Auditor save(final Auditor auditor) {
		final Auditor principal = this.findByPrincipal();
		Auditor res;
		Assert.notNull(principal);
		Assert.notNull(auditor);

		Assert.isTrue(principal.getUserAccount() == auditor.getUserAccount());
		res = this.auditorRepository.save(auditor);

		return res;
	}

	// SAVE
	public Auditor saveFirst(final Auditor auditor, final CreditCard creditCard) {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Auditor res;
		Assert.notNull(auditor);

		if (auditor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			auditor.getUserAccount().setPassword(passwordEncoder.encodePassword(auditor.getUserAccount().getPassword(), null));
		}

		res = this.auditorRepository.save(auditor);
		creditCard.setActor(res);
		this.creditCardService.save(creditCard);

		return res;
	}

	// OTHER METHODS
	// FIND ONE
	public Auditor findOne(final int auditorId) {

		Auditor res;

		res = this.auditorRepository.findOne(auditorId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Auditor> findAll() {

		Collection<Auditor> res;

		res = this.auditorRepository.findAll();

		return res;
	}

	// FindByPrincipal
	public Auditor findByPrincipal() {

		// Declaracion de variables
		Auditor res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findAuditorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}
	// FindAuditorByUserAccount
	public Auditor findAuditorByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Auditor res;

		res = this.auditorRepository.findAuditorByUserAccount(userAccountId);
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: ProviderForm --> Provider
	public Auditor reconstruct(final AuditorForm form, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(form.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			form.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + form.getPhoneNumber());

		// Declarar variables
		Auditor auditor;

		// Crear un nuevo Auditor
		auditor = this.create(form.getUsername(), form.getPassword());

		// Atributos de Actor
		auditor.setAddress(form.getAddress());
		auditor.setEmail(form.getEmail());
		auditor.setPhoto(form.getPhoto());
		auditor.setPhoneNumber(form.getPhoneNumber());
		auditor.setName(form.getName());
		auditor.setSurname(form.getSurname());
		auditor.setVatNumber(form.getVatNumber());

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(form.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!form.getPassword().equals(form.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!form.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(form.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && form.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return auditor;
	}

	public Auditor construct(final Auditor auditor) {

		final Auditor res = new Auditor();

		res.setId(auditor.getId());
		res.setName(auditor.getName());
		res.setSurname(auditor.getSurname());
		res.setPhoto(auditor.getPhoto());
		res.setEmail(auditor.getEmail());
		res.setPhoneNumber(auditor.getPhoneNumber());
		res.setAddress(auditor.getAddress());
		res.setVatNumber(auditor.getVatNumber());

		return res;
	}

	public Auditor findOneTrimmedByPrincipal() {
		// Initialize variables
		Auditor result;
		final Auditor principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Auditor();
		result.setAddress(principal.getAddress());
		result.setEmail(principal.getEmail());
		result.setName(principal.getName());
		result.setPhoneNumber(principal.getPhoneNumber());
		result.setPhoto(principal.getPhoto());
		result.setSurname(principal.getSurname());
		result.setVatNumber(principal.getVatNumber());

		result.setId(principal.getId());

		return result;
	}

	// Reconstruct the full object from a trimmed one
	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {
		Auditor result;
		Auditor aux;

		aux = this.findOne(auditor.getId());
		result = new Auditor();

		result.setAddress(auditor.getAddress());
		result.setEmail(auditor.getEmail());
		result.setName(auditor.getName());
		result.setPhoneNumber(auditor.getPhoneNumber());
		result.setPhoto(auditor.getPhoto());
		result.setSurname(auditor.getSurname());
		result.setVatNumber(auditor.getVatNumber());

		result.setId(auditor.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(auditor.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && auditor.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.auditorRepository.flush();
	}

	public void deleteByUserDropOut(int auditorId) {
		this.auditorRepository.delete(auditorId);
	}
	
	//Delete User Account
	public String deleteUserAccount() {
		// Initialize variables
		String exportedData;
		Auditor principal;
		UserAccount userAccountToDelete;
		CreditCard creditCardToDelete;
		List<Audit> auditsToDelete;
		List<SocialProfile> socialProfilesToDelete;
		List<Message> messagesToDelete;


		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToDelete = principal.getUserAccount();
		creditCardToDelete = this.creditCardService.findCreditCardByActorId(principal.getId());
		auditsToDelete = new ArrayList<Audit>(this.auditService.findByPrincipal());
		socialProfilesToDelete = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToDelete = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));


		exportedData = ("Username: " + userAccountToDelete.getUsername());

		// DELETING AUDITS
		exportedData = exportedData + "AUDITS: ";
		for (int i = 0; i < auditsToDelete.size(); i++) {
			List<Wolem> wolemsToDelete = new ArrayList<Wolem>(this.wolemService.wolemsToDeleteByAudit(auditsToDelete.get(i).getId()));
			
			exportedData = exportedData + 
					"Audit: " + i 
					+ "text: " + auditsToDelete.get(i).getText()
					+ "score: " + auditsToDelete.get(i).getScore()
					+ "is Draft: " + auditsToDelete.get(i).getIsDraft()
					+ "moment :" + auditsToDelete.get(i).getMoment()
					+ "position: " + auditsToDelete.get(i).getPosition().getTicker()
					;

			for (int j = 0; j < wolemsToDelete.size(); j++) {
				exportedData = exportedData 
						+ "Wolem: " + j
						+ "ticker: " +  wolemsToDelete.get(j).getTicker()
						+ "body: " +  wolemsToDelete.get(j).getBody()
						+ "publication Moment: " +  wolemsToDelete.get(j).getPublicationMoment()
						+ "picture: " +  wolemsToDelete.get(j).getPicture()
						+ "is Draft: " +  wolemsToDelete.get(j).getIsDraft()
						+ "\r\n";
						this.wolemService.deleteByUserDropOut(wolemsToDelete.get(j));
			}
			
			this.auditService.deleteByUserDropOut(auditsToDelete.get(i));
		}

		// DELETING SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfilesToDelete.size(); i++) {
			exportedData = exportedData + "Link"
					+ socialProfilesToDelete.get(i).getLink()
					+ " Social Network:"
					+ socialProfilesToDelete.get(i).getSocialNetwork() + "Nick"
					+ socialProfilesToDelete.get(i).getNick() + "\r\n";
			this.socialProfileService.deleteByUserDropOut(socialProfilesToDelete.get(i));
		}

		// DELETING MESSAGES
		exportedData = exportedData + "MESSAGES: ";
		for (int i = 0; i < messagesToDelete.size(); i++) {
			exportedData = exportedData + "Message"
					+ " subject:" + messagesToDelete.get(i).getSubject()
					+ " body:" + messagesToDelete.get(i).getBody()
					+ " moment:" + messagesToDelete.get(i).getMoment()
					+ " tags:" + messagesToDelete.get(i).getTags()
					+ " is Spam:" + messagesToDelete.get(i).getIsSpam()
					+ "\r\n";
			this.messageService.deleteByUserDropOut(messagesToDelete.get(i));
		}

		// DELETING CREDIT CARD
		exportedData = exportedData + "CreditCard: ";
		exportedData = exportedData 
				+ "holder: " + creditCardToDelete.getHolder()
				+ "number: " + creditCardToDelete.getNumber()
				+ "CVV: " + creditCardToDelete.getCVV()
				+ "make: " + creditCardToDelete.getMake()
				+ "expiration Month: " + creditCardToDelete.getExpirationMonth()
				+ "expiation Year: " + creditCardToDelete.getExpirationYear()
				+ "\r\n";
		this.creditCardService.deleteByUserDropOut(creditCardToDelete);
		
		this.deleteByUserDropOut(principal.getId());
		
		return exportedData;
	}
	
	//Export Data
	public String exportData() {
		// Initialize variables
		String exportedData;
		Auditor principal;
		UserAccount userAccountToExport;
		CreditCard creditCardToExport;
		List<Audit> auditsToExport;
		List<SocialProfile> socialProfilesToExport;
		List<Message> messagesToExport;


		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToExport = principal.getUserAccount();
		creditCardToExport = this.creditCardService.findCreditCardByActorId(principal.getId());
		auditsToExport = new ArrayList<Audit>(this.auditService.findByPrincipal());
		socialProfilesToExport = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToExport = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));


		exportedData = ("Username: " + userAccountToExport.getUsername());

		// EXPORTING AUDITS
		exportedData = exportedData + "AUDITS: ";
		for (int i = 0; i < auditsToExport.size(); i++) {
			List<Wolem> wolemsToExport = new ArrayList<Wolem>(this.wolemService.wolemsToDeleteByAudit(auditsToExport.get(i).getId()));
			
			exportedData = exportedData + 
					"Audit: " + i 
					+ "text: " + auditsToExport.get(i).getText()
					+ "score: " + auditsToExport.get(i).getScore()
					+ "is Draft: " + auditsToExport.get(i).getIsDraft()
					+ "moment :" + auditsToExport.get(i).getMoment()
					+ "position: " + auditsToExport.get(i).getPosition().getTicker()
					;

			for (int j = 0; j < wolemsToExport.size(); j++) {
				exportedData = exportedData 
						+ "Wolem: " + j
						+ "ticker: " +  wolemsToExport.get(j).getTicker()
						+ "body: " +  wolemsToExport.get(j).getBody()
						+ "publication Moment: " +  wolemsToExport.get(j).getPublicationMoment()
						+ "picture: " +  wolemsToExport.get(j).getPicture()
						+ "is Draft: " +  wolemsToExport.get(j).getIsDraft()
						+ "\r\n";
			}
			
		}

		// EXPORTING SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfilesToExport.size(); i++) {
			exportedData = exportedData + "Link"
					+ socialProfilesToExport.get(i).getLink()
					+ " Social Network:"
					+ socialProfilesToExport.get(i).getSocialNetwork() + "Nick"
					+ socialProfilesToExport.get(i).getNick() + "\r\n";
		}

		// EXPORTING MESSAGES
		exportedData = exportedData + "MESSAGES: ";
		for (int i = 0; i < messagesToExport.size(); i++) {
			exportedData = exportedData + "Message"
					+ " subject:" + messagesToExport.get(i).getSubject()
					+ " body:" + messagesToExport.get(i).getBody()
					+ " moment:" + messagesToExport.get(i).getMoment()
					+ " tags:" + messagesToExport.get(i).getTags()
					+ " is Spam:" + messagesToExport.get(i).getIsSpam()
					+ "\r\n";
		}

		// EXPORTING CREDIT CARD
		exportedData = exportedData + "CreditCard: ";
		exportedData = exportedData 
				+ "holder: " + creditCardToExport.getHolder()
				+ "number: " + creditCardToExport.getNumber()
				+ "CVV: " + creditCardToExport.getCVV()
				+ "make: " + creditCardToExport.getMake()
				+ "expiration Month: " + creditCardToExport.getExpirationMonth()
				+ "expiation Year: " + creditCardToExport.getExpirationYear()
				+ "\r\n";
		
		return exportedData;
	}

	public Collection<Auditor> findByPosition(int positionId) {
		return this.auditorRepository.findByPosition(positionId);
	}

	public void updateByUserDropOut(Auditor auditorToUpdate) {
		this.auditorRepository.save(auditorToUpdate);
	}
}

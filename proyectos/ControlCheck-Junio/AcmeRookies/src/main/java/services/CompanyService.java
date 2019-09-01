
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

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Audit;
import domain.Auditor;
import domain.Charge;
import domain.Company;
import domain.CreditCard;
import domain.Finder;
import domain.Invoice;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.Wolem;
import domain.SocialProfile;
import domain.Sponsorship;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository		companyRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CreditCardService		creditCardService;
	
	@Autowired
	private PositionService			positionService;
	
	@Autowired
	private MessageService			messageService;
	
	@Autowired
	private FinderService			finderService;
	
	@Autowired
	private SponsorshipService 		sponsorshipService;
	
	@Autowired
	private SocialProfileService	socialProfileService;
	
	@Autowired
	private ApplicationService		applicationService;
	
	@Autowired
	private InvoiceService			invoiceService;
	
	@Autowired
	private WolemService			wolemService;
	
	@Autowired
	private ProblemService			problemService;
	
	@Autowired
	private AuditService			auditService;
	
	@Autowired
	private AuditorService			auditorService;


	// CREATE
	// ---------------------------------------------------------------------------
	public Company create(final String username, final String password) {

		final Company res = new Company();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		final List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);

		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		ua.setEnabled(true);

		ua.setUsername(username);
		ua.setPassword(password);

		res.setUserAccount(ua);

		return res;
	}

	// SAVE
	public Company save(final Company company) {

		Company res;
		Company principal;
		Assert.notNull(company);

		// Si el id del Company es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (company.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			company.getUserAccount().setPassword(passwordEncoder.encodePassword(company.getUserAccount().getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una Company
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == company.getUserAccount());
		}

		// En cualquiera de los casos se guarda el Company en la BBDD
		res = this.companyRepository.save(company);

		return res;
	}
	// SAVE
	public Company saveFirst(final Company company, final CreditCard creditCard) {

		Company res;
		Assert.notNull(company);

		if (company.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			company.getUserAccount().setPassword(passwordEncoder.encodePassword(company.getUserAccount().getPassword(), null));
		}

		res = this.companyRepository.save(company);
		creditCard.setActor(res);
		this.creditCardService.save(creditCard);

		return res;
	}
	// FIND ONE
	public Company findOne(final int companyId) {

		// Declaracion de variables
		Company res;

		// Obtenemos la Company con la id indicada y comprobamos
		// que existe una Company con esa id
		res = this.companyRepository.findOne(companyId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Company> findAll() {

		// Declaracion de variables
		Collection<Company> res;

		// Obtenemos el conjunto de Company
		res = this.companyRepository.findAll();

		return res;
	}

	// OTHER METHODS
	// --------------------------------------------------------------

	public Company findByPrincipal() {

		// Declaracion de variables
		Company res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Company a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findCompanyByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Company findCompanyByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Company res;

		// Obtenemos la Company a traves de la query creada en el repositorio
		res = this.companyRepository.findCompanyByUserAccount(userAccountId);

		// Comprobamos que nos ha devuelto una Company
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: CompanyForm --> Company
	// Reconstruir una company a partir de un companyForm para registrarlo
	public Company reconstruct(final CompanyForm companyForm, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(companyForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			companyForm.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + companyForm.getPhoneNumber());

		// Declarar variables
		Company company;

		// Crear un nuevo Company
		company = this.create(companyForm.getUsername(), companyForm.getPassword());

		// Atributos de Actor
		company.setAddress(companyForm.getAddress());
		company.setEmail(companyForm.getEmail());
		company.setPhoto(companyForm.getPhoto());
		company.setPhoneNumber(companyForm.getPhoneNumber());
		company.setName(companyForm.getName());
		company.setSurname(companyForm.getSurname());
		company.setVatNumber(companyForm.getVatNumber());

		// Atributos de Company
		company.setCommercialName(companyForm.getCommercialName());

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(companyForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!companyForm.getPassword().equals(companyForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!companyForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(companyForm.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && companyForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return company;
	}

	public Company construct(final Company company) {

		final Company res = new Company();

		res.setId(company.getId());
		res.setName(company.getName());
		res.setCommercialName(company.getCommercialName());
		res.setSurname(company.getSurname());
		res.setPhoto(company.getPhoto());
		res.setEmail(company.getEmail());
		res.setPhoneNumber(company.getPhoneNumber());
		res.setAddress(company.getAddress());
		res.setVatNumber(company.getVatNumber());
		//res.setPolarity(company.getPolarity());

		return res;
	}

	public Company findOneTrimmedByPrincipal() {
		// Initialize variables
		Company result;
		final Company principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Company();
		result.setCommercialName(principal.getCommercialName());
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
	public Company reconstruct(final Company company, final BindingResult binding) {
		Company result;
		Company aux;

		aux = this.findOne(company.getId());
		result = new Company();

		result.setCommercialName(company.getCommercialName());
		result.setAddress(company.getAddress());
		result.setEmail(company.getEmail());
		result.setName(company.getName());
		result.setPhoneNumber(company.getPhoneNumber());
		result.setPhoto(company.getPhoto());
		result.setSurname(company.getSurname());
		result.setVatNumber(company.getVatNumber());

		result.setId(company.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		// result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(company.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && company.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.companyRepository.flush();
	}

	private void deleteByUserDropOut(int companyId) {
		this.companyRepository.delete(companyId);
		
	}
	
	//Delete User Account
	public String deleteUserAccount() {
		// Initialize variables
		String exportedData;
		Company principal;
		UserAccount userAccountToDelete;
		CreditCard creditCardToDelete;
		List<SocialProfile> socialProfilesToDelete;
		List<Message> messagesToDelete;
		List<Position> positionsToDelete;
		List<Problem> problemsToDelete;
		List<Audit> auditsToDelete;
		List<Auditor> auditorsToUpdate;
		List<Sponsorship> sponsorshipsToDelete;
		List<Finder> findersToUpdate;
		List<Application> applicationsToDelete;
		
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToDelete = principal.getUserAccount();
		creditCardToDelete = this.creditCardService.findCreditCardByActorId(principal.getId());
		socialProfilesToDelete = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToDelete = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));
		positionsToDelete = new ArrayList<Position>(this.positionService.findPositionsByCompanyId(principal.getId()));
		problemsToDelete =  new ArrayList<Problem> (this.problemService.findProblemsByCompanyId(principal.getId()));
		
		exportedData = ("Username: " + userAccountToDelete.getUsername());

		// DELETING POSITIONS
		exportedData = exportedData + "POSITIONS: ";
		for (int k = 0; k < positionsToDelete.size(); k++) {
			auditsToDelete = new ArrayList<Audit>(this.auditService.findAllAuditsByPosition(positionsToDelete.get(k).getId()));
			auditorsToUpdate = new ArrayList<Auditor>(this.auditorService.findByPosition(positionsToDelete.get(k).getId()));
			sponsorshipsToDelete = new ArrayList<Sponsorship>(this.sponsorshipService.findAllSponsorshipsByPositionId(positionsToDelete.get(k).getId()));
			findersToUpdate = new ArrayList<Finder>(this.finderService.findByPositionId(positionsToDelete.get(k).getId()));
			applicationsToDelete = new ArrayList<Application>(this.applicationService.findByPosition(positionsToDelete.get(k).getId()));
			
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
				exportedData = exportedData + "WOLEMS: ";
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
			
			for (int i = 0; i < auditorsToUpdate.size(); i++) {
				Auditor auditorToUpdate = auditorsToUpdate.get(i);
				List<Position> auditorPositions = new ArrayList<Position>(auditorToUpdate.getPositions());
				auditorPositions.remove(positionsToDelete.get(k));
				auditorToUpdate.setPositions(auditorPositions);
				this.auditorService.updateByUserDropOut(auditorToUpdate);
			}
			
			exportedData = exportedData + "SPONSORSHIPS: ";
			for (int i = 0; i < sponsorshipsToDelete.size(); i++) {
				Invoice invoiceToDelete = this.invoiceService.findInvoiceBySponsorshipId(sponsorshipsToDelete.get(i).getId());
				exportedData = exportedData + 
						"Sponsorship: " + i 
						+ "banner: " + sponsorshipsToDelete.get(i).getBanner()
						+ "target Page: " + sponsorshipsToDelete.get(i).getTargetPage()
						+ "is Enabled: " + sponsorshipsToDelete.get(i).getIsEnabled()
						+ "\r\n";
				exportedData = exportedData + "INVOICE CHARGES: ";
				if(invoiceToDelete != null){
					List<Charge> chargesToDelete = new ArrayList<Charge>(invoiceToDelete.getCharges());
					for (int j = 0; j < chargesToDelete.size(); j++) {
						exportedData = exportedData + 
							"Charge: " + j
							+ "moment: " + chargesToDelete.get(j).getMoment()
							+ "amount: " + chargesToDelete.get(j).getAmount()
							+ "tax: " + chargesToDelete.get(j).getTax()
							+ "\r\n";
					}
					this.invoiceService.deleteByUserDropOut(invoiceToDelete);
				}
				this.sponsorshipService.deleteByUserDropOut(sponsorshipsToDelete.get(i));
			}
			
			for (int i = 0; i < findersToUpdate.size(); i++) {
				Finder finderToUpdate = findersToUpdate.get(i);
				List<Position> finderPositions = new ArrayList<Position>(finderToUpdate.getPositions());
				finderPositions.remove(positionsToDelete.get(k));
				finderToUpdate.setPositions(finderPositions);
				this.finderService.updateByUserDropOut(finderToUpdate);
			}
			
			exportedData = exportedData + "APPLICATIONS:";
			for (int i = 0; i < applicationsToDelete.size(); i++) {
				exportedData = exportedData
								+ "Application to: "
								+ applicationsToDelete.get(i).getPosition().getTitle() 
								+ "CreationMoment: "
								+ applicationsToDelete.get(i).getCreationMoment()
								+ "Answer: " 
								+ applicationsToDelete.get(i).getAnswer()
								+ "LinkCode: " 
								+ applicationsToDelete.get(i).getLinkCode()
								+ "Status: " 
								+ applicationsToDelete.get(i).getStatus()
								+ "SubmittedMoment: " 
								+ applicationsToDelete.get(i).getSubmittedMoment()
								+ "\r\n";

				exportedData = exportedData + "\r\n";
				this.applicationService
						.deleteByUserDropOut(applicationsToDelete.get(i));
			}
			this.positionService.deleteByUserDropOut(positionsToDelete.get(k));
			
		}

		// DELETING PROBLEMS
		exportedData = exportedData + "PROBLEMS: ";
		for (int i = 0; i < problemsToDelete.size(); i++) {
			exportedData = exportedData
					+ "title: " + problemsToDelete.get(i).getTitle()
					+ "statement: " + problemsToDelete.get(i).getStatement() 
					+ "hint: " + problemsToDelete.get(i).getHint() 
					+ "attachments: " + problemsToDelete.get(i).getAttachments() 
					+ "is Draft: " + problemsToDelete.get(i).getIsDraft() 
					+ "\r\n";
			this.problemService.deleteByUserDropOut(problemsToDelete.get(i));
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
				+ "expiration Year: " + creditCardToDelete.getExpirationYear()
				+ "\r\n";
		this.creditCardService.deleteByUserDropOut(creditCardToDelete);
		
		this.deleteByUserDropOut(principal.getId());
		
		return exportedData;
	}

	//Export Comapny Data
	public String exportData() {
		// Initialize variables
		String exportedData;
		Company principal;
		UserAccount userAccountToExport;
		CreditCard creditCardToExport;
		List<SocialProfile> socialProfilesToExport;
		List<Message> messagesToExport;
		List<Position> positionsToExport;
		List<Problem> problemsToExport;
		List<Audit> auditsToExport;
		List<Sponsorship> sponsorshipsToExport;
		List<Application> applicationsToExport;
		
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToExport = principal.getUserAccount();
		creditCardToExport = this.creditCardService.findCreditCardByActorId(principal.getId());
		socialProfilesToExport = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToExport = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));
		positionsToExport = new ArrayList<Position>(this.positionService.findPositionsByCompanyId(principal.getId()));
		problemsToExport =  new ArrayList<Problem> (this.problemService.findProblemsByCompanyId(principal.getId()));
		
		exportedData = ("Username: " + userAccountToExport.getUsername());

		// EXPORTING POSITIONS
		exportedData = exportedData + "POSITIONS: ";
		for (int k = 0; k < positionsToExport.size(); k++) {
			auditsToExport = new ArrayList<Audit>(this.auditService.findAllAuditsByPosition(positionsToExport.get(k).getId()));
			sponsorshipsToExport = new ArrayList<Sponsorship>(this.sponsorshipService.findAllSponsorshipsByPositionId(positionsToExport.get(k).getId()));
			applicationsToExport = new ArrayList<Application>(this.applicationService.findByPosition(positionsToExport.get(k).getId()));
			
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
				exportedData = exportedData + "WOLEMS: ";
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
			
			exportedData = exportedData + "SPONSORSHIPS: ";
			for (int i = 0; i < sponsorshipsToExport.size(); i++) {
				Invoice invoiceToExport = this.invoiceService.findInvoiceBySponsorshipId(sponsorshipsToExport.get(i).getId());

				exportedData = exportedData + 
						"Sponsorship: " + i 
						+ "banner: " + sponsorshipsToExport.get(i).getBanner()
						+ "target Page: " + sponsorshipsToExport.get(i).getTargetPage()
						+ "is Enabled: " + sponsorshipsToExport.get(i).getIsEnabled()
						+ "\r\n";
				if(invoiceToExport != null){
					List<Charge> chargesToExport = new ArrayList<Charge>(invoiceToExport.getCharges());
					exportedData = exportedData + "INVOICE CHARGES: ";
					for (int j = 0; j < chargesToExport.size(); j++) {
						exportedData = exportedData + 
							"Charge: " + j
							+ "moment: " + chargesToExport.get(j).getMoment()
							+ "amount: " + chargesToExport.get(j).getAmount()
							+ "tax: " + chargesToExport.get(j).getTax()
							+ "\r\n";
					}
				}
			}
			
			exportedData = exportedData + "APPLICATIONS:";
			for (int i = 0; i < applicationsToExport.size(); i++) {
				exportedData = exportedData
								+ "Application to: "
								+ applicationsToExport.get(i).getPosition().getTitle() 
								+ "CreationMoment: "
								+ applicationsToExport.get(i).getCreationMoment()
								+ "Answer: " 
								+ applicationsToExport.get(i).getAnswer()
								+ "LinkCode: " 
								+ applicationsToExport.get(i).getLinkCode()
								+ "Status: " 
								+ applicationsToExport.get(i).getStatus()
								+ "SubmittedMoment: " 
								+ applicationsToExport.get(i).getSubmittedMoment()
								+ "\r\n";

				exportedData = exportedData + "\r\n";
			}
			
		}

		// EXPORTING PROBLEMS
		exportedData = exportedData + "PROBLEMS: ";
		for (int i = 0; i < problemsToExport.size(); i++) {
			exportedData = exportedData
					+ "title: " + problemsToExport.get(i).getTitle()
					+ "statement: " + problemsToExport.get(i).getStatement() 
					+ "hint: " + problemsToExport.get(i).getHint() 
					+ "attachments: " + problemsToExport.get(i).getAttachments() 
					+ "is Draft: " + problemsToExport.get(i).getIsDraft() 
					+ "\r\n";

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
		exportedData = exportedData + "CREDITCARD: ";
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
}

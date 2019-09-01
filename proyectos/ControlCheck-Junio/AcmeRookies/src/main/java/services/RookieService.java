
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

import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Curricula;
import domain.EducationData;
import domain.Finder;
import domain.Message;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import domain.Rookie;
import domain.SocialProfile;
import forms.RookieForm;

@Service
@Transactional
public class RookieService {

	@Autowired
	private RookieRepository		rookieRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;
	
	@Autowired
	private FinderService 			finderService;

	@Autowired
	private CreditCardService 		creditCardService;
	
	@Autowired
	private ApplicationService 		applicationService;
	
	@Autowired
	private CurriculaService 		curriculaService;
	
	@Autowired
	private SocialProfileService 	socialProfileService;
	
	@Autowired
	private MessageService 			messageService;

	// CREATE
	// ---------------------------------------------------------------------------
	public Rookie create(final String username, final String password) {

		final Rookie res = new Rookie();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
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
	public Rookie save(final Rookie rookie) {

		Rookie res;
		Rookie principal;
		Assert.notNull(rookie);

		// Si el id del rookie es 0 no esta en la BBDD (es una creacion
		// no una actualizacion) por lo que hay que codificar la password
		if (rookie.getId() == 0) {
			
			Finder finder = this.finderService.create();
			Finder savedFinder = this.finderService.saveFinder(finder);
			rookie.setFinder(savedFinder);
			
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			rookie.getUserAccount().setPassword(passwordEncoder.encodePassword(rookie.getUserAccount().getPassword(), null));

			// Si el id es distinto de 0 se esta produciendo una actualizacion
		} else {
			// Comprobacion de que el actor logeado es una rookie
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount() == rookie.getUserAccount());
		}

		// En cualquiera de los casos se guarda el rookie en la BBDD
		res = this.rookieRepository.save(rookie);

		return res;
	}
	
	public Rookie saveFirst(final Rookie rookie,CreditCard creditCard) {

		Rookie res;
		Assert.notNull(rookie);
		if (rookie.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			rookie.getUserAccount().setPassword(passwordEncoder.encodePassword(rookie.getUserAccount().getPassword(), null));
		}
		Finder finder = this.finderService.create();
		Finder savedFinder = this.finderService.saveFinder(finder);
		rookie.setFinder(savedFinder);
		// Guardamos primero el rookie y luego la tarjeta
		res = this.rookieRepository.save(rookie);
		creditCard.setActor(res);
		this.creditCardService.save(creditCard);

		return res;
	}

	// FIND ONE
	public Rookie findOne(final int rookieId) {

		// Declaracion de variables
		Rookie res;

		// Obtenemos el Rookie con la id indicada y comprobamos
		// que existe un Rookie con esa id
		res = this.rookieRepository.findOne(rookieId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Rookie> findAll() {

		// Declaracion de variables
		Collection<Rookie> res;

		// Obtenemos el conjunto de Rookie
		res = this.rookieRepository.findAll();

		return res;
	}

	// OTHER METHODS
	// --------------------------------------------------------------

	public Rookie findByPrincipal() {

		// Declaracion de variables
		Rookie res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Rookie a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findRookieByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}

	public Rookie findRookieByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Rookie res;

		// Obtenemos la Rookie a traves de la query creada en el repositorio
		res = this.rookieRepository.findRookieByUserAccount(userAccountId);

		// Comprobamos que nos ha devuelto una Rookie
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: RookieForm --> Rookie
	// Reconstruir una Rookie a partir de un rookieForm para registrarlo
	public Rookie reconstruct(final RookieForm rookieForm, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(rookieForm.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			rookieForm.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + rookieForm.getPhoneNumber());

		// Declarar variables
		Rookie rookie;

		// Crear un nuevo rookie
		rookie = this.create(rookieForm.getUsername(), rookieForm.getPassword());

		// Atributos de Actor
		rookie.setAddress(rookieForm.getAddress());
		rookie.setEmail(rookieForm.getEmail());
		rookie.setPhoto(rookieForm.getPhoto());
		rookie.setPhoneNumber(rookieForm.getPhoneNumber());
		rookie.setName(rookieForm.getName());
		rookie.setSurname(rookieForm.getSurname());
		rookie.setVatNumber(rookieForm.getVatNumber());

		// Validar formulario
		this.validator.validate(rookieForm, binding);

		// Comprobar que no haya otro Actor con ese nombre de usuario
		if (this.userAccountService.findByUsername(rookieForm.getUsername()) != null)
			binding.rejectValue("username", "actor.username.taken");

		// Comprobar que las contrasenias coinciden
		if (!rookieForm.getPassword().equals(rookieForm.getPasswordConfirmation()))
			binding.rejectValue("passwordConfirmation", "actor.passwordMiss");

		// Comprobar que se han aceptado los terminos y condiciones
		if (!rookieForm.getCheckTerms())
			binding.rejectValue("checkTerms", "actor.uncheck");

		// Comprobar que se cumple el patron de email
		if (!(rookieForm.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && rookieForm.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return rookie;
	}

	public Rookie findOneTrimmedByPrincipal() {
		// Initialize variables
		Rookie result;
		final Rookie principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Rookie();
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
	public Rookie reconstruct(final Rookie rookie, final BindingResult binding) {
		Rookie result;
		Rookie aux;

		aux = this.findOne(rookie.getId());
		result = new Rookie();

		result.setAddress(rookie.getAddress());
		result.setEmail(rookie.getEmail());
		result.setName(rookie.getName());
		result.setPhoneNumber(rookie.getPhoneNumber());
		result.setPhoto(rookie.getPhoto());
		result.setSurname(rookie.getSurname());
		result.setVatNumber(rookie.getVatNumber());

		result.setId(rookie.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		//result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(rookie.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && rookie.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.rookieRepository.flush();
	}
	
	public void deleteByUserDropOut(int rookieId) {
		this.rookieRepository.delete(rookieId);
	}

	public String deleteUserAccount() {
		// Initialize variables
		String exportedData;
		Rookie principal;
		UserAccount userAccountToDelete;
		Finder finderToDelete;
		CreditCard creditCardToDelete;
		List<Application> applicationsToDelete;
		List<Curricula> curriculasToDelete;
		List<SocialProfile> socialProfilesToDelete;
		List<Message> messagesToDelete;


		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToDelete = principal.getUserAccount();
		finderToDelete = principal.getFinder();
		creditCardToDelete = this.creditCardService.findCreditCardByActorId(principal.getId());
		applicationsToDelete = new ArrayList<Application>(this.applicationService.findApplicationsByRookie(principal.getId()));
		curriculasToDelete = new ArrayList<Curricula>(this.curriculaService.findByPrincipal());
		socialProfilesToDelete = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToDelete = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));


		exportedData = ("Username: " + userAccountToDelete.getUsername());

		// DELETING APPLICATIONS
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

		// DELETING CURRICULAS
		exportedData = exportedData + "CURRICULAS: ";
		for (int i = 0; i < curriculasToDelete.size(); i++) {
			List<EducationData> educationDataToDelete = new ArrayList<EducationData>(curriculasToDelete.get(i).getEducationData());
			List<MiscellaneousData> miscellaneousDataToDelete = new ArrayList<MiscellaneousData>(curriculasToDelete.get(i).getMiscellaneousData());
			List<PositionData> positionDataToDelete = new ArrayList<PositionData>(curriculasToDelete.get(i).getPositionData());
			PersonalData personalDataToDelete = curriculasToDelete.get(i).getPersonalData();
			exportedData = exportedData + 
					"Curricula: " + i;
			for (int j = 0; j < educationDataToDelete.size(); j++) {
				exportedData = exportedData 
						+ "Education Data: " + j
						+ "degree: " +  educationDataToDelete.get(j).getDegree()
						+ "institution: " +  educationDataToDelete.get(j).getInstitution()
						+ "mark: " +  educationDataToDelete.get(j).getMark()
						+ "start Date: " +  educationDataToDelete.get(j).getStartDate()
						+ "end Date: " +  educationDataToDelete.get(j).getEndDate()
						+ "\r\n";
			}
			for (int j = 0; j < miscellaneousDataToDelete.size(); j++) {
				exportedData = exportedData 
						+ "Miscellanoeus Data: " + j
						+ "text: " +  miscellaneousDataToDelete.get(j).getText()
						+ "attachments: " +  miscellaneousDataToDelete.get(j).getAttachments()
						+ "\r\n";
			}
			for (int j = 0; j < positionDataToDelete.size(); j++) {
				exportedData = exportedData 
						+ "Position Data: " + j
						+ "title: " +  positionDataToDelete.get(j).getTitle()
						+ "description: " +  positionDataToDelete.get(j).getDescription()
						+ "start Date: " +  positionDataToDelete.get(j).getStartDate()
						+ "end Date: " +  positionDataToDelete.get(j).getEndDate()
						+ "\r\n";
			}
			exportedData = exportedData + "PersonalData:"
						+ "full Name: " + personalDataToDelete.getFullName()
						+ "gitHub Link: " + personalDataToDelete.getGitHubLink()
						+ "linkedIn Link: " + personalDataToDelete.getLinkedInLink()
						+ "phone Number: " + personalDataToDelete.getPhoneNumber()
						+ "statement: " + personalDataToDelete.getStatement();

			exportedData = exportedData + "\r\n";
			this.curriculaService.deleteByUserDropOut(curriculasToDelete.get(i));
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

		// DELETING FINDER
		exportedData = exportedData + "FINDER: ";
		exportedData = exportedData 
				+ "Keyword: " + finderToDelete.getKeyword() 
				+ "DeadLine: " + finderToDelete.getDeadline() 
				+ "Last Update: " + finderToDelete.getLastUpdate() 
				+ "MinimumSalary: " + finderToDelete.getMinimumSalary() 
				+ "MaximumSalary: " + finderToDelete.getMaximumSalary()
				+ "\r\n";
		this.finderService.deleteByUserDropOut(finderToDelete);
		
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

	public String exportData() {
		// Initialize variables
		String exportedData;
		Rookie principal;
		UserAccount userAccounttoExport;
		Finder findertoExport;
		CreditCard creditCardtoExport;
		List<Application> applicationstoExport;
		List<Curricula> curriculastoExport;
		List<SocialProfile> socialProfilestoExport;
		List<Message> messagestoExport;


		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccounttoExport = principal.getUserAccount();
		findertoExport = principal.getFinder();
		creditCardtoExport = this.creditCardService.findCreditCardByActorId(principal.getId());
		applicationstoExport = new ArrayList<Application>(this.applicationService.findApplicationsByRookie(principal.getId()));
		curriculastoExport = new ArrayList<Curricula>(this.curriculaService.findByPrincipal());
		socialProfilestoExport = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagestoExport = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));

		exportedData = ("Username: " + userAccounttoExport.getUsername());

		// EXPORTING APPLICATIONS
		exportedData = exportedData + "APPLICATIONS:";
		for (int i = 0; i < applicationstoExport.size(); i++) {
			exportedData = exportedData
							+ "Application to: "
							+ applicationstoExport.get(i).getPosition().getTitle() 
							+ "CreationMoment: "
							+ applicationstoExport.get(i).getCreationMoment()
							+ "Answer: " 
							+ applicationstoExport.get(i).getAnswer()
							+ "LinkCode: " 
							+ applicationstoExport.get(i).getLinkCode()
							+ "Status: " 
							+ applicationstoExport.get(i).getStatus()
							+ "SubmittedMoment: " 
							+ applicationstoExport.get(i).getSubmittedMoment()
							+ "\r\n";
			exportedData = exportedData + "\r\n";
		}

		// EXPORTING CURRICULAS
		exportedData = exportedData + "CURRICULAS: ";
		for (int i = 0; i < curriculastoExport.size(); i++) {
			List<EducationData> educationDatatoExport = new ArrayList<EducationData>(curriculastoExport.get(i).getEducationData());
			List<MiscellaneousData> miscellaneousDatatoExport = new ArrayList<MiscellaneousData>(curriculastoExport.get(i).getMiscellaneousData());
			List<PositionData> positionDatatoExport = new ArrayList<PositionData>(curriculastoExport.get(i).getPositionData());
			PersonalData personalDatatoExport = curriculastoExport.get(i).getPersonalData();
			exportedData = exportedData + 
					"Curricula: " + i;
			for (int j = 0; j < educationDatatoExport.size(); j++) {
				exportedData = exportedData 
						+ "Education Data: " + j
						+ "degree: " +  educationDatatoExport.get(j).getDegree()
						+ "institution: " +  educationDatatoExport.get(j).getInstitution()
						+ "mark: " +  educationDatatoExport.get(j).getMark()
						+ "start Date: " +  educationDatatoExport.get(j).getStartDate()
						+ "end Date: " +  educationDatatoExport.get(j).getEndDate()
						+ "\r\n";
			}
			for (int j = 0; j < miscellaneousDatatoExport.size(); j++) {
				exportedData = exportedData 
						+ "Miscellanoeus Data: " + j
						+ "text: " +  miscellaneousDatatoExport.get(j).getText()
						+ "attachments: " +  miscellaneousDatatoExport.get(j).getAttachments()
						+ "\r\n";
			}
			for (int j = 0; j < positionDatatoExport.size(); j++) {
				exportedData = exportedData 
						+ "Position Data: " + j
						+ "title: " +  positionDatatoExport.get(j).getTitle()
						+ "description: " +  positionDatatoExport.get(j).getDescription()
						+ "start Date: " +  positionDatatoExport.get(j).getStartDate()
						+ "end Date: " +  positionDatatoExport.get(j).getEndDate()
						+ "\r\n";
			}
			exportedData = exportedData + "PersonalData:"
						+ "full Name: " + personalDatatoExport.getFullName()
						+ "gitHub Link: " + personalDatatoExport.getGitHubLink()
						+ "linkedIn Link: " + personalDatatoExport.getLinkedInLink()
						+ "phone Number: " + personalDatatoExport.getPhoneNumber()
						+ "statement: " + personalDatatoExport.getStatement();

			exportedData = exportedData + "\r\n";
		}

		// EXPORTING SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfilestoExport.size(); i++) {
			exportedData = exportedData + "Link"
					+ socialProfilestoExport.get(i).getLink()
					+ " Social Network:"
					+ socialProfilestoExport.get(i).getSocialNetwork() + "Nick"
					+ socialProfilestoExport.get(i).getNick() + "\r\n";
		}

		// EXPORTING MESSAGES
		exportedData = exportedData + "MESSAGES: ";
		for (int i = 0; i < messagestoExport.size(); i++) {
			exportedData = exportedData + "Message"
					+ " subject:" + messagestoExport.get(i).getSubject()
					+ " body:" + messagestoExport.get(i).getBody()
					+ " moment:" + messagestoExport.get(i).getMoment()
					+ " tags:" + messagestoExport.get(i).getTags()
					+ " is Spam:" + messagestoExport.get(i).getIsSpam()
					+ "\r\n";
		}

		// EXPORTING FINDER
		exportedData = exportedData + "FINDER: ";
		exportedData = exportedData 
				+ "Keyword: " + findertoExport.getKeyword() 
				+ "DeadLine: " + findertoExport.getDeadline() 
				+ "Last Update: " + findertoExport.getLastUpdate() 
				+ "MinimumSalary: " + findertoExport.getMinimumSalary() 
				+ "MaximumSalary: " + findertoExport.getMaximumSalary()
				+ "\r\n";
		
		exportedData = exportedData + "CreditCard: ";
		exportedData = exportedData 
				+ "holder: " + creditCardtoExport.getHolder()
				+ "number: " + creditCardtoExport.getNumber()
				+ "CVV: " + creditCardtoExport.getCVV()
				+ "make: " + creditCardtoExport.getMake()
				+ "expiration Month: " + creditCardtoExport.getExpirationMonth()
				+ "expiation Year: " + creditCardtoExport.getExpirationYear()
				+ "\r\n";

		return exportedData;
	}
}
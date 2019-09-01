
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

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Charge;
import domain.CreditCard;
import domain.Invoice;
import domain.Item;
import domain.Message;
import domain.Provider;
import domain.SocialProfile;
import domain.Sponsorship;
import forms.ProviderForm;

@Service
@Transactional
public class ProviderService {

	@Autowired
	private ProviderRepository		providerRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private InvoiceService			invoiceService;

	@Autowired
	private ItemService				itemService;


	//Constructor
	public ProviderService() {
		super();
	}

	//Create
	public Provider create(final String username, final String password) {

		final Provider res = new Provider();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
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

	//SAVE
	public Provider save(final Provider provider) {
		Provider res;
		Provider principal;
		Assert.notNull(provider);

		principal = this.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getUserAccount() == provider.getUserAccount());

		res = this.providerRepository.save(provider);
		return res;
	}

	// SAVE
	public Provider saveFirst(final Provider provider, final CreditCard creditCard) {

		Provider res;
		Assert.notNull(provider);

		if (provider.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			provider.getUserAccount().setPassword(passwordEncoder.encodePassword(provider.getUserAccount().getPassword(), null));
		}

		res = this.providerRepository.save(provider);
		creditCard.setActor(res);
		this.creditCardService.save(creditCard);

		return res;
	}

	// OTHER METHODS
	// FIND ONE
	public Provider findOne(final int providerId) {

		Provider res;

		res = this.providerRepository.findOne(providerId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Provider> findAll() {

		Collection<Provider> res;

		res = this.providerRepository.findAll();

		return res;
	}

	// FindByPrincipal
	public Provider findByPrincipal() {

		// Declaracion de variables
		Provider res;
		UserAccount userAccount;

		// Obtencion de la cuenta de usuario logeada y comprobacion de
		// que efectivamente hay un Actor logeado
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		// Obtencion del Provider a traves de la cuenta de usuario logeada
		// y comprobacion de que es distinta de null
		res = this.findProviderByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;

	}
	// FindProviderByUserAccount
	public Provider findProviderByUserAccount(final int userAccountId) {

		// Comprobamos que la id de la cuenta de usuario es distinta de 0,
		// es decir, existe esa cuenta de usuario
		Assert.isTrue(userAccountId != 0);

		// Declaracion de variables
		Provider res;

		// Obtenemos la Provider a traves de la query creada en el repositorio
		res = this.providerRepository.findProviderByUserAccount(userAccountId);

		// Comprobamos que nos ha devuelto una Provider
		Assert.notNull(res);

		return res;
	}

	// Reconstruct: ProviderForm --> Provider
	public Provider reconstruct(final ProviderForm form, final BindingResult binding) {

		// initialize variables
		final Pattern patron = Pattern.compile("^([0-9]+)$");
		final Matcher encaja = patron.matcher(form.getPhoneNumber());

		// RI2 Phone numbers with pattern "PN" must be added automatically a
		// default country code
		if (encaja.find())
			form.setPhoneNumber(this.configurationService.findConfiguration().getCountryCode() + form.getPhoneNumber());

		// Declarar variables
		Provider provider;

		// Crear un nuevo Provider
		provider = this.create(form.getUsername(), form.getPassword());

		// Atributos de Actor
		provider.setAddress(form.getAddress());
		provider.setEmail(form.getEmail());
		provider.setPhoto(form.getPhoto());
		provider.setPhoneNumber(form.getPhoneNumber());
		provider.setName(form.getName());
		provider.setSurname(form.getSurname());
		provider.setVatNumber(form.getVatNumber());

		// Atributos de Provider
		provider.setMake(form.getMakeProvider());

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

		return provider;
	}

	public Provider construct(final Provider provider) {

		final Provider res = new Provider();

		res.setId(provider.getId());
		res.setName(provider.getName());
		res.setMake(provider.getMake());
		res.setSurname(provider.getSurname());
		res.setPhoto(provider.getPhoto());
		res.setEmail(provider.getEmail());
		res.setPhoneNumber(provider.getPhoneNumber());
		res.setAddress(provider.getAddress());
		res.setVatNumber(provider.getVatNumber());

		return res;
	}
	public Provider findOneTrimmedByPrincipal() {
		// Initialize variables
		Provider result;
		final Provider principal = this.findByPrincipal();

		Assert.notNull(principal);

		result = new Provider();
		result.setMake(principal.getMake());
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
	public Provider reconstruct(final Provider provider, final BindingResult binding) {
		Provider result;
		Provider aux;

		aux = this.findOne(provider.getId());
		result = new Provider();

		result.setMake(provider.getMake());
		result.setAddress(provider.getAddress());
		result.setEmail(provider.getEmail());
		result.setName(provider.getName());
		result.setPhoneNumber(provider.getPhoneNumber());
		result.setPhoto(provider.getPhoto());
		result.setSurname(provider.getSurname());
		result.setVatNumber(provider.getVatNumber());

		result.setId(provider.getId());
		result.setVersion(aux.getVersion());

		result.setUserAccount(aux.getUserAccount());
		// result.setPolarity(aux.getPolarity());

		this.validator.validate(result, binding);

		// Checking that the new email match the pattern
		if (!(provider.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && provider.getEmail().length() > 0)
			binding.rejectValue("email", "actor.email.check");

		return result;
	}

	public void flush() {
		this.providerRepository.flush();
	}

	public void deleteByUserDropOut(final int providerId) {
		this.providerRepository.delete(providerId);
	}

	//Delete User Account
	public String deleteUserAccount() {
		// Initialize variables
		String exportedData;
		Provider principal;
		UserAccount userAccountToDelete;
		CreditCard creditCardToDelete;
		List<Sponsorship> sponsorshipsToDelete;
		final List<Item> itemsToDelete;
		List<SocialProfile> socialProfilesToDelete;
		List<Message> messagesToDelete;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToDelete = principal.getUserAccount();
		creditCardToDelete = this.creditCardService.findCreditCardByActorId(principal.getId());
		sponsorshipsToDelete = new ArrayList<Sponsorship>(this.sponsorshipService.findSponsorshipsByProviderId());
		socialProfilesToDelete = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToDelete = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));
		itemsToDelete = new ArrayList<Item>(this.itemService.findItemsByProvider(principal.getId()));

		exportedData = ("Username: " + userAccountToDelete.getUsername());

		// DELETING Sponsorships
		exportedData = exportedData + "SPONSORSHIPS: ";
		for (int i = 0; i < sponsorshipsToDelete.size(); i++) {
			final Invoice invoiceToDelete = this.invoiceService.findInvoiceBySponsorshipId(sponsorshipsToDelete.get(i).getId());
			exportedData = exportedData + "Sponsorship: " + i + "target page: " + sponsorshipsToDelete.get(i).getTargetPage() + "is Enabled: " + sponsorshipsToDelete.get(i).getIsEnabled() + "\r\n";

			if (invoiceToDelete != null) {
				final List<Charge> chargesToDelete = new ArrayList<Charge>(invoiceToDelete.getCharges());
				exportedData = exportedData + "INVOICE CHARGES: ";
				for (int j = 0; j < chargesToDelete.size(); j++)
					exportedData = exportedData + "Charge: " + j + "moment: " + chargesToDelete.get(j).getMoment() + "amount: " + chargesToDelete.get(j).getAmount() + "tax: " + chargesToDelete.get(j).getTax() + "\r\n";
				this.invoiceService.deleteByUserDropOut(invoiceToDelete);
			}

			this.sponsorshipService.deleteByUserDropOut(sponsorshipsToDelete.get(i));
		}

		// DELETING Items
		for (int i = 0; i < itemsToDelete.size(); i++) {
			exportedData = exportedData + "Item: " + i + " name: " + itemsToDelete.get(i).getName() + " description: " + itemsToDelete.get(i).getDescription() + " link: " + itemsToDelete.get(i).getLink() + " pictures: "
				+ itemsToDelete.get(i).getPictures();
			this.itemService.deleteByUserDropOut(itemsToDelete.get(i));
		}

		// DELETING SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfilesToDelete.size(); i++) {
			exportedData = exportedData + "Link" + socialProfilesToDelete.get(i).getLink() + " Social Network:" + socialProfilesToDelete.get(i).getSocialNetwork() + "Nick" + socialProfilesToDelete.get(i).getNick() + "\r\n";
			this.socialProfileService.deleteByUserDropOut(socialProfilesToDelete.get(i));
		}

		// DELETING MESSAGES
		exportedData = exportedData + "MESSAGES: ";
		for (int i = 0; i < messagesToDelete.size(); i++) {
			exportedData = exportedData + "Message" + " subject:" + messagesToDelete.get(i).getSubject() + " body:" + messagesToDelete.get(i).getBody() + " moment:" + messagesToDelete.get(i).getMoment() + " tags:" + messagesToDelete.get(i).getTags()
				+ " is Spam:" + messagesToDelete.get(i).getIsSpam() + "\r\n";
			this.messageService.deleteByUserDropOut(messagesToDelete.get(i));
		}

		// DELETING CREDIT CARD
		exportedData = exportedData + "CreditCard: ";
		exportedData = exportedData + "holder: " + creditCardToDelete.getHolder() + "number: " + creditCardToDelete.getNumber() + "CVV: " + creditCardToDelete.getCVV() + "make: " + creditCardToDelete.getMake() + "expiration Month: "
			+ creditCardToDelete.getExpirationMonth() + "expiation Year: " + creditCardToDelete.getExpirationYear() + "\r\n";
		this.creditCardService.deleteByUserDropOut(creditCardToDelete);

		this.deleteByUserDropOut(principal.getId());

		return exportedData;
	}
	//Export Data
	public String exportData() {
		String exportedData;
		Provider principal;
		UserAccount userAccountToDelete;
		CreditCard creditCardToDelete;
		List<Sponsorship> sponsorshipsToDelete;
		final List<Item> itemsToDelete;
		List<SocialProfile> socialProfilesToDelete;
		List<Message> messagesToDelete;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		userAccountToDelete = principal.getUserAccount();
		creditCardToDelete = this.creditCardService.findCreditCardByActorId(principal.getId());
		sponsorshipsToDelete = new ArrayList<Sponsorship>(this.sponsorshipService.findSponsorshipsByProviderId());
		socialProfilesToDelete = new ArrayList<SocialProfile>(this.socialProfileService.findByActor(principal.getId()));
		messagesToDelete = new ArrayList<Message>(this.messageService.findAllToDelete(principal.getId()));
		itemsToDelete = new ArrayList<Item>(this.itemService.findItemsByProvider(principal.getId()));

		exportedData = ("Username: " + userAccountToDelete.getUsername());

		// DELETING Sponsorships
		exportedData = exportedData + "SPONSORSHIPS: ";
		for (int i = 0; i < sponsorshipsToDelete.size(); i++) {
			final Invoice invoiceToDelete = this.invoiceService.findInvoiceBySponsorshipId(sponsorshipsToDelete.get(i).getId());

			exportedData = exportedData + "Sponsorship: " + i + "target page: " + sponsorshipsToDelete.get(i).getTargetPage() + "is Enabled: " + sponsorshipsToDelete.get(i).getIsEnabled() + "\r\n";
			if (invoiceToDelete != null) {
				final List<Charge> chargesToDelete = new ArrayList<Charge>(invoiceToDelete.getCharges());
				exportedData = exportedData + "INVOICE CHARGES: ";
				for (int j = 0; j < chargesToDelete.size(); j++)
					exportedData = exportedData + "Charge: " + j + "moment: " + chargesToDelete.get(j).getMoment() + "amount: " + chargesToDelete.get(j).getAmount() + "tax: " + chargesToDelete.get(j).getTax() + "\r\n";

			}

		}

		// DELETING Items
		for (int i = 0; i < itemsToDelete.size(); i++) {
			exportedData = exportedData + "Item: " + i + " name: " + itemsToDelete.get(i).getName() + " description: " + itemsToDelete.get(i).getDescription() + " link: " + itemsToDelete.get(i).getLink() + " pictures: "
				+ itemsToDelete.get(i).getPictures();
			this.itemService.deleteByUserDropOut(itemsToDelete.get(i));
		}

		// DELETING SOCIAL PROFILES
		exportedData = exportedData + "SOCIAL PROFILES: ";
		for (int i = 0; i < socialProfilesToDelete.size(); i++)
			exportedData = exportedData + "Link" + socialProfilesToDelete.get(i).getLink() + " Social Network:" + socialProfilesToDelete.get(i).getSocialNetwork() + "Nick" + socialProfilesToDelete.get(i).getNick() + "\r\n";

		// DELETING MESSAGES
		exportedData = exportedData + "MESSAGES: ";
		for (int i = 0; i < messagesToDelete.size(); i++)
			exportedData = exportedData + "Message" + " subject:" + messagesToDelete.get(i).getSubject() + " body:" + messagesToDelete.get(i).getBody() + " moment:" + messagesToDelete.get(i).getMoment() + " tags:" + messagesToDelete.get(i).getTags()
				+ " is Spam:" + messagesToDelete.get(i).getIsSpam() + "\r\n";

		// DELETING CREDIT CARD
		exportedData = exportedData + "CreditCard: ";
		exportedData = exportedData + "holder: " + creditCardToDelete.getHolder() + "number: " + creditCardToDelete.getNumber() + "CVV: " + creditCardToDelete.getCVV() + "make: " + creditCardToDelete.getMake() + "expiration Month: "
			+ creditCardToDelete.getExpirationMonth() + "expiation Year: " + creditCardToDelete.getExpirationYear() + "\r\n";

		return exportedData;
	}

}

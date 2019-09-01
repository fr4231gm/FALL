
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Charge;
import domain.CreditCard;
import domain.Invoice;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository --------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private Validator				validator;
	// Supporting services -------------------
	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private InvoiceService			invoiceService;
	@Autowired
	private ChargeService			chargeService;


	// Constructors -----------------

	public SponsorshipService() {
		super();
	}
	// CRUDS Methods ----------------------
	public Sponsorship create() {
		//Comprobamos que la persona logueada es un sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		//Nuevo Sponsorship
		final Sponsorship res = new Sponsorship();

		//Definimos las clases asociadas;
		final Parade parade = new Parade();
		final CreditCard creditCard = new CreditCard();

		res.setSponsor(principal);
		res.setParade(parade);
		res.setCreditCard(creditCard);
		res.setIsEnabled(true);

		return res;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		//Comprobamos que la persona logueada es un sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		final Calendar fecha = Calendar.getInstance();
		final int mes = fecha.get(Calendar.MONTH) + 1;
		final int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));

		//New Sponsorship
		Sponsorship res = null;

		if (sponsorship.getCreditCard().getExpirationYear() > anyo || (sponsorship.getCreditCard().getExpirationYear() == anyo && sponsorship.getCreditCard().getExpirationMonth() > mes))
			if (sponsorship.getId() == 0) {
				res = this.sponsorshipRepository.save(sponsorship);
				//call invoice service to create an invoice for a sponsorship
				this.invoiceService.create(res);
			} else {
				Assert.isTrue(sponsorship.getSponsor().getId() == principal.getId());
				res = this.sponsorshipRepository.save(sponsorship);
			}
		return res;
	}

	public void delete(final Integer sponsorshipId) {
		//Comprobamos que la persona logueada es un sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobamos que el sponsorship le pertenece al sponsor.
		final Sponsorship sponsorship = this.findOne(sponsorshipId);
		Assert.isTrue(sponsorship.getSponsor().getId() == principal.getId());
		sponsorship.setIsEnabled(false);
		this.sponsorshipRepository.save(sponsorship);

	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship res;
		res = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(res);
		return res;
	}

	public Sponsorship findOneToFail(final int sponsorshipId) {
		Sponsorship res;
		res = this.sponsorshipRepository.findOne(sponsorshipId);
		return res;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> res;

		res = this.sponsorshipRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}
	// Other business methods ------------------------------------------------
	public Collection<Sponsorship> findSponsorshipsBySponsorId(final int sponsorId) {
		Collection<Sponsorship> res;

		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.isTrue(principal.getId() == sponsorId);

		res = this.sponsorshipRepository.findSponsorshipsBySponsorId(sponsorId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Sponsorship> findSponsorshipsByCreditCardId(final int creditCardId) {
		Collection<Sponsorship> res;
		res = this.sponsorshipRepository.findSponsorshipsByCreditCardId(creditCardId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Sponsorship> findSponsorshipsByParadeId(final int paradeId) {
		Collection<Sponsorship> res;
		res = this.sponsorshipRepository.findSponsorshipsByParadeId(paradeId);
		Assert.notNull(res);
		return res;
	}
	public void activate(final Integer sponsorshipId) {
		//Comprobamos que la persona logueada es un sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobamos que el sponsorship le pertenece al sponsor.
		final Sponsorship sponsorship = this.findOne(sponsorshipId);
		Assert.isTrue(sponsorship.getSponsor().getId() == principal.getId());
		sponsorship.setIsEnabled(true);
		this.sponsorshipRepository.save(sponsorship);

	}

	public Sponsorship reconstruct(final SponsorshipForm form, final BindingResult binding) {
		Sponsorship res;

		if (form.getId() == 0)
			res = this.create();
		else {
			res = new Sponsorship();
			res.setSponsor(this.findOne(form.getId()).getSponsor());
		}

		//Sponsorship
		res.setId(form.getId());

		res.setParade(this.paradeService.findOne(form.getParadeId()));
		res.setCreditCard(this.creditCardService.findOne(form.getCreditCardId()));
		res.setBanner(form.getBanner());
		res.setTargetURL(form.getTargetURL());
		res.setVersion(form.getVersion());
		res.setIsEnabled(form.getIsEnabled());

		//Validating the form
		this.validator.validate(form, binding);
		return res;
	}

	public SponsorshipForm construct(final Sponsorship sponsorship) {
		final SponsorshipForm res = new SponsorshipForm();

		res.setId(sponsorship.getId());
		res.setVersion(sponsorship.getVersion());
		res.setBanner(sponsorship.getBanner());
		res.setTargetURL(sponsorship.getTargetURL());
		res.setIsEnabled(sponsorship.getIsEnabled());
		res.setParadeId(sponsorship.getParade().getId());
		res.setCreditCardId(sponsorship.getCreditCard().getId());
		return res;
	}

	// METHOD TO DE-ACTIVATE SPONSORSHIPS WITH EXPIRED CREDITCARDS
	public void deactivateAllExpired(final int id, final Collection<Sponsorship> sponsorships) {

		//check that is not null
		Assert.notNull(id);

		for (final Sponsorship p : sponsorships) {
			p.setIsEnabled(false);
			this.sponsorshipRepository.save(p);
		}
	}

	// Find Sponsorships With Expired CreditCards
	public Collection<Sponsorship> findSponsorshipsWithExpiredCreditCards(final int anyo, final int mes) {
		final Collection<Sponsorship> res = new ArrayList<>();
		Collection<Sponsorship> aux = new ArrayList<>();
		aux = this.sponsorshipRepository.findSponsorshipsWithExpiredCreditCards(anyo, mes);

		if (aux.size() > 0)
			for (final Sponsorship s : aux)
				res.add(s);
		return res;
	}

	// RNF#20. Select Random Sponsorship
	public Sponsorship selectRandomSponsorshipIfAny(final Integer paradeId) {

		Sponsorship res;

		//llamar al servicio para encontrar los sponsorships de esa parade si los hubiera
		List<Sponsorship> sponsorshipsFound = new ArrayList<>();

		sponsorshipsFound = (List<Sponsorship>) this.findSponsorshipsByParadeId(paradeId);

		if (!sponsorshipsFound.isEmpty()) {

			final Integer index = this.selectRandom(sponsorshipsFound.size() - 1);

			res = sponsorshipsFound.get(index);

			//llamar al metodo para hacer el cargo
			this.chargeSelectedSponsorship(res);

		} else
			res = null;

		return res;
	}
	// Method to generate a random number from entry integer
	private Integer selectRandom(final Integer tamanoColeccion) {
		final int randomNumber = (int) Math.floor(Math.random() * tamanoColeccion);
		return randomNumber;
	}
	/*
	 * // Method to generate 6 alphanumeric random characters
	 * for (int i = 0; i < tamanoColeccion; i++) {
	 * // Generate a random number
	 * final int randomNumber = (int) Math.floor(Math.random() * tamanoColeccion);
	 * // Keeping the char value for randomNumber of the alphanumeric
	 * c = alphanumeric.charAt(randomNumber);
	 * // Save in res actual value + char of c value generated
	 * res += randomNumber;
	 * }
	 */

	//Method to charge a fare to a sponsorship
	public void chargeSelectedSponsorship(final Sponsorship sponsorship) {

		Assert.notNull(sponsorship);

		// bringing objects
		//Double fare = this.configurationService.findConfiguration().getFare();
		//Double tax = this.configurationService.findConfiguration().getVat();
		//Date momment = Calendar.getInstance().getTime();
		final Invoice aux = this.invoiceService.findInvoiceBySponsorshipId(sponsorship.getId());

		//variables to use
		Invoice res, invoiceSaved, invoiceSavedWithCharge;
		Charge charge, chargeSaved;
		List<Charge> charges = new ArrayList<>();

		if (aux == null) {
			//hay que crearlo por primera vez
			res = this.invoiceService.create(sponsorship);
			//invoiceSaved = this.invoiceService.save(res);

			//meterle un cargo
			charge = this.chargeService.create();
			chargeSaved = this.chargeService.save(charge);

			this.chargeService.flush();

			//una vez persistido el charge, lo asocio al invoice
			charges.add(chargeSaved);
			res.setCharges(charges);

			invoiceSavedWithCharge = this.invoiceService.save(res);

			Assert.notNull(invoiceSavedWithCharge);

			this.invoiceService.flush();
		} else {
			//ya existe el invoice
			invoiceSaved = aux;
			charges = (List<Charge>) invoiceSaved.getCharges();

			//meterle un cargo
			charge = this.chargeService.create();
			chargeSaved = this.chargeService.save(charge);

			this.chargeService.flush();

			//una vez persistido el charge, lo asocio al invoice
			charges.add(chargeSaved);
			invoiceSaved.setCharges(charges);

			invoiceSavedWithCharge = this.invoiceService.save(invoiceSaved);

			Assert.notNull(invoiceSavedWithCharge);

			this.invoiceService.flush();
		}

	}

}

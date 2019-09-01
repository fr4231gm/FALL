
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Charge;
import domain.CreditCard;
import domain.Invoice;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository ---------------------------------------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private Validator				validator;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private InvoiceService			invoiceService;

	@Autowired
	private ChargeService			chargeService;


	// Constructors ---------------------------------------------------------
	public SponsorshipService() {
		super();
	}

	// Simple CRUDS
	public Sponsorship create() {

		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);

		final Sponsorship res = new Sponsorship();

		// Definimos clases asociadas
		final Position pos = new Position();

		res.setProvider(principal);
		res.setPosition(pos);
		res.setIsEnabled(true);

		return res;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Sponsorship res = null;
		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);
		final CreditCard cc = this.creditCardService.findCreditCardByActorId(principal.getId());
		if (!this.checkCreditCard(cc.getExpirationMonth(), cc.getExpirationYear()))
			if (sponsorship.getId() == 0)
				res = this.sponsorshipRepository.save(sponsorship);
			else {
				Assert.isTrue(sponsorship.getProvider().getId() == principal.getId());
				res = this.sponsorshipRepository.save(sponsorship);
			}
		return res;
	}

	public void delete(final Integer sponsorshipId) {
		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);

		final Sponsorship sp = this.findOne(sponsorshipId);
		Assert.isTrue(sp.getProvider().getId() == principal.getId());
		this.sponsorshipRepository.delete(sponsorshipId);
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

	public Collection<Sponsorship> finalAll() {
		Collection<Sponsorship> res;

		res = this.sponsorshipRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	// Other business methods ------------------------------------------------
	public Collection<Sponsorship> findSponsorshipsByProviderId() {
		Collection<Sponsorship> res;

		Provider principal;
		principal = this.providerService.findByPrincipal();

		res = this.sponsorshipRepository.findSponsorshipByProviderId(principal.getId());
		Assert.notNull(res);
		return res;
	}

	public Collection<Sponsorship> findSponsorshipsByPositionId(final int positionId) {
		Collection<Sponsorship> res;
		res = this.sponsorshipRepository
				.findEnabledSponsorshipByPositionId(positionId);
		Assert.notNull(res);
		return res;
	}

	public Sponsorship reconstruct(final SponsorshipForm form, final BindingResult binding) {
		Sponsorship res;
		if (form.getId() == 0)
			res = this.create();
		else {
			res = new Sponsorship();
			res.setProvider(this.findOne(form.getId()).getProvider());
		}

		// Sponsorship
		res.setId(form.getId());

		res.setBanner(form.getBanner());
		res.setTargetPage(form.getTargetPage());
		res.setIsEnabled(form.getIsEnabled());
		res.setPosition(this.positionService.findOne(form.getPositionId()));
		res.setVersion(form.getVersion());

		this.validator.validate(form, binding);
		return res;
	}

	public SponsorshipForm construct(final Sponsorship sponsorship) {
		final SponsorshipForm res = new SponsorshipForm();

		res.setId(sponsorship.getId());
		res.setVersion(sponsorship.getVersion());
		res.setBanner(sponsorship.getBanner());
		res.setTargetPage(sponsorship.getTargetPage());
		res.setIsEnabled(sponsorship.getIsEnabled());
		res.setPositionId(sponsorship.getPosition().getId());
		return res;
	}

	public boolean checkCreditCard(final Integer expMonth, final Integer expYear) {
		boolean res = false;
		final Calendar fecha = Calendar.getInstance();
		final int mes = fecha.get(Calendar.MONTH) + 1;
		final int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		if ((expYear < anyo || (expYear == anyo && expMonth < mes)))
			res = true;
		return res;
	}

	// RNF#15. Select Random Sponsorship to display in a Position
	public Sponsorship selectRandomSponsorshipIfAny(final Integer positionId) {

		Sponsorship res;

		// llamar al servicio para encontrar los sponsorships de esa parade si
		// los hubiera
		List<Sponsorship> sponsorshipsFound = new ArrayList<>();

		sponsorshipsFound = (List<Sponsorship>) this.findSponsorshipsByPositionId(positionId);

		if (!sponsorshipsFound.isEmpty()) {

			final Integer index = this.selectRandom(sponsorshipsFound.size() - 1);

			res = sponsorshipsFound.get(index);

			// llamar al metodo para hacer el cargo
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

	// Method to charge a fare to a sponsorship
	public void chargeSelectedSponsorship(final Sponsorship sponsorship) {

		Assert.notNull(sponsorship);

		final Invoice aux = this.invoiceService.findInvoiceBySponsorshipId(sponsorship.getId());

		// variables to use
		Invoice res, invoiceSaved, invoiceSavedWithCharge;
		Charge charge, chargeSaved;
		List<Charge> charges = new ArrayList<>();

		if (aux == null) {
			// hay que crearlo por primera vez
			res = this.invoiceService.create(sponsorship);
			// invoiceSaved = this.invoiceService.save(res);

			// meterle un cargo
			charge = this.chargeService.create();
			chargeSaved = this.chargeService.save(charge);

			this.chargeService.flush();

			// una vez persistido el charge, lo asocio al invoice
			charges.add(chargeSaved);
			res.setCharges(charges);

			invoiceSavedWithCharge = this.invoiceService.save(res);

			Assert.notNull(invoiceSavedWithCharge);

			this.invoiceService.flush();
		} else {
			// ya existe el invoice
			invoiceSaved = aux;
			charges = (List<Charge>) invoiceSaved.getCharges();

			// meterle un cargo
			charge = this.chargeService.create();
			chargeSaved = this.chargeService.save(charge);

			this.chargeService.flush();

			// una vez persistido el charge, lo asocio al invoice
			charges.add(chargeSaved);
			invoiceSaved.setCharges(charges);

			invoiceSavedWithCharge = this.invoiceService.save(invoiceSaved);

			Assert.notNull(invoiceSavedWithCharge);

			this.invoiceService.flush();
		}

	}

	public Collection<Sponsorship> findAllSponsorshipsByPositionId(int positionId) {
		return this.sponsorshipRepository.findSponsorshipByPositionId(positionId);
	}

	public void deleteByUserDropOut(Sponsorship sponsorship) {
		this.sponsorshipRepository.delete(sponsorship);
	}
}

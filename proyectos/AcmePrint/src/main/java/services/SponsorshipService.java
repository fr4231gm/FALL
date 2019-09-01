
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
import domain.CreditCard;
import domain.Designer;
import domain.Post;
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
	private PostService				postService;

	@Autowired
	private Validator				validator;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private DesignerService			designerService;


	// Constructors -----------------------------------------------------------
	public SponsorshipService() {
		super();
	}

	// Simple CRUDS -----------------------------------------------------------
	// CREATE
	public Sponsorship create() {

		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);

		final Sponsorship res = new Sponsorship();

		// Definimos clases asociadas
		final Post pos = new Post();
		final Double cost = 0.0;

		res.setProvider(principal);
		res.setPost(pos);
		res.setIsEnabled(true);
		res.setCost(cost);
		return res;
	}

	// SAVE
	public Sponsorship save(final Sponsorship sponsorship) {
		Sponsorship res = null;
		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(sponsorship.getPost().getIsDraft() == false);
		Assert.isTrue(sponsorship.getProvider().getId() == principal.getId());
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

	// DELETE
	public void desactivate(final Integer sponsorshipId) {
		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);

		final Sponsorship sp = this.findOne(sponsorshipId);
		Assert.isTrue(sp.getProvider().getId() == principal.getId());
		Assert.isTrue(sp.getIsEnabled() == true);
		sp.setIsEnabled(false);
		this.sponsorshipRepository.save(sp);
	}

	public void activate(final Integer sponsorshipId) {
		final Provider principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.creditCardService.findCreditCardByActorId(principal.getId()));

		final Sponsorship sp = this.findOne(sponsorshipId);
		Assert.isTrue(sp.getProvider().getId() == principal.getId());
		Assert.isTrue(sp.getIsEnabled() == false);
		sp.setIsEnabled(true);
		this.sponsorshipRepository.save(sp);

	}

	// FINDONE
	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship res;

		res = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(res);

		return res;
	}

	// FIND ONE TO FAIL
	public Sponsorship findOneToFail(final int sponsorshipId) {
		Sponsorship res;

		res = this.sponsorshipRepository.findOne(sponsorshipId);

		return res;
	}

	// FIND ALL
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
	public Collection<Sponsorship> findSponsorshipsByProviderId() {
		Collection<Sponsorship> res;

		Provider principal;
		principal = this.providerService.findByPrincipal();

		res = this.sponsorshipRepository.findSponsorshipByProviderId(principal.getId());
		Assert.notNull(res);
		return res;
	}

	public Collection<Sponsorship> findSponsorshipsByPostId(final int postId) {
		Collection<Sponsorship> res;
		res = this.sponsorshipRepository.findSponsorshipByPostId(postId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Sponsorship> findEnabledSponsorshipsByPostId(final int postId) {
		Collection<Sponsorship> res;
		res = this.sponsorshipRepository.findEnabledSponsorshipByPostId(postId);
		Assert.notNull(res);
		return res;
	}
	public Collection<Sponsorship> findSponsorshipsByDesignerId() {
		Collection<Sponsorship> res;
		final Designer principal = this.designerService.findByPrincipal();
		res = this.sponsorshipRepository.findSponsorshipByDesignerId(principal.getId());
		Assert.notNull(res);
		return res;
	}

	public Sponsorship reconstruct(final SponsorshipForm form, final BindingResult binding) {
		Sponsorship res = new Sponsorship();
		if (form.getId() == 0)
			res = this.create();
		else {
			res = new Sponsorship();
			res.setProvider(this.findOne(form.getId()).getProvider());
		}

		res.setId(form.getId());
		res.setCost(form.getCost());
		res.setBanner(form.getBanner());
		res.setTargetPage(form.getTargetPage());
		res.setIsEnabled(form.getIsEnabled());
		if (form.getPostId() != null)
			res.setPost(this.postService.findOneToFail(form.getPostId()));
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
		res.setPostId(sponsorship.getPost().getId());
		res.setCost(sponsorship.getCost());
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

	// RNF#15. Select Random Sponsorship to display in a Post
	public Sponsorship selectRandomSponsorshipIfAny(final Integer postId) {

		Sponsorship res;

		List<Sponsorship> sponsorshipsFound = new ArrayList<>();

		sponsorshipsFound = (List<Sponsorship>) this.findEnabledSponsorshipsByPostId(postId);

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

		sponsorship.setCost(sponsorship.getCost() + this.configurationService.findConfiguration().getFare());

		this.sponsorshipRepository.save(sponsorship);

		this.flush();
	}

	//MethodAnonymous
	public void saveAnonymous(final Sponsorship sp) {
		Assert.notNull(sp);
		this.sponsorshipRepository.save(sp);
	}

}

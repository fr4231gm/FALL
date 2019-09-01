
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import security.Authority;
import domain.Actor;
import domain.CreditCard;
import domain.Order;
import domain.Sponsorship;
import forms.CreditCardForm;

@Service
@Transactional
public class CreditCardService {

	// Managed Repository -----------------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private Validator				validator;

	// Supporting Service -----------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private OrderService			orderService;


	// Simple CRUDS Methods ---------------------------------------------------
	public CreditCard create() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.checkActor(principal));

		final CreditCard res = new CreditCard();
		res.setActor(principal);
		return res;
	}

	public CreditCard save(final CreditCard creditCard) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.checkActor(principal));
		CreditCard res;
		Assert.notNull(creditCard);
		if (creditCard.getId() != 0)
			Assert.isTrue(creditCard.getActor().getId() == principal.getId());
		else if (creditCard.getId() == 0)
			Assert.isTrue(this.findCreditCardByActorId(principal.getId()) == null);
		res = this.creditCardRepository.save(creditCard);
		return res;
	}

	public void delete() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.checkActor(principal));
		final CreditCard cc = this.findCreditCardByActorId(principal.getId());

		Assert.notNull(cc);
		if (this.checkCustomer(principal) == true) {
			final Collection<Order> or = this.orderService.findOrdersByCustomerIdWithNoPaymant(principal.getId());
			Assert.isTrue(or.size() == 0);
			Assert.isTrue(cc.getActor().getId() == principal.getId());
			this.creditCardRepository.delete(cc);
		} else {
			final Collection<Sponsorship> cs = this.sponsorshipService.findSponsorshipsByProviderId();
			for (final Sponsorship s : cs)
				s.setIsEnabled(false);
			Assert.isTrue(cc.getActor().getId() == principal.getId());
			this.creditCardRepository.delete(cc);
		}
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> res;
		res = this.creditCardRepository.findAll();
		return res;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard res;
		res = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(res);
		return res;
	}

	public CreditCard findOneToFail(final int creditCardId) {
		CreditCard res;
		res = this.creditCardRepository.findOne(creditCardId);
		return res;
	}

	// Other business methods -------------------------------------------------
	public CreditCard findCreditCardByActorId(final int actorId) {
		CreditCard res;
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.checkActor(principal));

		res = this.creditCardRepository.findCreditCardByActorId(actorId);
		//Assert.notNull(res);
		return res;
	}

	public boolean checkCreditCard(final String number, final Integer expMonth, final Integer expYear) {
		boolean res = false;
		if (this.checkFecha(expMonth, expYear) || this.checkNumber(number))
			res = true;
		return res;
	}

	public boolean checkFecha(final Integer expMonth, final Integer expYear) {
		boolean res = false;
		final Calendar fecha = Calendar.getInstance();
		final int mes = fecha.get(Calendar.MONTH) + 1;
		final int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		if (expYear == null || expMonth == null)
			res = true;
		else if ((expYear < anyo || (expYear == anyo && expMonth < mes)))
			res = true;
		return res;
	}

	public boolean checkNumber(final String number) {
		boolean res = false;
		final Collection<CreditCard> creditcards = this.findAll();

		for (final CreditCard c : creditcards)
			if (c.getNumber() == number) {
				res = true;
				break;
			}
		return res;
	}

	public boolean checkActor(final Actor actor) {
		boolean res = true;
		Assert.notNull(actor);
		final Collection<Authority> auth = actor.getUserAccount().getAuthorities();

		final Authority auth1 = new Authority();
		auth1.setAuthority(Authority.CUSTOMER);

		final Authority auth2 = new Authority();
		auth2.setAuthority(Authority.PROVIDER);

		if (!(auth.contains(auth1) || auth.contains(auth2)))
			res = false;
		return res;
	}

	public boolean checkCustomer(final Actor actor) {
		boolean res = true;
		Assert.notNull(actor);
		final Collection<Authority> auth = actor.getUserAccount().getAuthorities();

		final Authority auth1 = new Authority();
		auth1.setAuthority(Authority.CUSTOMER);

		if (!(auth.contains(auth1)))
			res = false;
		return res;
	}
	// Construct : CreditCardForm --> CreditCard
	public CreditCard reconstruct(final CreditCardForm form, final BindingResult binding) {
		CreditCard res;
		if (form.getId() == 0) {
			res = this.create();
			form.setActorId(res.getActor().getId());
			Assert.isTrue(form.getActorId() != 0);
		} else {
			res = new CreditCard();
			res.setActor(this.findOne(form.getId()).getActor());
		}
		// CreditCard Atributes
		res.setId(form.getId());
		res.setHolder(form.getHolder());
		res.setMake(form.getMake());
		res.setNumber(form.getNumber());
		res.setExpirationMonth(form.getExpirationMonth());
		res.setExpirationYear(form.getExpirationYear());
		res.setCVV(form.getCVV());
		res.setVersion(form.getVersion());
		// Validating the form
		this.validator.validate(form, binding);
		if (!(form.getNumber() == null || form.getExpirationMonth() == null || form.getExpirationYear() == null))
			if (this.checkFecha(form.getExpirationMonth(), form.getExpirationYear())) {
				binding.rejectValue("expirationMonth", "creditcard.date.invalid");
				binding.rejectValue("expirationYear", "creditcard.date.invalid");
			} else if (this.checkCreditCard(form.getNumber(), form.getExpirationMonth(), form.getExpirationYear()))
				binding.rejectValue("number", "creditcard.invalid");
		return res;
	}

	// Construct : CreditCard --> CreditCardForm
	public CreditCardForm construct(final CreditCard creditCard) {

		final CreditCardForm res = new CreditCardForm();

		res.setId(creditCard.getId());
		res.setHolder(creditCard.getHolder());
		res.setMake(creditCard.getMake());
		res.setNumber(creditCard.getNumber());
		res.setExpirationMonth(creditCard.getExpirationMonth());
		res.setExpirationYear(creditCard.getExpirationYear());
		res.setCVV(creditCard.getCVV());
		res.setVersion(creditCard.getVersion());
		return res;
	}

	public void flush() {
		this.creditCardRepository.flush();
	}

	public void deleteAnonymous() {
		final Actor principal = this.actorService.findByPrincipal();
		final CreditCard cc = this.findCreditCardByActorId(principal.getId());
		Assert.notNull(cc);
		this.creditCardRepository.delete(cc);
	}
}

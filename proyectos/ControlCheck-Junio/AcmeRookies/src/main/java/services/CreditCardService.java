
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
import domain.Actor;
import domain.CreditCard;
import forms.ActorForm;
import forms.CreditCardForm;

@Service
@Transactional
public class CreditCardService {

	//Managed Repository -----------------------------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private Validator				validator;
	//Supporting Service -----------------------------------------------------------------
	@Autowired
	private ActorService			actorService;


	//Constructors -----------------------------------------------------------------------
	public CreditCardService() {
		super();
	}

	//Simple CRUDS Methods ---------------------------------------------------------------
	public CreditCard create() {
		final CreditCard res = new CreditCard();
		return res;
	}

	public CreditCard save(final CreditCard creditCard) {
		CreditCard res;
		Assert.notNull(creditCard);
		if (creditCard.getId() != 0) {
			final Actor principal = this.actorService.findByPrincipal();
			Assert.isTrue(creditCard.getActor().getId() == principal.getId());
		}
		res = this.creditCardRepository.save(creditCard);
		return res;
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
	//Other business methods -------------------------------------------------------------
	public CreditCard findCreditCardByActorId(final int actorId) {
		CreditCard res;
		res = this.creditCardRepository.findCreditCardByActorId(actorId);
		Assert.notNull(res);
		return res;
	}

	//Contructors
	public CreditCard reconstruct(final ActorForm form, final BindingResult binding) {
		CreditCard res;

		res = this.create();
		res.setHolder(form.getHolder());
		res.setMake(form.getMake());
		res.setExpirationMonth(form.getExpirationMonth());
		res.setExpirationYear(form.getExpirationYear());
		res.setNumber(form.getNumber());
		res.setCVV(form.getCVV());

		//Validation the form
		this.validator.validate(form, binding);

		if (this.checkCreditCard(form.getNumber(), form.getExpirationMonth(), form.getExpirationYear()))
			binding.rejectValue("creditCard", "creditCard.invalid");

		return res;

	}

	public boolean checkCreditCard(final String number, final Integer expMonth, final Integer expYear) {
		boolean res = false;
		final Calendar fecha = Calendar.getInstance();
		final int mes = fecha.get(Calendar.MONTH) + 1;
		final int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		if (expYear != null || expMonth != null)
			if ((expYear < anyo || (expYear == anyo && expMonth < mes)))

				res = true;

		final Collection<CreditCard> creditcards = this.findAll();
		for (final CreditCard c : creditcards)
			if (c.getNumber() == number) {
				res = true;
				break;
			}
		return res;
	}

	//Construct : CreditCardForm --> CreditCard
	public CreditCard reconstruct2(final CreditCardForm form, final BindingResult binding) {
		CreditCard res;
		res = new CreditCard();
		res.setActor(this.findOne(form.getId()).getActor());

		//CreditCard Atributes
		res.setId(form.getId());
		res.setHolder(form.getHolder());
		res.setMake(form.getMake());
		res.setNumber(form.getNumber());
		res.setExpirationMonth(form.getExpirationMonth());
		res.setExpirationYear(form.getExpirationYear());
		res.setCVV(form.getCVV());
		res.setVersion(form.getVersion());
		//Validating the form
		this.validator.validate(form, binding);
		if (!(form.getNumber() == null || form.getExpirationMonth() == null || form.getExpirationYear() == null))
			if (this.checkCreditCard(form.getNumber(), form.getExpirationMonth(), form.getExpirationYear()))
				binding.rejectValue("number", "creditCard.invalid");
		return res;
	}

	//Construct : CreditCard --> CreditCardForm
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

	public void deleteByUserDropOut(CreditCard creditCard) {
		this.creditCardRepository.delete(creditCard);
		
	}

}

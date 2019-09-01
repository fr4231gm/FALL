package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import forms.CreditCardForm;

@Service
@Transactional

public class CreditCardService {
	// Managed repository --------------------
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private Validator validator;
	
	// Supporting services -------------------
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	// Constructors -----------------

	public CreditCardService() {
		super();
	}
	// CRUDS Methods ----------------------
	public CreditCard create(){
		//Comprobamos que la persona logueada es un sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		//Nuevo Sponsorship
		final CreditCard res = new CreditCard();
		
		//Definimos las clases asociadas;
		res.setSponsor(principal);
		
		return res;
	}
	
	public CreditCard save(final CreditCard creditCard){
		//Comprobamos que la persona logueada es un Sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		
		//New CreditCard
		CreditCard res = null;
		
//		if(creditCard.getId()==0){
//			res = this.creditCardRepository.save(creditCard);
//		}else {
//			Assert.isTrue(creditCard.getSponsor().getId() == principal.getId());
//			res = this.creditCardRepository.save(creditCard);
//		}
		
		res = this.creditCardRepository.save(creditCard);
		return res;
	}
	
	public void delete(final CreditCard creditCard){
		//Comprobamos que la persona logueada es un Sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(creditCard);
		
		//Comprobamos que la creditCard pertenece al sponsor.
		Assert.isTrue(creditCard.getSponsor().getId()== principal.getId());
		
		//Compruebo que la creditcard no esta asociada a ningun sponsorship
		Collection<Sponsorship> sp = this.sponsorshipService.findSponsorshipsByCreditCardId(creditCard.getId());
		Assert.isTrue(sp.size()==0);// solo dejo borrar si no hay sponsorships asociados
		
		//Llamo para borrar
		this.creditCardRepository.delete(creditCard);
	}
	
	public CreditCard findOne(final int ccId){
		CreditCard res;
		
		res = this.creditCardRepository.findOne(ccId);
		Assert.notNull(res);
		
		return res;
	}
	
	public CreditCard findOneToFail(final int ccId){
		CreditCard res;
		
		res = this.creditCardRepository.findOne(ccId);
		
		return res;
	}
	
	public Collection<CreditCard> findAll(){
		Collection<CreditCard> res;
		
		res = this.creditCardRepository.findAll();
		Assert.notNull(res);
		
		return res;
	}
	
	// Other business methods ------------------------------------------------

	public Collection<CreditCard> findCreditCardsBySponsorId(final int sponsorId){
		Collection<CreditCard> res;
		
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Assert.isTrue(principal.getId() == sponsorId);
		
		res = this.creditCardRepository.findCreditCardsBySponsorId(sponsorId);
		Assert.notNull(res);
		
		return res;
	}
	
	// Find ACTIVE CreditCars from Sponsor
	public Collection<CreditCard> findActiveCreditCardsBySponsorId (int sponsorId){
		Collection<CreditCard> aux = new ArrayList<>();
		Collection<CreditCard> res = new ArrayList<>();
		Calendar fecha = Calendar.getInstance();
		int mes = fecha.get(Calendar.MONTH)+1;
		int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		aux = this.creditCardRepository.findCreditCardsBySponsorId(sponsorId);
		
		for (CreditCard c : aux){
			if (c.getExpirationYear() > anyo 
					|| (c.getExpirationYear()==anyo && c.getExpirationMonth() > mes ))
			res.add(c);
		
		}
		Assert.notNull(res);
		return res;
	}
	
	//Construct : CreditCardForm --> CreditCard
	public CreditCard reconstruct(CreditCardForm form, BindingResult binding){
		CreditCard res;
		
		//Creating a new CreditCard
		if(form.getId() == 0){
			res = this.create();
			form.setSponsorId(res.getSponsor().getId());
			Assert.isTrue(form.getSponsorId()!=0);
		}else {
			res = new CreditCard();
			res.setSponsor(this.findOne(form.getId()).getSponsor());
			Assert.isTrue(form.getSponsorId().equals(res.getSponsor()));
		}

		//CreditCard Atributes
		res.setId(form.getId());
		res.setHolder(form.getHolder());
		res.setMake(form.getMake());
		res.setNumber(form.getNumber());
		res.setExpirationMonth(form.getExpirationMonth());
		res.setExpirationYear(form.getExpirationYear());
		res.setCVV(form.getCVV());
		
		//Validating the form
		this.validator.validate(form, binding);
		
		return res;
	}
	
	//Construct : CreditCard --> CreditCardForm
	public CreditCardForm construct(CreditCard creditCard){
		
		CreditCardForm res = new CreditCardForm();
		
		res.setId(creditCard.getId());
		res.setHolder(creditCard.getHolder());
		res.setMake(creditCard.getMake());
		res.setNumber(creditCard.getNumber());
		res.setExpirationMonth(creditCard.getExpirationMonth());
		res.setExpirationYear(creditCard.getExpirationYear());
		res.setCVV(creditCard.getCVV());
		res.setSponsorId(creditCard.getSponsor().getId());
		
		return res;
	}
	public void flush() {
		this.creditCardRepository.flush();
	}
}

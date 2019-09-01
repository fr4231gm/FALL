
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;


	public CreditCardService() {
		super();
	}

	//propiedades por defect?
	public CreditCard create() {
		CreditCard result;
		result = new CreditCard();
		Assert.notNull(result);
		return result;

	}

	public CreditCard save(final CreditCard c) {
		Assert.notNull(c);
		return this.creditCardRepository.save(c);
	}

	public Collection<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}

	public CreditCard findOne(int creditCardId) {
		
		return this.creditCardRepository.findOne(creditCardId);
	}
}


package services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChargeRepository;
import domain.Charge;

@Service
@Transactional
public class ChargeService {

	// Managed Repository------------------------------------------------------
	@Autowired
	private ChargeRepository		chargeRepository;

	// Supporting services-----------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors------------------------------------------------------------

	public ChargeService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	//CREATE
	public Charge create() {
		Charge res;

		res = new Charge();
		res.setAmount(this.configurationService.findConfiguration().getFare());
		res.setMoment(Calendar.getInstance().getTime());
		res.setTax(this.configurationService.findConfiguration().getVat());

		Assert.notNull(res);
		return res;
	}

	//SAVE
	@SuppressWarnings("deprecation")
	public Charge save(final Charge charge) {
		Charge res;
		Assert.notNull(charge);

		final Date moment = new Date();
		moment.setSeconds(charge.getMoment().getSeconds() - 1);
		charge.setMoment(moment);
		res = this.chargeRepository.save(charge);
		Assert.notNull(res);

		return res;
	}
	//FINDONE
	public Charge findOne(final int id) {
		Charge result;
		result = this.chargeRepository.findOne(id);
		return result;
	}
	//FLUSH
	public void flush() {
		this.chargeRepository.flush();
	}

}

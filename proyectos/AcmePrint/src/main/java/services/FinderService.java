
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Company;
import domain.Configuration;
import domain.Finder;
import domain.Order;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------

	@Autowired
	private FinderRepository		finderRepository;

	//Managed services --------------------

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD'S methods

	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<Order> orders = new ArrayList<Order>();
		result.setOrders(orders);

		final Date d = new Date(System.currentTimeMillis() - 1);
		result.setLastUpdate(d);
		return result;
	}

	public Finder saveFinder(final Finder finder) {

		Assert.notNull(finder);
		if (finder.getMinDate() != null && finder.getMaxDate() != null)
			Assert.isTrue(finder.getMinDate().before(finder.getMaxDate()));
		if (finder.getId() != 0) {
			final Company principal = this.companyService.findByPrincipal();
			Assert.isTrue(finder.equals(principal.getFinder()));
		}
		Collection<Order> orders = new ArrayList<Order>();

		String keyWord = "";

		final Calendar minDateCalendar = Calendar.getInstance();
		minDateCalendar.set(1900, 01, 01, 00, 00);
		Date minDate = new Date();
		minDate = minDateCalendar.getTime();

		final Calendar maxDateCalendar = Calendar.getInstance();
		maxDateCalendar.set(9999, 01, 01, 00, 00);
		Date maxDate = new Date();
		maxDate = maxDateCalendar.getTime();

		if (finder.getKeyword() != null)
			keyWord = finder.getKeyword();

		if (finder.getMinDate() != null)
			minDate = finder.getMinDate();

		if (finder.getMaxDate() != null)
			maxDate = finder.getMaxDate();

		final Collection<Order> orderes;
		orders = this.finderRepository.filter(keyWord, minDate, maxDate);
		if (orders.size() > this.configurationService.findConfiguration().getMaxFinder()) {
			final List<Order> list = new ArrayList<>(orders);
			orderes = list.subList(0, this.configurationService.findConfiguration().getMaxFinder());
			finder.setOrders(orderes);
		} else
			finder.setOrders(orders);

		final Date now = new Date(System.currentTimeMillis() - 1);
		finder.setLastUpdate(now);

		return this.finderRepository.save(finder);
	}

	//Metodo para comprobar que el finder ha expirado segun el lifespan
	public boolean isFInderExpired(final Finder finder) {

		boolean res = false;
		final Long actualDate = new Date().getTime();
		final Long dateFinder = finder.getLastUpdate().getTime();

		final Long compareDate = Math.abs(actualDate - dateFinder);

		final Configuration configuration = this.configurationService.findAll().iterator().next();
		final Long config = (long) configuration.getFinderLifeSpan();

		final Long limit = (config * 60) * 1000;
		if (compareDate >= limit)
			res = true;
		return res;
	}

	//No se si haría falta este realmente, pero al ser un método básico de los servicios, lo incluyo por si acaso, y si al terminar no sirve, se borra
	public Collection<Finder> findAll() {
		final Collection<Finder> all = new ArrayList<Finder>();
		all.addAll(this.finderRepository.findAll());
		return all;
	}

	public boolean filterFinder(final Finder f) {
		Collection<Finder> filtered = new ArrayList<Finder>();
		final Company principal = this.companyService.findByPrincipal();
		boolean res = true;

		String keyWord = "";

		final Calendar minDateCalendar = Calendar.getInstance();
		minDateCalendar.set(1900, 01, 01, 00, 00);
		Date minDate = new Date();
		minDate = minDateCalendar.getTime();

		final Calendar maxDateCalendar = Calendar.getInstance();
		maxDateCalendar.set(9999, 01, 01, 00, 00);
		Date maxDate = new Date();
		maxDate = maxDateCalendar.getTime();

		if (f.getKeyword() != null)
			keyWord = f.getKeyword();

		if (f.getMinDate() != null)
			minDate = f.getMinDate();

		if (f.getMaxDate() != null)
			maxDate = f.getMaxDate();

		filtered = this.finderRepository.filterFinder(keyWord, minDate, maxDate);
		if (filtered.contains(principal.getFinder()))
			;
		res = false;
		return res;
	}
	public void deleteByUserDropOut(final Finder finder) {
		this.finderRepository.delete(finder);

	}
	
	public Finder findByOrderId(final int orderId){
		Finder res;
		
		res = this.finderRepository.findByOrderId(orderId);
		
		return res;
	}

}

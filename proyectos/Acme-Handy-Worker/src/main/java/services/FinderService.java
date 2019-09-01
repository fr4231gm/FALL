
package services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository		finderRepository;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	public FinderService() {
		super();
	}

	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		result.setFixUpTasks(fixUpTasks);
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result.setHandyWorker(principal);
		final Date d = new Date(System.currentTimeMillis() - 1);
		result.setLastUpdate(d);
		return result;
	}
	
	public Finder create2() {
		Finder result;
		result = new Finder();
		final Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		result.setFixUpTasks(fixUpTasks);
		final Date d = new Date(System.currentTimeMillis() - 1);
		result.setLastUpdate(d);
		return result;
	}

	public Finder saveFinder(final Finder finder) {
		Assert.notNull(finder);
		if(finder.getPriceMax() != null){
		    Double price = finder.getPriceMax();
		    DecimalFormat decim = new DecimalFormat("0.00");
		    Double price2 = Double.parseDouble(decim.format(price));
		   	finder.setPriceMax(price2);
		}
		if(finder.getPriceMin() != null){
		    Double price3 = finder.getPriceMin();
		    DecimalFormat decim = new DecimalFormat("0.00");
		    Double price4 = Double.parseDouble(decim.format(price3));
		   	finder.setPriceMin(price4);
		}
		//Assert.isTrue(principal.equals(finder.getHandyWorker()));

		Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();

		String keyWord = "";
		Double minPrice = 0.0;
		Double maxPrice = 9999999.9;

		final Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(1950, 01, 01, 00, 00);
		Date startDate = new Date();
		startDate = startDateCalendar.getTime();

		final Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2100, 01, 01, 00, 00);
		Date endDate = new Date();
		endDate = endDateCalendar.getTime();

		if (finder.getKeyWord() != null)
			keyWord = finder.getKeyWord();

		if (finder.getPriceMin() != null)
			minPrice = finder.getPriceMin();

		if (finder.getPriceMax() != null)
			maxPrice = finder.getPriceMax();

		if (finder.getStartDate() != null)
			startDate = finder.getStartDate();

		if (finder.getEndDate() != null)
			endDate = finder.getEndDate();

		if (finder.getCategory() == null && finder.getWarranty() != null)
			fixUpTasks = this.finderRepository.filterNoCategory(keyWord, finder.getWarranty().getId(), minPrice, maxPrice, startDate, endDate);
		else if (finder.getWarranty() == null && finder.getCategory() != null)
			fixUpTasks = this.finderRepository.filterNoWarranty(keyWord, finder.getCategory().getId(), minPrice, maxPrice, startDate, endDate);
		else if (finder.getCategory() == null && finder.getWarranty() == null)
			fixUpTasks = this.finderRepository.filterNoCategoryWarranty(keyWord, minPrice, maxPrice, startDate, endDate);
		else
			fixUpTasks = this.finderRepository.filter(keyWord, finder.getCategory().getId(), finder.getWarranty().getId(), minPrice, maxPrice, startDate, endDate);

		final Date now = new Date(System.currentTimeMillis() - 1);
		finder.setLastUpdate(now);
		finder.setFixUpTasks(fixUpTasks);
		return this.finderRepository.save(finder);
	}
	public Finder findOneByPrincipal() {
		final Finder result;
		final HandyWorker hw = this.handyWorkerService.findByPrincipal();
		result = this.finderRepository.findOneByPrincipal(hw.getId());
		Assert.notNull(result);
		return result;
	}
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

	public Collection<Finder> findAll() {
		final Collection<Finder> all = new ArrayList<Finder>();
		all.addAll(this.finderRepository.findAll());
		return all;
	}

	public Collection<Finder> findByCategory(int id) {
		return this.finderRepository.findByCategory(id);
	}
}

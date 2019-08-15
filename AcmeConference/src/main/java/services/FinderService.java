
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------

	@Autowired
	private FinderRepository		finderRepository;

	//Managed services --------------------

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD'S methods

	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<Conference> confs = new ArrayList<Conference>();
		result.setAuthor(this.authorService.findByPrincipal());
		result.setConferences(confs);

		return result;
	}
	public Finder saveFinder(final Finder finder) {

		Assert.notNull(finder);
		if (finder.getStartDate() != null && finder.getEndDate() != null)
			Assert.isTrue(finder.getStartDate().before(finder.getEndDate()));
		if (finder.getId() != 0)
			Assert.isTrue(finder.equals(this.finderRepository.findAuthorByFinder(this.authorService.findByPrincipal().getId())));

		String keyWord = "";

		Double fee = 99999.0;

		final Calendar minDateCalendar = Calendar.getInstance();
		minDateCalendar.set(1900, 01, 01, 00, 00);
		Date minDate = new Date();
		minDate = minDateCalendar.getTime();

		final Calendar maxDateCalendar = Calendar.getInstance();
		maxDateCalendar.set(9999, 01, 01, 00, 00);
		Date maxDate = new Date();
		maxDate = maxDateCalendar.getTime();

		if (finder.getKeyWord() != null)
			keyWord = finder.getKeyWord();

		if (finder.getStartDate() != null)
			minDate = finder.getStartDate();

		if (finder.getEndDate() != null)
			maxDate = finder.getEndDate();
		if (finder.getFee() != null)
			fee = finder.getFee();

		final Collection<Conference> conferences2;
		conferences2 = this.finderRepository.filter(keyWord, minDate, maxDate, fee);
		finder.setConferences(conferences2);

		return this.finderRepository.save(finder);
	}
	//No se si haría falta este realmente, pero al ser un método básico de los servicios, lo incluyo por si acaso, y si al terminar no sirve, se borra
	public Collection<Finder> findAll() {
		final Collection<Finder> all = new ArrayList<Finder>();
		all.addAll(this.finderRepository.findAll());
		return all;
	}

	public void deleteByUserDropOut(final Finder finder) {
		this.finderRepository.delete(finder);

	}

	public Finder findAuthorByFinder() {
		final Finder f = this.finderRepository.findAuthorByFinder(this.authorService.findByPrincipal().getId());
		Assert.notNull(f);
		return f;
	}

}

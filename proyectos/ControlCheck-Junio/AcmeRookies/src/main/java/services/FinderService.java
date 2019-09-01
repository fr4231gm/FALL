
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;
import domain.Position;
import domain.Rookie;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------

	@Autowired
	private FinderRepository		finderRepository;

	//Managed services --------------------

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD'S methods

	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<Position> positions = new ArrayList<Position>();
		result.setPositions(positions);

		final Date d = new Date(System.currentTimeMillis() - 1);
		result.setLastUpdate(d);
		return result;
	}

	public Finder saveFinder(final Finder finder) {

		Assert.notNull(finder);
		if (finder.getMinimumSalary() != null && finder.getMaximumSalary() != null)
			Assert.isTrue(finder.getMinimumSalary() <= finder.getMaximumSalary());
		if (finder.getId() != 0) {
			final Rookie principal = this.rookieService.findByPrincipal();
			Assert.isTrue(finder.equals(principal.getFinder()));
		}
		Collection<Position> positions = new ArrayList<Position>();

		String keyWord = "";
		Double minimumSalary = 0.;
		Double maximumSalary = 99999.;

		final Calendar deadlineCalendar = Calendar.getInstance();
		deadlineCalendar.set(9999, 01, 01, 00, 00);
		Date deadline = new Date();
		deadline = deadlineCalendar.getTime();

		if (finder.getKeyword() != null)
			keyWord = finder.getKeyword();

		if (finder.getDeadline() != null)
			deadline = finder.getDeadline();

		if (finder.getMinimumSalary() != null)
			minimumSalary = finder.getMinimumSalary();

		if (finder.getMaximumSalary() != null)
			maximumSalary = finder.getMaximumSalary();
		final Collection<Position> positiones;
		positions = this.finderRepository.filter(keyWord, deadline, minimumSalary, maximumSalary);
		if (positions.size() > this.configurationService.findConfiguration().getMaxFinder()) {
			final List<Position> list = new ArrayList<>(positions);
			positiones = list.subList(0, this.configurationService.findConfiguration().getMaxFinder());
			finder.setPositions(positiones);
		} else
			finder.setPositions(positions);

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
		final Rookie principal = this.rookieService.findByPrincipal();
		boolean res = true;

		String keyWord = "";
		Double minimumSalary = 0.;
		Double maximumSalary = 99999.;

		final Calendar deadlineCalendar = Calendar.getInstance();
		deadlineCalendar.set(9999, 01, 01, 00, 00);
		Date deadline = new Date();
		deadline = deadlineCalendar.getTime();

		if (f.getKeyword() != null)
			keyWord = f.getKeyword();

		if (f.getDeadline() != null)
			deadline = f.getDeadline();

		if (f.getMinimumSalary() != null)
			minimumSalary = f.getMinimumSalary();

		if (f.getMaximumSalary() != null)
			maximumSalary = f.getMaximumSalary();

		filtered = this.finderRepository.filterFinder(keyWord, deadline, minimumSalary, maximumSalary);
		if (filtered.contains(principal.getFinder()))
			res = false;
		return res;
	}

	public void deleteByUserDropOut(final Finder finder) {
		this.finderRepository.delete(finder);

	}

	public Collection<Finder> findByPositionId(int positionId) {
		return this.finderRepository.findByPositionId(positionId);
	}

	public void updateByUserDropOut(Finder finderToUpdate) {
		this.finderRepository.save(finderToUpdate);
		
	}

}

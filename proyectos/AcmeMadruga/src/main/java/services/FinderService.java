
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------

	@Autowired
	private FinderRepository		finderRepository;

	//Managed services --------------------

	@Autowired
	private MemberService			memberService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD´S methods

	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<Procession> processions = new ArrayList<Procession>();
		result.setProcessions(processions);

		final Date d = new Date(System.currentTimeMillis() - 1);
		result.setLastUpdate(d);
		return result;
	}

	public Finder saveFinder(final Finder finder) {
		Assert.notNull(finder);
		if(finder.getEndDate() != null && finder.getStartDate() != null)
			Assert.isTrue(finder.getEndDate().after(finder.getStartDate()));
		if(finder.getId()!=0){
			final Member principal = this.memberService.findByPrincipal();
			Assert.isTrue(finder.equals(principal.getFinder()));
		}
		Collection<Procession> Processions = new ArrayList<Procession>();

		String keyWord = "";
		String area = "";

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

		if (finder.getArea() != null)
			area = finder.getArea();

		if (finder.getStartDate() != null)
			startDate = finder.getStartDate();

		if (finder.getEndDate() != null)
			endDate = finder.getEndDate();

		Processions = this.finderRepository.filter(keyWord, area, startDate, endDate);

		final Date now = new Date(System.currentTimeMillis() - 1);
		finder.setLastUpdate(now);
		finder.setProcessions(Processions);
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

	//No se si hará falta este realmente, pero al ser un método básico de los servicios, lo incluyo por si acaso, y si al terminar no sirve, se borra
	public Collection<Finder> findAll() {
		final Collection<Finder> all = new ArrayList<Finder>();
		all.addAll(this.finderRepository.findAll());
		return all;
	}

	public void deleteByUserDropOut(Finder finder) {
		this.finderRepository.delete(finder);
		
	}

}

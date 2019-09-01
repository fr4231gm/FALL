
package services;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	//Simple CRUD methods
	public Configuration create() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		final Configuration c = new Configuration();
		return c;
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		return this.configurationRepository.save(configuration);
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		this.configurationRepository.delete(configuration);
	}

	// Retrievers

	public Configuration findOne(final int id) {
		Assert.notNull(id);
		return this.configurationRepository.findOne(id);
	}

	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findConfiguration() {
		Configuration res;
		List<Configuration> configs;
		final Locale locale = LocaleContextHolder.getLocale();
		final String ls = locale.getLanguage();
		configs = this.configurationRepository.findAll();
		Assert.notNull(configs);
		if (configs.get(0).getLanguage().equals(ls))
			res = configs.get(0);
		else
			res = configs.get(1);
		return res;

	}

}

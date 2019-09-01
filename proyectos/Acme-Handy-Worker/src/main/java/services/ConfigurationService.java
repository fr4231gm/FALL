package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	// Managed repository

	@Autowired
	private ConfigurationRepository configurationRepository;

	// Supporting services --------------------------------

	@Autowired
	private ActorService actorService;

	// Simple CRUD methods
	public Configuration create() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		final Configuration c = new Configuration();
		return c;
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		final Actor principal = this.actorService.findByPrincipal();
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
		String lan = LocaleContextHolder.getLocale().getLanguage();
		Configuration config = this.configurationRepository.findByLanguage(lan);
		Assert.notNull(config);
		return config;
	}

	public List<String> findAllMakes() {
		final List<String> res = new ArrayList<String>();
		final String[] aux = this.configurationRepository.findAllMakes(this.findConfiguration().getId()).split("\n");
		for (int i = 0; i < aux.length; i++) {
			final String aux2 = aux[i].trim();
			res.add(aux2);
		}
		return res;
	}

}

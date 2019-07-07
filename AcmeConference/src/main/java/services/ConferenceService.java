
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	//Repositorios
	@Autowired
	private ConferenceRepository	conferenceRepository;


	public Collection<Conference> findRunningConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository.findRunningConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Conference findOne(final int conferenceId) {
		final Conference c = this.conferenceRepository.findOne(conferenceId);
		Assert.notNull(c);
		return c;
	}

	public Collection<Conference> searchConferenceAnonymousRunning(final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterRunning(keyword, actual);
		return res;
	}

	//Servicios externos

}

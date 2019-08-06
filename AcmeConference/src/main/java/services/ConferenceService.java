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

	// Repositorios
	@Autowired
	private ConferenceRepository conferenceRepository;

	public Conference update(Conference conference){
		Conference res;
		
		res = this.conferenceRepository.save(conference);
		
		return res;
	}
	
	public Collection<Conference> findRunningConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository.findRunningConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Collection<Conference> findForthcomingConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository
				.findForthcomingConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Collection<Conference> findPastConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository.findPastConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Conference findOne(final int conferenceId) {
		final Conference c = this.conferenceRepository.findOne(conferenceId);
		return c;
	}

	public Collection<Conference> searchConferenceAnonymousRunning(
			final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterRunning(keyword, actual);
		return res;
	}

	public Collection<Conference> searchConferenceAnonymousForthcomming(
			final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterForthcomming(keyword, actual);
		return res;
	}

	public Collection<Conference> searchConferenceAnonymousPast(
			final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterPast(keyword, actual);
		return res;
	}

	public Collection<Conference> findAll() {
		Collection<Conference> result;
		result = this.conferenceRepository.findAll();
		return result;
	}

	// Servicios externos

}

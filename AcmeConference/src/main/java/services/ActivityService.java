package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActivityRepository;
import domain.Activity;

@Service
@Transactional
public class ActivityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActivityRepository activityRepository;

	// Supporting services ----------------------------------------------------

	// Constructors ------------------------------------

	public ActivityService() {
		super();
	}

	// Simple CRUDs methods ---------------------------------------------------

	public Activity findOne(final int activityId) {

		return this.activityRepository.findOne(activityId);
	}

	public Collection<Activity> findAll() {
		Collection<Activity> result;

		result = this.activityRepository.findAll();

		return result;
	}

	// Other business methods

	public Collection<Activity> findActivitiesByConferenceId(int conferenceId) {
		Collection<Activity> res;

		res = this.activityRepository.findActivitiesByConferenceId(conferenceId);

		return res;
	}

}

package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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

		res = this.activityRepository
				.findActivitiesByConferenceId(conferenceId);

		return res;
	}

	public String getSchedule(Activity activity) {
		String res;
		Date startMoment;
		Integer duration;
		Calendar c;
		Date endMoment;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		startMoment = activity.getStartMoment();
		duration = activity.getDuration();

		c = Calendar.getInstance();
		c.setTime(startMoment);
		c.add(Calendar.MINUTE, duration);
		endMoment = c.getTime();

		res = formatter.format(startMoment) + " - "
				+ formatter.format(endMoment);

		return res;
	}

	// Return true if the start moment of tutorial is correct
	public boolean checkStartMoment(Activity activity) {
		boolean res = true;

		if (activity.getStartMoment().before(activity.getConference().getStartDate())) {
			res = false;
		}

		return res;
	}

}

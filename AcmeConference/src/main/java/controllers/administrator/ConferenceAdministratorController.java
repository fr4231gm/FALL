
package controllers.administrator;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Category;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/listDeadlineElapsed", method = RequestMethod.GET)
	public ModelAndView listDeadlineElapsed() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findDeadlineElapsed();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listDeadlineElapsed.do");

		return result;
	}

	@RequestMapping(value = "/listNotificationElapsed", method = RequestMethod.GET)
	public ModelAndView listNotificationElapsed() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findNotificationElapsed();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listNotificationElapsed.do");

		return result;
	}

	@RequestMapping(value = "/listCameraElapsed", method = RequestMethod.GET)
	public ModelAndView listCameraElapsed() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findCameraReadyElapsed();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listCameraElapsed.do");

		return result;
	}

	@RequestMapping(value = "/listFutureConferences", method = RequestMethod.GET)
	public ModelAndView listFutureConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findFutureConferences();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/administrator/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listFutureConferences.do");

		return result;
	}

	@RequestMapping(value = "/listAllConferences", method = RequestMethod.GET)
	public ModelAndView listAllConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findAll();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listAllConferences.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Conference conf;

		conf = this.conferenceService.create();

		res = this.createEditModelAndView(conf);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView res;
		Conference conf;
		try {

			this.administratorService.findByPrincipal();
			conf = this.conferenceService.findOne(conferenceId);
			Assert.isTrue(conf.getIsDraft() == true);
			res = this.createEditModelAndView(conf);
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final @Valid Conference c, final BindingResult binding) {
		ModelAndView res;
		final Date actual = new Date(System.currentTimeMillis());
		if (c.getIsDraft() == null)
			c.setIsDraft(false);
		if (c.getSubmissionDeadline() != null)
			if (c.getSubmissionDeadline().before(actual))
				binding.rejectValue("submissionDeadline", "date.submission.future");

		if (c.getNotificationDeadline() != null)
			if (c.getNotificationDeadline().before(actual))
				binding.rejectValue("notificationDeadline", "date.notification.future");
		if (c.getCameraReadyDeadline() != null)
			if (c.getCameraReadyDeadline().before(actual))
				binding.rejectValue("submissionDeadline", "date.camera.ready.future");
		if (c.getStartDate() != null)
			if (c.getStartDate().before(actual))
				binding.rejectValue("startDate", "date.start.future");
		if (c.getEndDate() != null && c.getStartDate() != null)
			if (c.getEndDate().before(c.getStartDate()))
				binding.rejectValue("endDate", "date.end.date.future");
		if (binding.hasErrors())
			res = this.createEditModelAndView(c);
		else
			try {
				this.conferenceService.save(c);
				res = new ModelAndView("redirect:listAllConferences.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(c, "conference.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(final @Valid Conference c, final BindingResult binding) {
		ModelAndView res;
		final Date actual = new Date(System.currentTimeMillis());
		if (c.getIsDraft() == null)
			c.setIsDraft(false);
		if (c.getSubmissionDeadline() != null)
			if (c.getSubmissionDeadline().before(actual))
				binding.rejectValue("submissionDeadline", "date.submission.future");

		if (c.getNotificationDeadline() != null)
			if (c.getNotificationDeadline().before(actual))
				binding.rejectValue("notificationDeadline", "date.notification.future");
		if (c.getCameraReadyDeadline() != null)
			if (c.getCameraReadyDeadline().before(actual))
				binding.rejectValue("submissionDeadline", "date.camera.ready.future");
		if (c.getStartDate() != null)
			if (c.getStartDate().before(actual))
				binding.rejectValue("startDate", "date.start.future");
		if (c.getEndDate() != null && c.getStartDate() != null)
			if (c.getEndDate().before(c.getStartDate()))
				binding.rejectValue("endDate", "date.end.date.future");
		if (binding.hasErrors())
			res = this.createEditModelAndView(c);
		else
			try {
				this.conferenceService.save(c);
				res = new ModelAndView("redirect:listAllConferences.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(c, "conference.commit.error");
			}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Conference conf) {
		ModelAndView result;
		result = this.createEditModelAndView(conf, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Conference conf, final String messageCode) {
		ModelAndView result;

		final Collection<Category> categories = this.categoryService.findAll();
		Locale locale;
		locale = LocaleContextHolder.getLocale();

		result = new ModelAndView("conference/edit");
		result.addObject("lan", locale.getLanguage());
		result.addObject("conference", conf);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);

		return result;
	}

}

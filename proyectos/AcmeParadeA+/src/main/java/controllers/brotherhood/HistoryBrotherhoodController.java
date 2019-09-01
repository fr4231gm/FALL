
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.HistoryService;
import services.InceptionRecordService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;

@Controller
@RequestMapping("/history/brotherhood")
public class HistoryBrotherhoodController extends AbstractController {

	// Services
	@Autowired
	private HistoryService			historyService;

	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	// Constructors
	public HistoryBrotherhoodController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		History history;
		final Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		history = this.historyService.findByPrincipal();
		result = new ModelAndView("history/display");

		if (history == null)
			result = new ModelAndView("history/noHistory");

		result.addObject("history", history);
		result.addObject("principal", principal);

		return result;

	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.create();
		result = this.createEditModelAndView(inceptionRecord);
		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int historyId) {
		ModelAndView result;
		History history;
		try {
			history = this.historyService.findOneToEdit(historyId);
			Assert.notNull(history);
			result = this.createEditModelAndView(history.getInceptionRecord());
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;
		if (this.inceptionRecordService.checkPictures(inceptionRecord.getPictures()))
			binding.rejectValue("pictures", "brotherhood.pictures.invalid");
		if (binding.hasErrors())
			result = this.createEditModelAndView(inceptionRecord);
		else
			try {
				this.inceptionRecordService.save(inceptionRecord);

				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(inceptionRecord, "history.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.historyService.delete(this.historyService.findByInceptionId(inceptionRecord.getId()));
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(inceptionRecord, "history.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final InceptionRecord inceptionRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(inceptionRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final InceptionRecord inceptionRecord, final String messageCode) {
		final ModelAndView result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();

		result = new ModelAndView("history/edit");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("principal", principal);

		result.addObject("message", messageCode);

		return result;

	}

}

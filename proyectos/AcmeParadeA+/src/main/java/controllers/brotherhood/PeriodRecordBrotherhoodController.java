
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

import services.HistoryService;
import services.PeriodRecordService;
import controllers.AbstractController;
import domain.History;
import domain.PeriodRecord;

@Controller
@RequestMapping("/periodRecord/brotherhood")
public class PeriodRecordBrotherhoodController extends AbstractController {

	// Services

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PeriodRecordService	periodRecordService;


	// Constructors

	public PeriodRecordBrotherhoodController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		PeriodRecord pr;

		pr = this.periodRecordService.create();

		result = this.createEditModelAndView(pr);

		return result;

	}
	
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int periodRecordId) {
		ModelAndView result;
		final PeriodRecord pr;
		try{
		pr = this.periodRecordService.findOneToEdit(periodRecordId);
		Assert.notNull(pr);
		result = this.createEditModelAndView(pr);
		}catch(Throwable oops){
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PeriodRecord periodRecord, final BindingResult binding) {
		ModelAndView result;
		if(this.periodRecordService.checkPictures(periodRecord.getPictures())){
			binding.rejectValue("pictures", "brotherhood.pictures.invalid");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(periodRecord);
		else
			try {
				this.periodRecordService.save(periodRecord);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(periodRecord, "pr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PeriodRecord periodRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.periodRecordService.delete(periodRecord);
			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(periodRecord, "pr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final PeriodRecord pr) {
		ModelAndView result;

		result = this.createEditModelAndView(pr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PeriodRecord pr, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		History history;

		history = this.historyService.findByPrincipal();

		if (pr.getId() == 0)
			permission = true;
		else
			for (final PeriodRecord miscRec : history.getPeriodRecord())
				if (pr.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("periodRecord/edit");
		result.addObject("periodRecord", pr);
		result.addObject("permission", permission);
		result.addObject("history", history);

		result.addObject("message", messageCode);

		return result;

	}

}

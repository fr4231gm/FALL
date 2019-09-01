
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.History;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/brotherhood")
public class MiscellaneousRecordBrotherhoodController extends AbstractController {

	// Services

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors

	public MiscellaneousRecordBrotherhoodController() {
		super();
	}

	// Listing

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		MiscellaneousRecord mr;

		mr = this.miscellaneousRecordService.create();

		result = this.createEditModelAndView(mr);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		final MiscellaneousRecord mr;
		try{
		mr = this.miscellaneousRecordService.findOneToEdit(miscellaneousRecordId);
		Assert.notNull(mr);
		result = this.createEditModelAndView(mr);
		}catch(Throwable oops){
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "mr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "mr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord mr) {
		ModelAndView result;

		result = this.createEditModelAndView(mr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord mr, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		History history;

		history = this.historyService.findByPrincipal();

		if (mr.getId() == 0)
			permission = true;
		else
			for (final MiscellaneousRecord miscRec : history.getMiscellaneousRecord())
				if (mr.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", mr);
		result.addObject("permission", permission);
		result.addObject("history", history);

		result.addObject("message", messageCode);

		return result;

	}

}

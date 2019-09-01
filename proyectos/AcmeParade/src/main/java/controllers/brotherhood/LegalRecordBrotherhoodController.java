
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
import services.LegalRecordService;
import controllers.AbstractController;
import domain.History;
import domain.LegalRecord;

@Controller
@RequestMapping("/legalRecord/brotherhood")
public class LegalRecordBrotherhoodController extends AbstractController {

	// Services

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private LegalRecordService	legalRecordService;


	// Constructors

	public LegalRecordBrotherhoodController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		LegalRecord pr;

		pr = this.legalRecordService.create();

		result = this.createEditModelAndView(pr);

		return result;

	}
	
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalRecordId) {
		ModelAndView result;
		final LegalRecord pr;
		try{
		pr = this.legalRecordService.findOneToEdit(legalRecordId);
		Assert.notNull(pr);
		result = this.createEditModelAndView(pr);
		}catch(Throwable oops){
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalRecord legalRecord, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(legalRecord);
		else
			try {
				this.legalRecordService.save(legalRecord);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(legalRecord, "pr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalRecord legalRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.legalRecordService.delete(legalRecord);
			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalRecord, "pr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final LegalRecord pr) {
		ModelAndView result;

		result = this.createEditModelAndView(pr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalRecord lr, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		History history;

		history = this.historyService.findByPrincipal();

		if (lr.getId() == 0)
			permission = true;
		else
			for (final LegalRecord miscRec : history.getLegalRecord())
				if (lr.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("legalRecord/edit");
		result.addObject("legalRecord", lr);
		result.addObject("permission", permission);
		result.addObject("history", history);

		result.addObject("message", messageCode);

		return result;

	}

}


package controllers.brotherhood;

import java.util.Collection;

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
import services.LinkRecordService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;

@Controller
@RequestMapping("/linkRecord/brotherhood")
public class LinkRecordBrotherhoodController extends AbstractController {

	// Services

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private LinkRecordService	linkRecordService;
	
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors

	public LinkRecordBrotherhoodController() {
		super();
	}

	// Listing

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		LinkRecord lr;

		lr = this.linkRecordService.create();

		result = this.createEditModelAndView(lr);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int linkRecordId) {
		ModelAndView result;
		final LinkRecord lr;
		try{
		lr = this.linkRecordService.findOneToEdit(linkRecordId);
		Assert.notNull(lr);
		result = this.createEditModelAndView(lr);
		}catch(Throwable oops){
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(linkRecord);
		else
			try {
				this.linkRecordService.save(linkRecord);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(linkRecord, "lr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.linkRecordService.delete(linkRecord);
			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(linkRecord, "lr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final LinkRecord lr) {
		ModelAndView result;

		result = this.createEditModelAndView(lr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LinkRecord lr, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		History history;
		Collection<Brotherhood> brotherhoods;
		
		brotherhoods = this.brotherhoodService.findAll();
		
		history = this.historyService.findByPrincipal();
		
		brotherhoods.remove(history.getBrotherhood());

		if (lr.getId() == 0)
			permission = true;
		else
			for (final LinkRecord miscRec : history.getLinkRecord())
				if (lr.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("linkRecord/edit");
		result.addObject("linkRecord", lr);
		result.addObject("permission", permission);
		result.addObject("history", history);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", messageCode);

		return result;

	}

}

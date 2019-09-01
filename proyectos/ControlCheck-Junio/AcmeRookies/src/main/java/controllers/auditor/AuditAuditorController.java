
package controllers.auditor;

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

import services.AuditService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	//Services
	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;


	//Constructor
	public AuditAuditorController() {
		super();
	}

	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Audit audit;
		audit = this.auditService.create();
		result = this.createEditModelAndView(audit);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(audit);
			result.addObject("bind", binding);
		} else
			try {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/audit/auditor/list.do");
				result.addObject("message", "Error");
			}
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int auditId) {
		ModelAndView result;
		Audit audit;

		audit = this.auditService.findOne(auditId);
		Assert.isTrue(audit.getAuditor().equals(this.auditorService.findByPrincipal()));
		result = this.createEditModelAndView(audit);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(audit);
			result.addObject("bind", binding);
		} else
			try {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/audit/auditor/list.do");
				result.addObject("message", "Error");
			}
		return result;
	}

	//Deleting
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		try {
			this.auditService.delete(id);
			result = new ModelAndView("redirect:/audit/auditor/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/audit/auditor/list.do");
			result.addObject("message", "Error");
		}
		return result;
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Audit> audits;
		try {

			final Auditor principal = this.auditorService.findByPrincipal();
			audits = this.auditService.findAuditsByAuditorId(principal.getId());
			res = new ModelAndView("audit/list");
			res.addObject("audits", audits);
			res.addObject("requestURI", "audit/auditor/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notfind");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Audit audit) {
		final ModelAndView result = this.createEditModelAndView(audit, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Audit audit, final String messagecode) {
		ModelAndView result;
		final Auditor principal = this.auditorService.findByPrincipal();
		final Collection<Position> positions = principal.getPositions();
		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("message", messagecode);
		result.addObject("positions", positions);
		return result;
	}

}

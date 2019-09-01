
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import domain.Actor;
import domain.Audit;
import domain.Company;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	//Services
	@Autowired
	private AuditService	auditService;
	
	@Autowired
	private ActorService	actorService;



	//Constructor
	public AuditController() {
		super();
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int auditId) {
		ModelAndView res;
		Audit audit;
		Actor principal;
		boolean permiso;
	
		try {
			audit = this.auditService.findOne(auditId);
			
			try {
				principal = this.actorService.findByPrincipal();
				if (principal instanceof Company){
					if (principal.getId() == audit.getPosition().getCompany().getId()){
						permiso = true;
					}
					else{
						permiso =  false;
					}
				} else {
					if (principal.getId() == audit.getAuditor().getId()){
						permiso = true;
					}
					else{
						permiso =  false;
					}
				}
			} catch (final Throwable oops) {
					permiso =  false;
			}
			
			res = new ModelAndView("audit/display");
			res.addObject("audit", audit);
			res.addObject("permiso", permiso);
			
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notfind");
		}
		return res;
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int positionId) {
		ModelAndView res;
		Collection<Audit> audits;
		try {
			audits = this.auditService.findAllPublishedAuditsByPosition(positionId);
			res = new ModelAndView("audit/list");
			res.addObject("audits", audits);
			res.addObject("requestURI", "audit/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notfind");
		}
		return res;
	}

}

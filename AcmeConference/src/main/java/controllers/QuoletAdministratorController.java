
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController {

	@Autowired
	private QuoletService			quoletService;

	@Autowired
	private AdministratorService	adminService;


	//List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Quolet> quolets = this.quoletService.findByAdministrator(this.adminService.findByPrincipal().getId());
		res = new ModelAndView("quolet/list");
		res.addObject("quolets", quolets);
		res.addObject("requestURI", "quolet/administrator/list.do");
		return res;
	}

}

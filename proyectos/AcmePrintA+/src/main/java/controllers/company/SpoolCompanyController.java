
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ConfigurationService;
import services.InventoryService;
import services.SpoolService;
import controllers.AbstractController;
import domain.Company;
import domain.Inventory;
import domain.Spool;
import forms.SpoolForm;

@Controller
@RequestMapping("/spool/company")
public class SpoolCompanyController extends AbstractController {

	// Services
	@Autowired
	private InventoryService		inventoryService;

	@Autowired
	private SpoolService	spoolService;

	@Autowired
	private CompanyService			companyService;
	
	@Autowired
	private ConfigurationService	configurationService;

	// Constructors
	public SpoolCompanyController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final SpoolForm res = new SpoolForm();

		result = this.createEditModelAndView(res);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int spoolId) {
		ModelAndView result;
		final Spool spool;
		final Company principal = this.companyService.findByPrincipal();
		final Inventory c = this.inventoryService.findBySpoolId(spoolId);
		try {
			spool = this.spoolService.findOne(spoolId);
			Assert.isTrue(c.getCompany().equals(principal));
			Assert.notNull(spool);
			final SpoolForm res = this.spoolService.construct(spool);
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SpoolForm spoolForm, final BindingResult binding) {
		ModelAndView result;
		final Spool sp ;
		sp = this.spoolService.reconstruct(spoolForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(spoolForm);
		else
			try {
				this.spoolService.save(sp, spoolForm.getInventory().getId());
				result = new ModelAndView("redirect:/inventory/company/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(spoolForm, "spool.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SpoolForm spoolForm, final BindingResult binding) {
		ModelAndView result;
		try {
			this.spoolService.delete(this.spoolService.findOne(spoolForm.getId()));
			result = new ModelAndView("redirect:/inventory/company/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(spoolForm, "delete.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final SpoolForm sp ) {
		ModelAndView result;

		result = this.createEditModelAndView(sp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SpoolForm sp , final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Inventory inventory;
		Collection<Inventory> inventories;

		inventory = this.inventoryService.findBySpoolId(sp.getId());
		inventories = this.inventoryService.findByPrincipal();

		if (sp.getId() == 0)
			permission = true;
		else
			for (final Spool s : inventory.getSpools())
				if (sp.getId() == s.getId()) {
					permission = true;
					break;
				}
		String[] materials = this.configurationService.findConfiguration()
				.getMaterials().split(",");
	

		result = new ModelAndView("spool/edit");
		result.addObject("spoolForm", sp);
		result.addObject("permission", permission);
		result.addObject("inventory", inventory);
		result.addObject("inventories", inventories);
		result.addObject("message", messageCode);
		result.addObject("materials", materials);

		return result;

	}

}

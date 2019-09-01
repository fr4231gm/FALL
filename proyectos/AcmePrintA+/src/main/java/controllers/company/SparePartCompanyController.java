
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

import services.InventoryService;
import services.SparePartService;
import services.CompanyService;
import controllers.AbstractController;
import domain.Inventory;
import domain.SparePart;
import domain.Company;
import forms.SparePartForm;

@Controller
@RequestMapping("/sparePart/company")
public class SparePartCompanyController extends AbstractController {

	// Services
	@Autowired
	private InventoryService		inventoryService;

	@Autowired
	private SparePartService	sparePartService;

	@Autowired
	private CompanyService			companyService;

	// Constructors
	public SparePartCompanyController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final SparePartForm res = new SparePartForm();

		result = this.createEditModelAndView(res);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sparePartId) {
		ModelAndView result;
		final SparePart sp ;
		final Company principal = this.companyService.findByPrincipal();
		final Inventory c = this.inventoryService.findBySparePartId(sparePartId);
		try {
			sp = this.sparePartService.findOneToEdit(sparePartId);
			Assert.isTrue(c.getCompany().equals(principal));
			Assert.notNull(sp);
			final SparePartForm res = this.sparePartService.contruct(sp.getId());
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SparePartForm sparePartForm, final BindingResult binding) {
		ModelAndView result;
		final SparePart sp ;
		sp = this.sparePartService.reconstruct(sparePartForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(sparePartForm);
		else
			try {
				this.sparePartService.save(sp, sparePartForm.getInventory().getId());
				result = new ModelAndView("redirect:/inventory/company/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sparePartForm, "ed.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SparePartForm sparePartForm, final BindingResult binding) {
		ModelAndView result;
		try {
			this.sparePartService.delete(sparePartForm.getId());
			result = new ModelAndView("redirect:/inventory/company/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sparePartForm, "ed.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final SparePartForm sp ) {
		ModelAndView result;

		result = this.createEditModelAndView(sp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SparePartForm sp , final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Inventory inventory;
		Collection<Inventory> inventories;

		inventory = this.inventoryService.findBySparePartId(sp.getId());
		inventories = this.inventoryService.findByPrincipal();

		if (sp.getId() == 0)
			permission = true;
		else
			for (final SparePart s : inventory.getSpareParts())
				if (sp.getId() == s.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("sparePart/edit");
		result.addObject("sparePartForm", sp);
		result.addObject("permission", permission);
		result.addObject("inventory", inventory);
		result.addObject("inventories", inventories);
		result.addObject("message", messageCode);

		return result;

	}

}


package controllers.company;

import java.util.ArrayList;
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

import services.CompanyService;
import services.ConfigurationService;
import services.InventoryService;
import services.PrinterService;
import services.SpoolService;
import controllers.AbstractController;
import domain.Company;
import domain.Inventory;
import domain.Printer;
import domain.Spool;
import forms.InventoryForm;

@Controller
@RequestMapping("/inventory/company")
public class InventoryCompanyController extends AbstractController {

	@Autowired
	private InventoryService		inventoryService;

	@Autowired
	private PrinterService			printerService;

	@Autowired
	private SpoolService			spoolService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------
	public InventoryCompanyController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int inventoryId) {
		ModelAndView result;
		Inventory inventory;
		final Company principal;

		try {
			principal = this.companyService.findByPrincipal();
			inventory = this.inventoryService.findOne(inventoryId);
			Assert.isTrue(inventory.getCompany().equals(principal));
			Assert.notNull(inventory);
			result = new ModelAndView("inventory/display");
			result.addObject("inventory", inventory);
			result.addObject("principal", principal);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect: /list.do");
		}
		return result;

	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		InventoryForm inventoryForm;

		inventoryForm = new InventoryForm();

		result = this.createEditModelAndView(inventoryForm);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Inventory> inventories;

		try {
			final Company principal = this.companyService.findByPrincipal();
			inventories = this.inventoryService.findByPrincipal();
			result = new ModelAndView("inventory/list");
			result.addObject("principal", principal);
			result.addObject("inventories", inventories);
			result.addObject("requestURI", "inventory/company/list");
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final InventoryForm inventoryForm, final BindingResult binding) {
		ModelAndView result;
		if (inventoryForm.getDatePurchase() != null && inventoryForm.getWarrantyDate() != null)
			if (inventoryForm.getDatePurchase().after(inventoryForm.getWarrantyDate()))
				binding.rejectValue("warrantyDate", "printer.error.purchase.warranty");

		if (inventoryForm.getRemainingLength() != null && inventoryForm.getLength() != null)
			if (inventoryForm.getRemainingLength() > inventoryForm.getLength())
				binding.rejectValue("remainingLength", "inventory.error.lenght");

		if (binding.hasErrors())
			result = this.createEditModelAndView(inventoryForm);
		else
			try {
				final Printer printer = this.printerService.reconstruct(inventoryForm, binding);
				final Spool spool = this.spoolService.reconstruct(inventoryForm, binding);
				final Printer savedPrinter = this.printerService.save(printer);
				final Collection<Printer> printers = new ArrayList<Printer>();
				printers.add(savedPrinter);
				final Spool savedSpool = this.spoolService.save(spool);
				final Collection<Spool> spools = new ArrayList<Spool>();
				spools.add(savedSpool);
				final Inventory inventory = this.inventoryService.create();
				inventory.setPrinters(printers);
				inventory.setSpools(spools);

				this.inventoryService.save(inventory);

				result = new ModelAndView("redirect: /list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(inventoryForm, "inventory.commit.error");
			}

		return result;
	}
	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int inventoryId) {

		ModelAndView res;

		try {
			this.inventoryService.delete(inventoryId);
			res = new ModelAndView("redirect: /list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect: /list.do");
			res.addObject("message", "delete.error");
		}
		return res;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final InventoryForm inventoryForm) {
		ModelAndView result;

		result = this.createEditModelAndView(inventoryForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final InventoryForm inventoryForm, final String messageCode) {
		final ModelAndView result;
		Company principal;

		principal = this.companyService.findByPrincipal();

		final String[] materials = this.configurationService.findConfiguration().getMaterials().split(",");

		result = new ModelAndView("inventory/edit");
		result.addObject("inventoryForm", inventoryForm);
		result.addObject("principal", principal);
		result.addObject("materials", materials);
		result.addObject("message", messageCode);

		return result;

	}

}


package controllers.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.InventoryService;
import services.PrintSpoolerService;
import services.PrinterService;
import controllers.AbstractController;
import domain.Company;
import domain.Inventory;
import domain.PrintSpooler;
import domain.Printer;
import domain.Request;
import forms.PrinterForm;

@Controller
@RequestMapping("/printer/company")
public class PrinterCompanyController extends AbstractController {

	// Services
	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private PrinterService		printerService;

	@Autowired
	private PrintSpoolerService	printSpoolerService;

	@Autowired
	private CompanyService		companyService;


	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final PrinterForm res = new PrinterForm();

		result = this.createEditModelAndView(res);

		return result;

	}

	@RequestMapping(value = "/addSpooler", method = RequestMethod.GET)
	public ModelAndView addSpooler(@RequestParam final int printerId) {
		ModelAndView result;
		try {
			final Printer printer = this.printerService.findOne(printerId);

			final PrintSpooler ps = this.printSpoolerService.create(printer);
			final PrintSpooler printSpooler = this.printSpoolerService.save(ps);

			result = new ModelAndView("printer/display");
			result.addObject("printer", printer);

			if (printSpooler != null) {
				final List<Request> all = new ArrayList<Request>(printSpooler.getRequests());
				final Collection<Request> printed = new ArrayList<Request>();
				final Collection<Request> notPrinted = new ArrayList<Request>();

				for (int i = 0; i < all.size(); i++)
					if (all.get(i).getNumber() > 0)
						notPrinted.add(all.get(i));
					else
						printed.add(all.get(i));
				result.addObject("notPrinted", notPrinted);
				result.addObject("printed", printed);
				result.addObject("havePrintSpooler", true);

			} else
				result.addObject("havePrintSpooler", false);
			result.addObject("requestURI", "printer/company/display");
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int printerId) {
		ModelAndView result;

		try {
			final Printer printer = this.printerService.findOne(printerId);
			final PrintSpooler printSpooler = this.printSpoolerService.findByPrinterId(printerId);

			result = new ModelAndView("printer/display");
			result.addObject("printer", printer);

			if (printSpooler != null) {
				final List<Request> all = new ArrayList<Request>(printSpooler.getRequests());
				final Collection<Request> printed = new ArrayList<Request>();
				final Collection<Request> notPrinted = new ArrayList<Request>();

				for (int i = 0; i < all.size(); i++)
					if (all.get(i).getNumber() > 0)
						notPrinted.add(all.get(i));
					else
						printed.add(all.get(i));
				result.addObject("notPrinted", notPrinted);
				result.addObject("printed", printed);
				result.addObject("havePrintSpooler", true);

			} else
				result.addObject("havePrintSpooler", false);
			result.addObject("requestURI", "printer/company/display");
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int printerId) {
		ModelAndView result;
		final Printer printer;
		final Company principal = this.companyService.findByPrincipal();
		final Inventory c = this.inventoryService.findByPrinterId(printerId);
		try {
			printer = this.printerService.findOne(printerId);
			Assert.isTrue(c.getCompany().equals(principal));
			Assert.notNull(printer);
			final PrinterForm res = this.printerService.construct(printer);
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PrinterForm printerForm, final BindingResult binding) {
		ModelAndView result;
		final Printer sp;
		sp = this.printerService.reconstruct(printerForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(printerForm);
		else
			try {

				this.printerService.save(sp, printerForm.getInventory().getId());
				result = new ModelAndView("redirect:/inventory/company/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(printerForm, "printer.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PrinterForm printerForm, final BindingResult binding) {
		ModelAndView result;
		try {
			this.printerService.delete(this.printerService.findOne(printerForm.getId()));
			result = new ModelAndView("redirect:/inventory/company/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(printerForm, "delete.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final PrinterForm sp) {
		ModelAndView result;

		result = this.createEditModelAndView(sp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PrinterForm sp, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Inventory inventory;
		Collection<Inventory> inventories;

		inventory = this.inventoryService.findByPrinterId(sp.getId());
		inventories = this.inventoryService.findByPrincipal();

		if (sp.getId() == 0)
			permission = true;
		else
			for (final Printer s : inventory.getPrinters())
				if (sp.getId() == s.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("printer/edit");
		result.addObject("printerForm", sp);
		result.addObject("permission", permission);
		result.addObject("inventory", inventory);
		result.addObject("inventories", inventories);
		result.addObject("message", messageCode);

		return result;

	}

}

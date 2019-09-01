
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PrinterRepository;
import domain.Company;
import domain.Inventory;
import domain.Printer;
import forms.InventoryForm;
import forms.PrinterForm;

@Service
@Transactional
public class PrinterService {

	// Managed Repository
	@Autowired
	private PrinterRepository	printerRepository;

	// Supporting services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private Validator			validator;


	// Supporting services -----------------------------------------------------

	// Simple CRUD methods
	public Printer create() {
		Printer result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = new Printer();
		result.setTicker(this.inventoryService.generateTicker(principal.getCommercialName()));
		Assert.notNull(result);

		return result;
	}

	public Printer save(final Printer Printer) {
		Printer result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = this.printerRepository.save(Printer);
		Assert.notNull(result);
		return result;

	}

	public Printer findOne(final int id) {
		Printer result;
		result = this.printerRepository.findOne(id);
		return result;
	}

	public Collection<Printer> findByPrincipal() {
		Collection<Printer> result;
		Company principal;

		principal = this.companyService.findByPrincipal();

		result = this.printerRepository.findByCompanyId(principal.getId());
		return result;
	}

	public void flush() {
		this.printerRepository.flush();
	}

	public void delete(final Printer printer) {
		Company principal;

		Assert.notNull(printer);
		Assert.isTrue(printer.getId() != 0);

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		final Inventory c = this.inventoryService.findByPrinterId(printer.getId());
		Assert.notNull(c);

		final Collection<Printer> listprinter = c.getPrinters();
		this.printerRepository.delete(printer);
		listprinter.remove(printer);

		c.setPrinters(listprinter);

	}

	public Printer save(final Printer sp, final int id) {
		Printer result;
		Company principal;
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.inventoryService.findByPrincipal());
		result = this.printerRepository.save(sp);
		Assert.notNull(result);

		if (sp.getId() == 0) {
			final Collection<Printer> printers = new ArrayList<Printer>(this.inventoryService.findOne(id).getPrinters());
			printers.add(result);
			this.inventoryService.findOne(id).setPrinters(printers);
		}

		return result;
	}

	public PrinterForm construct(final Printer printer) {
		final PrinterForm res = new PrinterForm();

		res.setId(printer.getId());
		res.setVersion(printer.getVersion());

		res.setInventory(this.inventoryService.findByPrinterId(printer.getId()));
		res.setDatePurchase(printer.getDatePurchase());
		res.setDescription(printer.getDescription());
		res.setDimensionX(printer.getDimensionX());
		res.setDimensionY(printer.getDimensionY());
		res.setDimensionZ(printer.getDimensionZ());
		res.setExtruderDiameter(printer.getExtruderDiameter());
		res.setIsActive(printer.getIsActive());
		res.setMake(printer.getMake());
		res.setMaterials(printer.getMaterials());
		res.setModel(printer.getModel());
		res.setNozzle(printer.getNozzle());
		res.setPhoto(printer.getPhoto());
		res.setShoppingWebsite(printer.getShoppingWebsite());
		res.setWarrantyDate(printer.getWarrantyDate());

		return res;
	}

	public Printer reconstruct(final PrinterForm printerForm, final BindingResult binding) {
		final Printer res = new Printer();

		res.setId(printerForm.getId());
		res.setVersion(printerForm.getVersion());
		res.setDatePurchase(printerForm.getDatePurchase());
		res.setDescription(printerForm.getDescription());
		res.setDimensionX(printerForm.getDimensionX());
		res.setDimensionY(printerForm.getDimensionY());
		res.setDimensionZ(printerForm.getDimensionZ());
		res.setExtruderDiameter(printerForm.getExtruderDiameter());
		res.setIsActive(printerForm.getIsActive());
		res.setMake(printerForm.getMake());
		res.setMaterials(printerForm.getMaterials());
		res.setModel(printerForm.getModel());
		res.setNozzle(printerForm.getNozzle());
		res.setPhoto(printerForm.getPhoto());
		res.setShoppingWebsite(printerForm.getShoppingWebsite());
		res.setWarrantyDate(printerForm.getWarrantyDate());

		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		res.setTicker(this.inventoryService.generateTicker(principal.getCommercialName()));
		if (res.getDatePurchase() != null && res.getWarrantyDate() != null)
			if (res.getDatePurchase().after(res.getWarrantyDate()))
				binding.rejectValue("warrantyDate", "printer.error.purchase.warranty");
		this.validator.validate(printerForm, binding);

		return res;
	}

	// FIND ALL
	public Collection<Printer> findAll() {
		Collection<Printer> res;

		res = this.printerRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Collection<Printer> findActivePrinters() {
		Collection<Printer> res;

		res = this.printerRepository.findActivePrinters();

		return res;
	}

	public Printer reconstruct(final InventoryForm printerForm, final BindingResult binding) {
		final Printer res = new Printer();

		res.setId(printerForm.getId());
		res.setVersion(printerForm.getVersion());

		res.setDatePurchase(printerForm.getDatePurchase());
		res.setDescription(printerForm.getDescription());
		res.setDimensionX(printerForm.getDimensionX());
		res.setDimensionY(printerForm.getDimensionY());
		res.setDimensionZ(printerForm.getDimensionZ());
		res.setExtruderDiameter(printerForm.getExtruderDiameter());
		res.setIsActive(printerForm.getIsActive());
		res.setMake(printerForm.getMake());
		res.setMaterials(printerForm.getMaterials());
		res.setModel(printerForm.getModel());
		res.setNozzle(printerForm.getNozzle());
		res.setPhoto(printerForm.getPhoto());
		res.setShoppingWebsite(printerForm.getShoppingWebsite());
		res.setWarrantyDate(printerForm.getWarrantyDate());

		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		res.setTicker(this.inventoryService.generateTicker(principal.getCommercialName()));
		if (res.getDatePurchase() != null && res.getWarrantyDate() != null)
			if (res.getDatePurchase().after(res.getWarrantyDate()))
				binding.rejectValue("warrantyDate", "printer.error.purchase.warranty");

		this.validator.validate(printerForm, binding);

		return res;
	}

	public Collection<Printer> findActiveByCompanyId(final int companyId) {
		final Collection<Printer> res = this.printerRepository.findActiveByCompanyId(companyId);
		return res;
	}

}

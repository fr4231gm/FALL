package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InventoryRepository;
import domain.Company;
import domain.Inventory;
import domain.Printer;
import domain.SparePart;
import domain.Spool;

@Service
@Transactional
public class InventoryService {

	// Managed repository -----------
	@Autowired
	private InventoryRepository inventoryRepository;

	// Supporting services ----------
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PrinterService printerService;
	// Supporting services -----------------------------------------------------
	

	// Simple CRUDs methods ----------
	public Inventory create() {
		// Comprobamos que la persona logueada es una company
		Company principal;
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		// Nuevo Inventory
		final Inventory res = new Inventory();

		res.setCompany(principal);
		res.setTicker(this.generateTicker());
		Collection<Printer> a= new ArrayList<Printer>();
		Collection<Spool> b= new ArrayList<Spool>();
		Collection<SparePart> spareparts = new ArrayList<SparePart>();
		res.setPrinters(a);
		res.setSpools(b);
		res.setSpareParts(spareparts);
		return res;
	}
	
	public Collection<Inventory> findAll(){
		// Declaracion de variables
		Collection<Inventory> res;

		// Obtenemos el conjunto de Company
		res = this.inventoryRepository.findAll();
		return res;
	}
	

	
	// Simple CRUD methods -----------------------------------------------------
	
	public Boolean haveMaterialActivePrinterAndMaterialNameSpool(final Inventory inventory){
		Boolean res = false;
		Collection<Printer> activePrinters = this.printerService.findActivePrinters();
		Collection<Spool> spools = inventory.getSpools();
		
		for(Printer p: activePrinters){
			for(Spool s: spools){
				if(inventory.getPrinters().contains(p) && p.getMaterials().contains(s.getMaterialName())){
					res = true;
				}
			}	
		}
		return res;
	}



	private String generateTicker() {
		String res = "";

		final Date fecha = new Date();
		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final String formattedDate = dateFormat.format(fecha);

		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i < 6; i++) {
			final int randomNumber = (int) Math.floor(Math.random()
					* (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}

		// Adding formatted date to alphanumeric code
		res = formattedDate + "-INV-" + res;

		return res;
	}
	
	public String generateTicker(String name) {
		String res = "";

		// Create date instance
		final Date fecha = new Date();
		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final String formattedDate = dateFormat.format(fecha);

		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		final String trimmedName = name.substring(0, Math.min(name.length(), 3));

		for (int i = 0; i < 6; i++) {
			final int randomNumber = (int) Math.floor(Math.random()
					* (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}

		// Adding formatted date and trimmed name to alphanumeric code
		res = formattedDate + "-" + trimmedName + "-" + res;

		return res;
	}

	public Inventory save(final Inventory inventory) {
		// Comprobamos que la persona logueada es una company
		Company principal;
		Inventory res;
		principal = this.companyService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(inventory.getCompany().equals(principal));
		// Printer and spools are mandatory
		Assert.isTrue(!inventory.getPrinters().isEmpty());
		Assert.isTrue(!inventory.getSpools().isEmpty());

		if (inventory.getId() == 0) {
			res = this.inventoryRepository.save(inventory);

		} else {
			res = this.inventoryRepository.save(inventory);
		}

		return res;

	}

	public void delete(final Inventory inventory) {
		Company principal;
		Collection<Inventory> inventories;

		principal = this.companyService.findByPrincipal();
		inventories = this.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(inventory);
		Assert.isTrue(inventory.getId() != 0);
		Assert.isTrue(inventories.contains(inventory));

		this.inventoryRepository.delete(inventory);
	}
	

	public Inventory findOne(final int inventoryId) {
		Company principal = this.companyService.findByPrincipal();
		Inventory res;
		res = this.inventoryRepository.findOne(inventoryId);
		Assert.isTrue(res.getCompany() == principal);
		return res;
	}

	public Collection<Inventory> findByPrincipal() {
		Company b = this.companyService.findByPrincipal();
		Collection<Inventory> res = new ArrayList<Inventory>(this.inventoryRepository.findInventoriesByCompanyId(b.getId()));
		return res;
	}

	public Inventory findByPrinterId(int id) {
		Inventory inventory = this.inventoryRepository.findByPrinterId(id);
		return inventory;
	}

	public Inventory findBySpoolId(int id) {
		Inventory inventory = this.inventoryRepository.findBySpoolId(id);
		return inventory;
	}

	public Inventory findBySparePartId(int id) {
		Inventory inventory = this.inventoryRepository.findBySparePartId(id);
		return inventory;
	}

	public Collection<Inventory> findByCompanyId(int id) {
		return this.inventoryRepository.findInventoriesByCompanyId(id);
	}

	public void flush() {
		this.inventoryRepository.flush();
	}

	public void delete(int inventoryId) {
		Company principal;
		Collection<Inventory> inventories;
		Inventory inventory = this.findOne(inventoryId);

		principal = this.companyService.findByPrincipal();
		inventories = this.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(inventory);
		Assert.isTrue(inventoryId != 0);
		Assert.isTrue(inventories.contains(inventory));

		this.inventoryRepository.delete(inventoryId);
	}
	
	public void saveAnonymous(Inventory inventory){
		Assert.notNull(inventory);
		this.inventoryRepository.save(inventory);
	}
}


package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SparePartRepository;
import domain.Company;
import domain.Inventory;
import domain.SparePart;
import forms.SparePartForm;

@Service
@Transactional
public class SparePartService {

	// Managed Repository
	@Autowired
	private SparePartRepository	sparePartRepository;

	// Supporting services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods
	public SparePart create() {
		SparePart result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = new SparePart();
		Assert.notNull(result);

		return result;
	}

	public SparePart save(final SparePart sparePart, final int inventoryId) {
		SparePart result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.inventoryService.findByPrincipal());

		result = this.sparePartRepository.save(sparePart);
		Assert.notNull(result);

		if (sparePart.getId() == 0) {
			final Collection<SparePart> spareParts = new ArrayList<SparePart>(this.inventoryService.findOne(inventoryId).getSpareParts());
			spareParts.add(result);
			this.inventoryService.findOne(inventoryId).setSpareParts(spareParts);
		}

		return result;

	}

	public void delete(final SparePart sparePart) {
		Company principal;

		Assert.notNull(sparePart);
		Assert.isTrue(sparePart.getId() != 0);

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		final Inventory c = this.inventoryService.findBySparePartId(sparePart.getId());
		Assert.notNull(c);

		final Collection<SparePart> listsparePart = c.getSpareParts();
		this.sparePartRepository.delete(sparePart);
		listsparePart.remove(sparePart);

		c.setSpareParts(listsparePart);

	}

	public SparePart findOne(final int id) {
		SparePart result;
		result = this.sparePartRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.sparePartRepository.flush();
	}

	public SparePart findOneToEdit(final int sparePartId) {
		SparePart result;
		result = this.sparePartRepository.findOne(sparePartId);
		final Inventory c = this.inventoryService.findBySparePartId(sparePartId);
		Assert.notNull(c);
		Assert.isTrue(c.getSpareParts().contains(result));
		return result;
	}

	public SparePartForm contruct(final int id) {
		final SparePartForm res = new SparePartForm();
		final SparePart aux = this.findOne(id);
		res.setPhoto(aux.getPhoto());
		res.setName(aux.getName());
		res.setDescription(aux.getDescription());
		res.setPurchaseDate(aux.getPurchaseDate());
		res.setPurchasePrice(aux.getPurchasePrice());
		res.setId(id);
		res.setVersion(aux.getVersion());
		res.setInventory(this.inventoryService.findBySparePartId(id));
		return res;
	}

	public SparePart reconstruct(final SparePartForm aux, final BindingResult binding) {
		final SparePart res = new SparePart();
		res.setPhoto(aux.getPhoto());
		res.setName(aux.getName());
		res.setDescription(aux.getDescription());
		res.setPurchaseDate(aux.getPurchaseDate());
		res.setPurchasePrice(aux.getPurchasePrice());
		res.setId(aux.getId());
		res.setTicker(this.inventoryService.generateTicker(aux.getName()));
		res.setVersion(aux.getVersion());
		this.validator.validate(aux, binding);
		return res;
	}

	public void delete(final int id) {
		Company principal;
		final SparePart sparePart = this.findOne(id);

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(sparePart);

		final Inventory c = this.inventoryService.findBySparePartId(sparePart.getId());
		Assert.notNull(c);

		final Collection<SparePart> listsparePart = c.getSpareParts();
		this.sparePartRepository.delete(sparePart);
		listsparePart.remove(sparePart);
		c.setSpareParts(listsparePart);
	}

}


package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SpoolRepository;
import domain.Company;
import domain.Inventory;
import domain.Spool;
import forms.InventoryForm;
import forms.SpoolForm;

@Service
@Transactional
public class SpoolService {

	// Managed Repository
	@Autowired
	private SpoolRepository		spoolRepository;

	// Supporting services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods
	public Spool create() {
		Spool result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = new Spool();
		result.setTicker("yymmdd-AAA-xxxxxx");
		Assert.notNull(result);

		return result;
	}

	public Spool save(final Spool spool) {
		Spool result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = this.spoolRepository.save(spool);
		Assert.notNull(result);

		return result;

	}

	public Spool findOne(final int id) {
		Spool result;
		result = this.spoolRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.spoolRepository.flush();
	}

	public void delete(final Spool spool) {
		Company principal;

		Assert.notNull(spool);
		Assert.isTrue(spool.getId() != 0);

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		final Inventory c = this.inventoryService.findBySpoolId(spool.getId());
		Assert.notNull(c);

		final Collection<Spool> listspool = c.getSpools();
		this.spoolRepository.delete(spool);
		listspool.remove(spool);

		c.setSpools(listspool);

	}

	public SpoolForm construct(final Spool spool) {
		final SpoolForm res = new SpoolForm();

		res.setId(spool.getId());
		res.setVersion(spool.getVersion());

		res.setColor(spool.getColor());
		res.setDiameter(spool.getDiameter());
		res.setInventory(this.inventoryService.findBySpoolId(spool.getId()));
		res.setLength(spool.getLength());
		res.setMaterialName(spool.getMaterialName());
		res.setMeltingTemperature(spool.getMeltingTemperature());
		res.setRemainingLength(spool.getRemainingLength());
		res.setWeight(spool.getWeight());

		return res;
	}

	public Spool reconstruct(final SpoolForm spoolForm, final BindingResult binding) {
		final Spool res = new Spool();

		res.setId(spoolForm.getId());
		res.setVersion(spoolForm.getVersion());

		res.setColor(spoolForm.getColor());
		res.setDiameter(spoolForm.getDiameter());
		res.setLength(spoolForm.getLength());
		res.setMaterialName(spoolForm.getMaterialName());
		res.setMeltingTemperature(spoolForm.getMeltingTemperature());
		res.setRemainingLength(spoolForm.getRemainingLength());
		res.setWeight(spoolForm.getWeight());

		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		res.setTicker(this.inventoryService.generateTicker(principal.getCommercialName()));
		if (res.getRemainingLength() != null && res.getLength() != null)
			if (res.getRemainingLength() > res.getLength())
				binding.rejectValue("remainingLength", "inventory.error.lenght");

		this.validator.validate(spoolForm, binding);

		return res;
	}

	public Spool save(final Spool sp, final int id) {
		Spool result;
		Company principal;
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.inventoryService.findByPrincipal());
		result = this.spoolRepository.save(sp);
		Assert.notNull(result);

		if (sp.getId() == 0) {
			final Collection<Spool> spools = new ArrayList<Spool>(this.inventoryService.findOne(id).getSpools());
			spools.add(result);
			this.inventoryService.findOne(id).setSpools(spools);
		}

		return result;
	}

	public Spool reconstruct(final InventoryForm spoolForm, final BindingResult binding) {
		final Spool res = new Spool();

		res.setId(spoolForm.getId());
		res.setVersion(spoolForm.getVersion());

		res.setColor(spoolForm.getColor());
		res.setDiameter(spoolForm.getDiameter());
		res.setLength(spoolForm.getLength());
		res.setMaterialName(spoolForm.getMaterialName());
		res.setMeltingTemperature(spoolForm.getMeltingTemperature());
		res.setRemainingLength(spoolForm.getRemainingLength());
		res.setWeight(spoolForm.getWeight());

		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		res.setTicker(this.inventoryService.generateTicker(principal.getCommercialName()));
		if (res.getRemainingLength() != null && res.getLength() != null)
			if (res.getRemainingLength() > res.getLength())
				binding.rejectValue("remainingLength", "inventory.error.lenght");

		this.validator.validate(spoolForm, binding);

		return res;
	}

}

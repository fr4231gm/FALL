
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Actor;
import domain.Administrator;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed repository

	@Autowired
	private WarrantyRepository	warrantyRepository;

	@Autowired
	private ActorService		actorService;


	// Constructor
	public WarrantyService() {
		super();
	}

	// CRUD methods

	public Warranty create() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		// Creating new entity
		final Warranty res = new Warranty();

		// It is first created as a draft
		res.setIsDraft(true);

		return res;
	}

	public Warranty save(final Warranty warranty) {
		// Checking parameter
		Assert.notNull(warranty);
		// A warranty can only be updated if it is a draft
		//Assert.isTrue(warranty.getIsDraft());

		// Checking permissions
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		return this.warrantyRepository.save(warranty);
	}

	public void delete(final Warranty warranty) {
		// Checking parameters
		Assert.notNull(warranty);

		// Checking whether it is a draft or not
		Assert.isTrue(warranty.getIsDraft());

		// Checking permissions
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		this.warrantyRepository.delete(warranty);
	}

	// Retrievers

	public Collection<Warranty> findAllWarranties() {
		// Creating set and adding the result values
		final Collection<Warranty> allWarranties = new HashSet<Warranty>();
		allWarranties.addAll(this.warrantyRepository.findAll());

		return allWarranties;
	}

	public Warranty findWarrantyById(final Integer id) {
		// Checking parameters
		Assert.notNull(id);

		// Retrieving the warranty
		final Warranty res = this.warrantyRepository.findOne(id);

		return res;
	}

	public Collection<Warranty> findAll() {
		return this.warrantyRepository.findAll();
	}

	public Collection<Warranty> findAllFinals() {
		return this.warrantyRepository.findFinals();
	}
}

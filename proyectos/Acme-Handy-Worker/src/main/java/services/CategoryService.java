package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	// Managed repository

	@Autowired
	private CategoryRepository categoryRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private FinderService finderService;

	// Constructor
	public CategoryService() {
		super();
	}

	// CRUD methods

	public Category create() {
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		// Creating new entity
		final Category res = new Category();

		return res;
	}

	public Category save(final Category category) {
		// Checking parameter
		Assert.notNull(category);
		// All categories but the root must have a parent category
		Assert.isTrue(category.getParentCategory() != null);
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		if (this.actorService.checkSpam(category.getName())) {
			principal.setIsSuspicious(true);
			this.administratorService.save((Administrator) principal);
		}

		return this.categoryRepository.save(category);
	}

	public void deleteaux(final Category category, final Category parent) {
		// Checking permissions
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		System.out.println("estoy aqui" + category);
		// Checking parameters
		Assert.notNull(category);
		List<Category> categories = new ArrayList<Category>( this.findChilds(category.getId()));
		List<FixUpTask> fixUptTasks = new ArrayList<FixUpTask>(
				this.fixUpTaskService.findByCategoryId(category.getId()));
		List<Finder> finders = new ArrayList<Finder>(this.finderService.findByCategory(category.getId()));
		for (int j = 0; j < fixUptTasks.size() ; j++) {
			System.out.println("piupiu");
			FixUpTask f = fixUptTasks.get(j);
			System.out.println("piupiu" + f);
			System.out.println("piupiu" + f.getCategory());
			f.setCategory(parent);
			System.out.println("piupiu" + f.getCategory());
			FixUpTask f2 = this.fixUpTaskService.save(f);
			System.out.println("piupiu" + f2.getCategory());
			this.fixUpTaskService.flush();
			this.categoryRepository.flush();
		}
		for (int k = 0; k < finders.size() ; k++) {
			Finder f = finders.get(k);
			f.setCategory(null);
			Finder f2 = this.finderService.saveFinder(f);
			System.out.println("piupiu" + f2.getCategory());
		}
		for (int i = 0; i < categories.size(); i++) {
			System.out.println("memuero" + this.findCategoryById(categories.get(i).getId()));
			this.deleteaux(this.findCategoryById(categories.get(i).getId()), parent);
			this.categoryRepository.flush();
		}
	}

	public void delete(final Category category) {
		Category parent = category.getParentCategory();
		System.out.println("hola"+ parent);
		this.deleteaux(category, parent);
		
		this.categoryRepository.delete(this.findCategoryById(category.getId()));
	}

	// Retrievers
	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Collection<Category> findChilds(int id) {
		return this.categoryRepository.findChilds(id);
	}

	public Category findCategoryById(final Integer id) {
		// Retrieving the category
		final Category cat = this.categoryRepository.findOne(id);

		return cat;
	}

}

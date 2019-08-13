package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Conference;
import forms.CategoryForm;

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
	private ConferenceService conferenceService;


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
		Assert.notNull(category.getParentCategory());
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		return this.categoryRepository.save(category);
	}

	public void deleteaux(final Category category, final Category parent) {
		// Checking permissions
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		// Checking parameters
		Assert.notNull(category);
		Assert.notNull(category.getParentCategory());
		List<Category> childs = new ArrayList<Category>(this.findChilds(category.getId()));
		List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findByCategoryId(category.getId()));
		
		
		for (int j = 0; j < conferences.size(); j++) {
			Conference f = conferences.get(j);
			f.setCategory(parent);
			this.conferenceService.update(f);
			this.conferenceService.flush();
			this.categoryRepository.flush();
		}

		for (int i = 0; i < childs.size(); i++) {
			this.deleteaux(childs.get(i), parent);
			this.categoryRepository.flush();
		}
		this.categoryRepository.delete(category);
	}

	public void delete(final Category category) {
		Category parent = category.getParentCategory();
		this.deleteaux(category, parent);

		
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

	public CategoryForm construct(Category category) {
		CategoryForm categoryForm = new CategoryForm();
		categoryForm.setId(category.getId());
		categoryForm.setVersion(category.getVersion());
		categoryForm.setNameEn(category.getName().get("en"));
		categoryForm.setNameEs(category.getName().get("es"));
		categoryForm.setParentCategory(category.getParentCategory());
		return categoryForm;
	}

	public Category reconstruct(final CategoryForm categoryForm) {
		final Map<String, String> map = new HashMap<String, String>();
		final Category res ;
		
		if (categoryForm.getId() != 0){
			res = this.findCategoryById(categoryForm.getId());
		} else {
			res = new Category();
			res.setId(categoryForm.getId());
			res.setVersion(categoryForm.getVersion());
		}
		res.setParentCategory(categoryForm.getParentCategory());
		map.put("es", categoryForm.getNameEs());
		map.put("en", categoryForm.getNameEn());
		
		res.setName(map);
		
		return res;

	}

}

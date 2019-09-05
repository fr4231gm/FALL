
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Conference;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private CategoryService	categoryService;
	
	@Autowired
	private ConferenceService	conferenceService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveCategory() {
		final Category category, saved, parent;
		Collection<Category> categories;
		super.authenticate("administrator1");
		categories = this.categoryService.findAll();
		parent = categories.iterator().next();
		category = this.categoryService.create();
		Map<String, String> name = new HashMap<String, String>();
		name.put("en", "testing Category");
		name.put("es", "prueba de categorias");
		category.setName(name);
		category.setParentCategory(parent);
		saved = this.categoryService.save(category);
		categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));
	}
	
	@Test
	public void testDeleteCategory() {
		final Category category, saved, parent, child, saved2;
		final Conference f;
		f = this.conferenceService.findAll().iterator().next();
		Collection<Category> categories;
		super.authenticate("administrator1");
		categories = this.categoryService.findAll();
		parent = categories.iterator().next();
		category = this.categoryService.create();
		Map<String, String> name = new HashMap<String, String>();
		name.put("en", "testing Category");
		name.put("es", "prueba de categorias");
		category.setName(name);
		category.setParentCategory(parent);
		saved = this.categoryService.save(category);
		child = this.categoryService.create();
		Map<String, String> childName = new HashMap<String, String>();
		name.put("en", "testing Child Category");
		name.put("es", "probando la categoria hija");
		child.setName(childName);
		child.setParentCategory(saved);
		saved2 = this.categoryService.save(child);
		categories = this.categoryService.findAll();
		f.setCategory(saved2);
		this.conferenceService.save(f);
		Assert.isTrue(categories.contains(saved2));
		Assert.isTrue(categories.contains(saved));
		this.categoryService.delete(saved);
		categories = this.categoryService.findAll();
		Assert.isTrue(!((categories.contains(saved))&&(categories.contains(saved2))));

	}

}

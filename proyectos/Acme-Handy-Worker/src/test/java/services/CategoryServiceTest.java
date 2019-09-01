
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private CategoryService	categoryService;
	
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveCategory() {
		final Category category, saved, parent;
		Collection<Category> categories;
		super.authenticate("admin");
		categories = this.categoryService.findAll();
		parent = categories.iterator().next();
		category = this.categoryService.create();
		//... Initialise the Category...
		category.setName("testing Category");
		category.setParentCategory(parent);
		saved = this.categoryService.save(category);
		categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));
	}
	
	@Test
	public void testDeleteCategory() {
		final Category category, saved, parent, child, saved2;
		final FixUpTask f;
		f = this.fixUpTaskService.findAll().iterator().next();
		Collection<Category> categories;
		super.authenticate("admin");
		categories = this.categoryService.findAll();
		parent = categories.iterator().next();
		category = this.categoryService.create();
		category.setName("testing Category");
		category.setParentCategory(parent);
		saved = this.categoryService.save(category);
		child = this.categoryService.create();
		child.setName("testing child category");
		child.setParentCategory(saved);
		saved2 = this.categoryService.save(child);
		categories = this.categoryService.findAll();
		f.setCategory(saved2);
		this.fixUpTaskService.saveFixUpTask(f);
		Assert.isTrue(categories.contains(saved2));
		Assert.isTrue(categories.contains(saved));
		this.categoryService.delete(saved);
		categories = this.categoryService.findAll();
		Assert.isTrue(!((categories.contains(saved))&&(categories.contains(saved2))));

	}

}

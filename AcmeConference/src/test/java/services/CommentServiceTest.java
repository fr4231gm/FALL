package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest extends AbstractTest {

	@Autowired
	private CommentService commentService;

	@Test
	public void createCommentTemplate() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "Title", "Author", "Text", null },

				// TEST NEGATIVO: Título en blanco
				{ "", "Author", "Text", ConstraintViolationException.class }

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createCommentTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
	}

	protected void createCommentTemplate(String title, String author,
			String text, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Comment toSave;

		try {

			Comment c = new Comment();
			c.setTitle(title);
			c.setAuthor(author);
			c.setText(text);

			toSave = this.commentService.save(c);

			this.commentService.flush();

			Assert.isTrue(toSave.getId() != 0);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

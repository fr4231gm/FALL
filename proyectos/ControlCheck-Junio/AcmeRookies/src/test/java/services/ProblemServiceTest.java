
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ProblemService	problemService;


	// Requirement: 9.2 An actor who is authenticated as a company must be able to:

	// 1. Manage their database of problems, which includes listing, showing, 
	//    creating, updating, and deleting them.

	//List of Problems
	// 1. Listar problems siendo company
	// 2. Listar problems siendo company
	// In the case of negative tests, the business rule that is intended to be broken:
	// 3. Listar problems sin actor.

	// Analysis of sentence coverage total: Covered 7.7% 16/209 total instructions
	// Analysis of sentence coverage listProblemByCompanyId(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void listProblemByCompanyIdTestDriver() {

		final Object testingData[][] = {

			// 1.TEST POSITIVO:
			{
				"company1", null
			},
			// 2.TEST POSITIVO:
			{
				"company2", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listProblemByCompanyIdTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//CreateEditProblem
	// 1. Crear un problem siendo una company
	// In the case of negative tests, the business rule that is intended to be broken:
	// 2. Crear un problem siendo un administrador.

	// Analysis of sentence coverage total: Covered 22% 46/209 total instructions
	// Analysis of sentence coverage create(): Covered 100% 19/19 total instructions
	// Analysis of sentence coverage save(): Covered 55.5% 20/36 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of data coverage: 50%
	@Test
	public void createEditProblemTestDrive() {

		final Object testingData[][] = {
			//1. TEST POSITIVO:
			{
				"company1", "SQL", "statement", "hint", "attachments", true, null
			},

			//2. TEST NEGATIVO:
			{
				"admin1", "HTTP 500 error", "no messages found", "messages", "attachments", true, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createEditProblemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (boolean) testingData[i][5], (Class<?>) testingData[i][6]);
			this.rollbackTransaction();
		}
	}
	//DisplayProblem
	// 1. Mostrar un problem 
	// 2. Mostrar un problem 
	// 3. Mostrar un problem 
	// In the case of negative tests, the business rule that is intended to be broken:
	// 4. Pasamos un null como ID.
	// Analysis of sentence coverage total: Covered 8.6% 18/209 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void displayProblemTestDrive() {

		final Object testingData[][] = {
			//1. TEST POSITIVO:
			{
				"problem1", null
			},
			//2. TEST POSITIVO:
			{
				"problem10", null
			},
			//3. TEST POSITIVO:
			{
				"problem20", null
			},

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayProblemTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}
	//DeleteProblem
	// 1. Eliminar un problema de una company
	// 2. Eliminar un problema de una company
	// In the case of negative tests, the business rule that is intended to be broken:
	// 3. Eliminar el problema de otra company.
	// 4. Eliminar el problema de otra company.
	// 5. Eliminar el problema de otra company

	// Analysis of sentence coverage total: Covered 22% 41/209 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage delete(): Covered 100% 23/33 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void deleteProblemTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO:
			{
				"company1", "problem51", null
			},
			// 2. TEST POSITIVO:	
			{
				"company1", "problem52", null
			},
			// 3. TESTS NEGATIVOS:
			{
				"company2", "problem1", IllegalArgumentException.class
			},
			// 4. TESTS NEGATIVOS:	
			{
				"company2", "problem51", IllegalArgumentException.class
			},
			// 5. TESTS NEGATIVOS:
			{
				"company2", "problem52", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteProblemTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	// Templates---------------------------------------------------------------------------------
	private void listProblemByCompanyIdTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final int aux = this.getEntityId(username);

			this.problemService.findProblemsByCompanyId(aux);
			this.problemService.flush();
			//Hacemos el desautenticado.
			this.unauthenticate();
		} catch (final Throwable Oops) {
			caught = Oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private void createEditProblemTemplate(final String username, final String title, final String statement, final String hint, final String attachments, final boolean isDraft, final Class<?> expected) {

		Class<?> caught = null;
		try {
			super.authenticate(username);
			final Problem problem = this.problemService.create();

			problem.setTitle(title);
			problem.setStatement(statement);
			problem.setHint(hint);
			problem.setAttachments(attachments);
			problem.setIsDraft(isDraft);

			this.problemService.save(problem);
			this.problemService.flush();
			this.unauthenticate();

		} catch (final Throwable Oops) {
			caught = Oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private void displayProblemTemplate(final String problem, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.problemService.findOne(this.getEntityId(problem));
			this.problemService.flush();
		} catch (final Throwable Oops) {
			caught = Oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	private void deleteProblemTemplate(final String username, final String problem, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			this.problemService.delete(this.problemService.findOne(this.getEntityId(problem)));
			this.problemService.flush();
			this.unauthenticate();
		} catch (final Throwable Oops) {
			caught = Oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}

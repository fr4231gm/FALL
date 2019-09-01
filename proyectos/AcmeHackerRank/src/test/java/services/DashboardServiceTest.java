package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService administratorService;

	// Requirement
	// C-RF-11.2. Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*
	 * Display a dashboard with the following information: The average, the
	 * minimum, the maximum, and the standard deviation of the number of
	 * positions per company. The average, the minimum, the maximum, and the
	 * standard deviation of the number of applications per hacker. The
	 * companies that have offered more positions. The hackers who have made
	 * more applications. The average, the minimum, the maximum, and the
	 * standard deviation of the salaries offered. The best and the worst
	 * position in terms of salary
	 */
	// B-RF-18.1. Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*
	 * Display a dashboard with the following information: The minimum, the
	 * maximum, the average, and the standard deviation of the number of
	 * curricula per hacker. The minimum, the maximum, the average, and the
	 * standard deviation of the number of results in the finders. The ratio of
	 * empty versus non-empty finders
	 */

	//In the case of negative tests, the business rule that is intended to be broken:
	/* Intentar mostrar el dashboard a una company
	 * Intentar mostrar el dashboard a un hacker
	 */
	
	// Analysis of sentence coverage total: Covered 32.7% 230/704 total instructions
	
	// Analysis of sentence coverage findTop5ApplyHackers(): Covered 57.8% 26/45 total instructions
	// Analysis of sentence coverage findTop5PublishableCompanies(): Covered 57.8% 26/45 total instructions
	// Analysis of sentence coverage findBestPositionBySalary(): Covered 86% 37/43 total instructions
	// Analysis of sentence coverage findWorstPositionBySalary(): Covered 86% 37/43 total instructions
	// Analysis of sentence coverage findAdministratorByUserAccountId(): Covered 93.3% 14/15 total instructions
	
	// Analysis of sentence coverage checkPrincipal(): Covered 100% 6/6 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevApplicationsPerHacker(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevPositionsPerCompany(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevSalaryOffered(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
	
	// Analysis of sentence coverage findMinMaxAvgSddevCurriculaPerHacker(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findMinMaxAvgStdevFindersResults(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findRatioEmptyVsNotEmptyFinders(): Covered 100% 8/8 total instructions
	
		
		// Analysis of data coverage: 100%
	
	// Driver
	@Test
	public void dashboardDriver(){
		final Object testingData[][]={
				// TEST POSITIVO:
				{
					"admin", null
				}, // Muestra el dashboard para un administrador

				// TEST NEGATIVO:
				{
					"company1", IllegalArgumentException.class
				}, // Intenta mostrar el dashboard a una company

				// TEST NEGATIVO:
				{
					"hacker1", IllegalArgumentException.class
				}, // Intenta mostrar el dashboard a un hacker

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.dashboardTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}
	
	//Template
	protected void dashboardTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			//Acme-Hacker-Rank 
			
			//C1: The average, the minimum, the maximum, and the standard deviation of
			// the number of positions per company.
			this.administratorService.findAvgMinMaxStddevPositionsPerCompany();
			
			// C2: The average, the minimum, the maximum, and the standard deviation of
			// the number of applications per hacker.
			this.administratorService.findAvgMinMaxStddevApplicationsPerHacker();
			
			// C3: The companies that have offered more positions.
			this.administratorService.findTop5PublishableCompanies();
			
			// C4: The hackers who have made more applications.
			this.administratorService.findTop5ApplyHackers();
			
			// C5: The average, the minimum, the maximum, and the standard deviation of
			// the salaries offered.
			this.administratorService.findAvgMinMaxStddevSalaryOffered();
			
			// C6-1: The best and the worst position in terms of salary.
			// BEST
			this.administratorService.findBestPositionBySalary();
			
			// C6-2: The best and the worst position in terms of salary.
			// WORST
			this.administratorService.findWorstPositionBySalary();
			
			// B1: The minimum, the maximum, the average, and the standard deviation of
			// the number of curricula per hacker.
			this.administratorService.findMinMaxAvgStddevCurriculaPerHacker();
			
			// B2: The minimum, the maximum, the average, and the standard deviation of
			// the number of results in the finders.
			this.administratorService.findMinMaxAvgStdevFindersResults();
			
			// B3: The ratio of empty versus non-empty finders.
			this.administratorService.findRatioEmptyVsNotEmptyFinders();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
			
}

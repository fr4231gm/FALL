
package services;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	// Requirement
	// C-RF-11.2. Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*
	 * Display a dashboard with the following information: The average, the
	 * minimum, the maximum, and the standard deviation of the number of
	 * positions per company. The average, the minimum, the maximum, and the
	 * standard deviation of the number of applications per rookie. The
	 * companies that have offered more positions. The rookies who have made
	 * more applications. The average, the minimum, the maximum, and the
	 * standard deviation of the salaries offered. The best and the worst
	 * position in terms of salary
	 */
	// B-RF-18.1. Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*
	 * Display a dashboard with the following information: The minimum, the
	 * maximum, the average, and the standard deviation of the number of
	 * curricula per rookie. The minimum, the maximum, the average, and the
	 * standard deviation of the number of results in the finders. The ratio of
	 * empty versus non-empty finders
	 */
	// C-RF-4.4 Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*The average, the minimum, the maximum, and the standard deviation of the audit score of the positions stored in the system.
	  The average, the minimum, the maximum, and the standard deviation of the audit score of the companies that are registered in the system.
	  The companies with the highest audit score.
	  The average salary offered by the positions that have the highest average audit score.
	*/
	// B-RF-11.1 Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*The minimum, the maximum, the average, and the standard deviation of the number of items per provider.
	  The top-5 providers in terms of total number of items provided.
	*/
	// A-RF-14.1 Administrator -> Dashboard
	// An actor who is authenticated as an administrator must be able to:
	/*The average, the minimum, the maximum, and the standard deviation of the number of sponsorships per provider.
	  The average, the minimum, the maximum, and the standard deviation of the number of sponsorships per position.
	  The providers who have a number of sponsorships that is at least 10% above the average number of sponsorships per provider.
	*/
	
	
	//In the case of negative tests, the business rule that is intended to be broken:
	/*
	 * Intentar mostrar el dashboard a una company
	 * Intentar mostrar el dashboard a un rookie
	 * Intentar mostrar el dashboard a un provider
	 * Intentar mostrar el dashboard a un auditor
	 */

	// Analysis of sentence coverage total: Covered 40.3% 399/991 total instructions

	// AcmeHackerRank
	
	// Analysis of sentence coverage findTop5ApplyRookies(): Covered 57.8% 26/45 total instructions
	// Analysis of sentence coverage findTop5PublishableCompanies(): Covered 57.8% 26/45 total instructions
	// Analysis of sentence coverage findBestPositionBySalary(): Covered 86% 37/43 total instructions
	// Analysis of sentence coverage findWorstPositionBySalary(): Covered 86% 37/43 total instructions
	// Analysis of sentence coverage findAdministratorByUserAccountId(): Covered 93.3% 14/15 total instructions

	// Analysis of sentence coverage checkPrincipal(): Covered 100% 6/6 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevApplicationsPerRookie(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevPositionsPerCompany(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevSalaryOffered(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions

	// Analysis of sentence coverage findMinMaxAvgSddevCurriculaPerRookie(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findMinMaxAvgStdevFindersResults(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findRatioEmptyVsNotEmptyFinders(): Covered 100% 8/8 total instructions

	//AcmeRookies
	// Analysis of sentence coverage findAvgMinMaxStddevAuditsScore(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevCompanyScore(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findTop5Companies(): Covered 64.4% 29/45 total instructions
	// Analysis of sentence coverage findTopSalaryPositions(): Covered 64.4% 29/45 total instructions
	// Analysis of sentence coverage findMinMaxAvgStddevItemsPerProvider(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findTop5Providers(): Covered 86.7% 39/45 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevSponsorshipsPerProvider(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevSponsorshipsPerPosition(): Covered 100% 12/12 total instructions
	// Analysis of sentence coverage findActiveProviders(): Covered 100% 12/12 total instructions
	
	// Analysis of data coverage: 100%

	// Driver
	@Test
	public void dashboardDriver() {
		final Object testingData[][] = {
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
				"rookie1", IllegalArgumentException.class
			}, // Intenta mostrar el dashboard a un rookie

			// TEST NEGATIVO:
			{
				"provider1", IllegalArgumentException.class
			}, // Intenta mostrar el dashboard a un rookie
			
			// TEST NEGATIVO:
			{
				"auditor1", IllegalArgumentException.class
			},
			
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
			// the number of applications per rookie.
			this.administratorService.findAvgMinMaxStddevApplicationsPerRookie();

			// C3: The companies that have offered more positions.
			this.administratorService.findTop5PublishableCompanies();

			// C4: The rookies who have made more applications.
			this.administratorService.findTop5ApplyRookies();

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
			// the number of curricula per rookie.
			this.administratorService.findMinMaxAvgStddevCurriculaPerRookie();

			// B2: The minimum, the maximum, the average, and the standard deviation of
			// the number of results in the finders.
			this.administratorService.findMinMaxAvgStdevFindersResults();

			// B3: The ratio of empty versus non-empty finders.
			this.administratorService.findRatioEmptyVsNotEmptyFinders();

			//Acme-Rookie
			
			// C1: The average, the minimum, the maximum, and the standard deviation of the
			// audit score of the positions stored in the system.
			this.administratorService.findAvgMinMaxStddevAuditsScore();
			
			// C2: The average, the minimum, the maximum, and the standard deviation of 
			// the audit score of the companies that are registered in the system.
			this.administratorService.findAvgMinMaxStddevCompanyScore();
			
			// C3: The companies with the highest audit score
			// We considered a Top3 companies with the highest audit score
			this.administratorService.findTop5Companies();
			
			// C4: The average salary offered by the positions that have the highest 
			// average audit score.
			this.administratorService.findTopSalaryPositions();
			
			// B1: The minimum, the maximum, the average, and the standard 
			// deviation of the number of items per provider.
			this.administratorService.findMinMaxAvgStddevItemsPerProvider();
			
			// B2: The top-5 providers in terms of total number of items provided
			this.administratorService.findTop5Providers();
			
			// A1: The average, the minimum, the maximum, and the standard deviation of 
			// the number of sponsorships per provider.
			// Note: We consider active sponsorships
			this.administratorService.findAvgMinMaxStddevSponsorshipsPerProvider();
			
			// A2: The average, the minimum, the maximum, and the standard deviation of 
			// the number of sponsorships per position.
			// Note: We consider active sponsorships
			this.administratorService.findAvgMinMaxStddevSponsorshipsPerPosition();
			
			// A3: The providers who have a number of sponsorships that is at least 10% 
			// above the average number of sponsorships per provider.
			// Note: We consider active sponsorships
			this.administratorService.findActiveProviders();
			
			
			
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

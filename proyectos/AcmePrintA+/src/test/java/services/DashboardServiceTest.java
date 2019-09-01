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

	
	// Requiremet: 24.8. Display a dashboard with the following information: 
	// The average, the minimum, the maximum, and the standard deviation of the number of orders per customer.
	// The average, the minimum, the maximum, and the standard deviation of the number of applications per order.
	// The average, the minimum, the maximum, and the standard deviation of the price offered in the applications.
	// The ratio of pending applications.
	// The ratio of accepted applications.
	// The ratio of rejected applications.
	// The listing of customers who have published at least 10% more orders than the average, ordered by number of applications.
	// The listing of companies who have got accepted at least 10% more applications than the average, ordered by number of applications.
	// The minimum, the maximum, the average, and the standard deviation of the number of sponsorships per post.
	// The minimum, the maximum, the average, and the standard deviation of the number of printers per company.
	// The ratio of published orders whose pieces are in a printer spooler.
	// The ratio of post with and without sponsorships.
	// The top-five designers in terms of number of posts.
	// The top-five designers in terms of score.
	// The top-five designers in terms of sponsorships received.
	// The top-five companies in terms of number of printers.
	// The top-five provider in terms of sponsorships.
	// - The customers that place 10% more orders than the average. 
	// - The top-five companies in terms of print spoolers inventory.
	 
	// In the case of negative tests, the business rule that is intended to be
	// broken:
	/*
	 * Intentar mostrar el dashboard a una company Intentar mostrar el dashboard
	 * a un customer Intentar mostrar el dashboard a un provider Intentar
	 * mostrar el dashboard a un designer
	 */

	// Analysis of sentence coverage total: Covered 44.9% 485/1079 total
	// instructions

	
	// Analysis of sentence coverage findAvgMinMaxStddevOrdersPerCustomer(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevApplicationsPerOrder(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevOfferedPriceOfApplications(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage findRatioApplicationsByStatusPending(): Covered 100.0% 8/8 total instructions
	// Analysis of sentence coverage findRatioApplicationsByStatusAccepted(): Covered 100.0% 8/8 total instructions
	// Analysis of sentence coverage findRatioApplicationsByStatusRejected(): Covered 100.0% 8/8 total instructions
	// Analysis of sentence coverage findActiveCustomers(): Covered 64.4% 29/45 total instructions
	// Analysis of sentence coverage findActiveCompanies(): Covered 86.7% 39/45 total instructions
	// Analysis of sentence coverage findAvgMinMaxStddevSponsorshipsPerPost(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage findMinMaxAvgStddevPrintersPerCompany(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage findRatioOnPrinterSpoolerVsPublished(): Covered 85.2% 23/27 total instructions
	// Analysis of sentence coverage findRatioPostWithAndWithoutSponsorships(): Covered 100.0% 8/8 total instructions
	// Analysis of sentence coverage findTop5DesignersByPostsPublished(): Covered 86.7% 29/45 total instructions
	// Analysis of sentence coverage findTop5DesignersByScore(): Covered 64.4% 29/45 total instructions
	// Analysis of sentence coverage findTop5DesignersBySponsorshipsReceived(): Covered 86.7% 29/45 total instructions
	// Analysis of sentence coverage findCompaniesWithMorePrinters(): Covered 57.8% 26/45 total instructions
	// Analysis of sentence coverage findTop5Providers(): Covered 86.7% 29/45 total instructions
	// Analysis of sentence coverage findTop5Customers(): Covered 64.4% 29/45 total instructions
	// Analysis of sentence coverage findCompaniesWithMoreSpools(): Covered 57.8% 26/45 total instructions
	// Analysis of sentence coverage findTop5ActiveCompanies(): Covered 86.7% 39/45 total instructions
	
	// Analysis of data coverage: 100%

	// Driver
	@Test
	public void dashboardDriver() {
		final Object testingData[][] = {
				// TEST POSITIVO: Muestra el dashboard para un administrador
				{ "admin", null },

				// TEST NEGATIVO: Intenta mostrar el dashboard a una company
				{ "company1", IllegalArgumentException.class },

				// TEST NEGATIVO: Intenta mostrar el dashboard a un customer
				{ "customer1", IllegalArgumentException.class },

				// TEST NEGATIVO: Intenta mostrar el dashboard a un provider
				{ "provider1", IllegalArgumentException.class },

				// TEST NEGATIVO: Intenta mostrar el dashboard a un designer
				{ "designer", IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.dashboardTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	// Template
	protected void dashboardTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);

			this.administratorService.findAvgMinMaxStddevOrdersPerCustomer();

			this.administratorService.findAvgMinMaxStddevApplicationsPerOrder();

			this.administratorService
					.findAvgMinMaxStddevOfferedPriceOfApplications();

			this.administratorService.findRatioApplicationsByStatusPending();

			this.administratorService.findRatioApplicationsByStatusAccepted();

			this.administratorService.findRatioApplicationsByStatusRejected();

			this.administratorService.findActiveCustomers();

			this.administratorService.findActiveCompanies();

			this.administratorService.findAvgMinMaxStddevSponsorshipsPerPost();

			this.administratorService.findMinMaxAvgStddevPrintersPerCompany();

			this.administratorService.findRatioOnPrinterSpoolerVsPublished();

			this.administratorService.findRatioPostWithAndWithoutSponsorships();

			this.administratorService.findTop5DesignersByPostsPublished();

			this.administratorService.findTop5DesignersByScore();

			this.administratorService.findTop5DesignersBySponsorshipsReceived();

			this.administratorService.findCompaniesWithMorePrinters();

			this.administratorService.findTop5Providers();

			this.administratorService.findTop5Customers();

			this.administratorService.findCompaniesWithMoreSpools();

			this.administratorService.findTop5ActiveCompanies();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

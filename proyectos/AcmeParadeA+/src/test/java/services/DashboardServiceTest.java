
package services;

import java.util.Date;

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
	
	//Requierement: 4. 8. 18. An actor who is authenticated as an administrator must be able to:
	//1. Display a dashboard with the following information:
	//The average, the minimum, the maximum, and the standard deviation of the number of records per history.
	//The brotherhood with the largest history.
	//The brotherhoods whose history is larger than the average.
	//The ratio of areas that are not co-ordinated by any chapters.
	//The average, the minimum, the maximum, and the standard deviation of the number of parades co-ordinated by the chapters.
	//The chapters that co-ordinate at least 10% more parades than the average.
	//The ratio of parades in draft mode versus parades in final mode.
	//The ratio of parades in final mode grouped by status.
	//The ratio of active sponsorships.
	//The average, the minimum, the maximum, and the standard deviation of ac-tive sponsorships per sponsor.
	//The top-5 sponsors in terms of number of active sponsorships

	@Autowired
	private ActorService			actorService;


	//In the case of negative tests, the business rule that is intended to be broken:
	//Intentar mostrar el dashboard a un cabildo
	//Intentar mostrar el dashboard a un miembro
	//Intentar mostrar el dashboard a un patrocinador
	//Intentar mostrar el dashboard a una hermandad
	
	//Analysis of sentence coverage: Covered 100%
								
	//Analysis of data coverage: Covered 91 /91 total instructions

	@Test
	public void dashboardDriver() {

		/** Aqui crea las colecciones **/

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"admin", null
			}, // Muestra el dashboard para un administrador

			// TEST NEGATIVO:
			{
				"chapter1", null
			}, // Intenta mostrar el dashboard a un cabildo

			// TEST NEGATIVO:
			{
				"member1", null
			}, // Intenta mostrar el dashboard a un miembro

			// TEST NEGATIVO:
			{
				"sponsor1", null
			}, // Intenta mostrar el dashboard a un patrocinador

			// TEST NEGATIVO:
			{
				"brotherhood1", null
			}
		// Intenta mostrar el dashboard a una hermandad

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.dashboardTemplate((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (int) testingData[1][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
	}

	protected void dashboardTemplate(final String username, final Date ini, final Date end, final int actorId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			/**
			 * TODO: Para cada linea --> Assert.isTrue(lineaActual == parametroX)
			 * Tienes que a�adir los parametros en este metodo y pasar los valores en el driver
			 */
			this.administratorService.findMemberPerBrotherhoodStats();
			this.administratorService.findLargestBrotherhoods();
			this.administratorService.findSmallestBrotherhoods();
			this.administratorService.findRatioRequestsByStatusApproved();
			this.administratorService.findRatioRequestsByStatusRejected();
			this.administratorService.findRatioRequestsByStatusPending();
			this.administratorService.findRatioRequestsByStatusApproved();
			this.administratorService.findRatioRequestsByStatusRejected();
			this.administratorService.findRatioRequestsByStatusPending();
			this.administratorService.findMembersApproved();
			this.administratorService.findFollowingParades(ini, end);
			this.administratorService.findPositionsHistogram();
			this.administratorService.findRatioBrotherhoodsPerArea();
			this.administratorService.findCountBrotherhoodsPerArea();
			this.administratorService.findMinBrotherhoodsPerArea();
			this.administratorService.findMaxBrotherhoodsPerArea();
			this.administratorService.findAvgBrotherhoodsPerArea();
			this.administratorService.findStdevBrotherhoodsPerArea();
			this.administratorService.findRatioMinMaxAvgDesvBrotherhoodsPerArea();
			this.administratorService.findMinMaxAvgStdevFindersResults();
			this.administratorService.findRatioEmptyVsNotEmptyFinders();
			this.administratorService.findSpammersRatio();
			this.administratorService.findPolarityAverage();
			this.administratorService.getParadeAndRatiosPendingApprovedRejected();
			this.administratorService.polarity(this.actorService.findOne(actorId));
			this.administratorService.findAvgMinMaxStdevRecordsPerHistory();
			this.administratorService.findLargestBrotherhood();
			this.administratorService.findRatioAreasNotCoordinated();
			this.administratorService.findAvgMinMaxStdevParadesPerChapters();
			this.administratorService.findActiveChapters();
			this.administratorService.findRatioDraftVsFinalModeParades();
			this.administratorService.findRatioParadesByStatus();
			this.administratorService.findRatioActiveSponsorships();
			this.administratorService.findAvgMinMaxStdevSponsorshipsPerSponsor();
			this.administratorService.findTop5Sponsors();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

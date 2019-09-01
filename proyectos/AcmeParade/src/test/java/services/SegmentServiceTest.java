
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Coordinate;
import domain.Segment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SegmentServiceTest extends AbstractTest {

	@Autowired
	private SegmentService segmentService;
	
	//Requierement: RF-3.3 An actor who is authenticated as a brotherhood must be able to:
	//Manage the paths of their parades, which includes listing

	//In the case of negative tests, the business rule that is intended to be broken: No procede
					
	//Analysis of sentence coverage: Covered 100%
											
	//Analysis of data coverage: Covered 39 /39 total instructions

	@Test
	public void listSegmentsTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"brotherhood1", null
			}, // Listar los segmentos

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listSegmentsTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listSegmentsTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.segmentService.findAll();

			this.segmentService.flush();

			// Hacemos log out para la siguiente iteraci�n
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-3.3 An actor who is authenticated as a brotherhood must be able to:
	//Manage the paths of their parades, which includes listing

	//In the case of negative tests, the business rule that is intended to be broken: No procede
					
	//Analysis of sentence coverage: Covered 100%
												
	//Analysis of data coverage: 49 /49 total instructions
	@Test
	public void displaySegmentTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"brotherhood1", "segment1", null
			}
		// Mostrar un segmento
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displaySegmentTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displaySegmentTemplate(final String username, final String segmentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.segmentService.findOne(this.getEntityId(segmentId));

			this.segmentService.flush();

			// Hacemos log out para la siguiente iteraci�n
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-3.3 An actor who is authenticated as a brotherhood must be able to:
	//Manage the paths of their parades, which includes listing

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un segmento como administrador
						
	//Analysis of sentence coverage: Covered 100%
													
	//Analysis of data coverage: Covered 80 /80 total instructions
	
	@Test
	public void deleteSegmentTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"brotherhood1", "segment4", "parade2", null
			}, // Mostrar un segmento

			// TEST NEGATIVO:
			{
				"administrator1", "segment1", "parade1", IllegalArgumentException.class
			}
		//Intentar borrar un segmento como administrador

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteSegmentTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
	}

	protected void deleteSegmentTemplate(final String username, final String segmentId, final String paradeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.segmentService.delete(this.getEntityId(paradeId), this.getEntityId(segmentId));

			this.segmentService.flush();

			// Hacemos log out para la siguiente iteraci�n
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-3.3 An actor who is authenticated as a brotherhood must be able to:
	//Manage the paths of their parades, which includes listing

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intento crear un segmento sin ser un Brotherhood
							
	//Analysis of sentence coverage: Covered 100%
														
	//Analysis of data coverage: Covered 154 /154 total instructions
	
	@Test
	public void createEditSegmentTestDriver() {

		final Coordinate inicio;
		final Coordinate fin;
		final Date startTime;
		final Date endTime;

		startTime = new Date();
		startTime.setYear(2019);
		startTime.setMonth(2);
		startTime.setDate(25);

		endTime = new Date();
		endTime.setYear(2019);
		endTime.setMonth(2);
		endTime.setDate(25);

		inicio = new Coordinate();
		inicio.setLatitude(12.);
		inicio.setLongitude(13.);

		fin = new Coordinate();
		fin.setLatitude(15.);
		fin.setLongitude(16.);

		final Object testingData[][] = {

			//TEST POSITIVO:
			{
				"brotherhood1", inicio, fin, startTime, endTime, null
			}, //Intento crear un segmento correctamente

			//TEST NEGATIVO:
			{
				"chapter1", inicio, fin, startTime, endTime, IllegalArgumentException.class
			}
		//Intento crear un segmento sin ser un Brotherhood
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.createEditSegmentTemplate((String) testingData[i][0], (Coordinate) testingData[i][1], (Coordinate) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4], (Class<?>) testingData[i][5]);
			this.rollbackTransaction();
		}
	}

	protected void createEditSegmentTemplate(final String username, final Coordinate inicio, final Coordinate fin, final Date startTime, final Date endTime, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final Segment segment = this.segmentService.create();

			segment.setDestination(inicio);
			segment.setOrigin(fin);
			segment.setStartTime(startTime);
			segment.setEndTime(endTime);

			this.segmentService.save(segment);

			this.segmentService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}

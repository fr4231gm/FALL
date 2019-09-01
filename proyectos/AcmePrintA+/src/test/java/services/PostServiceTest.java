package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import utilities.AbstractTest;
import domain.Guide;
import domain.Post;
import forms.PostForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PostServiceTest extends AbstractTest {

	@Autowired
	private PostService postService;

	@Autowired
	private GuideService guideService;

	/***********************************************************************/
	// Requierement: 18.2 An actor who is not authenticated must be able to:
	// List the posts published and navigate to the profile of the corresponding
	// designer.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede

	// Analysis of sentence coverage total: Covered 4.1% 17/417 total
	// instructions
	// Analysis of sentence coverage findPostsFinalMode(): Covered 100.0% 10/10
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void listPostsTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ null } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listPostsTemplate((Class<?>) testingData[i][0]);
			this.rollbackTransaction();
		}
	}

	protected void listPostsTemplate(final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.postService.findPostsFinalMode();

			this.postService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/***********************************************************************/
	// Req. 19.1 Do the same as an actor who is not authenticated, but register
	// to the system.
	// Refers to -> Req. 18.2. An actor who is not authenticated must be able to:
	// List the posts published and navigate to the profile of the corresponding
	// designer.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede

	// Analysis of sentence coverage total: Covered 4.1% 17/418 total
	// instructions
	// Analysis of sentence coverage findPostsFinalMode(): Covered 100.0% 10/10
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void listPostsAuthenticatedTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "customer1", null },

				// TEST POSITIVO:
				{ "company1", null },

				// TEST POSITIVO:
				{ "provider1", null }

		// TEST NEGATIVO: NO PROCEDE

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listPostsAutheticatedTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listPostsAutheticatedTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.postService.findPostsFinalMode();
			this.postService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/***********************************************************************/
	// Req. 18.4.Search for a post using: a single key word that must be
	// contained in its title, its description, its type, its section's title,
	// its section's text, or the name of the corresponding designer.

	// Analysis of sentence coverage total: Covered 3.3% 14/418 total
	// instructions
	// Analysis of sentence coverage searchPostAnoymous(): Covered 100.0% 7/7
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void testSearchPostDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "pla", null },

				// TEST POSITIVO:
				{ "", null },

				// TEST POSITIVO:
				{ "http", null },

		// TEST NEGATIVO: NO PROCEDE

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.testSearchPostTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void testSearchPostTemplate(final String keyword,
			final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.postService.searchPostAnonymous(keyword);
			this.postService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/***********************************************************************/
	// Req. 19. An actor who is authenticated must be able to:
	// 1. Do the same as an actor who is not authenticated, but register to the
	// system.
	// Refers to -> Req. 18.4.Search for a post using: a single key word that
	// must be
	// contained in its title, its description, its type, its section's title,
	// its section's text, or the name of the corresponding designer.

	// Analysis of sentence coverage total: Covered 3.3% 14/418 total
	// instructions
	// Analysis of sentence coverage searchPostAnoymous(): Covered 100.0% 7/7
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void testSearchPostAuthenticatedDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "customer1", "pla", null },

				// TEST POSITIVO:
				{ "company1", "pla", null },

				// TEST POSITIVO:
				{ "designer1", "pla", null },

				// TEST POSITIVO:
				{ "provider1", "pla", null },

		// TEST NEGATIVO: NO PROCEDE

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.testSearchPostAuthenticatedTemplate(
					(String) testingData[i][0], (String) testingData[i][1],
					(Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void testSearchPostAuthenticatedTemplate(final String username,
			final String keyword, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			this.postService.searchPostAnonymous(keyword);
			this.postService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/***********************************************************************/
	// Req. 20. An actor who is authenticated as a designer must be able to:
	// 1. Manage his or her posts, which includes listing, showing, creating,
	// and updating them.

	// LIST

	// Analysis of sentence coverage total: Covered 6.5% 28/431 total
	// instructions
	// Analysis of sentence coverage findAllByDesigner(): Covered 95.5% 21/22
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void testListPostsDesignerTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "designer1", null },

				// TEST POSITIVO:
				{ "designer2", null },

				// TEST NEGATIVO:
				{ "customer1", IllegalArgumentException.class },

				// TEST NEGATIVO:
				{ null, NullPointerException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.testListPostsDesignerTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void testListPostsDesignerTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			this.postService.findAllByDesigner(this.getEntityId(username));
			this.postService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/***********************************************************************/
	// Req. 20. An actor who is authenticated as a designer must be able to:
	// 1. Manage his or her posts, which includes listing, showing, creating,
	// and updating them.

	// DISPLAY post logged and un-logged

	// Analysis of sentence coverage total: Covered 26.5% 114/431 total
	// instructions
	// Analysis of sentence coverage findOneToFail(): Covered 100.0% 9/9
	// total instructions
	// Analysis of sentence coverage construct(): Covered 100.0% 98/98 total
	// instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void testDisplayPostTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "designer1", "post1", null },

				// TEST POSITIVO:
				{ "designer1", "post2", null },

				// TEST NEGATIVO:
				{ "designer1", null, NullPointerException.class },

				// TEST NEGATIVO:
				{ "designer2", null, NullPointerException.class },

				// TEST NEGATIVO:
				{ "", null, IllegalArgumentException.class },

				// TEST NEGATIVO:
				{ null, null, NullPointerException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.testDisplayPostDesignerTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void testDisplayPostDesignerTemplate(final String username,
			final String postId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			Post post = this.postService
					.findOneToFail(this.getEntityId(postId));
			this.postService.construct(post);
			this.postService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/***********************************************************************/
	// Req. 20. An actor who is authenticated as a designer must be able to:
	// 1. Manage his or her posts, which includes listing, showing, creating,
	// and updating them.

	// CREATE post by designer

	//PostService:
	
	// Analysis of sentence coverage total: Covered 67.5% 291/431 total
	// instructions
	// Analysis of sentence coverage create(): Covered 100.0% 33/33
	// total instructions
	// Analysis of sentence coverage construct(): Covered 100.0% 98/98
	// total instructions
	// Analysis of sentence coverage reconstruct(): Covered 95.5% 105/110
	// total instructions
	// Analysis of sentence coverage save(): Covered 84.3% 43/51 total
	// instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	//GuideService:
	
	// Analysis of sentence coverage total: Covered 31.4% 27/86 total
	// instructions
	// Analysis of sentence coverage save(): Covered 76.9% 20/26
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4
	// total instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void testCreatePostDesignerTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{
						"designer1",
						"title",
						"description",
						2.5,
						true,
						"https://www.mercadona.com/estaticos/images/mercadona_logo/es/mercadona-imagotipo-color-300.png",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "advices", null },

				// TEST POSITIVO:
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"https://www.mercadona.com/estaticos/images/mercadona_logo/es/mercadona-imagotipo-color-300.png",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "advices", null },

				// TEST POSITIVO:
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "advices", null },

				// TEST POSITIVO:
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						0, 0, 0.0, 0, 0, "", null },

				// TEST POSITIVO:
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "", null },

				// TEST NEGATIVO: sin designer logueado
				{
						"",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "",
						IllegalArgumentException.class },

				// TEST NEGATIVO: stl no es url
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 2500, 2000, "", ConstraintViolationException.class },

				// TEST NEGATIVO: stl vacío
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 2500, 2000, "", ConstraintViolationException.class },

				// TEST NEGATIVO: score fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						6.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: score fuera de rango por abajo
				{
						"designer1",
						"title",
						"description",
						-1.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: extruder temp fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						401, 60, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: extruder temp fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						-1, 60, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: hotbed temp fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 201, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: hotbed temp fuera de rango por abajo
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, -1, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: layerHeight fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 3.0, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: layerHeight fuera de rango por abajo
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, -1.0, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: printSpeed fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.2, 10001, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: printSpeed fuera de rango por abajo
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.8, -1, 5000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: retractionSpeed fuera de rango por arriba
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.4, 5000, 10001, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: retractionSpeed fuera de rango por abajo
				{
						"designer1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.6, 5000, -1, "",
						ConstraintViolationException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.testCreatePostDesignerTemplate(
					(String) testingData[i][0],
					(String) testingData[i][1],
					(String) testingData[i][2],
					(Double) testingData[i][3],
					(Boolean) testingData[i][4],
					(String) testingData[i][5],
					(String) testingData[i][6],
					(String) testingData[i][7],
					(Integer) testingData[i][8], 
					(Integer) testingData[i][9],
					(Double) testingData[i][10], 
					(Integer) testingData[i][11],
					(Integer) testingData[i][12], 
					(String) testingData[i][13],
					(Class<?>) testingData[i][14]);
			this.rollbackTransaction();
		}
	}

	protected void testCreatePostDesignerTemplate(final String username,
			final String title, final String description, final Double score,
			final Boolean isDraft, final String pictures,
			final String category, final String stl, final Integer extruder,
			final Integer hotbed, final Double layer, final Integer print,
			final Integer retraction, final String advices,
			final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			PostForm postForm = this.postService.construct(this.postService
					.create());

			postForm.setTitle(title);
			postForm.setDescription(description);
			postForm.setScore(score);
			postForm.setIsDraft(isDraft);
			postForm.setPictures(pictures);
			postForm.setCategory(category);
			postForm.setStl(stl);

			postForm.setExtruderTemp(extruder);
			postForm.setHotbedTemp(hotbed);
			postForm.setLayerHeight(layer);
			postForm.setPrintSpeed(print);
			postForm.setRetractionSpeed(retraction);
			postForm.setAdvices(advices);

			BindingResult binding = null;
			Post post = this.postService.reconstruct(postForm, binding);
			Guide guide = this.guideService.save(post.getGuide());
			this.guideService.flush();
			post.setGuide(guide);
			this.postService.save(post);
			this.postService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/***********************************************************************/
	// Req. 20. An actor who is authenticated as a designer must be able to:
	// 1. Manage his or her posts, which includes listing, showing, creating,
	// and updating them.

	// EDIT post by designer

	//PostService:
	
	// Analysis of sentence coverage total: Covered 60.1% 259/431 total
	// instructions
	// Analysis of sentence coverage findOneToFail(): Covered 100.0% 9/9
	// total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11
	// total instructions
	// Analysis of sentence coverage construct(): Covered 100.0% 98/98
	// total instructions
	// Analysis of sentence coverage reconstruct(): Covered 100.0% 110/110
	// total instructions
	// Analysis of sentence coverage save(): Covered 47.1% 24/51 total
	// instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total
	// instructions

	//GuideService:
	
	// Analysis of sentence coverage total: Covered 30.2% 26/86 total
	// instructions
	// Analysis of sentence coverage save(): Covered 73.1% 19/26
	// total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4
	// total instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void testEditPostDesignerTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{
						"designer1",
						"post1",
						"title",
						"description",
						2.5,
						true,
						"https://www.mercadona.com/estaticos/images/mercadona_logo/es/mercadona-imagotipo-color-300.png",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "advices", null },

				// TEST POSITIVO:
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"https://www.mercadona.com/estaticos/images/mercadona_logo/es/mercadona-imagotipo-color-300.png",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "advices", null },

				// TEST POSITIVO:
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "advices", null },

				// TEST POSITIVO:
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						0, 0, 0.0, 0, 0, "", null },

				// TEST POSITIVO:
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "", null },

				// TEST NEGATIVO: sin designer logueado, salta excepción 
				{
						"",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "",
						IllegalArgumentException.class },
						
				// TEST NEGATIVO: sin post
				{
						"designer1",
						"",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "",
						NumberFormatException.class },

				// TEST NEGATIVO: stl no es url
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 2500, 2000, "", ConstraintViolationException.class },

				// TEST NEGATIVO: stl vacío
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 2500, 2000, "", ConstraintViolationException.class },

				// TEST NEGATIVO: score fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						6.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						400, 200, 2.0, 10000, 10000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: score fuera de rango por abajo
				{
						"designer1",
						"post1",
						"title",
						"description",
						-1.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						210, 60, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: extruder temp fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						401, 60, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: extruder temp fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						-1, 60, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: hotbed temp fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 201, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: hotbed temp fuera de rango por abajo
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, -1, 0.4, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: layerHeight fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 3.0, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: layerHeight fuera de rango por abajo
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, -1.0, 2500, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: printSpeed fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.2, 10001, 2000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: printSpeed fuera de rango por abajo
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.8, -1, 5000, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: retractionSpeed fuera de rango por arriba
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.4, 5000, 10001, "",
						ConstraintViolationException.class },

				// TEST NEGATIVO: retractionSpeed fuera de rango por abajo
				{
						"designer1",
						"post1",
						"title",
						"description",
						5.0,
						false,
						"",
						"TOOLS",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/SkullPawn.stl",
						220, 60, 0.6, 5000, -1, "",
						ConstraintViolationException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.testEditPostDesignerTemplate(
					(String) testingData[i][0],
					(String) testingData[i][1],//post
					(String) testingData[i][2],
					(String) testingData[i][3],
					(Double) testingData[i][4],
					(Boolean) testingData[i][5],
					(String) testingData[i][6],
					(String) testingData[i][7],
					(String) testingData[i][8],
					(Integer) testingData[i][9], 
					(Integer) testingData[i][10],
					(Double) testingData[i][11], 
					(Integer) testingData[i][12],
					(Integer) testingData[i][13], 
					(String) testingData[i][14],
					(Class<?>) testingData[i][15]);
			this.rollbackTransaction();
		}
	}

	protected void testEditPostDesignerTemplate(final String username, final String postId,
			final String title, final String description, final Double score,
			final Boolean isDraft, final String pictures,
			final String category, final String stl, final Integer extruder,
			final Integer hotbed, final Double layer, final Integer print,
			final Integer retraction, final String advices,
			final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			Post post = this.postService.findOneToFail(this.getEntityId(postId));
			PostForm postForm = this.postService.construct(post);

			postForm.setTitle(title);
			postForm.setDescription(description);
			postForm.setScore(score);
			postForm.setIsDraft(isDraft);
			postForm.setPictures(pictures);
			postForm.setCategory(category);
			postForm.setStl(stl);

			postForm.setExtruderTemp(extruder);
			postForm.setHotbedTemp(hotbed);
			postForm.setLayerHeight(layer);
			postForm.setPrintSpeed(print);
			postForm.setRetractionSpeed(retraction);
			postForm.setAdvices(advices);

			BindingResult binding = null;
			Post post2 = this.postService.reconstruct(postForm, binding);
			Guide guide = this.guideService.save(post2.getGuide());
			this.guideService.flush();
			post2.setGuide(guide);
			this.postService.save(post2);
			this.postService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
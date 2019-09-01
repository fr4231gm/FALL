package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.PostRepository;
import utilities.Tools;
import domain.Designer;
import domain.Guide;
import domain.Post;
import forms.PostForm;

@Service
@Transactional
public class PostService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private PostRepository postRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private DesignerService designerService;

	// @Autowired
	// private Validator validator;

	// Simple CRUD methods ----------------------------------------------------

	// CREATE
	public Post create() {

		Designer principal;
		final Guide guide = new Guide();

		principal = this.designerService.findByPrincipal();
		Assert.notNull(principal);

		// Create instance of the result
		final Post res = new Post();
		res.setMoment(this.createMoment());

		// Setting defaults
		res.setDesigner(principal);
		res.setGuide(guide);
		res.setTicker(Tools.generateTicker());
		res.setIsDraft(true); // when created, it's set to draft

		return res;
	}

	// SAVE
	public Post save(final Post post) {

		final Designer principal;

		principal = this.designerService.findByPrincipal();
		Assert.isTrue(post.getDesigner().getId() == principal.getId());

		Post res;

		// Check if it's an updating
		if (post.getId() == 0) {

			if (principal instanceof Designer)
				Assert.isTrue(post.getDesigner().getId() == principal.getId());

			// Checking that the ticker is not store in the database
			Assert.isNull(this.postRepository.findPostByTicker(post.getTicker()));

			// Calling the repository
			res = this.postRepository.save(post);

		} else
			// Calling the repository
			res = this.postRepository.save(post);

		// Return the result
		return res;
	}

	// DELETE
	public void delete(final Post post) {

		Designer principal;

		// Comprobamos que la persona logueda es un designer
		principal = this.designerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(post);

		Assert.isTrue(post.getIsDraft() == true);

		// Comprobamos que el post pertenece al designer.
		Assert.isTrue(post.getDesigner().getId() == principal.getId());

		this.postRepository.delete(post);
	}

	// FINDALL
	public Collection<Post> findAll() {
		// Create instance of the result
		Collection<Post> res = new ArrayList<Post>();

		// Call the repository
		res = this.postRepository.findAll();

		// Return the results in the collection
		return res;
	}

	// FINDONE
	public Post findOne(final int postId) {

		// Create instance of the result
		Post res;

		// Call the repository
		res = this.postRepository.findOne(postId);

		// Check the res is not null
		Assert.notNull(res);

		// Return the result
		return res;
	}

	// FINDONE TO FAIL
	public Post findOneToFail(final int postId) {

		// Create instance of the result
		Post res;

		// Call the repository
		res = this.postRepository.findOne(postId);

		// Return the result
		return res;
	}

	// Other methods ----------------------------------------------------------

	// Find Post by its Ticker
	public Post findPostByTicker(final String ticker) {
		Post res = null;
		res = this.postRepository.findPostByTicker(ticker);
		return res;
	}

	// Find Posts by DesignerId
	public Collection<Post> findPostsByDesignerId(final int designerId) {
		Collection<Post> res = new ArrayList<>();
		res = this.postRepository.findPostsByDesignerId(designerId);
		return res;
	}

	// Find Posts in final mode by DesignerId
	public Collection<Post> findPostsFinalModeByDesignerId(final int designerId) {
		Collection<Post> res = new ArrayList<>();
		res = this.postRepository.findPostsFinalModeByDesignerId(designerId);
		return res;
	}

	// Find Posts in final mode
	public Collection<Post> findPostsFinalMode() {
		Collection<Post> res = new ArrayList<>();
		res = this.postRepository.findPostsFinalMode();
		return res;
	}

	public Collection<Post> findAllByDesigner(int designerId) {
		Designer principal = this.designerService.findByPrincipal();
		Assert.isTrue(designerId == principal.getId());
		Assert.notNull(principal);
		final Collection<Post> posts = this.postRepository.findAllByDesigner(principal.getId());
		return posts;
	}

	// Construct: PostForm <-- Post
	public PostForm construct(final Post post) {
		final PostForm res = new PostForm();

		res.setId(post.getId());
		res.setVersion(post.getVersion());
		res.setTicker(post.getTicker());
		res.setMoment(post.getMoment());
		res.setDescription(post.getDescription());
		res.setTitle(post.getTitle());
		res.setScore(post.getScore());
		res.setIsDraft(post.getIsDraft());
		res.setPictures(post.getPictures());
		res.setCategory(post.getCategory());
		res.setStl(post.getStl());

		res.setDesigner(post.getDesigner().getId());

		res.setGuideId(post.getGuide().getId());
		res.setGuideVersion(post.getGuide().getVersion());
		res.setExtruderTemp(post.getGuide().getExtruderTemp());
		res.setHotbedTemp(post.getGuide().getHotbedTemp());
		res.setLayerHeight(post.getGuide().getLayerHeight());
		res.setPrintSpeed(post.getGuide().getPrintSpeed());
		res.setRetractionSpeed(post.getGuide().getRetractionSpeed());
		res.setAdvices(post.getGuide().getAdvices());

		return res;
	}

	// Reconstruct: Post <-- PostForm
	public Post reconstruct(final PostForm postForm, final BindingResult binding) {
		Post result;
		Post aux = new Post();
		final Guide guide = new Guide();

		if (postForm.getId() != 0)
			aux = this.findOne(postForm.getId());

		aux.setId(postForm.getId());
		aux.setVersion(postForm.getVersion());
		aux.setTicker(postForm.getTicker());
		aux.setMoment(postForm.getMoment());
		aux.setDescription(postForm.getDescription());
		aux.setTitle(postForm.getTitle());
		aux.setScore(postForm.getScore());
		aux.setIsDraft(postForm.getIsDraft());
		aux.setPictures(postForm.getPictures());
		aux.setCategory(postForm.getCategory());
		aux.setStl(Tools.fromGithubToRaw(postForm.getStl()));

		aux.setDesigner(this.designerService.findOne(postForm.getDesigner()));

		guide.setId(postForm.getGuideId());
		guide.setVersion(postForm.getGuideVersion());
		guide.setExtruderTemp(postForm.getExtruderTemp());
		guide.setHotbedTemp(postForm.getHotbedTemp());
		guide.setLayerHeight(postForm.getLayerHeight());
		guide.setPrintSpeed(postForm.getPrintSpeed());
		guide.setRetractionSpeed(postForm.getRetractionSpeed());
		guide.setAdvices(postForm.getAdvices());

		aux.setGuide(guide);

		// validator.validate(aux, binding);

		result = aux;

		return result;
	}

	protected Date createMoment() {
		Date res;
		res = Calendar.getInstance().getTime();
		return res;
	}

	public Collection<Post> searchPostAnonymous(final String keyword) {
		Collection<Post> res;

		res = this.postRepository.filter(keyword);
		return res;
	}

	public void flush() {

		this.postRepository.flush();
	}

	//MethodAnonymous
	public void saveAnonymous(final Post post){
		Assert.notNull(post);
		this.postRepository.save(post);
	}

}

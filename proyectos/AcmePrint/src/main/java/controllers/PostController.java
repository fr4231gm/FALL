
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.ConfigurationService;
import services.PostService;
import services.SponsorshipService;
import domain.Comment;
import domain.Post;
import domain.Sponsorship;
import forms.PostForm;

@Controller
@RequestMapping("/post")
public class PostController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private PostService				postService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private CommentService			commentService;


	// Constructor ------------------------------------------------------------

	public PostController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Post> posts;

		posts = this.postService.findPostsFinalMode();

		result = new ModelAndView("post/list");
		result.addObject("posts", posts);
		result.addObject("requestURI", "post/list.do");
		result.addObject("general", true);

		return result;
	}

	@RequestMapping(value = "/listSearch", method = RequestMethod.GET, params = {
		"keyword"
	})
	public ModelAndView listSearch(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Post> posts;

		posts = this.postService.searchPostAnonymous(keyword);

		result = new ModelAndView("post/list");

		result = new ModelAndView("post/list");
		result.addObject("general", true);
		result.addObject("posts", posts);
		result.addObject("requestURI", "post/listSearch.do");

		return result;
	}

	@RequestMapping(value = "/listposts", method = RequestMethod.GET, params = "designerId")
	public ModelAndView list(@RequestParam final int designerId) {
		ModelAndView result;
		Collection<Post> posts;

		posts = this.postService.findPostsFinalModeByDesignerId(designerId);

		result = new ModelAndView("post/list");
		result.addObject("posts", posts);
		result.addObject("requestURI", "post/list.do?designerId=" + designerId);

		return result;
	}

	// DISPLAY - GET
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int postFormId) {
		ModelAndView res;
		final Boolean readOnly = false;
		// String action = "post/designer/edit.do";
		// Designer principal = this.designerService.findByPrincipal();
		final Collection<Comment> comments;

		comments = this.commentService.findCommentsByPostId(postFormId);
		Post post = this.postService.findOneToFail(postFormId);
		if (postFormId != 0 && post == null)
			res = new ModelAndView("security/notfind");
		else if (post.getIsDraft() == true)
			res = new ModelAndView("security/hacking");
		else
			try {
				post = this.postService.findOne(postFormId);
				final Sponsorship sponsorship = this.sponsorshipService.selectRandomSponsorshipIfAny(post.getId());
				if (sponsorship == null) {
					Assert.notNull(post);
					final PostForm postForm = this.postService.construct(post);
					res = this.createEditModelAndView(postForm);
					res.addObject("readOnly", readOnly);
				} else {
					Assert.notNull(post);
					final PostForm postForm = this.postService.construct(post);
					res = this.createEditModelAndView(postForm);
					res.addObject("bannerSponsorship", sponsorship.getBanner());
					res.addObject("targetURLbanner", sponsorship.getTargetPage());
					res.addObject("readOnly", readOnly);

				}
				res.addObject("comments", comments);
			} catch (final Throwable oops) {
				res = new ModelAndView("security/hacking");

			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final PostForm postForm) {
		ModelAndView res;
		res = this.createEditModelAndView(postForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final PostForm postForm, final String messageCode) {
		ModelAndView res;
		final String[] categories = this.configurationService.findConfiguration().getCategories().split(",");

		res = new ModelAndView("post/display");
		res.addObject("postForm", postForm);
		res.addObject("message", messageCode);
		res.addObject("categories", categories);

		return res;
	}
}

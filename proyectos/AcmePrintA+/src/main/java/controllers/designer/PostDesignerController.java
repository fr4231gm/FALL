/*
 * DesignerController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.designer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.DesignerService;
import services.GuideService;
import services.PostService;
import controllers.AbstractController;
import domain.Designer;
import domain.Guide;
import domain.Post;
import forms.PostForm;

@Controller
@RequestMapping("/post/designer")
public class PostDesignerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private PostService postService;

	@Autowired
	private DesignerService designerService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private GuideService guideService;

	// Constructors -----------------------------------------------------------

	public PostDesignerController() {
		super();
	}

	// Methods ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Post> posts;
		Designer principal = this.designerService.findByPrincipal();
		posts = this.postService.findAllByDesigner(principal.getId());

		result = new ModelAndView("post/list");
		result.addObject("posts", posts);
		result.addObject("requestURI", "post/designer/list.do");
		result.addObject("principalId", principal.getId());
		return result;
	}

	// REGISTER - GET
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;
		Boolean readOnly = false;
		String action = "post/designer/register.do";
		// final Designer principal = this.designerService.findByPrincipal();
		final PostForm postForm = this.postService.construct(this.postService
				.create());
		try {
			res = this.createEditModelAndView(postForm);
			res.addObject("readOnly", readOnly);
			res.addObject("action", action);
		} catch (Throwable oops) {
			res = new ModelAndView();
		}
		return res;
	}

	// REGISTER - POST
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PostForm postForm,
			final BindingResult binding) {
		ModelAndView res;
		Boolean readOnly = false;
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(postForm);
			res.addObject("readOnly", readOnly);
		} else {
			Post post2 = this.postService.reconstruct(postForm, binding);
			Guide saved = this.guideService.save(post2.getGuide());
			this.guideService.flush();
			post2.setGuide(saved);
			this.postService.save(post2);
			res = new ModelAndView("redirect:/post/designer/list.do");
		}

		return res;
	}

	// EDIT - GET
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int postId) {
		ModelAndView res;
		Boolean readOnly = false;
		String action = "post/designer/edit.do";
		Designer principal = this.designerService.findByPrincipal();
		Post post = this.postService.findOneToFail(postId);
		if (postId != 0 && post == null) {
			res = new ModelAndView("security/notfind");
		} else if (post.getDesigner().getId() != principal.getId()) {
			res = new ModelAndView("security/hacking");
		} else {
			try {
				Assert.isTrue(this.postService.findOne(postId).getDesigner()
						.getId() == principal.getId());
				post = this.postService.findOne(postId);
				Assert.notNull(post);
				PostForm postForm = this.postService.construct(post);
				res = this.createEditModelAndView(postForm);
				res.addObject("readOnly", readOnly);
				res.addObject("action", action);
			} catch (Throwable oops) {
				res = new ModelAndView("security/hacking");

			}
		}
		return res;
	}

	// EDIT - POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveOnEdit(@Valid final PostForm postForm,
			final BindingResult binding) {
		ModelAndView res;
		Boolean readOnly = false;
		Designer principal = this.designerService.findByPrincipal();
		// Post post = this.postService.findOneToFail(postForm.getId());

		if (postForm == null) {
			res = new ModelAndView("security/notfind");
		} else if (postForm.getDesigner() != principal.getId()) {
			res = new ModelAndView("security/hacking");
		} else if (binding.hasErrors()) {
			res = this.createEditModelAndView(postForm);
			res.addObject("readOnly", readOnly);
		} else {
			Post post = this.postService.reconstruct(postForm, binding);
			Guide saved = this.guideService.save(post.getGuide());
			this.guideService.flush();
			post.setGuide(saved);
			this.postService.save(post);
			res = new ModelAndView("redirect:/post/designer/list.do");
		}

		return res;
	}

	

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final PostForm postForm) {
		ModelAndView res;
		res = this.createEditModelAndView(postForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final PostForm postForm,
			final String messageCode) {
		ModelAndView res;
		String[] categories = this.configurationService.findConfiguration()
				.getCategories().split(",");

		res = new ModelAndView("post/edit");
		res.addObject("postForm", postForm);
		res.addObject("message", messageCode);
		res.addObject("categories", categories);
		
		return res;
	}

}

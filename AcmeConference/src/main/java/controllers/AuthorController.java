/*
 * LegalTermsController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import domain.Author;
import forms.ActorForm;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

	@Autowired
	private AuthorService	authorService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		Author author;

		author = this.authorService.findByPrincipal();
		Assert.notNull(author);
		res = this.createEditModelAndView(author);

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"authorId"
	})
	public ModelAndView display(@RequestParam final int authorId) {
		ModelAndView result;

		// Initialize variables
		Author author;
		author = this.authorService.findOne(authorId);

		//

		result = new ModelAndView("author/display");
		result.addObject("author", author);
		result.addObject("requestURI", "author/display.do?authorId=" + authorId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Author author, final BindingResult binding) {
		ModelAndView res;

		try {
			if (!(author.getEmail().matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]")) && author.getEmail().length() > 0)
				binding.rejectValue("email", "actor.email.check");
			if (binding.hasErrors())
				res = this.createEditModelAndView(author);
			else {
				final Author saved = this.authorService.save(author);
				res = new ModelAndView("welcome/index");
				res.addObject("name", saved.getName());
				res.addObject("exitCode", "actor.edit.success");
				res.addObject("moment", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			}
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(author, "author.commit.error");

		}

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		ActorForm actorForm;

		actorForm = new ActorForm();
		actorForm.setCheckTerms(false);

		result = new ModelAndView("author/register");
		result.addObject("actorForm", actorForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		//Initialize Variables
		ModelAndView result;
		Author author;

		//Create the author object from the actorForm
		author = this.authorService.reconstruct(actorForm, binding);
		try {
			//If the form has errors prints it
			if (binding.hasErrors())
				result = new ModelAndView("author/register");
			else {
				//If the form does not have errors, try to save it
				this.authorService.save(author);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
				result.addObject("name", author.getName());
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("author/register");
			result.addObject("actorForm", actorForm);
			result.addObject("message", "actor.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Author author) {
		final ModelAndView result = this.createEditModelAndView(author, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Author author, final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("author/edit");

		result.addObject("author", author);
		result.addObject("message", messagecode);

		return result;
	}
}

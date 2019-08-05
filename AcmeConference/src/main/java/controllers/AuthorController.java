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
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import domain.Author;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

    @Autowired
    private AuthorService authorService;
    
    // Constructors -----------------------------------------------------------
    public AuthorController() {
        super();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView res;
        Author author;

        author = this.authorService.findByPrincipal();
        Assert.notNull(author);
        res = this.createEditModelAndView(author);
        
        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params="save")
    public ModelAndView save(@Valid Author author, BindingResult binding) {
        ModelAndView res;
        Author toSave;
        
        if(binding.hasErrors()){
            res = this.createEditModelAndView(author);
            
        } else {
            try {
                toSave = this.authorService.save(author);
                res = new ModelAndView("welcome/index");

                res.addObject("name", toSave.getName());
                res.addObject("exitCode", "actor.edit.success");
                res.addObject("moment", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            } catch (Throwable oops) {
                res = this.createEditModelAndView(author, "author.commit.error");
            }
        }

        return res;
    }
    
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		AuthorForm authorForm;

		areas = this.areaService.findAll();

		authorForm = new AuthorForm();
		authorForm.setCheckTerms(false);

		result = new ModelAndView("author/register");
		result.addObject("AuthorForm", authorForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(final AuthorForm AuthorForm, final BindingResult binding) {
		//Initialize Variables
		ModelAndView result;
		Author author;

		//Create the author object from the AuthorForm
		author = this.authorService.reconstruct(AuthorForm, binding);

		//If the form has errors prints it
		if (binding.hasErrors()) {
			result = new ModelAndView("author/register");
		} else
			//If the form does not have errors, try to save it
			try {
				this.authorService.save(author);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
				result.addObject("name", author.getName());
			} catch (final Throwable oops) {
				result = new ModelAndView("author/register");
				result.addObject("AuthorForm", AuthorForm);
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

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

import services.ReviewerService;
import domain.Reviewer;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController extends AbstractController {

    @Autowired
    private ReviewerService reviewerService;
    
    // Constructors -----------------------------------------------------------
    public ReviewerController() {
        super();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView res;
        Reviewer reviewer;

        reviewer = this.reviewerService.findByPrincipal();
        Assert.notNull(reviewer);
        res = this.createEditModelAndView(reviewer);
        
        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params="save")
    public ModelAndView save(@Valid Reviewer reviewer, BindingResult binding) {
        ModelAndView res;
        Reviewer toSave;
        
        if(binding.hasErrors()){
            res = this.createEditModelAndView(reviewer);            
        } else {
            try {
                toSave = this.reviewerService.save(reviewer);
                res = new ModelAndView("welcome/index");

                res.addObject("name", toSave.getName());
                res.addObject("exitCode", "actor.edit.success");
                res.addObject("moment", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            } catch (Throwable oops) {
                res = this.createEditModelAndView(reviewer, "reviewer.commit.error");
            }
        }

        return res;
    }

    protected ModelAndView createEditModelAndView(final Reviewer reviewer) {
        final ModelAndView result = this.createEditModelAndView(reviewer, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Reviewer reviewer, final String messagecode) {
        final ModelAndView result;

        result = new ModelAndView("reviewer/edit");

        result.addObject("reviewer", reviewer);
        result.addObject("message", messagecode);

        return result;
    }
}

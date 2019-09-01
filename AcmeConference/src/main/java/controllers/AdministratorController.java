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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

    @Autowired
    private AdministratorService administratorService;
    
    // Constructors -----------------------------------------------------------
    public AdministratorController() {
        super();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView res;
        Administrator administrator;

        administrator = this.administratorService.findByPrincipal();
        Assert.notNull(administrator);
        res = this.createEditModelAndView(administrator);
        
        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params="save")
    public ModelAndView save(@Valid Administrator administrator, BindingResult binding) {
        ModelAndView res;
        Administrator toSave;
        
        if(binding.hasErrors()){
            res = this.createEditModelAndView(administrator);
            for(ObjectError s: binding.getAllErrors()){
            	System.out.println(s);
            }
            
        } else {
            try {
                toSave = this.administratorService.save(administrator);
                res = new ModelAndView("welcome/index");

                res.addObject("name", toSave.getName());
                res.addObject("exitCode", "actor.edit.success");
                res.addObject("moment", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            } catch (Throwable oops) {
                res = this.createEditModelAndView(administrator, "administrator.commit.error");
            }
        }

        return res;
    }
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView res;

        try {
            Assert.notNull(this.administratorService.findByPrincipal());
            res = new ModelAndView("dashboard");
            
            res.addObject("SubmissionsPerConference", this.administratorService.SubmissionsPerConference());
            res.addObject("RegistrationsPerConference", this.administratorService.RegistrationsPerConference());
            res.addObject("ConferencesFeesStats", this.administratorService.ConferencesFeesStats());
            res.addObject("ConferencesDaysStats", this.administratorService.ConferencesDaysStats());
            res.addObject("ConferencesPerCategory", this.administratorService.ConferencesPerCategory());
            res.addObject("CommentsPerConference", this.administratorService.CommentsPerConference());
            res.addObject("CommentsPerActivity", this.administratorService.CommentsPerActivity());
            //Control Check
            res.addObject("QuoletsPerConferenceStats", this.administratorService.QuoletsPerConferenceStats());
            res.addObject("QuoletsPerAdministratorRatio", this.administratorService.QuoletsPerAdministratorRatio());
            res.addObject("DraftQuoletsRatio", this.administratorService.DraftQuoletsRatio());
            
        } catch (Throwable oops) {
        	res = new ModelAndView("welcome/index");
        }

        return res;
    }
    
    @RequestMapping(value = "/compute", method = RequestMethod.GET)
    public ModelAndView computeScore() {
        ModelAndView res;

        try {
            Assert.notNull(this.administratorService.findByPrincipal());
        	res = new ModelAndView("welcome/index");
            this.administratorService.computeScore();
            res.addObject("message", "compute.success");
            
        } catch (Throwable oops) {
        	res = new ModelAndView("welcome/index");
        }

        return res;
    }
    
    @RequestMapping(value = "/computebuzzwords", method = RequestMethod.GET)
    public ModelAndView computeBuzzWords() {
        ModelAndView res;

        try {
            Assert.notNull(this.administratorService.findByPrincipal());
        	res = new ModelAndView("welcome/index");
            String buzzWords = this.administratorService.computeBuzzWords();
            res.addObject("buzzwords", buzzWords);
        } catch (Throwable oops) {
        	res = new ModelAndView("welcome/index");
        }

        return res;
    }

    protected ModelAndView createEditModelAndView(final Administrator administrator) {
        final ModelAndView result = this.createEditModelAndView(administrator, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Administrator administrator, final String messagecode) {
        final ModelAndView result;

        result = new ModelAndView("administrator/edit");

        result.addObject("administrator", administrator);
        result.addObject("message", messagecode);

        return result;
    }
}

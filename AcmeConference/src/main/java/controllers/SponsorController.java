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

import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

    @Autowired
    private SponsorService sponsorService;
    
    // Constructors -----------------------------------------------------------
    public SponsorController() {
        super();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView res;
        Sponsor sponsor;

        sponsor = this.sponsorService.findByPrincipal();
        Assert.notNull(sponsor);
        res = this.createEditModelAndView(sponsor);
        
        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params="save")
    public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {
        ModelAndView res;
        Sponsor toSave;
        
        if(binding.hasErrors()){
            res = this.createEditModelAndView(sponsor);
            for(ObjectError s: binding.getAllErrors()){
            	System.out.println(s);
            }
            
        } else {
            try {
                //TODO: Falta creditCard
            	//toSave = this.sponsorService.save(sponsor);
                res = new ModelAndView("welcome/index");

                //res.addObject("name", toSave.getName());
                res.addObject("exitCode", "actor.edit.success");
                res.addObject("moment", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            } catch (Throwable oops) {
                res = this.createEditModelAndView(sponsor, "sponsor.commit.error");
            }
        }

        return res;
    }

    protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
        final ModelAndView result = this.createEditModelAndView(sponsor, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String messagecode) {
        final ModelAndView result;

        result = new ModelAndView("sponsor/edit");

        result.addObject("sponsor", sponsor);
        result.addObject("message", messagecode);

        return result;
    }
}

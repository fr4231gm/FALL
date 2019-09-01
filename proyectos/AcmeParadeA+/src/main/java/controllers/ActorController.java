/*
 * ActorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import services.ConfigurationService;
import forms.ActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

    // Singletons
    @Autowired
    private ConfigurationService configService;
    
    // Constructors -----------------------------------------------------------

    public ActorController() {
        super();
    }

    protected ModelAndView createEditModelAndView(ActorForm actor) {
        ModelAndView result = createEditModelAndView(actor, null);
        return result;
    }

    private ModelAndView createEditModelAndView(ActorForm actor, String messagecode) {
        ModelAndView result;
        result = new ModelAndView("actor/edit");
        result.addObject("actorForm", actor);
        result.addObject("message", messagecode);
        result.addObject("configuration", this.configService.findConfiguration());
        return result;
    }
}

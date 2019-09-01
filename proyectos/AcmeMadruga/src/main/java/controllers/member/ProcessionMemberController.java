/*
 * MemberController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Member;
import domain.Procession;
import services.MemberService;
import services.ProcessionService;

@Controller
@RequestMapping("/procession/member")
public class ProcessionMemberController extends AbstractController {

    // Singletons
    @Autowired
    private ProcessionService processionService;
    
    @Autowired
    private MemberService memberService;

    // Constructors -----------------------------------------------------------

    public ProcessionMemberController() {
        super();
    }

    // List the procession of a brotherhood
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView res;
        Collection<Procession> processions;
       	Member principal = memberService.findByPrincipal();

        processions = this.processionService.findProcessionRequestablesByMemberId(principal.getId());

        res = new ModelAndView("procession/list");
        res.addObject("processions", processions);
        res.addObject("requestURI", "procession/list.do");
		res.addObject("permiso", true);
        return res;
    }
   
}

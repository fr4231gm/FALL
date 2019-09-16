/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

@Controller
public class AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	@ModelAttribute
	public void getConfiguationObjects(final Model result) {
		final Configuration configuration = this.configurationService.findConfiguration();
		String ls;
		try{
			final Locale locale = LocaleContextHolder.getLocale();
			ls = locale.getLanguage();
		} catch(Throwable oops){
			ls = "en";
		}
		
		result.addAttribute("banner", configuration.getBanner());
		result.addAttribute("systemName", configuration.getsystemName());
		result.addAttribute("welcomeMessage", configuration.getWelcomeMessage());
		result.addAttribute("config", configuration);
		result.addAttribute("langcode", ls);

	}

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

}

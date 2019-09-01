/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import forms.Calculator;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/inspiringQuotes", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		final List<String> quotes = new ArrayList<String>();
		quotes.add("You know you’re in love when you can’t fall asleep because reality is finally better than your dreams. -Dr. Suess");
		quotes.add("Get busy living or get busy dying. -Stephen King");
		quotes.add("When I dare to be powerful – to use my strength in the service of my vision, then it becomes less and less important whether I am afraid. -Audre Lorde");
		quotes.add("Great minds discuss ideas; average minds discuss events; small minds discuss people. -Eleanor Roosevelt");
		quotes.add("When one door closes, another opens; but we often look so long and so regretfully upon the closed door that we do not see the one that has opened for us. -Alexander Graham Bell");
		quotes.add("En algún lugar muy dentro de mí hay un hombre decente, sólo que no se puede ver. -Eminem");
		quotes.add("El amor no necesita ser entendido, solo necesita ser demostrado. -Bob Marley");
		quotes.add("It had long since come to my attention that people of accomplishment rarely sat back and let things happen to them. They went out and happened to things. -Leonardo Da Vinci");
		quotes.add("Ante Dios y el mundo, el más fuerte tiene el derecho de hacer prevalecer su voluntad. -Adolf Hitler");
		quotes.add("Este es un país en el que hablamos inglés, no español. -Donald Trump");
		quotes.add("If you want to be happy, be. -Leo Tolstoy");
		quotes.add("No soy una mujer, así que no tengo dias malos. -Vladimir Putin");
		Collections.shuffle(quotes);
		final Collection<String> res = quotes.subList(0, 3);
		result = new ModelAndView("profile/inspiringQuotes");
		result.addObject("res", res);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	public ModelAndView action2Get() {
		ModelAndView result;
		Calculator calculator;
		calculator = new Calculator();
		result = new ModelAndView("profile/calculator");
		result.addObject("calculator", calculator);
		return result;
	}

	@RequestMapping(value = "/calculator", method = RequestMethod.POST)
	public ModelAndView action2Post(@Valid final Calculator calculator, final BindingResult binding) {
		ModelAndView result;
		calculator.compute();
		result = new ModelAndView("profile/calculator");
		result.addObject("calculator", calculator);
		result.addObject("x", calculator.getX());
		result.addObject("y", calculator.getY());
		result.addObject("operator", calculator.getOperator());
		return result;
	}

	// Action-3 ---------------------------------------------------------------		

	@RequestMapping("/panic")
	public ModelAndView action3() {
		throw new RuntimeException("Oops! An *expected* exception was thrown. This is normal behaviour.");
	}

}

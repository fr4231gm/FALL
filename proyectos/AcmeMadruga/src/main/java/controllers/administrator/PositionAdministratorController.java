/*
 * PositionAdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Position;
import forms.PositionForm;

@Controller
@RequestMapping("/position/administrator")
public class PositionAdministratorController extends AbstractController {

	// Singletons
	@Autowired
	private PositionService			positionService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConfigurationService	configService;


	// Constructors -----------------------------------------------------------

	public PositionAdministratorController() {
		super();
	}

	//List--------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	ModelAndView list(final Locale locale) {
		ModelAndView result;
		Administrator principal;
		Collection<Position> positions;
		String language;

		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);

		language = locale.getLanguage();

		positions = this.positionService.findAll();

		result = new ModelAndView("position/administrator/list");
		result.addObject("positions", positions);
		result.addObject("language", language);
		result.addObject("requestURI", "position/administrator/list.do");
		return result;
	}

	//Create-----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		Position position;
		position = this.positionService.create();

		result = this.createEditModelAndView(position);
		return result;

	}

	// Show ----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId, final Locale locale) {
		ModelAndView result;
		Position position;
		String language;

		position = this.positionService.findOne(positionId);
		result = new ModelAndView("position/administrator/show");

		language = locale.getLanguage();

		result.addObject("position", position);
		result.addObject("language", language);
		result.addObject("cancelURI", "position/administrator/list.do");

		return result;
	}
	// Edito ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;
		try {
			position = this.positionService.findOne(positionId);
			Assert.notNull(position);
			result = this.createEditModelAndView(position);
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;

	}

	// Edito ----------------------------------------------------------------
	@RequestMapping(value = "/editFail", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId, @RequestParam final String message) {
		ModelAndView result;
		Position position;
		if (positionId != 0 && this.positionService.findOneToFail(positionId) == null)
			result = new ModelAndView("security/notfind");
		else
			try {
				position = this.positionService.findOne(positionId);
				Assert.notNull(position);
				result = this.createEditModelAndView(position);
				result.addObject("message", message);
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		return result;

	}

	// Delete -----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET, params = "positionId")
	public ModelAndView delete(@RequestParam final int positionId) {
		ModelAndView result;
		final Position p = this.positionService.findOne(positionId);
		try {
			this.positionService.delete(p);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:editFail.do?positionId=" + positionId);
			result.addObject("message", "position.commit.error");
		}
		return result;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PositionForm positionForm, final BindingResult binding) {
		ModelAndView result;
		final Position position = this.positionService.reconstruct(positionForm, binding);
		if (positionForm.getId() != 0 && this.positionService.findOneToFail(positionForm.getId()) == null)
			result = new ModelAndView("security/notfind");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		return result;
	}

	//Save create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final PositionForm positionForm, final BindingResult binding) {
		ModelAndView result;
		final Position position = this.positionService.reconstruct(positionForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position) {
		final ModelAndView result = this.createEditModelAndView(position, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String messagecode) {
		ModelAndView result;
		final PositionForm positionForm = this.positionService.construct(position);
		result = new ModelAndView("position/administrator/edit");
		result.addObject("positionForm", positionForm);
		result.addObject("message", messagecode);
		result.addObject("configuration", this.configService.findConfiguration());
		return result;
	}
}

/*
 * LegalTermsController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.chapter;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChapterService;
import services.ProclaimService;
import controllers.AbstractController;
import domain.Chapter;
import domain.Proclaim;

@Controller
@RequestMapping("/proclaim/chapter")
public class ProclaimChapterController extends AbstractController {

	@Autowired
	private ProclaimService	proclaimService;

	@Autowired
	private ChapterService	chapterService;


	// Constructors -----------------------------------------------------------
	public ProclaimChapterController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final Proclaim proclaim = this.proclaimService.create();

		res = this.createEditModelAndView(proclaim);

		return res;
	}

	// List my proclaims

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Proclaim> proclaims;
		final Chapter principal = this.chapterService.findByPrincipal();

		proclaims = this.proclaimService.findProclaimsByChapterId(principal.getId());

		result = new ModelAndView("proclaim/list");
		result.addObject("proclaims", proclaims);
		result.addObject("requestURI", "/proclaim/chapter/list.do");
		result.addObject("permiso", true);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int proclaimId) {
		ModelAndView res;
		Proclaim proclaim;
		final Chapter principal = this.chapterService.findByPrincipal();
		boolean permiso = false;
		Assert.isTrue(this.proclaimService.findOne(proclaimId).getChapter().equals(principal));
		if (proclaimId != 0 && this.proclaimService.findOneToFail(proclaimId) == null)
			res = new ModelAndView("security/notfind");
		else
			try {
				if (this.proclaimService.findOne(proclaimId).getChapter() == principal)
					permiso = true;
				try {
					proclaim = this.proclaimService.findOne(proclaimId);
					Assert.notNull(proclaim);
					res = new ModelAndView("proclaim/edit");
					res.addObject("proclaim", proclaim);
					res.addObject("permiso", permiso);
				} catch (final Throwable oops) {
					res = new ModelAndView();
				}
			} catch (final Throwable oops) {
				res = new ModelAndView("security/hacking");
			}
		return res;
	}

	/* Guardar proclaim mia */

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Proclaim proclaim, final BindingResult binding) {
		// Initialize variables
		ModelAndView res;

		if (proclaim.getId() != 0 && this.proclaimService.findOneToFail(proclaim.getId()) == null)
			res = new ModelAndView("security/notfind");
		else if (binding.hasErrors())
			res = this.createEditModelAndView(proclaim);
		else
			try {
				this.proclaimService.save(proclaim);
				res = new ModelAndView("redirect:/proclaim/chapter/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(proclaim, "proclaim.commit.error");
			}
		return res;

	}

	// Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Proclaim proclaim, final BindingResult binding) {
		// Initialize variables
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(proclaim);
		else
			try {
				this.proclaimService.save(proclaim);
				res = new ModelAndView("redirect:/proclaim/chapter/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(proclaim, "proclaim.commit.error");
			}
		return res;

	}
	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int proclaimId) {

		ModelAndView res;
		final Proclaim proclaim = this.proclaimService.findOne(proclaimId);
		final Chapter principal = this.chapterService.findByPrincipal();
		Assert.isTrue(this.proclaimService.findOne(proclaimId).getChapter().equals(principal));
		
		try {
			this.proclaimService.delete(proclaim);
			res = new ModelAndView("redirect:/proclaim/chapter/list.do");

		} catch (final Throwable oops) {
			Collection<Proclaim> proclaims;
			proclaims = this.proclaimService.findProclaimsByChapterId(principal.getId());

			res = new ModelAndView("proclaim/list");
			res.addObject("proclaims", proclaims);
			res.addObject("permiso", true);
			res.addObject("requestURI", "/proclaim/chapter/list.do");

		}
		return res;
	}

	// Publish 

	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam final int proclaimId) {
		ModelAndView res;
		final Proclaim proclaim = this.proclaimService.findOne(proclaimId);
		if (proclaim.getId() != 0 && this.proclaimService.findOneToFail(proclaim.getId()) == null)
			res = new ModelAndView("security/notfind");
		else
			try {
				this.proclaimService.publish(proclaim);
				res = new ModelAndView("redirect:/proclaim/chapter/list.do");
				res.addObject("proclaim", proclaim);
			} catch (final Throwable oops) {
				res = new ModelAndView();
			}

		return res;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Proclaim proclaim) {
		ModelAndView res;
		res = this.createEditModelAndView(proclaim, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Proclaim proclaim, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("proclaim/edit");
		res.addObject("proclaim", proclaim);
		res.addObject("message", messageCode);

		return res;
	}
}

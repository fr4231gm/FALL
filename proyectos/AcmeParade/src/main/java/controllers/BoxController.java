
package controllers;

import java.util.ArrayList;
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

import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	// Services
	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configService;


	// Constructors
	public BoxController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ArrayList<Box> boxes;
		Box currentBox;
		ModelAndView result;
		try {
			boxes = (ArrayList<Box>) this.boxService.findRootBoxesByPrincipal();
			currentBox = this.boxService.findOne(boxes.get(0).getId());
			Assert.isTrue(currentBox.getActor().getId() == this.actorService.findByPrincipal().getId());
			result = new ModelAndView("box/list");
			result.addObject("boxes", boxes);
			result.addObject("currentBox", currentBox);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"boxId"
	})
	public ModelAndView list(@RequestParam final int boxId) {
		ModelAndView result;
		Box currentBox;
		final Collection<Box> boxes;
		final Collection<Box> childboxes;
		final Actor principal = this.actorService.findByPrincipal();
		try {
			Assert.isTrue(this.boxService.findOne(boxId).getActor() == principal);
			try {
				currentBox = this.boxService.findOne(boxId);
				boxes = this.boxService.findRootBoxesByPrincipal();
				childboxes = this.boxService.getchilds(boxId);
				Assert.isTrue(currentBox.getActor().getId() == this.actorService.findByPrincipal().getId());
				result = new ModelAndView("box/list");
				result.addObject("boxes", boxes);
				result.addObject("currentBox", currentBox);
				result.addObject("childboxes", childboxes);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = {
		"id"
	})
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;
		Box box;
		final Collection<Box> boxes;
		boxes = this.boxService.findBoxesByPrincipal();

		box = this.boxService.findOne(id);
		boxes.remove(box);
		Assert.notNull(box);

		result = this.createEditModelAndView(box);
		result.addObject("boxes", boxes);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			Box box2;
			result = this.createEditModelAndView(box);
			final Collection<Box> boxes;
			boxes = this.boxService.findBoxesByPrincipal();
			box2 = this.boxService.findOne(box.getId());
			boxes.remove(box2);
			result.addObject("boxes", boxes);
		} else
			try {

				this.boxService.save(box);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				final Collection<Box> boxes;
				boxes = this.boxService.findBoxesByPrincipal();
				result = this.createEditModelAndView(box, "box.commit.error");
				result.addObject("boxes", boxes);
			}
		return result;
	}

	// Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createBox() {
		ModelAndView result;
		final Collection<Box> boxes;
		Box box;
		boxes = this.boxService.findBoxesByPrincipal();
		box = this.boxService.create();
		result = this.createEditModelAndView(box);
		result.addObject("boxes", boxes);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			result = this.createEditModelAndView(box);
			final Collection<Box> boxes;
			boxes = this.boxService.findBoxesByPrincipal();
			result.addObject("boxes", boxes);
		} else
			try {

				box.setActor(this.actorService.findByPrincipal());
				this.boxService.save(box);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				final Collection<Box> boxes;
				boxes = this.boxService.findBoxesByPrincipal();
				result = this.createEditModelAndView(box, "box.commit.error");
				result.addObject("boxes", boxes);
			}
		return result;
	}

	// Deleting
	@RequestMapping(value = "/remove", method = RequestMethod.GET, params = {
		"id"
	})
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		final Collection<Box> boxes;
		try {
			this.boxService.delete(this.boxService.findOne(id));
			boxes = this.boxService.findBoxesByPrincipal();
			result = new ModelAndView("redirect:/box/list.do");
			result.addObject("boxes", boxes);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box) {
		final ModelAndView result = this.createEditModelAndView(box, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Box box, final String messagecode) {
		ModelAndView result;
		result = new ModelAndView("box/edit");
		result.addObject("box", box);
		result.addObject("message", messagecode);
		result.addObject("configuration", this.configService.findConfiguration());
		return result;
	}
}

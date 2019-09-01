
package controllers.provider;

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

import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item/provider")
public class ItemProviderController extends AbstractController {

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;


	// Constructors -----------------------------------------------------------

	public ItemProviderController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Item item;

		item = this.itemService.create();
		result = this.createEditModelAndView(item);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;

		if (itemId != 0 && this.itemService.findOne(itemId) == null)
			result = new ModelAndView("security/notfind");
		else
			try {
				final Provider principal = this.providerService.findByPrincipal();

				item = this.itemService.findOne(itemId);
				Assert.isTrue(item.getProvider().equals(principal));
				result = this.createEditModelAndView(item);
			} catch (final Throwable oops) {
				result = new ModelAndView("security/hacking");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Item item, final BindingResult binding) {
		ModelAndView result;

		if (item.getId() != 0 && this.itemService.findOne(item.getId()) == null)
			result = new ModelAndView("security/notfind");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(item);
		else
			try {
				this.itemService.save(item);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("security/hacking");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;

		item = this.itemService.findOne(itemId);

		try {
			this.itemService.delete(item);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Provider principal;
		Collection<Item> items;

		principal = this.providerService.findByPrincipal();
		items = this.itemService.findItemsByProvider(principal.getId());

		result = new ModelAndView("item/list");
		result.addObject("permiso", true);
		result.addObject("items", items);
		result.addObject("requestURI", "item/provider/list.do");

		return result;
	}

	// Ancillary metods

	protected ModelAndView createEditModelAndView(final Item item) {
		ModelAndView result;
		result = this.createEditModelAndView(item, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("message", messageCode);

		return result;
	}
}

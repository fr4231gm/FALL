
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService	itemService;


	// Constructors -----------------------------------------------------------

	public ItemController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int providerId) {
		ModelAndView result;
		Collection<Item> items;

		items = this.itemService.findItemsByProvider(providerId);

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "item/list.do?providerId=" + providerId);

		return result;
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Item> items;

		items = this.itemService.findAll();

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "item/listAll.do");

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int itemId) {
		ModelAndView result;
		Item i;
		String pictures;

		i = this.itemService.findOne(itemId);

		pictures = i.getPictures();
		pictures.replace(" ", "");
		final String[] aux = pictures.split("\r\n");

		result = new ModelAndView("item/display");
		result.addObject("item", i);
		result.addObject("picturesList", aux);

		return result;
	}
}

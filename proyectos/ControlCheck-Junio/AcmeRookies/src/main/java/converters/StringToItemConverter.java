
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ItemRepository;
import domain.Item;

@Component
@Transactional
public class StringToItemConverter implements Converter<String, Item> {

	@Autowired
	ItemRepository	itemRepository;


	@Override
	public Item convert(final String textIn) {
		Item res;
		int id;

		try {
			if (StringUtils.isEmpty(textIn))
				res = null;
			else {
				id = Integer.valueOf(textIn);
				res = this.itemRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}

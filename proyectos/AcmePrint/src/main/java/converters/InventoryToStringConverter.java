package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Inventory;

@Component
@Transactional
public class InventoryToStringConverter implements Converter<Inventory, String>{

	@Override
	public String convert(final Inventory inventory) {
		String res;
		
		if (inventory == null){
			res = null;
		}else{
			res = String.valueOf(inventory.getId());
		}
		
		return res;
	}

}

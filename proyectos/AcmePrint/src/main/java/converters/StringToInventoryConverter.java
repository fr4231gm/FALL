package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.InventoryRepository;
import domain.Inventory;

@Component
@Transactional
public class StringToInventoryConverter implements Converter<String, Inventory>{

	@Autowired
	InventoryRepository inventoryRepository;
	
	@Override
	public Inventory convert (final String textIn){
		Inventory res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.inventoryRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SparePartRepository;
import domain.SparePart;

@Component
@Transactional
public class StringToSparePartConverter implements Converter<String, SparePart>{

	@Autowired
	SparePartRepository sparePartRepository;
	
	@Override
	public SparePart convert (final String textIn){
		SparePart res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.sparePartRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

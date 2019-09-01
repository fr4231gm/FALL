package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SparePart;

@Component
@Transactional
public class SparePartToStringConverter implements Converter<SparePart, String>{

	@Override
	public String convert(final SparePart sparePart) {
		String res;
		
		if (sparePart == null){
			res = null;
		}else{
			res = String.valueOf(sparePart.getId());
		}
		
		return res;
	}

}

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Box;

@Component
@Transactional
public class BoxToStringConverter implements Converter<Box, String>{

	@Override
	public String convert(final Box box) {
		String res;
		
		if (box == null){
			res = null;
		}else{
			res = String.valueOf(box.getId());
		}
		
		return res;
	}

}

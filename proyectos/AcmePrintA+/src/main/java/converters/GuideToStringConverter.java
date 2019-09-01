package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Guide;

@Component
@Transactional
public class GuideToStringConverter implements Converter<Guide, String>{

	@Override
	public String convert(final Guide guide) {
		String res;
		
		if (guide == null){
			res = null;
		}else{
			res = String.valueOf(guide.getId());
		}
		
		return res;
	}

}

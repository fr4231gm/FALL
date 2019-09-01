package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Finder;

@Component
@Transactional
public class FinderToStringConverter implements Converter<Finder, String>{

	@Override
	public String convert(final Finder finder) {
		String res;
		
		if (finder == null){
			res = null;
		}else{
			res = String.valueOf(finder.getId());
		}
		
		return res;
	}

}

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Curricula;

@Component
@Transactional
public class CurriculaToStringConverter implements Converter<Curricula, String>{

	@Override
	public String convert(final Curricula curricula) {
		String res;
		
		if (curricula == null){
			res = null;
		}else{
			res = String.valueOf(curricula.getId());
		}
		
		return res;
	}

}

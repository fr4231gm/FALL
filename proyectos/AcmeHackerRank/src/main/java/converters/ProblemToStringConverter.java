package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Problem;

@Component
@Transactional
public class ProblemToStringConverter implements Converter<Problem,String> {
	
	@Override
	public String convert(final Problem problem){
		String res;
		
		if(problem == null){
			res = null;
		}else{
			res = String.valueOf(problem.getId());
		}
		return res;
	}
}

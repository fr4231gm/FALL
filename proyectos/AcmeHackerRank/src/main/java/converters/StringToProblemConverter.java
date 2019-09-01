package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProblemRepository;
import org.springframework.util.StringUtils;
import domain.Problem;

@Component
@Transactional
public class StringToProblemConverter implements Converter<String,Problem> {
	
	@Autowired
	ProblemRepository problemRepository;
	
	@Override
	public Problem convert(String text){
		Problem res;
		int id;
		try{
			if(StringUtils.isEmpty(text)){
				res = null;
			}else{
				id = Integer.valueOf(text);
				res = this.problemRepository.findOne(id);
			}
		}catch(final Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}

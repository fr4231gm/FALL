package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EducationDataRepository;
import domain.EducationData;

@Component
@Transactional
public class StringToEducationDataConverter implements Converter<String, EducationData>{
	
	@Autowired
	EducationDataRepository educationDataRepository;
	
	@Override
	public EducationData convert(String textIn) {
		EducationData res;
		int id;
		
		try {
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.educationDataRepository.findOne(id);
			}
		}catch(final Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

	
}

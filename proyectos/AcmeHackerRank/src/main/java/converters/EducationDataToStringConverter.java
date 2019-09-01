package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationData;

@Component
@Transactional
public class EducationDataToStringConverter implements Converter<EducationData, String>{

	@Override
	public String convert(final EducationData personalData) {
		String res;
		
		if (personalData == null){
			res = null;
		}else{
			res = String.valueOf(personalData.getId());
		}
		
		return res;
	}

}

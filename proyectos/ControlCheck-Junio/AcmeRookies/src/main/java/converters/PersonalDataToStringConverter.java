package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalData;

@Component
@Transactional
public class PersonalDataToStringConverter implements Converter<PersonalData, String>{

	@Override
	public String convert(final PersonalData personalData) {
		String res;
		
		if (personalData == null){
			res = null;
		}else{
			res = String.valueOf(personalData.getId());
		}
		
		return res;
	}

}

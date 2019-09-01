package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PersonalDataRepository;
import domain.PersonalData;

@Component
@Transactional
public class StringToPersonalDataConverter implements Converter<String, PersonalData>{
	
	@Autowired
	PersonalDataRepository personalDataRepository;
	
	@Override
	public PersonalData convert(String textIn) {
		PersonalData res;
		int id;
		
		try {
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.personalDataRepository.findOne(id);
			}
		}catch(final Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

	
}

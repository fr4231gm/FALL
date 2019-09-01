package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousData;

@Component
@Transactional
public class MiscellaneousDataToStringConverter implements Converter<MiscellaneousData, String>{

	@Override
	public String convert(final MiscellaneousData miscellaneousData) {
		String res;
		
		if (miscellaneousData == null){
			res = null;
		}else{
			res = String.valueOf(miscellaneousData.getId());
		}
		
		return res;
	}

}

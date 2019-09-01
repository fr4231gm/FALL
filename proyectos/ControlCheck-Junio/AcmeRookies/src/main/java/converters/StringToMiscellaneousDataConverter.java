package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;

@Component
@Transactional
public class StringToMiscellaneousDataConverter implements Converter<String, MiscellaneousData>{
	
	@Autowired
	MiscellaneousDataRepository miscellaneousDataRepository;
	
	@Override
	public MiscellaneousData convert(String textIn) {
		MiscellaneousData res;
		int id;
		
		try {
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.miscellaneousDataRepository.findOne(id);
			}
		}catch(final Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

	
}

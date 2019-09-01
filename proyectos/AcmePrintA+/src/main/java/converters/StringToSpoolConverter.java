package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SpoolRepository;
import domain.Spool;

@Component
@Transactional
public class StringToSpoolConverter implements Converter<String, Spool>{

	@Autowired
	SpoolRepository spoolRepository;
	
	@Override
	public Spool convert (final String textIn){
		Spool res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.spoolRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

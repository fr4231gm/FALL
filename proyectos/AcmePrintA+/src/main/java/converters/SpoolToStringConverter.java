package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Spool;

@Component
@Transactional
public class SpoolToStringConverter implements Converter<Spool, String>{

	@Override
	public String convert(final Spool spool) {
		String res;
		
		if (spool == null){
			res = null;
		}else{
			res = String.valueOf(spool.getId());
		}
		
		return res;
	}

}

package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PrintSpoolerRepository;
import domain.PrintSpooler;

@Component
@Transactional
public class StringToPrintSpoolerConverter implements Converter<String, PrintSpooler>{

	@Autowired
	PrintSpoolerRepository printSpoolerRepository;
	
	@Override
	public PrintSpooler convert (final String textIn){
		PrintSpooler res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.printSpoolerRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

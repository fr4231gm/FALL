package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PrintSpooler;

@Component
@Transactional
public class PrintSpoolerToStringConverter implements Converter<PrintSpooler, String>{

	@Override
	public String convert(final PrintSpooler printSpooler) {
		String res;
		
		if (printSpooler == null){
			res = null;
		}else{
			res = String.valueOf(printSpooler.getId());
		}
		
		return res;
	}

}

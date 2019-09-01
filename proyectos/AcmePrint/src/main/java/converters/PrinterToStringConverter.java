package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Printer;

@Component
@Transactional
public class PrinterToStringConverter implements Converter<Printer, String>{

	@Override
	public String convert(final Printer printer) {
		String res;
		
		if (printer == null){
			res = null;
		}else{
			res = String.valueOf(printer.getId());
		}
		
		return res;
	}

}

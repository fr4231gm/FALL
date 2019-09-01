package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Invoice;

@Component
@Transactional
public class InvoiceToStringConverter implements Converter<Invoice, String>{

	@Override
	public String convert(final Invoice invoice) {
		String res;
		
		if (invoice == null){
			res = null;
		}else{
			res = String.valueOf(invoice.getId());
		}
		
		return res;
	}

}

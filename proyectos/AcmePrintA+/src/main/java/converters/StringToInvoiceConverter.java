package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.InvoiceRepository;
import domain.Invoice;

@Component
@Transactional
public class StringToInvoiceConverter implements Converter<String, Invoice>{

	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Override
	public Invoice convert (final String textIn){
		Invoice res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.invoiceRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

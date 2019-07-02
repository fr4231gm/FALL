package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.CreditCard;
@Component @Transactional public class CreditCardToStringConverter implements Converter<CreditCard, String>{
	@Override 	public String convert(final CreditCard creditcard) {		String res; 		if (creditcard == null){ 			res = null; 		}else{ 			res = String.valueOf(creditcard.getId()); 		} 		return res; 	}}
package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Base;
@Component @Transactional public class BaseToStringConverter implements Converter<Base, String>{
	@Override 	public String convert(final Base base) {		String res; 		if (base == null){ 			res = null; 		}else{ 			res = String.valueOf(base.getId()); 		} 		return res; 	}}
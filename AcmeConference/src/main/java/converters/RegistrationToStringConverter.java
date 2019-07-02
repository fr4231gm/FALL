package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Registration;
@Component @Transactional public class RegistrationToStringConverter implements Converter<Registration, String>{
	@Override 	public String convert(final Registration registration) {		String res; 		if (registration == null){ 			res = null; 		}else{ 			res = String.valueOf(registration.getId()); 		} 		return res; 	}}
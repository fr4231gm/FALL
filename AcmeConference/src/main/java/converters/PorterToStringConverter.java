package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Porter;
@Component @Transactional public class PorterToStringConverter implements Converter<Porter, String>{
	@Override 	public String convert(final Porter porter) {		String res; 		if (porter == null){ 			res = null; 		}else{ 			res = String.valueOf(porter.getId()); 		} 		return res; 	}}
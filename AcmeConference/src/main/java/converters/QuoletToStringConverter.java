package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Quolet;
@Component @Transactional public class QuoletToStringConverter implements Converter<Quolet, String>{
	@Override 	public String convert(final Quolet quolet) {		String res; 		if (quolet == null){ 			res = null; 		}else{ 			res = String.valueOf(quolet.getId()); 		} 		return res; 	}}
package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Tutorial;
@Component @Transactional public class TutorialToStringConverter implements Converter<Tutorial, String>{
	@Override 	public String convert(final Tutorial tutorial) {		String res; 		if (tutorial == null){ 			res = null; 		}else{ 			res = String.valueOf(tutorial.getId()); 		} 		return res; 	}}
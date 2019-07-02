package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Reviewer;
@Component @Transactional public class ReviewerToStringConverter implements Converter<Reviewer, String>{
	@Override 	public String convert(final Reviewer reviewer) {		String res; 		if (reviewer == null){ 			res = null; 		}else{ 			res = String.valueOf(reviewer.getId()); 		} 		return res; 	}}
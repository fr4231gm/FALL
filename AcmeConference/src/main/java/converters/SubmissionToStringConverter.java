package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Submission;
@Component @Transactional public class SubmissionToStringConverter implements Converter<Submission, String>{
	@Override 	public String convert(final Submission submission) {		String res; 		if (submission == null){ 			res = null; 		}else{ 			res = String.valueOf(submission.getId()); 		} 		return res; 	}}
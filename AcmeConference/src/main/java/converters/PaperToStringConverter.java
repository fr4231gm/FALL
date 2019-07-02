package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Paper;
@Component @Transactional public class PaperToStringConverter implements Converter<Paper, String>{
	@Override 	public String convert(final Paper paper) {		String res; 		if (paper == null){ 			res = null; 		}else{ 			res = String.valueOf(paper.getId()); 		} 		return res; 	}}
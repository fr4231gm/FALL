package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Author;
@Component @Transactional public class AuthorToStringConverter implements Converter<Author, String>{
	@Override 	public String convert(final Author author) {		String res; 		if (author == null){ 			res = null; 		}else{ 			res = String.valueOf(author.getId()); 		} 		return res; 	}}
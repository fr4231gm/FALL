package converters;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.core.convert.converter.Converter;import org.springframework.stereotype.Component;import org.springframework.transaction.annotation.Transactional;import org.springframework.util.StringUtils;import repositories.AuthorRepository;import domain.Author;
@Component @Transactional public class StringToAuthorConverter implements Converter<String, Author>{
	@Autowired 	AuthorRepository authorRepository; 	@Override 	public Author convert(String textIn) { 		Author res; 		int id; 		try {			if (StringUtils.isEmpty(textIn)){				res = null;			}else{				id = Integer.valueOf(textIn);				res = this.authorRepository.findOne(id);			}		}catch(final Throwable oops){			throw new IllegalArgumentException(oops);		}		return res;	}}
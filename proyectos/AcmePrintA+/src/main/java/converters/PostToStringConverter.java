package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Post;

@Component
@Transactional
public class PostToStringConverter implements Converter<Post, String>{

	@Override
	public String convert(final Post post) {
		String res;
		
		if (post == null){
			res = null;
		}else{
			res = String.valueOf(post.getId());
		}
		
		return res;
	}

}

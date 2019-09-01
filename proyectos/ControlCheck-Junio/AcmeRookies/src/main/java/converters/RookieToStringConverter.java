package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rookie;

@Component
@Transactional
public class RookieToStringConverter implements Converter<Rookie, String> {

	@Override
	public String convert(final Rookie rookie) {
		String res;

		if (rookie == null) {
			res = null;
		} else {
			res = String.valueOf(rookie.getId());
		}

		return res;
	}

}

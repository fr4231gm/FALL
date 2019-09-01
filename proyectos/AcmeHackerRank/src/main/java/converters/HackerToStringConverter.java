package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Hacker;

@Component
@Transactional
public class HackerToStringConverter implements Converter<Hacker, String> {

	@Override
	public String convert(final Hacker hacker) {
		String res;

		if (hacker == null) {
			res = null;
		} else {
			res = String.valueOf(hacker.getId());
		}

		return res;
	}

}

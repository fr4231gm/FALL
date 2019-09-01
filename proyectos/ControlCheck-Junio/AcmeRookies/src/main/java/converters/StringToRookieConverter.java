package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RookieRepository;

import domain.Rookie;

@Component
@Transactional
public class StringToRookieConverter implements Converter<String, Rookie> {

	@Autowired
	RookieRepository rookieRepository;

	@Override
	public Rookie convert(String textIn) {
		Rookie res;
		int id;

		try {
			if (StringUtils.isEmpty(textIn)) {
				res = null;
			} else {
				id = Integer.valueOf(textIn);
				res = this.rookieRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}

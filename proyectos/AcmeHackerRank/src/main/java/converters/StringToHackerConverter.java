package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HackerRepository;

import domain.Hacker;

@Component
@Transactional
public class StringToHackerConverter implements Converter<String, Hacker> {

	@Autowired
	HackerRepository hackerRepository;

	@Override
	public Hacker convert(String textIn) {
		Hacker res;
		int id;

		try {
			if (StringUtils.isEmpty(textIn)) {
				res = null;
			} else {
				id = Integer.valueOf(textIn);
				res = this.hackerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}

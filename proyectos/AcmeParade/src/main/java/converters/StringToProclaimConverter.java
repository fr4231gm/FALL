package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ProclaimRepository;
import domain.Proclaim;

@Component
@Transactional
public class StringToProclaimConverter implements Converter<String, Proclaim> {

	@Autowired
	ProclaimRepository	ProclaimRepository;


	@Override
	public Proclaim convert(final String text) {
		Proclaim result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.ProclaimRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}

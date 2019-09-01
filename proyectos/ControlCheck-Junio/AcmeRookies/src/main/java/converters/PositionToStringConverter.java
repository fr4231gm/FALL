package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Position;

@Component
@Transactional
public class PositionToStringConverter implements Converter<Position, String> {

	@Override
	public String convert(final Position position) {
		String res;

		if (position == null) {
			res = null;
		} else {
			res = String.valueOf(position.getId());
		}

		return res;
	}
}

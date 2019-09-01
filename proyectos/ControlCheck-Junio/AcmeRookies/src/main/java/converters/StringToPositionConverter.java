package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PositionRepository;

import domain.Position;

@Component
@Transactional
public class StringToPositionConverter implements Converter<String, Position> {

	@Autowired
	PositionRepository positionRepository;

	@Override
	public Position convert(String textIn) {
		Position res;
		int id;

		try {
			if (StringUtils.isEmpty(textIn)) {
				res = null;
			} else {
				id = Integer.valueOf(textIn);
				res = this.positionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}

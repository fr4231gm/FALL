package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.History;

@Component
@Transactional
public class HistoryToStringConverter implements Converter<History, String> {

	@Override
	public String convert(final History History) {
		String result;

		if (History == null)
			result = null;
		else
			result = String.valueOf(History.getId());
		return result;
	}
}

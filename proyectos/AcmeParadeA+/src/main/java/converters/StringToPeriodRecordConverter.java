package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PeriodRecordRepository;
import domain.PeriodRecord;

@Component
@Transactional
public class StringToPeriodRecordConverter implements Converter<String, PeriodRecord> {

	@Autowired
	PeriodRecordRepository	PeriodRecordRepository;


	@Override
	public PeriodRecord convert(final String text) {
		PeriodRecord result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.PeriodRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PeriodRecord;

@Component
@Transactional
public class PeriodRecordToStringConverter implements Converter<PeriodRecord, String> {

	@Override
	public String convert(final PeriodRecord PeriodRecord) {
		String result;

		if (PeriodRecord == null)
			result = null;
		else
			result = String.valueOf(PeriodRecord.getId());
		return result;
	}
}

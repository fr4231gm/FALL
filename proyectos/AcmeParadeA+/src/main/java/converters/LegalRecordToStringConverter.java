package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LegalRecord;

@Component
@Transactional
public class LegalRecordToStringConverter implements Converter<LegalRecord, String> {

	@Override
	public String convert(final LegalRecord LegalRecord) {
		String result;

		if (LegalRecord == null)
			result = null;
		else
			result = String.valueOf(LegalRecord.getId());
		return result;
	}
}

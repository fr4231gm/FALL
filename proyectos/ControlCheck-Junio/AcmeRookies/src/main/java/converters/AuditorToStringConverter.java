
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Auditor;

@Component
@Transactional
public class AuditorToStringConverter implements Converter<Auditor, String> {

	@Override
	public String convert(final Auditor auditor) {
		String res;

		if (auditor == null)
			res = null;
		else
			res = String.valueOf(auditor.getId());

		return res;
	}

}

package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Paper;

@Component
@Transactional
public class StringToPaperConverter implements Converter<String, Paper> {

	@Override
	public Paper convert(String text) {
		Paper result;
		String parts[];

		if (text == null) {
			result = null;
		} else {
			try {
				parts = text.split("|");
				result = new Paper();
				result.setTitle(URLDecoder.decode(parts[0], "UTF-8"));
				result.setSummary(URLDecoder.decode(parts[1], "UTF-8"));
				result.setDocument(URLDecoder.decode(parts[2], "UTF-8"));
				result.setCameraReadyPaper(Boolean.valueOf(URLDecoder.decode(
						parts[2], "UTF-8")));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		}

		return result;
	}
}

package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Paper;

@Component
@Transactional
public class PaperToStringConverter implements Converter<Paper, String> {

	@Override
	public String convert(Paper paper) {
		String result;
		StringBuilder builder;

		if (paper == null) {
			result = null;
		} else {
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(paper.getTitle(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(paper.getSummary(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(paper.getDocument(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(
						Boolean.toString(paper.isCameraReadyPaper()), "UTF-8"));
				result = builder.toString();
			} catch (Throwable oops) {
				throw new RuntimeException(oops);
			}
		}

		return result;
	}
}

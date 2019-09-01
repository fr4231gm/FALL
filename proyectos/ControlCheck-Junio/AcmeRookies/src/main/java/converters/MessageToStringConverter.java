
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Message;

@Component
@Transactional
public class MessageToStringConverter implements Converter<Message, String> {

	@Override
	public String convert(final Message message) {
		String res;

		if (message == null)
			res = null;
		else
			res = String.valueOf(message.getId());

		return res;
	}

}

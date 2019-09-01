
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialProfile;

@Component
@Transactional
public class SocialProfileToStringConverter implements Converter<SocialProfile, String> {

	@Override
	public String convert(final SocialProfile socialProfile) {
		String res;

		if (socialProfile == null)
			res = null;
		else
			res = String.valueOf(socialProfile.getId());

		return res;
	}

}

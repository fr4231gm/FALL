
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Component
@Transactional
public class StringToSocialProfileConverter implements Converter<String, SocialProfile> {

	@Autowired
	SocialProfileRepository	socialProfileRepository;


	@Override
	public SocialProfile convert(final String textIn) {
		SocialProfile res;
		int id;

		try {
			if (StringUtils.isEmpty(textIn))
				res = null;
			else {
				id = Integer.valueOf(textIn);
				res = this.socialProfileRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}

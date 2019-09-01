package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ApplicationRepository;
import domain.Application;

@Component
@Transactional
public class StringToApplicationConverter implements Converter<String, Application> {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Application convert(String textIn) {
        Application res;
        int id;

        try {
            if (StringUtils.isEmpty(textIn)) {
                res = null;
            } else {
                id = Integer.valueOf(textIn);
                res = this.applicationRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return res;
    }

}

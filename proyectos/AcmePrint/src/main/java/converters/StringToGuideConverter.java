package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GuideRepository;
import domain.Guide;

@Component
@Transactional
public class StringToGuideConverter implements Converter<String, Guide>{

	@Autowired
	GuideRepository guideRepository;
	
	@Override
	public Guide convert (final String textIn){
		Guide res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.guideRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

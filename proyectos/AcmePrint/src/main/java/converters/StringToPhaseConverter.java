package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PhaseRepository;
import domain.Phase;

@Component
@Transactional
public class StringToPhaseConverter implements Converter<String, Phase>{

	@Autowired
	PhaseRepository phaseRepository;
	
	@Override
	public Phase convert (final String textIn){
		Phase res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.phaseRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

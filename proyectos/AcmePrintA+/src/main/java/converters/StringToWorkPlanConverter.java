package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WorkPlanRepository;
import domain.WorkPlan;

@Component
@Transactional
public class StringToWorkPlanConverter implements Converter<String, WorkPlan>{

	@Autowired
	WorkPlanRepository workPlanRepository;
	
	@Override
	public WorkPlan convert (final String textIn){
		WorkPlan res;
		int id;
		
		try{
			if (StringUtils.isEmpty(textIn)){
				res = null;
			}else{
				id = Integer.valueOf(textIn);
				res = this.workPlanRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}

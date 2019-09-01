package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorkPlan;

@Component
@Transactional
public class WorkPlanToStringConverter implements Converter<WorkPlan, String>{

	@Override
	public String convert(final WorkPlan workPlan) {
		String res;
		
		if (workPlan == null){
			res = null;
		}else{
			res = String.valueOf(workPlan.getId());
		}
		
		return res;
	}

}

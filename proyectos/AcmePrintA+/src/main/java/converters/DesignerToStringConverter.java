package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Designer;

@Component
@Transactional
public class DesignerToStringConverter implements Converter<Designer, String>{

	@Override
	public String convert(final Designer designer) {
		String res;
		
		if (designer == null){
			res = null;
		}else{
			res = String.valueOf(designer.getId());
		}
		
		return res;
	}

}

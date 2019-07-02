package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Section;
@Component @Transactional public class SectionToStringConverter implements Converter<Section, String>{
	@Override 	public String convert(final Section section) {		String res; 		if (section == null){ 			res = null; 		}else{ 			res = String.valueOf(section.getId()); 		} 		return res; 	}}
package converters;
import org.springframework.core.convert.converter.Converter; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import domain.Panel;
@Component @Transactional public class PanelToStringConverter implements Converter<Panel, String>{
	@Override 	public String convert(final Panel panel) {		String res; 		if (panel == null){ 			res = null; 		}else{ 			res = String.valueOf(panel.getId()); 		} 		return res; 	}}
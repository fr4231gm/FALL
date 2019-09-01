package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Order;

@Component
@Transactional
public class OrderToStringConverter implements Converter<Order, String>{

	@Override
	public String convert(final Order order) {
		String res;
		
		if (order == null){
			res = null;
		}else{
			res = String.valueOf(order.getId());
		}
		
		return res;
	}

}

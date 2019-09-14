package converters;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.core.convert.converter.Converter;import org.springframework.stereotype.Component;import org.springframework.transaction.annotation.Transactional;import org.springframework.util.StringUtils;import repositories.QulpRepository;import domain.Qulp;@Component@Transactionalpublic class StringToQulpConverter implements Converter<String, Qulp> {	@Autowired	QulpRepository	qulpRepository;	@Override	public Qulp convert(final String textIn) {		Qulp res;		int id;		try {			if (StringUtils.isEmpty(textIn))				res = null;			else {				id = Integer.valueOf(textIn);				res = this.qulpRepository.findOne(id);			}		} catch (final Throwable oops) {			throw new IllegalArgumentException(oops);		}		return res;	}}
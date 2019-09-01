
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.RequestRepository;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	// Managed Repository -----------------------------------------------------
	@Autowired
	private RequestRepository	requestRepository;

	@Autowired
	private CompanyService		companyService;


	// Simple CRUDS Methods ---------------------------------------------------
	public Request create() {
		final Request res = new Request();

		return res;
	}

	public Request save(final Request request) {
		Request res;
		Assert.notNull(request);
		this.companyService.findByPrincipal();
		res = this.requestRepository.save(request);
		return res;
	}

	public void delete(final Request r) {
		Assert.notNull(r);

		this.requestRepository.delete(r);
	}

	public Collection<Request> findAll() {
		Collection<Request> res;
		res = this.requestRepository.findAll();
		return res;
	}

	public Request findOne(final int RequestId) {
		Request res;
		res = this.requestRepository.findOne(RequestId);
		Assert.notNull(res);
		return res;
	}

	public Request reconstruct(final RequestForm r, final BindingResult binding) {
		final Request res = new Request();

		res.setId(r.getId());
		res.setVersion(r.getVersion());

		res.setStartDate(r.getStartDate());
		res.setEndDate(r.getEndDate());

		res.setExtruderTemp(r.getExtruderTemp());
		res.setHotbedTemp(r.getHotbedTemp());
		res.setLayerHeight(r.getLayerHeight());
		res.setOrder(r.getOrder());

		return res;
	}

	public void flush() {
		this.requestRepository.flush();
	}

	public Request findByOrderId(final int applicationId) {
		final Request res = this.requestRepository.findByOrderId(applicationId);
		return res;
	}

	public Integer findLastNumberOfPrintSpooler(final int printspoolerId) {
		Integer res = this.requestRepository.findLastNumberOfPrintSpooler(printspoolerId);
		if (res == null)
			res = 0;
		return res;
	}

}


package services;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Customer;
import domain.Invoice;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services -----------------------------------------------------

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private OrderService			orderService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private InvoiceService			invoiceService;

	@Autowired
	private MessageService			messageService;


	// Simple CRUD methods -----------------------------------------------------

	public Application create(final int orderId) {
		Assert.notNull(orderId);

		Company principal;
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Application res;
		res = new Application();
		res.setCompany(principal);
		res.setStatus("PENDING");
		res.setOrder(this.orderService.findOne(orderId));
		res.setMoment(new Date(System.currentTimeMillis() - 1));

		return res;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;
		Actor actor;
		Customer customer;
		Company company;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		if (actor instanceof Customer) {
			Assert.isTrue(application.getId() != 0);
			customer = this.customerService.findByPrincipal();
			Assert.notNull(customer);
			Assert.isTrue(application.getOrder().getCustomer().equals(customer));
			Assert.isTrue(application.getStatus() != "PENDING");

		} else if (actor instanceof Company) {
			Assert.isTrue(application.getId() == 0);
			company = this.companyService.findByPrincipal();
			Assert.notNull(company);
			Assert.isTrue(application.getCompany().equals(company));
			Assert.isTrue(application.getStatus().equals("PENDING"));
		}

		result = this.applicationRepository.save(application);

		return result;
	}

	public Application updateStatus(final Application application) {
		Assert.notNull(application);
		Application result;
		Assert.isTrue(application.getStatus() != "PENDING");

		result = this.applicationRepository.save(application);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;

	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	// Other business methods ---------------------------
	public Application accept(final Application application) {
		Invoice i;

		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getId() != 0);

		Collection<Application> applicationsSameOrder;
		Application result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		application.setStatus("ACCEPTED");
		applicationsSameOrder = this.findApplicationsPendingsByOrderAndCustomer(application.getOrder().getId(), principal.getId());

		// When an application is accepted, the others applications of the same order will be rejected automatically
		for (final Application a : applicationsSameOrder)
			this.reject(a);

		this.messageService.notificateApplicationStatusChange(application.getCompany(), application.getOrder().getCustomer(), "Submitted", "Accepted");

		result = this.applicationRepository.save(application);

		application.getOrder().setStatus("IN PROGRESS");

		// Creation of invoice of accepted application
		Locale locale;
		locale = LocaleContextHolder.getLocale();
		i = this.invoiceService.create(result.getId());
		if (locale.getLanguage().equals("es"))
			i.setDescription("Esta es la factura del cliente " + application.getOrder().getCustomer().getName() + " emitida por la empresa " + application.getCompany().getCommercialName());
		else
			i.setDescription("This is the invoice for customer " + application.getOrder().getCustomer().getName() + " made by the company " + application.getCompany().getCommercialName());
		this.invoiceService.save(i);

		return result;
	}

	public Application reject(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(application.getOrder().getCustomer().getId() == this.customerService.findByPrincipal().getId());

		Application result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		application.setStatus("REJECTED");

		this.messageService.notificateApplicationStatusChange(application.getCompany(), application.getOrder().getCustomer(), "Submitted", "Rejected");

		result = this.applicationRepository.save(application);

		return result;
	}

	public Collection<Application> findApplicationsByCustomerId(final int customerId) {
		Collection<Application> res;

		res = this.applicationRepository.findApplicationsByCustomerId(customerId);

		return res;
	}

	public Collection<Application> findApplicationsByOrderId(final int orderId) {
		Collection<Application> res;

		res = this.applicationRepository.findApplicationsByOrderId(orderId);

		return res;
	}

	public Collection<Application> findApplicationsByCompanyId(final int companyId) {
		Collection<Application> res;

		res = this.applicationRepository.findApplicationsByCompanyId(companyId);

		return res;
	}

	public Collection<Application> findApplicationsPendingsByOrderAndCustomer(final int orderId, final int customerId) {
		Collection<Application> res;

		res = this.applicationRepository.findApplicationsPendingsByOrderAndCustomer(orderId, customerId);

		return res;
	}

	public Collection<Application> findApplicationsByOrderAndCustomer(final int orderId, final int customerId) {
		Collection<Application> res;

		res = this.applicationRepository.findApplicationsByOrderAndCustomer(orderId, customerId);

		return res;
	}

	public Application findAcceptedApplicationByOrderId(final int orderId) {
		Application res;
		res = this.applicationRepository.findAcceptedApplicationByOrderId(orderId);
		return res;

	}

	//MethodAnonymous
	public void saveAnonymous(final Application app) {
		Assert.notNull(app);
		this.applicationRepository.save(app);
	}

	public void deleteAnonymous(final Application app) {
		Assert.notNull(app);
		this.applicationRepository.delete(app);
	}
}

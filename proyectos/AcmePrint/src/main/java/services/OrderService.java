
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrderRepository;
import utilities.Tools;
import domain.Application;
import domain.Company;
import domain.Customer;
import domain.Order;
import domain.Post;

@Service
@Transactional
public class OrderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private OrderRepository		orderRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CreditCardService 	creditCardService;

	@Autowired
	private PostService			postService;

	@Autowired
	private MessageService		messageService;


	// Constructors -----------------------------------------------------------
	public OrderService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	// CREATE
	public Order create() {

		final Customer principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.creditCardService.findCreditCardByActorId(principal.getId()));

		final Order res = new Order();

		// Definimos clases asociadas

		res.setCustomer(principal);
		res.setIsDraft(true);
		res.setStatus("DRAFT");
		res.setIsCancelled(false);

		return res;
	}

	// CREATE BY POST
	public Order create(final int postId) {

		final Customer principal = this.customerService.findByPrincipal();
		Assert.notNull(postId);
		Assert.notNull(principal);
		Assert.notNull(this.creditCardService.findCreditCardByActorId(principal.getId()));

		final Order res = new Order();

		// Definimos clases asociadas

		res.setCustomer(principal);
		res.setIsDraft(true);
		res.setStatus("DRAFT");

		res.setIsCancelled(false);


		Post post = this.postService.findOne(postId);
		res.setPost(post);
		res.setStl(post.getStl());

		return res;
	}

	// SAVE
	public Order save(final Order order) {
		Order res = null;
		final Customer principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		if (order.getId() == 0) {
			order.setMoment(new Date(System.currentTimeMillis() - 1));
			if (order.getIsDraft() == false)
				order.setStatus("PUBLISHED");
			order.setStl(Tools.fromGithubToRaw(order.getStl()));
			res = this.orderRepository.save(order);
		} else {
			if (order.getIsDraft() == false)
				order.setStatus("PUBLISHED");
			Assert.isTrue(order.getCustomer().getId() == principal.getId());
			order.setStl(Tools.fromGithubToRaw(order.getStl()));
			res = this.orderRepository.save(order);
		}
		return res;
	}

	// DELETE
	public void delete(final Integer orderId) {
		final Customer principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);


		Order order = this.findOne(orderId);
		Assert.isTrue(order.getCustomer().getId() == principal.getId());
		Assert.isTrue(order.getIsDraft());
		this.orderRepository.delete(orderId);

	}

	// FINDONE
	public Order findOne(final int orderId) {
		Order res;

		res = this.orderRepository.findOne(orderId);
		Assert.notNull(res);

		return res;
	}

	// FINDONE TO FAIL
	public Order findOneToFail(final int orderId) {
		Order res;

		res = this.orderRepository.findOne(orderId);

		return res;
	}

	// FIND ALL
	public Collection<Order> findAll() {
		Collection<Order> res;

		res = this.orderRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void flush() {
		this.orderRepository.flush();
	}

	public Collection<Order> findOrdersByCustomerId(final int customerId) {
		Collection<Order> res;

		res = this.orderRepository.findOrdersByCustomerId(customerId);

		return res;
	}

	public Collection<Order> findOrdersWithApplicationsNotAccepted() {
		Collection<Order> res;

		res = this.orderRepository.findOrdersWithApplicationsNotAccepted();

		return res;
	}

	public void updateOrderStatus(final int orderId, final String status) {

		final Order order = this.findOne(orderId);
		Company company = null;

		final Application a = this.findAcceptedApplicationByOrderId(orderId);
		if (a != null)
			company = a.getCompany();

		this.messageService.notificateOrderStatusChange(company, order.getCustomer(), order.getStatus(), status);
		order.setStatus(status);

		this.orderRepository.save(order);
	}

	public Application findAcceptedApplicationByOrderId(final int orderId) {
		Application res;

		res = this.orderRepository.findAcceptedApplicationByOrderId(orderId);

		return res;
	}

	public Order cancel(final Order order) {
		final Company c = this.companyService.findByPrincipal();
		Assert.notNull(c);
		Assert.notNull(order);
		order.setIsCancelled(true);
		return this.orderRepository.save(order);
	}

	public Collection<Order> findOrdersByCustomerIdWithNoPaymant(final int id) {
		Collection<Order> res;
		res = this.orderRepository.findOrdersByCustomerIdWithNoPaymant(id);

		return res;
	}

	// MethodAnonymous
	public void saveAnonymous(final Order order) {
		Assert.notNull(order);
		this.orderRepository.save(order);
	}

	// MethodAnonymous
	public void deleteAnonymous(final Order order) {
		Assert.notNull(order);
		this.orderRepository.delete(order);
	}
}

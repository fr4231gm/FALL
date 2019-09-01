
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Comment;
import domain.Company;
import domain.CreditCard;
import domain.Customer;
import domain.Designer;
import domain.Inventory;
import domain.Invoice;
import domain.Order;
import domain.Post;
import domain.Printer;
import domain.Provider;
import domain.SocialProfile;
import domain.SparePart;
import domain.Sponsorship;
import domain.Spool;

@Service
public class ExportDataService {

	// Suppoting Services -----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private OrderService			orderService;

	@Autowired
	private DesignerService			designerService;

	@Autowired
	private PostService				postService;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private InventoryService		inventoryService;

	@Autowired
	private InvoiceService			invoiceService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private AnonymousService		anonymousService;


	/*******************************************************************/

	public String exportData() {
		final Actor principal = this.actorService.findByPrincipal();

		final Collection<Integer> auths = this.anonymousService.checkActor(principal);

		String res = "";

		//Customer
		if (auths.contains(1))
			res = this.exportCustomer();

		//Provider
		if (auths.contains(2))
			res = this.exportProvider();

		//Administrator
		if (auths.contains(3))
			res = this.exportAdministrator();

		//Designer
		if (auths.contains(4))
			res = this.exportDesigner();

		//Company
		if (auths.contains(5))
			res = this.exportCompany();

		return res;
	}

	protected String exportCustomer() {
		String res = "";
		final Customer customer = this.customerService.findByPrincipal();
		final String noData = ("No data found. / No se han encontrado datos.");

		final UserAccount userAccount = customer.getUserAccount();
		final CreditCard creditCard = this.creditCardService.findCreditCardByActorId(customer.getId());
		Collection<SocialProfile> socialProfiles = new ArrayList<>();

		Collection<Order> orders = new ArrayList<>();
		Collection<Application> applications = new ArrayList<>();
		Collection<Invoice> invoices = new ArrayList<>();

		res += ("Username: " + userAccount.getUsername() + "\n");
		res += ("Customer: " + customer.toString() + "\n");
		res += ("Creditcard: " + creditCard.toString());

		//SocialProfiles
		res += ("SocialProfiles: " + "\n");
		socialProfiles = this.socialProfileService.findByActor(customer.getId());
		if (socialProfiles.size() != 0)
			for (final SocialProfile s : socialProfiles)
				res += ("SocialProfile: " + s.toString() + "\n");
		else
			res += noData;

		//Orders - Applications - Invoices
		res += ("Orders: " + "\n");
		orders = this.orderService.findOrdersByCustomerId(customer.getId());
		if (orders.size() != 0)
			for (final Order o : orders) {
				res += ("Order: " + o.toString() + "\n");
				applications = this.applicationService.findApplicationsByOrderAndCustomer(o.getId(), customer.getId());
				if (applications.size() != 0)
					for (final Application a : applications) {
						res += ("Application: " + a.toString() + "\n");
						final Invoice invoice = this.invoiceService.findInvoiceByApplicationId(a.getId());
						if (invoice != null)
							res += ("Invoice: " + invoice.toString() + "\n");
					}
			}
		else
			res += noData;

		//Invoices
		invoices = this.invoiceService.findInvoicesByCustomerId(customer.getId());
		if (invoices.size() != 0) {
			res += ("Invoices: " + "\n");
			for (final Invoice i : invoices)
				res += ("Invoice: " + i.toString() + "\n");
		} else
			res += noData;

		return res;
	}

	protected String exportProvider() {
		String res = "";
		final Provider provider = this.providerService.findByPrincipal();
		final String noData = ("No data found. / No se han encontrado datos.");

		final UserAccount userAccount = provider.getUserAccount();
		final CreditCard creditCard = this.creditCardService.findCreditCardByActorId(provider.getId());
		Collection<SocialProfile> socialProfiles = new ArrayList<>();

		Collection<Sponsorship> sponsorships = new ArrayList<>();

		res += ("Username: " + userAccount.getUsername() + "\n");
		res += ("Provider: " + provider.toString() + "\n");
		res += ("Creditcard: " + creditCard.toString());

		//SocialProfiles
		res += ("SocialProfiles: " + "\n");
		socialProfiles = this.socialProfileService.findByActor(provider.getId());
		if (socialProfiles.size() != 0)
			for (final SocialProfile s : socialProfiles)
				res += ("SocialProfile: " + s.toString() + "\n");
		else
			res += noData;

		//Sponsorships
		res += ("Sponsorships: " + "\n");
		sponsorships = this.sponsorshipService.findSponsorshipsByProviderId();
		if (sponsorships.size() != 0)
			for (final Sponsorship s : sponsorships)
				res += ("Sponsorships: " + s.toString() + "\n");
		else
			res += noData;

		return res;
	}

	protected String exportAdministrator() {
		String res = "";
		final Administrator administrator = this.administratorService.findByPrincipal();
		final String noData = ("No data found. / No se han encontrado datos.");

		final UserAccount userAccount = administrator.getUserAccount();
		
		Collection<SocialProfile> socialProfiles = new ArrayList<>();

		res += ("Username: " + userAccount.getUsername() + "\n");
		res += ("Administrator: " + administrator.toString() + "\n");
		

		//SocialProfiles
		res += ("SocialProfiles: " + "\n");
		socialProfiles = this.socialProfileService.findByActor(administrator.getId());
		if (socialProfiles.size() != 0)
			for (final SocialProfile s : socialProfiles)
				res += ("SocialProfile: " + s.toString() + "\n");
		else
			res += noData;

		return res;
	}

	protected String exportDesigner() {
		String res = "";
		final Designer designer = this.designerService.findByPrincipal();
		final String noData = ("No data found. / No se han encontrado datos.");

		final UserAccount userAccount = designer.getUserAccount();
		Collection<SocialProfile> socialProfiles = new ArrayList<>();
		Collection<Post> posts = new ArrayList<>();
		Collection<Comment> comments = new ArrayList<>();

		res += ("Username: " + userAccount.getUsername() + "\n");
		res += ("Designer: " + designer.toString() + "\n");

		//SocialProfiles
		res += ("SocialProfiles: " + "\n");
		socialProfiles = this.socialProfileService.findByActor(designer.getId());
		if (socialProfiles.size() != 0)
			for (final SocialProfile s : socialProfiles)
				res += ("SocialProfile: " + s.toString() + "\n");
		else
			res += noData;

		//Posts
		res += ("Posts: " + "\n");
		posts = this.postService.findPostsByDesignerId(designer.getId());
		if (posts.size() != 0)
			for (final Post p : posts) {
				res += ("Post: " + p.toString() + "\n");
				res += ("Guide: " + p.getGuide().toString() + "\n");
				comments = this.commentService.findCommentsByPostId(p.getId());
				if (comments.size() != 0)
					for (final Comment c : comments)
						res += ("Comment: " + c.toString() + "\n");
			}
		else
			res += noData;

		return res;
	}
	protected String exportCompany() {
		String res = "";
		final Company company = this.companyService.findByPrincipal();
		final String noData = ("No data found. / No se han encontrado datos.");

		final UserAccount userAccount = company.getUserAccount();
		Collection<SocialProfile> socialProfiles = new ArrayList<>();

		Collection<Application> applications = new ArrayList<>();
		Collection<Inventory> inventories = new ArrayList<>();
		Collection<Printer> printers = new ArrayList<>();
		Collection<Spool> spools = new ArrayList<>();
		Collection<SparePart> spareParts = new ArrayList<>();

		res += ("Username: " + userAccount.getUsername() + "\n");
		res += ("Company: " + company.toString() + "\n");
		res += ("Finder: " + company.getFinder().toString() + "\n");

		//SocialProfiles
		res += ("SocialProfiles: " + "\n");
		socialProfiles = this.socialProfileService.findByActor(company.getId());
		if (socialProfiles.size() != 0)
			for (final SocialProfile s : socialProfiles)
				res += ("SocialProfile: " + s.toString() + "\n");
		else
			res += noData;

		//Applications
		applications = this.applicationService.findApplicationsByCompanyId(company.getId());
		if (applications.size() != 0)
			for (final Application a : applications) {
				res += ("Application: " + a.toString() + "\n");
				final Invoice i = this.invoiceService.findInvoiceByApplicationId(a.getId());
				if (i != null)
					res += ("Invoice: " + i.toString() + "\n");
			}
		else
			res += noData;

		//Inventories
		inventories = this.inventoryService.findByCompanyId(company.getId());
		if (inventories.size() != 0)
			for (final Inventory i : inventories) {
				res += ("Inventory: " + i.toString() + "\n");
				printers = i.getPrinters();
				spools = i.getSpools();
				spareParts = i.getSpareParts();

				if (printers.size() != 0)
					for (final Printer pr : printers)
						res += ("Printer: " + pr.toString() + "\n");
				if (spools.size() != 0)
					for (final Spool s : spools)
						res += ("Spool: " + s.toString() + "\n");
				if (spareParts.size() != 0)
					for (final SparePart sp : spareParts)
						res += ("SparePart: " + sp.toString() + "\n");
			}
		else
			res += noData;

		return res;
	}

}

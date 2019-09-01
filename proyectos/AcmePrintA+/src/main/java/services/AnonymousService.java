package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Customer;
import domain.Designer;
import domain.Inventory;
import domain.Order;
import domain.Post;
import domain.Provider;
import domain.Sponsorship;

@Service
public class AnonymousService {
	
	// Suppoting Services -----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private FinderService finderService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DesignerService designerService;

	@Autowired
	private PostService postService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private SocialProfileService socialProfileService;

	@Autowired
	private InventoryService inventoryService;

	// Constructor
	private AnonymousService() {
		super();
	}

	// Methods -> Este método actualiza todos las dependencias antes de eliminar
	// el actor
	public void changeThings() {
		Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Integer> authorities = this.checkActor(principal);

		// Customer
		if (authorities.contains(1)) {
			this.updateOrder(principal);
			if (this.creditCardService.findCreditCardByActorId(principal
					.getId()) != null) {
				this.creditCardService.deleteAnonymous();
			}
		}

		// Provider
		if (authorities.contains(2)) {
			this.updateSponsorship();
			if (this.creditCardService.findCreditCardByActorId(principal
					.getId()) != null) {
				this.creditCardService.deleteAnonymous();
			}

		}

		// Administrator
		if (authorities.contains(3)) {

		}

		// Designer
		if (authorities.contains(4)) {
			this.updatePost(principal);
		}

		// Company
		if (authorities.contains(5)) {
			this.updateApplication(principal);
			this.updateInventory(principal);
		}

		// Methods that always ejecuted

		this.socialProfileService.deleteAnonymous();

	}

	public void dropOut(Actor actor) {

		Collection<Integer> x = this.checkActor(actor);
		
		if (x.contains(5)) {
			Company c = null;
			c = this.companyService.findOne(actor.getId());
			this.actorService.delete(actor);
			this.finderService.deleteByUserDropOut(c.getFinder());
		}else{
			this.actorService.delete(actor);
		}
	}

	// Other business methods
	// Esté método saca todas las AUTHORITY del actor.
	public Collection<Integer> checkActor(Actor principal) {
		// Metodo a devolver
		Collection<Integer> res = new ArrayList<Integer>();

		// Comprobamos el actor
		Assert.notNull(principal);
		Collection<Authority> auth = principal.getUserAccount()
				.getAuthorities();

		// Creamos las Authority
		Authority auth1 = new Authority();
		auth1.setAuthority(Authority.CUSTOMER);

		Authority auth2 = new Authority();
		auth2.setAuthority(Authority.PROVIDER);

		Authority auth3 = new Authority();
		auth3.setAuthority(Authority.ADMINISTRATOR);

		Authority auth4 = new Authority();
		auth4.setAuthority(Authority.DESIGNER);

		Authority auth5 = new Authority();
		auth5.setAuthority(Authority.COMPANY);

		// Compruebo si las authorities contienen a cada authority.
		if (auth.contains(auth1)) {
			res.add(1);
		}
		if (auth.contains(auth2)) {
			res.add(2);
		}
		if (auth.contains(auth3)) {
			res.add(3);
		}
		if (auth.contains(auth4)) {
			res.add(4);
		}
		if (auth.contains(auth5)) {
			res.add(5);
		}
		return res;
	}

	public void updateOrder(Actor principal) {
		UserAccount ua = this.userAccountService.findByUsername("anonymous");
		Customer anonymous = (Customer) this.customerService
				.findCustomerByUserAccount(ua.getId());
		Collection<Order> orders = this.orderService
				.findOrdersByCustomerId(principal.getId());
		if (orders.size() != 0) {
			for (Order o : orders) {
				if (o.getStatus().equals("DRAFT")
						|| o.getStatus().equals("BORRADOR")) {
					this.orderService.deleteAnonymous(o);
				} else if (o.getStatus().equals("PICKED-UP")
						|| o.getStatus().equals("RECOGIDO")) {
					o.setCustomer(anonymous);
					this.orderService.saveAnonymous(o);
				} else {
					o.setIsCancelled(true);
					o.setCustomer(anonymous);
					this.orderService.saveAnonymous(o);
				}
			}
		}
	}

	public void updateSponsorship() {
		UserAccount ua = this.userAccountService.findByUsername("anonymous");
		Provider anonymous = (Provider) this.providerService
				.findProviderByUserAccount(ua.getId());
		Collection<Sponsorship> sps = this.sponsorshipService
				.findSponsorshipsByProviderId();
		if (sps.size() != 0) {
			for (Sponsorship s : sps) {
				s.setProvider(anonymous);
				s.setIsEnabled(false);
				this.sponsorshipService.saveAnonymous(s);
			}
		}
	}

	public void updateApplication(Actor principal) {
		UserAccount ua = this.userAccountService.findByUsername("anonymous");
		Company anonymous = (Company) this.companyService
				.findCompanyByUserAccount(ua.getId());
		Collection<Application> apps = this.applicationService
				.findApplicationsByCompanyId(principal.getId());
		if (apps.size() != 0) {
			for (Application app : apps) {
				if (app.getStatus().equals("ACCEPTED")
						&& (!app.getOrder().getStatus().equals("PICKED-UP") || !app
								.getOrder().getStatus().equals("RECOGIDO"))) {


					Order o = app.getOrder();
					o.setIsCancelled(true);
					this.orderService.saveAnonymous(o);
					app.setCompany(anonymous);
					this.applicationService.saveAnonymous(app);
				} else if (app.getStatus().equals("ACCEPTED")) {
					app.setCompany(anonymous);
					this.applicationService.saveAnonymous(app);
				} else {
					this.applicationService.deleteAnonymous(app);
				}
			}
		}
	}

	public void updatePost(Actor principal) {
		UserAccount ua = this.userAccountService.findByUsername("anonymous");
		Designer anonymous = (Designer) this.designerService
				.findDesignerByUserAccount(ua.getId());
		Collection<Post> posts = this.postService
				.findPostsByDesignerId(principal.getId());
		if (posts.size() != 0) {
			for (Post p : posts) {
				p.setDesigner(anonymous);
				this.postService.saveAnonymous(p);
			}
		}
	}

	public void updateInventory(Actor principal) {
		UserAccount ua = this.userAccountService.findByUsername("anonymous");
		Company anonymous = (Company) this.companyService
				.findCompanyByUserAccount(ua.getId());
		Collection<Inventory> inventories = this.inventoryService
				.findByCompanyId(principal.getId());
		if (inventories.size() != 0) {
			for (Inventory i : inventories) {
				i.setCompany(anonymous);
				this.inventoryService.saveAnonymous(i);
			}
		}
	}

}

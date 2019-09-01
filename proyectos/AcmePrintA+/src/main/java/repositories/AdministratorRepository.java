package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Customer;
import domain.Designer;
import domain.Provider;

@Repository
public interface AdministratorRepository extends
		JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findAdministratorByUserAccountId(int userAccountId);

	@Query("select a from Administrator a where a.userAccount.username = ?1")
	Administrator findAdministratorByUsername(String username);

	@Query("select m.sender from Message m where m.isSpam = true group by m.sender having count(m) > ( select 0.1*count(me) from Message me where me.isSpam=true )")
	Collection<Actor> findSpammersToFlag();

	/************************ DASHBOARD *************************************/

	// C - level --------------------------------------------------------------

	// The average, the minimum, the maximum, and the standard deviation of the
	// number of orders per customer.

	@Query("select "
			+ "avg(1.0*(select count(o.customer) from Order o where o.customer.id=c.id)), "
			+ "min(1.0*(select count(o.customer) from Order o where o.customer.id=c.id)), "
			+ "max(1.0*(select count(o.customer) from Order o where o.customer.id=c.id)), "
			+ "stddev(1.0*(select count(o.customer) from Order o where o.customer.id=c.id)) "
			+ "from Customer c")
	public Collection<Double> findAvgMinMaxStddevOrdersPerCustomer();

	// The average, the minimum, the maximum, and the standard deviation of the
	// number of applications per order.
	@Query("select "
			+ "avg(1.0*(select count(a.order) from Application a where a.order.id=o.id)), "
			+ "min(1.0*(select count(a.order) from Application a where a.order.id=o.id)), "
			+ "max(1.0*(select count(a.order) from Application a where a.order.id=o.id)), "
			+ "stddev(1.0*(select count(a.order) from Application a where a.order.id=o.id)) "
			+ "from Order o where o.isDraft = false and o.isCancelled = false")
	public Collection<Double> findAvgMinMaxStddevApplicationsPerOrder();

	// The average, the minimum, the maximum, and the standard deviation of the
	// price offered in the applications.
	@Query("select " + "avg(a.offeredPrice), " + "min(a.offeredPrice), "
			+ "max(a.offeredPrice), " + "stddev(a.offeredPrice) "
			+ "from Application a")
	public Collection<Double> findAvgMinMaxStddevOfferedPriceOfApplications();

	// The ratio of pending applications.
	@Query("select 1.* (select count(a) from Application a where a.status='PENDING')/count(ap) from Application ap")
	Double findRatioApplicationsByStatusPending();

	// The ratio of accepted applications.
	@Query("select 1.* (select count(a) from Application a where a.status='ACCEPTED')/count(ap) from Application ap")
	Double findRatioApplicationsByStatusAccepted();

	// The ratio of rejected applications.
	@Query("select 1.* (select count(a) from Application a where a.status='REJECTED')/count(ap) from Application ap")
	Double findRatioApplicationsByStatusRejected();

	// The listing of customers who have published at least 10% more orders than
	// the average, ordered by number of applications.
	@Query("select a.order.customer from Application a where a.order.status != 'DRAFT' group by a.order.customer having count(a) > (select 0.1*count(ap) from Application ap)")
	List<Customer> findActiveCustomers();

	// The listing of companies who have got accepted at least 10% more
	// applications than the average, ordered by number of applications.
	@Query("select a.company from Application a where a.status = 'ACCEPTED' group by a.company having count(a) > (select 0.1*count(ap) from Application ap where ap.status = 'ACCEPTED')")
	List<Company> findActiveCompanies();

	// B-level ----------------------------------------------------------------

	// The minimum, the maximum, the average, and the standard deviation of the
	// number of sponsorships per post.
	@Query("select "
			+ "avg(1.0*(select count(sp) from Sponsorship sp where sp.post.id=p.id and sp.isEnabled = true)),"
			+ "min(1.0*(select count(sp) from Sponsorship sp where sp.post.id=p.id and sp.isEnabled = true)),"
			+ "max(1.0*(select count(sp) from Sponsorship sp where sp.post.id=p.id and sp.isEnabled = true)),"
			+ "stddev(1.0*(select count(sp) from Sponsorship sp where sp.post.id=p.id and sp.isEnabled = true))"
			+ " from Post p")
	Collection<Double> findAvgMinMaxStddevSponsorshipsPerPost();

	// The minimum, the maximum, the average, and the standard deviation of the
	// number of printers per company.
	@Query("select "
			+ "min(1.0*(select count(p) from Inventory i join i.printers p where i.company.id=c.id)), "
			+ "max(1.0*(select count(p) from Inventory i join i.printers p where i.company.id=c.id)), "
			+ "avg(1.0*(select count(p) from Inventory i join i.printers p where i.company.id=c.id)), "
			+ "stddev(1.0*(select count(p) from Inventory i join i.printers p where i.company.id=c.id)) "
			+ "from Company c")
	public Collection<Double> findMinMaxAvgStddevPrintersPerCompany();
	
	// The ratio of published orders whose pieces are in a printer spooler.
	@Query("select count(o) from Order o where o.isDraft = false and (o.status = 'ON PRINTER SPOOLER' or o.status = 'EN COLA DE IMPRESIÓN')")
	Double findOrdersOnPrinterSpooler();
	
	@Query("select count(od) from Order od where od.isDraft = false and od.status = 'PUBLISHED'")
	Double findOrdersPublished();
	
	// The ratio of post with and without sponsorships.
	@Query("select 1.* (select count(sp.post) from Sponsorship sp where sp.isEnabled = true)/count(p) from Post p where p.isDraft = false")
	Double findRatioPostWithAndWithoutSponsorships();
	
	// The top-five designers in terms of number of posts.
	@Query("select p.designer from Post p where p.isDraft = false group by p.designer having max(p)>1")
	List<Designer> findTop5DesignersByPostsPublished();
	
	// The top-five designers in terms of score.
	@Query("select p.designer from Post p where p.isDraft = false group by p.designer.score")
	List<Designer> findTop5DesignersByScore();
	
	// The top-five designers in terms of sponsorships received.
	@Query("select sp.post.designer from Sponsorship sp where sp.post.isDraft = false group by sp.post.designer having max(sp)>1")
	List<Designer> findTop5DesignersBySponsorshipsReceived();
	
	// The top-five companies in terms of number of printers.
	@Query("select i.company , count(p) from Inventory i join i.printers p group by i.company")
	List<Company> findCompaniesWithMorePrinters();
	
	// The top-five provider in terms of sponsorships.
	@Query("select sp.provider from Sponsorship sp where sp.isEnabled = true group by sp.provider")
	List<Provider> findTop5Providers();


	
	// A-level ----------------------------------------------------------------

	// The customers that place 10% more orders than the average.
	@Query("select o.customer from Order o where o.isDraft = false group by o.customer having count(o) > (select 0.1*avg(od) from Order od where od.isDraft = false)")
	List<Customer> findTop5Customers();
	
	// The top-five companies in terms of print spoolers.
	@Query("select i.company from Inventory i join i.spools p group by i.company")
	List<Company> findCompaniesWithMoreSpools();
	
	// The top-five companies in terms of invoices.
	@Query("select i.application.company from Invoice i group by i.application.company")
	List<Company> findTop5ActiveCompanies();
	
	/************************* END DASHBOARD *******************************/
	
}

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.Endorsement;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private EndorsementService endorsementService;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private UserAccountService	userAccountService;

	public CustomerService() {
		super();
	}

	public Customer create() {
		Customer result;
		result = new Customer();
		UserAccount ua = this.userAccountService.create("CUSTOMER");
		result.setUserAccount(ua);
		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		boolean isNew = false;
		Customer result;
		if (customer.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			customer.getUserAccount().setPassword(passwordEncoder.encodePassword(customer.getUserAccount().getPassword(), null));
			isNew=true;
		}
		result = this.customerRepository.save(customer);
		if(isNew){
			this.boxService.generateDefaultFolders(result);
		}
		return result;
	}

	public Customer findById(final Integer id) {
		Assert.isTrue(id != 0);
		return this.customerRepository.findOne(id);
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Collection<FixUpTask> showFixUpTasks() {
		Customer principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
		return this.fixUpTaskService.findFixUpTaskPerCustomer(principal);
	}

	public Collection<Application> showApplications() {
		Customer principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
		final Collection<Application> applications = new ArrayList<Application>();
		applications.addAll(this.fixUpTaskService
				.findApplicationFixUpTask(principal.getId()));
		return applications;

	}

	public void saveApplication(final Application a) {
		Assert.notNull(a);
		if (a.getStatus().equals("ACCEPTED"))
			Assert.isTrue(a.getCreditCard().getNumber().matches("^0-9"));
		this.applicationService.save(a);
	}

	public Collection<Complaint> showComplaints() {
		Customer principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
		return this.complaintService.findCustomerComplaints(principal);
	}

	public Complaint showComplaint(final Integer id) {
		Assert.isTrue(id != 0);
		return this.complaintService.findOneComplaint(id);
	}

	public Complaint createComplaint() {
		return this.complaintService.create();
	}

	// También añadir un save para poder guardarla una vez creada?
	public void createNote() {
		this.noteService.create();
	}

	public void saveNote(final Note n) {
		Assert.notNull(n);
		this.noteService.save(n);
	}

	// Esto va en note
	public void writeNote(final Integer id, final Note note) {

		Report r;
		r = this.complaintService.findOneComplaint(id).getReport();
		final Collection<Note> n = r.getNotes();
		n.add(note);
		r.setNotes(n);
		this.reportService.save(r);

	}

	public Collection<Note> showAllNotes() {
		final List<Complaint> comp = new ArrayList<Complaint>(
				this.showComplaints());
		final List<Note> n = new ArrayList<Note>();
		for (int i = 0; i < comp.size(); i++)
			if (!comp.get(i).getReport().equals(null))
				n.addAll(comp.get(i).getReport().getNotes());
		return n;
	}

	// QUE HAGO CURRO
	public void writeCommentNote(final String s, final Integer id) {
		final Note n = this.noteService.findOne(id);
		final String com = n.getCustomerComments();
		n.setCustomerComments(com + s);
		this.noteService.save(n);
	}

	public Collection<Endorsement> showEndorsements() {
		final Customer principal = this.findByPrincipal();
		return this.endorsementService.findPerCreator(principal.getId());

	}

	public Endorsement showEndorsement(final Integer id) {
		Assert.isTrue(id != 0);
		return this.endorsementService.findOne(id);
	}

	public Endorsement createEndorsement() {

		return this.endorsementService.create();
	}

	public Endorsement saveEndorsement(final HandyWorker h, final Endorsement e) {
		final Customer c = this.findByPrincipal();
		Assert.notNull(c);
		e.setEndorsed(h);
		return this.endorsementService.save(e);

	}

	public void deleteEndorsement(final Endorsement e) {
		this.endorsementService.delete(e);
	}

	public Customer findByPrincipal() {
		Customer result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			result = null;
		else
			result = this.customerRepository.finByUserAccountId(userAccount
					.getId());

		return result;
	}

	public void flush() {
		this.customerRepository.flush();
		
	}

}

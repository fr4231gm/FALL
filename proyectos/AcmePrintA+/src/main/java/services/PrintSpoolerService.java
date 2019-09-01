package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrintSpoolerRepository;
import domain.Company;
import domain.Order;
import domain.PrintSpooler;
import domain.Printer;
import domain.Request;

@Service
@Transactional
public class PrintSpoolerService {

	// Managed Repository -----------------------------------------------------
	@Autowired
	private PrintSpoolerRepository printSpoolerRepository;
	
	//Supporting services
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MessageService messageService;


	// Simple CRUDS Methods ---------------------------------------------------
	public PrintSpooler create(Printer printer) {
		final PrintSpooler res = new PrintSpooler();
		
		Assert.isTrue(printer.getIsActive());
		res.setPrinter(printer);
		Collection <Request> requests = new ArrayList<Request>();
		res.setRequests(requests);
		
		return res;
	}

	public PrintSpooler save(final PrintSpooler PrintSpooler) {
		PrintSpooler res;
		Assert.notNull(PrintSpooler);
		
		res = this.printSpoolerRepository.save(PrintSpooler);
		return res;
	}

	public Collection<PrintSpooler> findAll() {
		Collection<PrintSpooler> res;
		res = this.printSpoolerRepository.findAll();
		return res;
	}

	public PrintSpooler findOne(final int PrintSpoolerId) {
		PrintSpooler res;
		res = this.printSpoolerRepository.findOne(PrintSpoolerId);
		Assert.notNull(res);
		return res;
	}
	
	public PrintSpooler findByPrinterId(final int printerId) {
		PrintSpooler res;
		res = this.printSpoolerRepository.findByPrinterId(printerId);
		return res;
	}


	public void flush() {
		this.printSpoolerRepository.flush();
	}

	public Collection<PrintSpooler> findByPrincipal() {
		Company principal = this.companyService.findByPrincipal();
		Collection<PrintSpooler> res = this.printSpoolerRepository.findByCompanyId(principal.getId());
		return res;
	}

	public PrintSpooler findByRequest(int id) {
		PrintSpooler res =  this.printSpoolerRepository.findByRequestId(id);
		return res;
	}

	public void removeRequest(Request toDelete, PrintSpooler p) {
		List<Request> requests = new ArrayList<Request>(p.getRequests());
		Assert.isTrue(toDelete.getNumber() > 0);
		Order order = toDelete.getOrder();
		this.messageService.notificateErrorProcessingOrder(this.companyService.findByPrincipal(), order.getCustomer());
		this.orderService.cancel(order);
		requests.remove(toDelete);
		Collection<Request> aux = new ArrayList<Request>();
		for (int i =0; i< requests.size(); i++){
			Request r = requests.get(i);
			if(r.getNumber() > toDelete.getNumber()){
				r.setNumber(r.getNumber() - 1);
				this.requestService.save(r);
			}
			aux.add(r);
		}
		p.setRequests(aux);
	}

	public void doneRequest(Request toDone, PrintSpooler p) {
		List<Request> requests = new ArrayList<Request>(p.getRequests());
		Assert.isTrue(toDone.getNumber() == 1);
		Collection<Request> aux = new ArrayList<Request>();
		for (int i =0; i< requests.size(); i++){
			Request r = requests.get(i);
			r.setNumber(r.getNumber() - 1);
			this.requestService.save(r);
			aux.add(r);
		}
		p.setRequests(aux);
	}

}


package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrintSpoolerService;
import services.PrinterService;
import controllers.AbstractController;
import domain.PrintSpooler;
import domain.Printer;
import domain.Request;

@Controller
@RequestMapping("/printer")
public class PrinterController extends AbstractController {

	// Services
	@Autowired
	private PrinterService	printerService;
	
	@Autowired
	private PrintSpoolerService		printSpoolerService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int printerId) {
		final ModelAndView result;

		final Printer printer = this.printerService.findOne(printerId);
		final PrintSpooler printSpooler = this.printSpoolerService.findByPrinterId(printerId);
		
		result = new ModelAndView("printer/display");
		result.addObject("printer", printer);
		
		if(printSpooler != null){
			List<Request> all = new ArrayList<Request>(printSpooler.getRequests());
			Collection<Request> printed = new ArrayList<Request>();
			Collection<Request> notPrinted = new ArrayList<Request>();
		
			for(int i = 0; i<all.size(); i ++){
				if(all.get(i).getNumber()>0){
					notPrinted.add(all.get(i));
				} else{
					printed.add(all.get(i));
				}
			}
			result.addObject("notPrinted", notPrinted);
			result.addObject("printed", printed);
			result.addObject("havePrintSpooler", true);
		} else{
			result.addObject("havePrintSpooler", false);
		}
		result.addObject("requestURI", "printer/display");
		return result;

	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int companyId) {
		ModelAndView result;
		Collection<Printer> printers;
		
		try {
			printers = this.printerService.findActiveByCompanyId(companyId);
			result = new ModelAndView("printer/list");
			result.addObject("printers", printers);
			result.addObject("requestURI", "printer/list");
			
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}


}

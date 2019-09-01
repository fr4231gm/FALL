
package controllers.brotherhood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public RequestBrotherhoodController() {
		super();
	}


	// Service
	@Autowired
	private RequestService	requestService;


	// List of all request that belongs to the logged brotherhood
	@RequestMapping("/list")
	public ModelAndView list() {
		// Initialization
		ModelAndView res;
		Collection<Request> requests;

		// Find all request by brotherhoodId
		requests = this.requestService.findByPrincipal();

		// Assign ModelAndView
		res = new ModelAndView("request/brotherhood/list");
		// Inject the elements in the view
		res.addObject("requests", requests);
		res.addObject("requestURI", "/request/brotherhood/list.do");

		return res;
	}

	// Edit 
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = {
		"requestId"
	})
	public ModelAndView edit(final int requestId) {
		// Declaring instances
		ModelAndView res;
		Request request;

		// Select a request
		request = this.requestService.findOne(requestId);

		// Create the ModelAndView
		res = new ModelAndView("request/brotherhood/edit");
		res.addObject("request", request);

		return res;
	}
	// Save - assign status to pending request
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = {
		"save"
	})
	public ModelAndView save(@Valid final Request request, final BindingResult binding) {
		// Declaring instances
		ModelAndView res;
		//Request requestToSave;
		final List<String> cosa = new ArrayList<String>(request.getProcession().getLocations());
		Collections.sort(cosa);
		if (request.getRowRequest() == null){
			request.setRowRequest(1);
		}
		if (request.getColumnRequest() == null){
			request.setColumnRequest(1);
		}
		final String[] cosa2 = cosa.get(request.getRowRequest() - 1).split("#");
		if (request.getColumnRequest() < cosa2.length)
			if (Integer.valueOf(cosa2[request.getColumnRequest()]) != 0 )
				binding.rejectValue("rowRequest", "request.row.error");
		String messageCode;
		if (request.getRejectReason().isEmpty() && request.getStatus().equals("REJECTED"))
			binding.rejectValue("rejectReason", "request.rejectnull");

		if (binding.hasErrors()) {
			res = new ModelAndView();
			res.addObject("bind", binding);

		} else {

			try {
				this.requestService.save(request);
				res = new ModelAndView("redirect:/request/brotherhood/list.do");
				messageCode = "request.success";

			} catch (final Throwable oops) {
				res = new ModelAndView();
				messageCode = "request.commit.error";
			}

			// Adding elements to the view
			res.addObject("message", messageCode);
		}
		return res;
	}

}

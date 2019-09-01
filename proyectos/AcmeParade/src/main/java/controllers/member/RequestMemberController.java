
package controllers.member;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import services.ParadeService;
import services.RequestService;
import controllers.AbstractController;
import domain.Parade;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public RequestMemberController() {
		super();
	}


	// Service
	@Autowired
	private RequestService	requestService;

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private MemberService	memberService;


	// --------------------------------------------------------------------

	// List of all request that belongs to the logged member
	@RequestMapping("/list")
	public ModelAndView list() {
		// Initialization
		ModelAndView res;
		Collection<Request> requests;

		// Find all request by memberId
		requests = this.requestService.findByPrincipal();

		// Assign ModelAndView
		res = new ModelAndView("request/member/list");
		// Inject the elements in the view
		res.addObject("requests", requests);
		res.addObject("requestURI", "/request/member/list.do");

		return res;
	}

	// Edit 
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = {
		"requestId"
	})
	public ModelAndView edit(final int requestId) {
		// Declaring instances
		final ModelAndView res = new ModelAndView("request/member/edit");
		Collection<Parade> parades = new ArrayList<>();
		Request request = null;

		try {
			// Calling the service for parades available to do a request for
			final int memberId = this.memberService.findByPrincipal().getId();

			parades = this.paradeService.findParadesByMemberId(memberId);

			// Select a request
			request = this.requestService.findOne(requestId);
		} catch (final Throwable oops) {
			res.addObject("requestResult", "fail");
		}
		// Create the ModelAndView
		res.addObject("request", request);
		res.addObject("parades", parades);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = {
		"save"
	})
	public ModelAndView save(@Valid final Request request, final BindingResult binding) {
		// Declaring instances
		ModelAndView res;
		String messageCode;

		// try to assign the status
		try {
			this.requestService.save(request);
			res = new ModelAndView("redirect:/request/member/list.do");
			messageCode = "request.success";
		} catch (final Throwable oops) {
			res = new ModelAndView();
			messageCode = "request.commit.error";
		}

		// Adding elements to the view
		res.addObject("message", messageCode);

		return res;
	}

	//	// Delete - assign status to pending request
	//	@RequestMapping(value = "/d", method = RequestMethod.GET, params = {
	//		"delete"
	//	})
	//	public ModelAndView delete(@Valid final Request request, final BindingResult binding) {
	//		// Declare ModelAndView to redirect when deleting
	//		final ModelAndView res = new ModelAndView("redirect:/request/member/list.do");
	//
	//		// Try to delete the request
	//		try {
	//			this.requestService.delete(request);
	//		} catch (final Throwable oops) {
	//			res.addObject("message", "request.commit.error");
	//		}
	//
	//		return res;
	//
	//	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, params = "requestId")
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
		final Request rr = this.requestService.findOne(requestId);
		try {
			this.requestService.delete(rr);
			result = new ModelAndView("redirect:/request/member/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = {
		"paradeId"
	})
	public ModelAndView create(final int paradeId) {
		// Declaring instances
		ModelAndView res;
		Request r;
		String messageCode;

		// try to assign the status
		try {
			r = this.requestService.create();
			Assert.isTrue(this.paradeService.findParadeRequestablesByMemberId(this.memberService.findByPrincipal().getId()).contains(this.paradeService.findOne(paradeId)));
			r.setParade(this.paradeService.findOne(paradeId));
			this.requestService.save(r);
			res = new ModelAndView("redirect:/request/member/list.do");
			messageCode = "request.create.success";
		} catch (final Throwable oops) {
			res = new ModelAndView("welcome/index");
			messageCode = "request.create.failure";
		}

		// Adding elements to the view
		res.addObject("message", messageCode);

		return res;
	}
}

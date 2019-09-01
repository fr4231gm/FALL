
package controllers.brotherhood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ParadeService;
import services.SegmentService;
import controllers.AbstractController;
import domain.Parade;
import domain.Segment;
import forms.SegmentForm;

@Controller
@RequestMapping("/segment/brotherhood")
public class SegmentBrotherhoodController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SegmentBrotherhoodController() {
		super();
	}


	// Service
	@Autowired
	private SegmentService		segmentService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ParadeService		paradeService;


	// LIST

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int paradeId) {

		ModelAndView res;
		Collection<Segment> segments;
		//Brotherhood principal;
		Parade parade;

		parade = this.paradeService.findOne(paradeId);
		res = new ModelAndView("segment/list");
		//principal = this.brotherhoodService.findByPrincipal();
		segments = parade.getSegments();
		final List<Segment> segmentos = new ArrayList<Segment>(segments);

		if (!segmentos.isEmpty()) {
			final Segment last = segmentos.get(segmentos.size() - 1);
			res.addObject("last", last);
		}

		res.addObject("parade", parade);
		res.addObject("segment", segments);
		res.addObject("requestURI", "/segment/brotherhood/list.do");
		return res;
	}
	//DISPLAY

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"segmentId"
	})
	public ModelAndView display(final int segmentId) {

		ModelAndView res;
		Segment segment;

		segment = this.segmentService.findOne(segmentId);

		res = new ModelAndView("segment/display");
		res.addObject("segment", segment);

		return res;
	}

	//DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int paradeId, final int segmentId) {

		//Declaracion de variables
		ModelAndView res;
		Parade parade;
		Collection<Segment> segments;

		parade = this.paradeService.findOne(paradeId);
		segments = parade.getSegments();

		try {

			this.segmentService.delete(paradeId, segmentId);
			res = new ModelAndView("redirect:/segment/brotherhood/list.do?paradeId=" + paradeId);

		} catch (final Throwable oops) {

			segments = parade.getSegments();

			res = new ModelAndView("parade/list");
			res.addObject("segment", segments);
			res.addObject("paradeId", paradeId);
			res.addObject("requestURI", "/parade/brotherhood/list.do");
		}

		return res;
	}

	//create
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int paradeId) {
		ModelAndView result;

		Segment segment;
		Assert.isTrue(this.paradeService.findOne(paradeId).getBrotherhood().equals(this.brotherhoodService.findByPrincipal()));

		segment = this.segmentService.create();
		result = this.createEditModelAndView(segment);
		final List<Segment> segmentitos = new ArrayList<>(this.paradeService.findOne(paradeId).getSegments());
		if (!segmentitos.isEmpty()) {
			final Segment segmentoUltimo = segmentitos.get(segmentitos.size() - 1);
			final Double latitude = segmentoUltimo.getDestination().getLatitude();
			final Double longitude = segmentoUltimo.getDestination().getLongitude();
			final Date fecha = segmentoUltimo.getEndTime();
			result.addObject("latitude", latitude);
			result.addObject("longitude", longitude);
			result.addObject("fechaHora", fecha.getHours());
			result.addObject("fechaMinutos", fecha.getMinutes());
			result.addObject("vacia", false);
		} else
			result.addObject("vacia", true);

		result.addObject("crear", true);

		result.addObject("paradeId", paradeId);
		return result;

	}

	//Edito
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId, @RequestParam final int segmentId) {
		ModelAndView result;
		Segment segment;
		Assert.isTrue(this.paradeService.findOne(paradeId).getBrotherhood().equals(this.brotherhoodService.findByPrincipal()));
		segment = this.segmentService.findOne(segmentId);
		Assert.notNull(segment);
		result = this.createEditModelAndView(segment);
		result.addObject("crear", false);
		result.addObject("paradeId", paradeId);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int paradeId, @Valid final SegmentForm segmentForm, final BindingResult binding) {
		ModelAndView result;

		final Segment segment = this.segmentService.reconstruct(segmentForm, binding);
		final Parade parade = this.paradeService.findOne(paradeId);
		final Collection<Segment> segments = parade.getSegments();

		if (binding.hasErrors()) {

			result = this.createEditModelAndView(segment);
			result.addObject("fechaHora", segmentForm.getStartTimeHour());
			result.addObject("fechaMinutos", segmentForm.getStartTimeMinutes());
			result.addObject("segmentForm", segmentForm);
			result.addObject("binding", binding);
			if (segments.isEmpty())
				result.addObject("vacia", true);
			else

				result.addObject("vacia", false);

			result.addObject("crear", true);

		} else
			try {
				segments.add(segment);

				parade.setSegments(segments);
				this.segmentService.save(segment);

				this.paradeService.save(parade);

				result = new ModelAndView("redirect:/segment/brotherhood/list.do?paradeId=" + parade.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(segment, "segment.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@RequestParam final int paradeId, @Valid final SegmentForm segmentForm, final BindingResult binding) {
		ModelAndView result;
		final Segment segment = this.segmentService.reconstruct(segmentForm, binding);
		final Collection<Segment> segments = this.paradeService.findOne(paradeId).getSegments();
		final List<Segment> segmentList = new ArrayList<>(segments);
		final int x = segmentList.indexOf(segment);

		if (binding.hasErrors()) {

			result = this.createEditModelAndView(segment);
			result.addObject("segmentForm", segmentForm);
			result.addObject("binding", binding);
			if (segments.isEmpty())
				result.addObject("vacia", true);
			else
				result.addObject("vacia", false);
			result.addObject("crear", false);

		} else
			try {
				if (x + 1 < segmentList.size()) {
					final Segment siguiente = segmentList.get(x + 1);
					siguiente.setOrigin(segment.getDestination());
					siguiente.setStartTime(segment.getEndTime());
					this.segmentService.save(siguiente);
				}

				if (x - 1 >= 0) {
					final Segment anterior = segmentList.get(x - 1);
					anterior.setDestination(segment.getOrigin());
					anterior.setEndTime(segment.getStartTime());
					this.segmentService.save(anterior);
				}
				this.segmentService.save(segment);
				result = new ModelAndView("redirect:/segment/brotherhood/list.do?paradeId=" + paradeId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(segment, "segment.commit.error");
			}
		result.addObject("binding", binding);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Segment segment) {
		ModelAndView result;

		result = this.createEditModelAndView(segment, null);
		return result;
	}

	@SuppressWarnings("deprecation")
	protected ModelAndView createEditModelAndView(final Segment segment, final String messageCode) {

		final SegmentForm segmentForm = new SegmentForm();
		if (!(segment.getId() == 0)) {
			segmentForm.setStartTimeMinutes(segment.getStartTime().getMinutes());
			segmentForm.setStartTimeHour(segment.getStartTime().getHours());
			segmentForm.setEndTimeMinutes(segment.getEndTime().getMinutes());
			segmentForm.setEndTimeHour(segment.getEndTime().getHours());

		}

		segmentForm.setDestination(segment.getDestination());
		segmentForm.setOrigin(segment.getOrigin());
		segmentForm.setVersion(segment.getVersion());
		segmentForm.setId(segment.getId());

		final ModelAndView result = new ModelAndView("segment/brotherhood/edit");
		Assert.notNull(segment);
		result.addObject("segmentForm", segmentForm);
		result.addObject("message", messageCode);

		return result;
	}

}

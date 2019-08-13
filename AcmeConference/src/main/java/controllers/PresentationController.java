package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.PresentationService;
import domain.Presentation;

@Controller
@RequestMapping("/presentation")
public class PresentationController extends AbstractController {

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int conferenceId) {
        ModelAndView res;
        Presentation presentation;

        presentation = this.presentationService.create(conferenceId);
        Assert.notNull(presentation);
        res = this.createEditModelAndView(presentation);

        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Presentation presentation, BindingResult binding) {
        ModelAndView res;

        if (binding.hasErrors()) {
            res = this.createEditModelAndView(presentation);
        } else {
            try {
                this.presentationService.save(presentation);
                res = new ModelAndView("presentation/display");

            } catch (Throwable oops) {
                res = this.createEditModelAndView(presentation, "activity.commit.error");
            }
        }

        return res;
    }

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int presentationId) {
        ModelAndView res;
        Presentation presentation;

        presentation = this.presentationService.findOne(presentationId);

        res = new ModelAndView("presentation/display");
        res.addObject("presentation", presentation);
        res.addObject("schedule", this.activityService.getSchedule(presentation));

        return res;
    }

    // Ancilliary methods

    protected ModelAndView createEditModelAndView(final Presentation presentation) {
        final ModelAndView result = this.createEditModelAndView(presentation, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Presentation presentation, final String messagecode) {
        final ModelAndView result;

        result = new ModelAndView("presentation/edit");

        result.addObject("presentation", presentation);
        result.addObject("message", messagecode);

        return result;
    }
}

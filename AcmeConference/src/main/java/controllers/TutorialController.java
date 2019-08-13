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
import services.TutorialService;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int conferenceId) {
        ModelAndView res;
        Tutorial tutorial;

        tutorial = this.tutorialService.create(conferenceId);
        Assert.notNull(tutorial);
        res = this.createEditModelAndView(tutorial);

        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Tutorial tutorial, BindingResult binding) {
        ModelAndView res;

        if (binding.hasErrors()) {
            res = this.createEditModelAndView(tutorial);
        } else {
            try {
                this.tutorialService.save(tutorial);
                res = new ModelAndView("tutorial/display");

            } catch (Throwable oops) {
                res = this.createEditModelAndView(tutorial, "activity.commit.error");
            }
        }

        return res;
    }

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int tutorialId) {
        ModelAndView res;
        Tutorial tutorial;

        tutorial = this.tutorialService.findOne(tutorialId);

        res = new ModelAndView("tutorial/display");
        res.addObject("tutorial", tutorial);
        res.addObject("schedule", this.activityService.getSchedule(tutorial));

        return res;
    }

    // Ancilliary methods

    protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
        final ModelAndView result = this.createEditModelAndView(tutorial, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String messagecode) {
        final ModelAndView result;

        result = new ModelAndView("tutorial/edit");

        result.addObject("tutorial", tutorial);
        result.addObject("message", messagecode);

        return result;
    }
}

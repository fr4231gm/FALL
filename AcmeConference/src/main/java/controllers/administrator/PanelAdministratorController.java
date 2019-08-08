package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;
import domain.Conference;

import services.PanelService;

@Controller
@RequestMapping("/panel/administrator")
public class PanelAdministratorController {

	@Autowired
	private PanelService panelService;
	
	/*@RequestMapping(value = "/createByConference", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView res;
		Comment comment;

		comment = this.commentService.create();
		Assert.notNull(comment);
		res = this.createEditModelAndView(comment);

		return res;
	}

	@RequestMapping(value = "/createByConference", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, @RequestParam int conferenceId, BindingResult binding) {
		ModelAndView res;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(comment);
		} else {
			try {
				//this.conferenceService.addCommentToConference(conference, comment);
				this.commentService.save(comment);
				res = new ModelAndView("welcome/index");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(comment,
						"comment.commit.error");
			}
		}

		return res;
	}*/
}

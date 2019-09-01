package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExportDataService;

@Controller
@RequestMapping("/actor/export-data")
public class ExportDataController extends AbstractController {

	// Supported Services -----------------------------------------------------
	@Autowired
	private ExportDataService exportDataService;

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	ModelAndView exportData(){
		ModelAndView result;
		String exportedData;
		
		try{
			exportedData = this.exportDataService.exportData();
			result = new ModelAndView("export-data/export");
			result.addObject("exportedData", exportedData);
		}catch(Throwable oops){
			result = new ModelAndView("export-data/export");
			result.addObject("message", "actor.commit.error");

		}
		return result;
	}

	
}

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.ChapterService;
import services.ParadeService;
import domain.Area;
import domain.Chapter;
import domain.Parade;
import forms.ChapterForm;
import forms.ParadeForm;

@Controller
@RequestMapping("/chapter")
public class ChapterController extends AbstractController {

	// Singletons
	@Autowired
	private ChapterService chapterService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ParadeService paradeService;

	// Constructor -------------------------------------

	public ChapterController() {
		super();
	}

	// List------------------------------------------------------
	@RequestMapping(value = "/selectArea", method = RequestMethod.GET)
	ModelAndView selectArea() {
		ModelAndView result;

		Collection<Area> areas;
		Collection<Area> areasB;

		areasB = this.areaService.findAll();
		areas = this.areaService.findWithoutChapter();
		boolean comprobante = false;
		for (final Area a : areasB)
			if (a.getChapter() != null)
				if (a.getChapter()
						.equals(this.chapterService.findByPrincipal()))
					comprobante = true;
		if (comprobante) {

			result = new ModelAndView("chapter/errorArea");
			result.addObject("area", this.areaService.findByChapter());
		} else {

			result = new ModelAndView("area/list");
			result.addObject("areas", areas);
			result.addObject("requestURI", "chapter/selectArea.do");
		}
		return result;
	}

	// Decide------------------------------------------------------
	@RequestMapping(value = "/decide", method = RequestMethod.GET)
	ModelAndView decideParade(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;
		Chapter chapter = this.chapterService.findByPrincipal();
		parade = this.paradeService.findOne(paradeId);
		Assert.isTrue(this.areaService.findAreaByChapterId(chapter.getId()).equals(parade.getBrotherhood().getArea()));
		
		try {
			parade = this.paradeService.findOne(paradeId);
			Assert.notNull(parade);
			result = this.createEditModelAndView(parade);
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;
	}

	// Save decision
	@RequestMapping(value = "/decide", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ParadeForm paradeForm,
			final BindingResult binding) {
		ModelAndView result;

		final Parade parade = this.paradeService.reconstruct(paradeForm,
				binding);
		if (paradeForm.getId() != 0
				&& this.paradeService.findOneToFail(paradeForm.getId()) == null)
			result = new ModelAndView("security/notfind");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(parade);
		else if (paradeForm.getStatus().equals("REJECTED"))
			result = new ModelAndView(
					"redirect:decideRejectReason.do?paradeId="
							+ paradeForm.getId());
		else
			try {
				this.paradeService.save(parade);
				result = new ModelAndView("redirect:manageParades.do");
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		return result;
	}

	// Decide in case of rejection
	@RequestMapping(value = "/decideRejectReason", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final ParadeForm paradeForm,
			final BindingResult binding) {
		ModelAndView result;
		final String reject = paradeForm.getRejectReason();
		final Parade parade = this.paradeService.findOne(paradeForm.getId());
		
		if (paradeForm.getId() != 0
				&& this.paradeService.findOneToFail(paradeForm.getId()) == null) {
			result = new ModelAndView("security/notfind");

		} else {

			try {
				parade.setRejectReason(reject);
				parade.setStatus("REJECTED");
				this.paradeService.save(parade);
				result = new ModelAndView("redirect:manageParades.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("chapter/decideParadeRejected");
				result.addObject("error", "rejecto");
			}
		}
		return result;
	}

	// Decide------------------------------------------------------
	@RequestMapping(value = "/decideRejectReason", method = RequestMethod.GET)
	ModelAndView decideParadeRejectReason(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		try {
			parade = this.paradeService.findOne(paradeId);
			parade.setStatus("REJECTED");
			Assert.notNull(parade);
			result = this.createEditModelAndView(parade);
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;
	}

	// List------------------------------------------------------
	@RequestMapping(value = "/manageParades", method = RequestMethod.GET)
	ModelAndView manageParades() {
		ModelAndView res;

		final Area a = this.areaService.findByChapter();
		final Collection<Parade> parades;
		parades = this.paradeService.findByArea(a);

		res = new ModelAndView("parade/list");
		res.addObject("parades", parades);
		res.addObject("requestURI", "/chapter/manageParades.do");

		return res;
	}

	// Asignar area
	@RequestMapping(value = "/assign", method = RequestMethod.GET, params = "areaId")
	public ModelAndView assign(@RequestParam final int areaId) {

		ModelAndView result;
		final Area a = this.areaService.findOne(areaId);
		final Chapter c = this.chapterService.findByPrincipal();
		a.setChapter(c);
		try {
			this.areaService.save(a);
			result = new ModelAndView("redirect:selectArea.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:selectArea.do");
			result.addObject("message", "area.assign.error");
		}
		return result;
	}

	// Crear un nuevo Chapter
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		ChapterForm chapterForm;

		// Crear un chapterForm e inicializarlo con los terminos y condiciones
		// marcados como no leidos
		chapterForm = new ChapterForm();
		chapterForm.setCheckTerms(false);

		// Construir path para el Tiles
		res = new ModelAndView("chapter/register");
		res.addObject("chapterForm", chapterForm);

		return res;
	}

	// Guardar un nuevo Chapter
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ChapterForm chapterForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Chapter chapter;

		// Crear el objeto Chapter a partir del chapterForm
		chapter = this.chapterService.reconstruct(chapterForm, binding);

		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors())
			res = new ModelAndView("chapter/register");
		// Si el formulario no tiene errores intenta guardarse
		else
			try {
				this.chapterService.save(chapter);
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", chapter.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("chapter/register");
				res.addObject("message", "actor.commit.error");
			}
		return res;
	}

	// Editar chapter existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Chapter chapter;

		chapter = this.chapterService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("chapter/edit");
			result.addObject("chapter", chapter);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(chapter, "actor.commit.error");
		}

		return result;
	}

	// Actualizar chapter existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Chapter chapter, final BindingResult binding) {
		ModelAndView result;
		Chapter toSave;
		SimpleDateFormat formatter;
		String moment;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		toSave = this.chapterService.reconstruct(chapter, binding);

		if (binding.hasErrors())
			result = new ModelAndView("chapter/edit");
		else
			try {
				this.chapterService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("moment", moment);
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	// 14.1 List the chapters that are registered in the system
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	ModelAndView list() {
		ModelAndView result;
		Collection<Chapter> chapters;

		chapters = this.chapterService.findAll();
		result = new ModelAndView("chapter/list");
		result.addObject("chapters", chapters);
		result.addObject("requestURI", "chapter/list.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chapter chapter) {
		final ModelAndView result = this.createEditModelAndView(chapter, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Chapter chapter,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("chapter", chapter);
		result.addObject("message", messagecode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Parade parade) {
		final ModelAndView result = this.createEditModelAndView(parade, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Parade parade,
			final String messagecode) {
		ModelAndView result;
		ParadeForm paradeForm = new ParadeForm();
		paradeForm.setId(parade.getId());
		paradeForm.setRejectReason("");
		if (parade.getStatus().equals("REJECTED"))
			result = new ModelAndView("chapter/decideParadeRejected");
		else {
			paradeForm = this.paradeService.construct(parade);
			result = new ModelAndView("chapter/decideParade");
		}

		result.addObject("paradeForm", paradeForm);
		result.addObject("message", messagecode);
		return result;
	}

}

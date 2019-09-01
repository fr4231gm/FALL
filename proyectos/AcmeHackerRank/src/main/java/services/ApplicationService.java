
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Inheritance;
import javax.transaction.Transactional;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.Hacker;
import domain.Problem;
import forms.ApplicationForm;

@Service
@Transactional
@Inheritance
public class ApplicationService {

	// Managed repository -----------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services ----------------
	@Autowired
	private HackerService			hackerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private Validator				validator;


	// Constructors ------------------------------------

	public ApplicationService() {
		super();
	}

	// Simple CRUDs methods ------------------------

	public Application create(final int positionId) {
		Assert.notNull(positionId);
		Application result;
		Hacker principal;
		final Problem problem;
		final Collection<Problem> problemsByPosition;
		final List<Problem> problemsByPositionList;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Application();
		result.setHacker(principal);
		result.setStatus("PENDING");
		result.setPosition(this.positionService.findOne(positionId));
		// Assign random problem registered to corresponding position
		problemsByPosition = result.getPosition().getProblems();
		problemsByPositionList = new ArrayList<Problem>(problemsByPosition);
		final int randomIndex = RandomUtils.nextInt(problemsByPosition.size());
		problem = problemsByPositionList.get(randomIndex);
		result.setProblem(problem);

		Assert.notNull(result.getPosition());
		Assert.notNull(result.getProblem());

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;
		Actor actor;
		Hacker hacker;
		Company company;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		if (actor instanceof Hacker) {
			hacker = this.hackerService.findByPrincipal();
			Assert.notNull(hacker);
			Assert.isTrue(application.getHacker().equals(hacker));
			Assert.isTrue(application.getStatus().equals("PENDING"));
			// Creating an application
			if (application.getId() == 0)
				application.setCreationMoment(new Date(System.currentTimeMillis() - 1));
			else
				Assert.isTrue(application.getSubmittedMoment() == null);

		} else if (actor instanceof Company) {
			company = this.companyService.findByPrincipal();
			Assert.notNull(company);
			Assert.isTrue(application.getPosition().getCompany().equals(company));
			//Assert.isTrue(application.getStatus().equals("ACCEPTED") || application.getStatus().equals("REJECTED"));
		}

		result = this.applicationRepository.save(application);

		return result;
	}
	public Collection<Application> findAll() {

		Collection<Application> result;

		result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	// Other business methods ---------------------------

	public Application submit(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getId() != 0);

		Application result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(application.getHacker() == principal);

		application.setStatus("SUBMITTED");
		application.setSubmittedMoment(new Date(System.currentTimeMillis() - 1));
		Assert.isTrue(!application.getLinkCode().isEmpty());
		Assert.isTrue(!application.getAnswer().isEmpty());

		result = this.applicationRepository.save(application);

		return result;
	}

	public Application accept(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		Assert.isTrue(application.getId() != 0);

		Application result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		application.setStatus("ACCEPTED");

		result = this.applicationRepository.save(application);

		return result;
	}

	public Application reject(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		Assert.isTrue(application.getId() != 0);

		Application result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		application.setStatus("REJECTED");

		result = this.applicationRepository.save(application);

		return result;
	}

	public Application findOneToFail(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Collection<Application> findApplicationsByHacker(final int hackerId) {
		Collection<Application> result;

		result = this.applicationRepository.findApplicationsByHacker(hackerId);

		return result;
	}

	public Collection<Application> findApplicationsByPosition(final int positionId) {
		Collection<Application> result;

		result = this.applicationRepository.findApplicationsByPosition(positionId);

		return result;
	}

	public Collection<Application> findApplicationsByCompany(final int companyId) {
		Collection<Application> result;

		result = this.applicationRepository.findApplicationsByCompany(companyId);

		return result;
	}

	// Construct: Application --> ApplicationForm
	// Construir un applicationForm a partir de una application
	public ApplicationForm construct(final Application application) {

		// Declarar variables
		final ApplicationForm res = new ApplicationForm();

		// Atributos de Application
		res.setId(application.getId());
		res.setVersion(application.getVersion());
		res.setCreationMoment(application.getCreationMoment());
		res.setAnswer(application.getAnswer());
		res.setLinkCode(application.getLinkCode());
		res.setStatus(application.getStatus());
		res.setSubmittedMoment(application.getSubmittedMoment());
		res.setPosition(application.getPosition());

		return res;
	}

	// Reconstruct: ApplicationForm --> Application
	// Reconstruir una application a partir de un applicationForm
	public Application reconstruct(final ApplicationForm applicationForm, final BindingResult binding) {

		// Declarar variables
		Application application;

		// Crear un nuevo Company
		application = this.create(applicationForm.getPosition().getId());

		// Atributos de Application
		if (applicationForm.getId() != 0) {
			application.setHacker(this.applicationRepository.findOne(applicationForm.getId()).getHacker());
			application.setProblem(this.applicationRepository.findOne(applicationForm.getId()).getProblem());
			application.setCurricula(this.applicationRepository.findOne(applicationForm.getId()).getCurricula());
		} else {
			final Curricula copied = this.curriculaService.attachToApplication(applicationForm.getCurricula());
			application.setCurricula(copied);
		}
		application.setPosition(applicationForm.getPosition());
		application.setId(applicationForm.getId());
		application.setVersion(applicationForm.getVersion());
		application.setCreationMoment(applicationForm.getCreationMoment());
		application.setAnswer(applicationForm.getAnswer());
		application.setLinkCode(applicationForm.getLinkCode());
		application.setStatus(applicationForm.getStatus());
		application.setSubmittedMoment(applicationForm.getSubmittedMoment());

		


		// Validar formulario
		this.validator.validate(applicationForm, binding);

		return application;
	}
	public Collection<Application> findByPosition(final int positionId) {
		Collection<Application> res;

		res = this.applicationRepository.findByPosition(positionId);

		return res;
	}

	public void deleteApplicationWithoutPosition(final int positionId) {

		Company principal;

		// Comprobamos que la persona logueda es un company
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		for (final Application r : this.findByPosition(positionId))
			this.applicationRepository.delete(r);
		Assert.isTrue(this.findByPosition(positionId).isEmpty());
	}
}

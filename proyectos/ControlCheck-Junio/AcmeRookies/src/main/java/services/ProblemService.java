
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import domain.Company;
import domain.Problem;
import forms.ProblemForm;

@Service
@Transactional
public class ProblemService {

	//Managed repository -----------------------------------------------------------------
	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private Validator			validator;
	//Supporting services ----------------------------------------------------------------

	@Autowired
	private CompanyService		CompanyService;


	//Constructors -----------------------------------------------------------------------
	public ProblemService() {
		super();
	}

	//Simple CRUDS methods ---------------------------------------------------------------
	public Problem create() {
		//Comprobamos que la persona logeada sea un Company
		final Company principal = this.CompanyService.findByPrincipal();
		Assert.notNull(principal);

		//New Problem
		final Problem res = new Problem();
		res.setCompany(principal);
		res.setIsDraft(true);
		return res;
	}

	public Problem save(final Problem problem) {
		//Comprobamos que la persona logeada sea un Company
		final Company principal = this.CompanyService.findByPrincipal();
		Assert.notNull(principal);

		Problem res = null;
		if (problem.getId() == 0)
			res = this.problemRepository.save(problem);
		else {
			Assert.isTrue(problem.getCompany().getId() == principal.getId());
			res = this.problemRepository.save(problem);
		}

		return res;
	}

	public void delete(final Problem problem) {
		//Comprobamos que la persona logeada sea un Company
		final Company principal = this.CompanyService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(problem);

		//Comprobamos que el problem pertenece a la company
		Assert.isTrue(problem.getCompany().getId() == principal.getId());
		this.problemRepository.delete(problem);

	}

	public Problem findOne(final int problemId) {
		Problem res;
		res = this.problemRepository.findOne(problemId);
		Assert.notNull(res);
		return res;
	}

	public Problem findOneToFail(final int problemId) {
		Problem res;
		res = this.problemRepository.findOne(problemId);
		return res;
	}

	public Collection<Problem> findAll() {
		Collection<Problem> res;
		res = this.problemRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	//Other business methods -------------------------------------------------------------

	public Collection<Problem> findProblemsByCompanyId(final int companyId) {
		Collection<Problem> res;
		res = this.problemRepository.findProblemsByCompanyId(companyId);
		Assert.notNull(res);
		return res;
	}

	public Problem reconstruct(final ProblemForm form, final BindingResult binding) {
		Problem res;

		//Creating a new Float
		if (form.getId() == 0)
			res = this.create();
		else {
			res = new Problem();
			res.setCompany(this.findOne(form.getId()).getCompany());
		}

		//Problem Atributes.
		res.setId(form.getId());
		res.setTitle(form.getTitle());
		res.setStatement(form.getStatement());
		res.setHint(form.getHint());
		res.setAttachments(form.getAttachments());
		res.setIsDraft(form.getIsDraft());
		res.setVersion(form.getVersion());

		//Validating the form
		this.validator.validate(form, binding);

		return res;
	}

	public ProblemForm construct(final Problem problem) {

		final ProblemForm res = new ProblemForm();

		res.setId(problem.getId());
		res.setVersion(problem.getVersion());
		res.setTitle(problem.getTitle());
		res.setStatement(problem.getStatement());
		res.setHint(problem.getHint());
		res.setAttachments(problem.getAttachments());
		res.setIsDraft(problem.getIsDraft());
		return res;

	}

	public void flush() {
		this.problemRepository.flush();
	}

	public Collection<Problem> findProblemsByCompanyIdNoDraft(final int id) {
		Collection<Problem> res;
		res = this.problemRepository.findProblemsByCompanyIdNoDraft(id);
		Assert.notNull(res);
		return res;
	}

	public void deleteByUserDropOut(Problem problem) {
		this.problemRepository.delete(problem);
		
	}
}

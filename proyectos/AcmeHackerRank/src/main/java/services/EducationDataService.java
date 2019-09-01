
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationDataRepository;
import domain.Curricula;
import domain.Hacker;
import domain.EducationData;
import forms.EducationDataForm;

@Service
@Transactional
public class EducationDataService {

	// Managed Repository
	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Supporting services
	@Autowired
	private HackerService				hackerService;

	@Autowired
	private CurriculaService			curriculaService;
	
	@Autowired
	private Validator				validator;


	// Constructors

	public EducationDataService() {
		super();
	}

	// Simple CRUD methods

	public EducationData create() {
		EducationData result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);

		result = new EducationData();
		Assert.notNull(result);

		return result;
	}


	public EducationData save(final EducationData educationData, int curriculaId) {
		EducationData result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.curriculaService.findByPrincipal());

		result = this.educationDataRepository.save(educationData);
		Assert.notNull(result);

		if (educationData.getId() == 0) {
			final Collection<EducationData> educationDatas = new ArrayList<EducationData>(this.curriculaService.findOne(curriculaId).getEducationData());
			educationDatas.add(result);
			this.curriculaService.findOne(curriculaId).setEducationData(educationDatas);
		} else {
			Assert.isNull(this.curriculaService.findCopiedById(this.curriculaService.findByEducationDataId(educationData.getId()).getId()));
		}

		return result;

	}

	public void delete(final EducationData educationData) {
		Hacker principal;

		Assert.notNull(educationData);
		Assert.isTrue(educationData.getId() != 0);

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		
		Curricula c = this.curriculaService.findByEducationDataId(educationData.getId());
		Assert.notNull(c);
		
		final Collection<EducationData> listEducationData = c.getEducationData();
		this.educationDataRepository.delete(educationData);
		listEducationData.remove(educationData);

		c.setEducationData(listEducationData);

	}

	public EducationData findOne(final int id) {
		EducationData result;
		result = this.educationDataRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.educationDataRepository.flush();
	}

	public EducationData findOneToEdit(final int educationDataId) {
		EducationData result;
		result = this.educationDataRepository.findOne(educationDataId);
		Curricula c = this.curriculaService.findByEducationDataId(educationDataId);
		Assert.notNull(c);
		Assert.isTrue(c.getEducationData().contains(result));
		return result;
	}
	
	public EducationDataForm contruct(int id){
		EducationDataForm res = new EducationDataForm();
		EducationData aux = this.findOne(id);
		res.setDegree(aux.getDegree());
		res.setEndDate(aux.getEndDate());
		res.setInstitution(aux.getInstitution());
		res.setMark(aux.getMark());
		res.setStartDate(aux.getStartDate());
		res.setId(id);
		res.setVersion(aux.getVersion());
		res.setCurricula(this.curriculaService.findByEducationDataId(id));
		return res;
	}
	
	public EducationData reconstruct(EducationDataForm aux, final BindingResult binding){
		EducationData res = new EducationData();
		res.setDegree(aux.getDegree());
		res.setEndDate(aux.getEndDate());
		res.setInstitution(aux.getInstitution());
		res.setMark(aux.getMark());
		res.setStartDate(aux.getStartDate());
		res.setId(aux.getId());
		res.setVersion(aux.getVersion());
		this.validator.validate(aux, binding);
		return res;
	}
	
	public void delete(int id) {
		Hacker principal;
		EducationData educationData = this.findOne(id);
		
		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(educationData);
		
		Curricula c = this.curriculaService.findByEducationDataId(educationData.getId());
		Assert.notNull(c);
		
		final Collection<EducationData> listEducationData = c.getEducationData();
		this.educationDataRepository.delete(educationData);
		listEducationData.remove(educationData);
		c.setEducationData(listEducationData);
	}

}

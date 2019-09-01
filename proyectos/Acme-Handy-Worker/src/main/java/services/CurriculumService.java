
package services;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.HandyWorker;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services
	@Autowired
	private HandyWorkerService			handyWorkerService;

	// Constructors

	public CurriculumService() {
		super();
	}

	// Additional functions
	private String generateTicker() {
		final Date fecha = new Date();
		final String res = fecha.toString() + "-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		return res;
	}

	// Simple CRUD methods
	public Curriculum findOne(final int curriculumId) {
		Curriculum result;
		result = this.curriculumRepository.findOne(curriculumId);
		return result;
	}
	public Curriculum create() {
		Curriculum result;
		HandyWorker principal;

		PersonalRecord personalRecord;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isNull(this.findCurriculumByPrincipal());
		result = new Curriculum();
		result.setHandyworker(principal);
		result.setTicker(this.generateTicker());
		personalRecord = new PersonalRecord();
		personalRecord.setFullName(principal.getSurname());
		result.setPersonalRecord(personalRecord);
		result.setEducationRecords(new ArrayList<EducationRecord>());
		result.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		result.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		result.setEndorserRecords(new ArrayList<EndorserRecord>());

		return result;

	}
	public Curriculum save(final Curriculum curriculum) {
		HandyWorker principal;
		Curriculum result;
		Assert.notNull(curriculum);
		principal = this.handyWorkerService.findByPrincipal();
		Assert.isTrue(curriculum.getHandyworker() == principal);
		result = this.curriculumRepository.save(curriculum);
		result.getHandyworker().setCurriculum(result);

		return result;
	}

	// Other business methods
	public Curriculum findCurriculumByPrincipal() {
		Curriculum result;
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.curriculumRepository.findCurriculumByPrincipal(principal.getId());
		Assert.isTrue(principal.getCurriculum() == result);
		return result;

	}

	public void flush() {
		this.curriculumRepository.flush();
	}

}

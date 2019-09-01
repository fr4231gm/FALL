
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// Managed Repository
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private ActorService					actorService;

	// Constructors

	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<EducationRecord> findByPrincipal() {
		Collection<EducationRecord> result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getEducationRecords();

		Assert.notNull(result);

		return result;
	}

	public EducationRecord create() {
		EducationRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new EducationRecord();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		EducationRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());
		if (educationRecord.getEndStudying() != null){
		Assert.isTrue(educationRecord.getEndStudying().after(educationRecord.getStartStudying()));
		}
		Collection<EducationRecord> educationRecords = new ArrayList<EducationRecord>(principal.getCurriculum().getEducationRecords());
		result = this.educationRecordRepository.save(educationRecord);
		Assert.notNull(result);
		if (educationRecord.getId() == 0) {
			educationRecords.add(result);
			principal.getCurriculum().setEducationRecords(educationRecords);
		}
		if(this.actorService.checkSpam(result.getComments())){
			principal.setIsSuspicious(true);
			this.handyWorkerService.saveHandyWorker(principal);
		}
		if(this.actorService.checkSpam(result.getDiplomaTitle())){
			principal.setIsSuspicious(true);
			this.handyWorkerService.saveHandyWorker(principal);
		}
		if(this.actorService.checkSpam(result.getInstitution())){
			principal.setIsSuspicious(true);
			this.handyWorkerService.saveHandyWorker(principal);
		}
		return result;

	}

	public void delete(final EducationRecord educationRecord) {
		HandyWorker principal;

		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Collection<EducationRecord> listEducationRecord = principal.getCurriculum().getEducationRecords();

		this.educationRecordRepository.delete(educationRecord);
		listEducationRecord.remove(educationRecord);

		principal.getCurriculum().setEducationRecords(listEducationRecord);

	}

	public EducationRecord findOne(final int id) {
		EducationRecord result;
		result = this.educationRecordRepository.findOne(id);
		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;
		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.educationRecordRepository.flush();
	}

	// Other business methods

}

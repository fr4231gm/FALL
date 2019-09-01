
package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.HandyWorker;



@Service
@Transactional
public class PersonalRecordService {

	// Managed Repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	

	
	// Constructors

	public PersonalRecordService() {
		super();
	}

	
	// Simple CRUD methods

	
	public PersonalRecord findByPrincipal() {
		PersonalRecord result;
		HandyWorker principal;
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		result = principal.getCurriculum().getPersonalRecord();
	
		Assert.notNull(result);
		
		return result;
	}
	
	
	public PersonalRecord create() {
		PersonalRecord result;
		HandyWorker principal;
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new PersonalRecord();
		Assert.notNull(result);

		return result;
	}
	
	
	public PersonalRecord save(PersonalRecord personalRecord) {
		PersonalRecord result;
		HandyWorker principal;
		
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		if(this.actorService.checkSpam(personalRecord.getFullName())){
			principal.setIsSuspicious(true);
			this.handyWorkerService.saveHandyWorker(principal);
		}
		
		result = this.personalRecordRepository.save(personalRecord);
		Assert.notNull(result);
		
		return result;
	
	}
	
	
	public void delete(PersonalRecord personalRecord) {
		HandyWorker principal;
		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Curriculum c = principal.getCurriculum();
		c.setPersonalRecord(null);
		this.curriculumService.save(c);
		this.personalRecordRepository.delete(personalRecord);
	}

	public PersonalRecord findOne(int id) {
		return this.personalRecordRepository.findOne(id);
	}
	
 
	
}

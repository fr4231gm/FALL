
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	// Managed Repository
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	@Autowired
	private HandyWorkerService			handyWokerService;
	
	@Autowired
	private ActorService				actorService;


	// Constructors
	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<EndorserRecord> findByPrincipal() {
		Collection<EndorserRecord> result;
		HandyWorker principal;

		principal = this.handyWokerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getEndorserRecords();

		Assert.notNull(result);

		return result;
	}

	public EndorserRecord create() {
		EndorserRecord result;
		HandyWorker principal;
		principal = this.handyWokerService.findByPrincipal();
		Assert.notNull(principal);
		result = new EndorserRecord();
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		EndorserRecord result;
		HandyWorker principal;
		principal = this.handyWokerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());
		Collection<EndorserRecord> listEndorserRecord = principal.getCurriculum().getEndorserRecords();
		result = this.endorserRecordRepository.save(endorserRecord);
		Assert.notNull(result);
		if (endorserRecord.getId() == 0) {
			listEndorserRecord.add(result);
			principal.getCurriculum().setEndorserRecords(listEndorserRecord);
		}
		if(this.actorService.checkSpam(endorserRecord.getComments())){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		if(this.actorService.checkSpam(endorserRecord.getFullName())){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		return result;

	}

	public void delete(final EndorserRecord endorserRecord) {
		HandyWorker principal;
		
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		principal = this.handyWokerService.findByPrincipal();
		Assert.notNull(principal);

		Collection<EndorserRecord> listEndorserRecord = principal.getCurriculum().getEndorserRecords();

		this.endorserRecordRepository.delete(endorserRecord);
		listEndorserRecord.remove(endorserRecord);

		principal.getCurriculum().setEndorserRecords(listEndorserRecord);

	}

	public EndorserRecord findOne(final int id) {
		EndorserRecord result;
		result = this.endorserRecordRepository.findOne(id);
		return result;
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;
		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.endorserRecordRepository.flush();
	}

	// Other business methods

}

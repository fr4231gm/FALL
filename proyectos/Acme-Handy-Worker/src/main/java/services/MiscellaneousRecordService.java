
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;
import domain.HandyWorker;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services

	@Autowired
	private HandyWorkerService					handyWorkerService;
	
	@Autowired
	private ActorService					actorService;


	// Constructors

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<MiscellaneousRecord> findByPrincipal() {
		Collection<MiscellaneousRecord> result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getMiscellaneousRecords();

		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord create() {
		MiscellaneousRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new MiscellaneousRecord();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		MiscellaneousRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>(principal.getCurriculum().getMiscellaneousRecords());
		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		Assert.notNull(result);
		if (miscellaneousRecord.getId() == 0) {
			miscellaneousRecords.add(result);
			principal.getCurriculum().setMiscellaneousRecords(miscellaneousRecords);
		}
		if(this.actorService.checkSpam(miscellaneousRecord.getComments())){
			principal.setIsSuspicious(true);
			this.handyWorkerService.saveHandyWorker(principal);
		}
		if(this.actorService.checkSpam(miscellaneousRecord.getTitle())){
			principal.setIsSuspicious(true);
			this.handyWorkerService.saveHandyWorker(principal);
		}
		return result;

	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		HandyWorker principal;

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Collection<MiscellaneousRecord> listMiscellaneousRecord = principal.getCurriculum().getMiscellaneousRecords();
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		listMiscellaneousRecord.remove(miscellaneousRecord);

		principal.getCurriculum().setMiscellaneousRecords(listMiscellaneousRecord);

	}

	public MiscellaneousRecord findOne(final int id) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(id);
		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}

	// Other business methods

}

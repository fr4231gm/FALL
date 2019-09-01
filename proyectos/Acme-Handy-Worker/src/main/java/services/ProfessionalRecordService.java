
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;
import domain.HandyWorker;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed Repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services

	@Autowired
	private HandyWorkerService					handyWorkerService;
	
	@Autowired
	private ActorService					actorService;


	// Constructors

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<ProfessionalRecord> findByPrincipal() {
		Collection<ProfessionalRecord> result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getProfessionalRecords();

		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord create() {
		ProfessionalRecord result;
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result = new ProfessionalRecord();
		Assert.notNull(result);
		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		ProfessionalRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		if(this.actorService.checkSpam(professionalRecord.getComments())){
			principal.setIsSuspicious(true);
		}
		if(this.actorService.checkSpam(professionalRecord.getCompanyName())){
			principal.setIsSuspicious(true);
		}
		if(this.actorService.checkSpam(professionalRecord.getRole())){
			principal.setIsSuspicious(true);
		}
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		if (professionalRecord.getEndWorking() != null){
		Assert.isTrue(professionalRecord.getEndWorking().after(professionalRecord.getStartWorking()));
		}
		Collection<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>(principal.getCurriculum().getProfessionalRecords());
		result = this.professionalRecordRepository.save(professionalRecord);
		Assert.notNull(result);
		if (professionalRecord.getId() == 0) {
			professionalRecords.add(result);
			principal.getCurriculum().setProfessionalRecords(professionalRecords);
		}
		return result;

	}

	public void delete(final ProfessionalRecord professionalRecord) {
		HandyWorker principal;

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Collection<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>(principal.getCurriculum().getProfessionalRecords());

		this.professionalRecordRepository.delete(professionalRecord);

		professionalRecords.remove(professionalRecord);

		principal.getCurriculum().setProfessionalRecords(professionalRecords);

	}

	public ProfessionalRecord findOne(final int id) {
		ProfessionalRecord result;
		result = this.professionalRecordRepository.findOne(id);
		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.professionalRecordRepository.flush();
	}

	// Other business methods

}

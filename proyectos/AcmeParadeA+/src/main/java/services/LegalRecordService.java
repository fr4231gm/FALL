
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import domain.LegalRecord;
import domain.Brotherhood;

@Service
@Transactional
public class LegalRecordService {

	// Managed Repository
	@Autowired
	private LegalRecordRepository	legalRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService					brotherhoodService;
	
	@Autowired
	private HistoryService					historyService;

	// Constructors

	public LegalRecordService() {
		super();
	}

	// Simple CRUD methods
	
	public LegalRecord create() {
		LegalRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new LegalRecord();
		Assert.notNull(result);

		return result;
	}

	public Collection<LegalRecord> findByPrincipal() {
		Collection<LegalRecord> result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = this.historyService.findByPrincipal().getLegalRecord();

		Assert.notNull(result);

		return result;
	}

	public LegalRecord save(final LegalRecord legalRecord) {
		LegalRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.historyService.findByPrincipal());

		Collection<LegalRecord> legalRecords = new ArrayList<LegalRecord>(this.historyService.findByPrincipal().getLegalRecord());
		result = this.legalRecordRepository.save(legalRecord);
		Assert.notNull(result);
		
		if (legalRecord.getId() == 0) {
			legalRecords.add(result);
			this.historyService.findByPrincipal().setLegalRecord(legalRecords);
		}

		return result;

	}

	public void delete(final LegalRecord legalRecord) {
		Brotherhood principal;

		Assert.notNull(legalRecord);
		Assert.isTrue(legalRecord.getId() != 0);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Collection<LegalRecord> listLegalRecord = this.historyService.findByPrincipal().getLegalRecord();
		this.legalRecordRepository.delete(legalRecord);
		listLegalRecord.remove(legalRecord);

		this.historyService.findByPrincipal().setLegalRecord(listLegalRecord);

	}

	public LegalRecord findOne(final int id) {
		LegalRecord result;
		result = this.legalRecordRepository.findOne(id);
		return result;
	}


	public void flush() {
		this.legalRecordRepository.flush();
	}

	public LegalRecord findOneToEdit(int legalRecordId) {
		LegalRecord result;
		result = this.legalRecordRepository.findOne(legalRecordId);
		Assert.isTrue(this.historyService.findByPrincipal().getLegalRecord().contains(result));
		return result;
	}

}

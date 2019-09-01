
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Brotherhood;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private HistoryService					historyService;


	// Constructors

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public MiscellaneousRecord create() {
		MiscellaneousRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new MiscellaneousRecord();
		Assert.notNull(result);

		return result;
	}

	public Collection<MiscellaneousRecord> findByPrincipal() {
		Collection<MiscellaneousRecord> result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = this.historyService.findByPrincipal().getMiscellaneousRecord();

		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		MiscellaneousRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.historyService.findByPrincipal());

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		Assert.notNull(result);

		if (miscellaneousRecord.getId() == 0) {
			final Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>(this.historyService.findByPrincipal().getMiscellaneousRecord());
			miscellaneousRecords.add(result);
			this.historyService.findByPrincipal().setMiscellaneousRecord(miscellaneousRecords);
		}

		return result;

	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Brotherhood principal;

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		final Collection<MiscellaneousRecord> listMiscellaneousRecord = this.historyService.findByPrincipal().getMiscellaneousRecord();
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		listMiscellaneousRecord.remove(miscellaneousRecord);

		this.historyService.findByPrincipal().setMiscellaneousRecord(listMiscellaneousRecord);

	}

	public MiscellaneousRecord findOne(final int id) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}

	public MiscellaneousRecord findOneToEdit(final int miscellaneousRecordId) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.isTrue(this.historyService.findByPrincipal().getMiscellaneousRecord().contains(result));
		return result;
	}

}


package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import domain.Brotherhood;
import domain.PeriodRecord;

@Service
@Transactional
public class PeriodRecordService {

	// Managed Repository
	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private HistoryService			historyService;


	// Constructors

	public PeriodRecordService() {
		super();
	}

	// Simple CRUD methods

	public PeriodRecord create() {
		PeriodRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new PeriodRecord();
		Assert.notNull(result);

		return result;
	}

	public Collection<PeriodRecord> findByPrincipal() {
		Collection<PeriodRecord> result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = this.historyService.findByPrincipal().getPeriodRecord();

		Assert.notNull(result);

		return result;
	}

	public PeriodRecord save(final PeriodRecord periodRecord) {
		PeriodRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.historyService.findByPrincipal());

		Assert.isTrue(!this.checkPictures(periodRecord.getPictures()));
		if (periodRecord.getEndYear() != null)
			Assert.isTrue(periodRecord.getEndYear() >= periodRecord.getStartYear());
		final Calendar cal = Calendar.getInstance();
		cal.setTime(principal.getEstablishmentDate());
		Assert.isTrue(periodRecord.getStartYear() >= cal.get(Calendar.YEAR));

		final Collection<PeriodRecord> periodRecords = new ArrayList<PeriodRecord>(this.historyService.findByPrincipal().getPeriodRecord());
		result = this.periodRecordRepository.save(periodRecord);
		Assert.notNull(result);

		if (periodRecord.getId() == 0) {
			periodRecords.add(result);
			this.historyService.findByPrincipal().setPeriodRecord(periodRecords);
		}

		return result;

	}

	public void delete(final PeriodRecord periodRecord) {
		Brotherhood principal;

		Assert.notNull(periodRecord);
		Assert.isTrue(periodRecord.getId() != 0);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		final Collection<PeriodRecord> listPeriodRecord = this.historyService.findByPrincipal().getPeriodRecord();
		this.periodRecordRepository.delete(periodRecord);
		listPeriodRecord.remove(periodRecord);

		this.historyService.findByPrincipal().setPeriodRecord(listPeriodRecord);

	}

	public PeriodRecord findOne(final int id) {
		PeriodRecord result;
		result = this.periodRecordRepository.findOne(id);
		return result;
	}

	// Return true if at least one of the pictures is not an URL
	public boolean checkPictures(final String pictures) {
		boolean res = false;
		final String[] aux = pictures.split(", ");
		for (int i = 0; i < aux.length; i++)
			if (!(aux[i].matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) && aux[i].length() > 0)
				res = true;
		return res;
	}

	public void flush() {
		this.periodRecordRepository.flush();
	}

	public PeriodRecord findOneToEdit(final int periodRecordId) {
		PeriodRecord result;
		result = this.periodRecordRepository.findOne(periodRecordId);
		Assert.isTrue(this.historyService.findByPrincipal().getPeriodRecord().contains(result));
		return result;
	}

}

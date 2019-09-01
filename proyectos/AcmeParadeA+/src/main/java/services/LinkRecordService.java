
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LinkRecordRepository;
import domain.Brotherhood;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	// Managed Repository
	@Autowired
	private LinkRecordRepository	linkRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private HistoryService			historyService;


	// Constructors

	public LinkRecordService() {
		super();
	}

	// Simple CRUD methods

	public LinkRecord create() {
		LinkRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new LinkRecord();
		Assert.notNull(result);

		return result;
	}

	public Collection<LinkRecord> findByPrincipal() {
		Collection<LinkRecord> result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = this.historyService.findByPrincipal().getLinkRecord();

		Assert.notNull(result);

		return result;
	}

	public LinkRecord save(final LinkRecord linkRecord) {
		LinkRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.historyService.findByPrincipal());

		//No se puede "linkar" a si mismo
		Assert.isTrue(linkRecord.getBrotherhood().getId() != principal.getId());

		final Collection<LinkRecord> linkRecords = new ArrayList<LinkRecord>(this.historyService.findByPrincipal().getLinkRecord());
		result = this.linkRecordRepository.save(linkRecord);
		Assert.notNull(result);

		if (linkRecord.getId() == 0) {
			linkRecords.add(result);
			this.historyService.findByPrincipal().setLinkRecord(linkRecords);
		}

		return result;

	}

	public void delete(final LinkRecord linkRecord) {
		Brotherhood principal;

		Assert.notNull(linkRecord);
		Assert.isTrue(linkRecord.getId() != 0);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		final Collection<LinkRecord> listLinkRecord = this.historyService.findByPrincipal().getLinkRecord();
		this.linkRecordRepository.delete(linkRecord);
		listLinkRecord.remove(linkRecord);

		this.historyService.findByPrincipal().setLinkRecord(listLinkRecord);

	}

	public LinkRecord findOne(final int id) {
		LinkRecord result;
		result = this.linkRecordRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.linkRecordRepository.flush();
	}

	public LinkRecord findOneToEdit(final int linkRecordId) {
		LinkRecord result;
		result = this.linkRecordRepository.findOne(linkRecordId);
		Assert.isTrue(this.historyService.findByPrincipal().getLinkRecord().contains(result));
		return result;
	}

}

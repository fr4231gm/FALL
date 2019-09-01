
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import domain.History;
import domain.InceptionRecord;
import domain.Brotherhood;

@Service
@Transactional
public class InceptionRecordService {

	// Managed Repository
	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService					brotherhoodService;
	
	@Autowired
	private HistoryService				historyService;



	// Constructors

	public InceptionRecordService() {
		super();
	}

	// Simple CRUD methods
	
	public InceptionRecord create() {
		InceptionRecord result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new InceptionRecord();
		Assert.notNull(result);

		return result;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		InceptionRecord result;
		Brotherhood principal;
		Boolean isNew = false;
		if(inceptionRecord.getId() == 0){
			isNew = true;
		}

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(!checkPictures(inceptionRecord.getPictures()));

		result = this.inceptionRecordRepository.save(inceptionRecord);
		Assert.notNull(result);
		if(isNew){
			History h = this.historyService.create();
			h.setInceptionRecord(result);
			this.historyService.save(h);
		}
	
		return result;

	}


	public InceptionRecord findOne(final int id) {
		InceptionRecord result;
		result = this.inceptionRecordRepository.findOne(id);
		return result;
	}

	
	// Return true if at least one of the pictures is not an URL
	public boolean checkPictures(String pictures) {
		boolean res = false;
		String[] aux = pictures.split(", ");
		for (int i = 0; i < aux.length; i++) {
			if (!(aux[i]
					.matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))
					&& aux[i].length() > 0) {
				res = true;
			}
		}
		return res;
	}

	public void flush() {
		this.inceptionRecordRepository.flush();
	}

}

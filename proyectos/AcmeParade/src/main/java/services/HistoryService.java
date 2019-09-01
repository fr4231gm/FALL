package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.Brotherhood;
import domain.History;

@Service
@Transactional
public class HistoryService {
	// Managed repository -----------
	@Autowired
	private HistoryRepository historyRepository;

	// Supporting services ----------

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Constructors -----------------

	public HistoryService() {
		super();
	}

	// Simple CRUDs methods ----------
	public History create() {
		// Comprobamos que la persona logueada es una fraternidad y no tiene ya
		// una history
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isNull(this.findByPrincipal());
		
		

		// Nuevo History
		final History res = new History();

		res.setBrotherhood(principal);
		return res;
	}

	public History save(final History history) {
		// Comprobamos que la persona logueada es una brotherhood
		Brotherhood principal;
		History res;
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		// Inception record is mandatory
		Assert.notNull(history.getInceptionRecord());

		if (history.getId() == 0) {
			res = this.historyRepository.save(history);

		} else {
			// Comprobamos que le pertenece la history que se está editando
			Assert.isTrue(this.findByPrincipal().getId() == history.getId());

			res = this.historyRepository.save(history);
		}

		return res;

	}

	public void delete(final History history) {
		Brotherhood principal;
		History result;
		Assert.isTrue(history.getId() != 0);
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		result = this.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(history.equals(result));
		this.historyRepository.delete(history);
	}

	public History findOne(final int historyId) {
		History res;
		res = this.historyRepository.findOne(historyId);
		return res;
	}
	
	public History findByPrincipal() {
		Brotherhood b = this.brotherhoodService.findByPrincipal();
		History res;
		res = this.historyRepository.findByBrotherhoodId(b.getId());
		return res;
	}
	
	public void flush() {
		this.historyRepository.flush();
	}

	public History findByInceptionId(int id) {
		History h = this.historyRepository.findByInceptionId(id);
		return h;
	}
	
	public History findByBrotherhoodId(int id){
		return this.historyRepository.findByBrotherhoodId(id);
	}

	public History findOneToEdit(int historyId) {
		Brotherhood principal=this.brotherhoodService.findByPrincipal();
		History res;
		res = this.historyRepository.findOne(historyId);
		Assert.isTrue(res.getBrotherhood() == principal);
		return res;
	}
	
}

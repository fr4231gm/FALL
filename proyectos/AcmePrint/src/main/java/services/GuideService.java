package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GuideRepository;
import domain.Designer;
import domain.Guide;

@Service
@Transactional
public class GuideService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GuideRepository guideRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private DesignerService designerService;
	
	
	// Simple CRUD methods ----------------------------------------------------
	public Guide create() {
		
		final Designer principal = this.designerService.findByPrincipal();
		Assert.notNull(principal);

		//New Guide
		Guide res = new Guide();
		
		return res;
	}

	public Guide save(final Guide guide) {
		//Comprobamos que la persona logeada sea un Designer
		final Designer principal = this.designerService.findByPrincipal();
		Assert.notNull(principal);

		Guide res = null;
		if (guide.getId() == 0)
			res = this.guideRepository.save(guide);
		else {
			res = this.guideRepository.save(guide);
		}

		return res;
	}

	public void delete(final Guide guide) {
		//Comprobamos que la persona logeada sea un Designer
		final Designer principal = this.designerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(guide);

		//Comprobamos que el guide pertenece a la designer
		this.guideRepository.delete(guide);

	}

	public Guide findOne(final int guideId) {
		Guide res;
		res = this.guideRepository.findOne(guideId);
		Assert.notNull(res);
		return res;
	}

	public Guide findOneToFail(final int guideId) {
		Guide res;
		res = this.guideRepository.findOne(guideId);
		return res;
	}

	public Collection<Guide> findAll() {
		Collection<Guide> res;
		res = this.guideRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	
	
	// Other methods ----------------------------------------------------------
	
	//FLUSH
	public void flush(){
		this.guideRepository.flush();
	}
}

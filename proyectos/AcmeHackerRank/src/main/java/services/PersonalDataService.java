
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Curricula;
import domain.PersonalData;
import domain.Hacker;

@Service
@Transactional
public class PersonalDataService {

	// Managed Repository
	@Autowired
	private PersonalDataRepository	personalDataRepository;

	// Supporting services
	@Autowired
	private HackerService					hackerService;
	
	@Autowired
	private CurriculaService				curriculaService;



	// Constructors

	public PersonalDataService() {
		super();
	}

	// Simple CRUD methods
	
	public PersonalData create() {
		PersonalData result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);

		result = new PersonalData();
		Assert.notNull(result);

		return result;
	}

	public PersonalData save(final PersonalData personalData) {
		PersonalData result;
		Hacker principal;
		Boolean isNew = false;
		if(personalData.getId() == 0){
			isNew = true;
		}

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		

		result = this.personalDataRepository.save(personalData);
		Assert.notNull(result);
		if(isNew){
			Curricula h = this.curriculaService.create();
			h.setPersonalData(result);
			this.curriculaService.save(h);
		} else {
			Assert.isNull(this.curriculaService.findCopiedById(this.curriculaService.findByPersonalDataId(personalData.getId()).getId()));
		
		}
	
		return result;

	}


	public PersonalData findOne(final int id) {
		PersonalData result;
		result = this.personalDataRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.personalDataRepository.flush();
	}

}

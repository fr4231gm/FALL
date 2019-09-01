
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousDataRepository;
import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed Repository
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Supporting services
	@Autowired
	private HackerService				hackerService;

	@Autowired
	private CurriculaService					curriculaService;
	
	@Autowired
	private Validator				validator;


	// Constructors

	public MiscellaneousDataService() {
		super();
	}

	// Simple CRUD methods

	public MiscellaneousData create() {
		MiscellaneousData result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);

		result = new MiscellaneousData();
		Assert.notNull(result);

		return result;
	}


	public MiscellaneousData save(final MiscellaneousData miscellaneousData, int curriculaId) {
		MiscellaneousData result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.curriculaService.findByPrincipal());

		result = this.miscellaneousDataRepository.save(miscellaneousData);
		Assert.notNull(result);

		if (miscellaneousData.getId() == 0) {
			final Collection<MiscellaneousData> miscellaneousDatas = new ArrayList<MiscellaneousData>(this.curriculaService.findOne(curriculaId).getMiscellaneousData());
			miscellaneousDatas.add(result);
			this.curriculaService.findOne(curriculaId).setMiscellaneousData(miscellaneousDatas);
		} else {
			Assert.isNull(this.curriculaService.findCopiedById(this.curriculaService.findByMiscellaneousDataId(miscellaneousData.getId()).getId()));
		}

		return result;

	}

	public void delete(final MiscellaneousData miscellaneousData) {
		Hacker principal;

		Assert.notNull(miscellaneousData);
		Assert.isTrue(miscellaneousData.getId() != 0);

		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		
		Curricula c = this.curriculaService.findByMiscellaneousDataId(miscellaneousData.getId());
		Assert.notNull(c);
		
		final Collection<MiscellaneousData> listMiscellaneousData = c.getMiscellaneousData();
		this.miscellaneousDataRepository.delete(miscellaneousData);
		listMiscellaneousData.remove(miscellaneousData);

		c.setMiscellaneousData(listMiscellaneousData);

	}

	public MiscellaneousData findOne(final int id) {
		MiscellaneousData result;
		result = this.miscellaneousDataRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}
	
	public MiscellaneousDataForm contruct(int id){
		MiscellaneousDataForm res = new MiscellaneousDataForm();
		MiscellaneousData aux = this.findOne(id);
		res.setAttachments(aux.getAttachments());
		res.setText(aux.getText());
		res.setId(id);
		res.setVersion(aux.getVersion());
		res.setCurricula(this.curriculaService.findByMiscellaneousDataId(id));
		return res;
	}
	
	public MiscellaneousData reconstruct(MiscellaneousDataForm aux, final BindingResult binding){
		MiscellaneousData res = new MiscellaneousData();
		res.setAttachments(aux.getAttachments());
		res.setText(aux.getText());
		res.setId(aux.getId());
		res.setVersion(aux.getVersion());
		this.validator.validate(aux, binding);
		return res;
	}

	public MiscellaneousData findOneToEdit(final int miscellaneousDataId) {
		MiscellaneousData result;
		result = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Curricula c = this.curriculaService.findByMiscellaneousDataId(miscellaneousDataId);
		Assert.notNull(c);
		Assert.isTrue(c.getMiscellaneousData().contains(result));
		return result;
	}

	public void delete(int id) {
		Hacker principal;
		MiscellaneousData miscellaneousData = this.findOne(id);
		
		principal = this.hackerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(miscellaneousData);
		
		Curricula c = this.curriculaService.findByMiscellaneousDataId(miscellaneousData.getId());
		Assert.notNull(c);
		
		final Collection<MiscellaneousData> listMiscellaneousData = c.getMiscellaneousData();
		this.miscellaneousDataRepository.delete(miscellaneousData);
		listMiscellaneousData.remove(miscellaneousData);
		c.setMiscellaneousData(listMiscellaneousData);
	}

}

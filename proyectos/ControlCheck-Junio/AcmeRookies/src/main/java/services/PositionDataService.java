
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionDataRepository;
import domain.Curricula;
import domain.Rookie;
import domain.PositionData;
import forms.PositionDataForm;

@Service
@Transactional
public class PositionDataService {

	// Managed Repository
	@Autowired
	private PositionDataRepository	positionDataRepository;

	// Supporting services
	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private Validator				validator;


	// Constructors

	public PositionDataService() {
		super();
	}

	// Simple CRUD methods

	public PositionData create() {
		PositionData result;
		Rookie principal;

		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);

		result = new PositionData();
		Assert.notNull(result);

		return result;
	}

	public PositionData save(final PositionData positionData, final int curriculaId) {
		PositionData result;
		Rookie principal;

		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(this.curriculaService.findByPrincipal());
		
		result = this.positionDataRepository.save(positionData);
		Assert.notNull(result);

		if (positionData.getId() == 0) {
			final Collection<PositionData> positionDatas = new ArrayList<PositionData>(this.curriculaService.findOne(curriculaId).getPositionData());
			positionDatas.add(result);
			this.curriculaService.findOne(curriculaId).setPositionData(positionDatas);
		} else {
			Assert.isNull(this.curriculaService.findCopiedById(this.curriculaService.findByPositionDataId(positionData.getId()).getId()));
		}

		return result;

	}

	public void delete(final PositionData positionData) {
		Rookie principal;

		Assert.notNull(positionData);
		Assert.isTrue(positionData.getId() != 0);

		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);

		final Curricula c = this.curriculaService.findByPositionDataId(positionData.getId());
		Assert.notNull(c);

		final Collection<PositionData> listpositionData = c.getPositionData();
		this.positionDataRepository.delete(positionData);
		listpositionData.remove(positionData);

		c.setPositionData(listpositionData);

	}

	public PositionData findOne(final int id) {
		PositionData result;
		result = this.positionDataRepository.findOne(id);
		return result;
	}

	public void flush() {
		this.positionDataRepository.flush();
	}

	public PositionData findOneToEdit(final int positionDataId) {
		PositionData result;
		result = this.positionDataRepository.findOne(positionDataId);
		final Curricula c = this.curriculaService.findByPositionDataId(positionDataId);
		Assert.notNull(c);
		Assert.isTrue(c.getPositionData().contains(result));
		return result;
	}

	public PositionDataForm contruct(final int id) {
		final PositionDataForm res = new PositionDataForm();
		final PositionData aux = this.findOne(id);
		res.setTitle(aux.getTitle());
		res.setEndDate(aux.getEndDate());
		res.setDescription(aux.getDescription());
		res.setStartDate(aux.getStartDate());
		res.setId(id);
		res.setVersion(aux.getVersion());
		res.setCurricula(this.curriculaService.findByPositionDataId(id));
		return res;
	}

	public PositionData reconstruct(final PositionDataForm aux, final BindingResult binding) {
		final PositionData res = new PositionData();
		res.setTitle(aux.getTitle());
		res.setEndDate(aux.getEndDate());
		res.setDescription(aux.getDescription());
		res.setStartDate(aux.getStartDate());
		res.setId(aux.getId());
		res.setVersion(aux.getVersion());
		this.validator.validate(aux, binding);
		return res;
	}

	public void delete(final int id) {
		Rookie principal;
		final PositionData positionData = this.findOne(id);

		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(positionData);

		final Curricula c = this.curriculaService.findByPositionDataId(positionData.getId());
		Assert.notNull(c);

		final Collection<PositionData> listpositionData = c.getPositionData();
		this.positionDataRepository.delete(positionData);
		listpositionData.remove(positionData);
		c.setPositionData(listpositionData);
	}

}

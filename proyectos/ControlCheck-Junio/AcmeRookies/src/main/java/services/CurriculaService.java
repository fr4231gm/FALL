
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import domain.Rookie;

@Service
@Transactional
public class CurriculaService {

	// Managed repository -----------
	@Autowired
	private CurriculaRepository	curriculaRepository;

	// Supporting services ----------

	@Autowired
	private RookieService		rookieService;


	// Constructors -----------------

	public CurriculaService() {
		super();
	}

	// Simple CRUDs methods ----------
	public Curricula create() {
		// Comprobamos que la persona logueada es unrookie y no tiene ya
		// una curricula
		Rookie principal;
		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);

		// Nuevo Curricula
		final Curricula res = new Curricula();

		res.setRookie(principal);
		return res;
	}

	public Curricula save(final Curricula curricula) {
		// Comprobamos que la persona logueada es una rookie
		Rookie principal;
		Curricula res;
		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);
		// PersonalData is mandatory
		Assert.notNull(curricula.getPersonalData());

		if (curricula.getId() == 0)
			res = this.curriculaRepository.save(curricula);
		else {
			Assert.isNull(this.findCopiedById(curricula.getId()));
			// Comprobamos que le pertenece la curricula que se está editando
			Assert.isTrue(this.findByPrincipal().contains(curricula));

			res = this.curriculaRepository.save(curricula);
		}

		return res;

	}

	public Curricula findCopiedById(final int id) {
		final Curricula res = this.curriculaRepository.findCopiedById(id);
		return res;
	}

	public Curricula attachToApplication(final Curricula toCopy) {
		// Comprobamos que la persona logueada es una rookie
		Rookie principal;
		Curricula res;
		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);

		final List<MiscellaneousData> mdToCopy = new ArrayList<MiscellaneousData>(toCopy.getMiscellaneousData());
		final List<EducationData> edToCopy = new ArrayList<EducationData>(toCopy.getEducationData());
		final List<PositionData> pdToCopy = new ArrayList<PositionData>(toCopy.getPositionData());

		final Collection<MiscellaneousData> md = new ArrayList<MiscellaneousData>();
		final Collection<EducationData> ed = new ArrayList<EducationData>();
		final Collection<PositionData> pd2 = new ArrayList<PositionData>();

		//Copio el personal data
		final PersonalData pd = new PersonalData();
		pd.setFullName(toCopy.getPersonalData().getFullName());
		pd.setStatement(toCopy.getPersonalData().getStatement());
		pd.setPhoneNumber(toCopy.getPersonalData().getPhoneNumber());
		pd.setGitHubLink(toCopy.getPersonalData().getGitHubLink());
		pd.setLinkedInLink(toCopy.getPersonalData().getLinkedInLink());

		//Copio los miscellaneous Data
		for (int i = 0; i < mdToCopy.size(); i++) {
			final MiscellaneousData copy = new MiscellaneousData();

			copy.setText(mdToCopy.get(i).getText());
			copy.setAttachments(mdToCopy.get(i).getAttachments());

			md.add(copy);
		}

		//Copio los education Data
		for (int i = 0; i < edToCopy.size(); i++) {
			final EducationData copy = new EducationData();

			copy.setDegree(edToCopy.get(i).getDegree());
			copy.setEndDate(edToCopy.get(i).getEndDate());
			copy.setStartDate(edToCopy.get(i).getStartDate());
			copy.setMark(edToCopy.get(i).getMark());
			copy.setInstitution(edToCopy.get(i).getInstitution());

			ed.add(copy);
		}

		//Copio el positionData
		for (int i = 0; i < pdToCopy.size(); i++) {
			final PositionData copy = new PositionData();

			copy.setTitle(pdToCopy.get(i).getTitle());
			copy.setEndDate(pdToCopy.get(i).getEndDate());
			copy.setStartDate(pdToCopy.get(i).getStartDate());
			copy.setDescription(pdToCopy.get(i).getDescription());

			pd2.add(copy);
		}

		//Copio el cuuricula y le asigno las copias creadas y el application
		res = this.create();
		res.setEducationData(ed);
		res.setMiscellaneousData(md);
		res.setPersonalData(pd);
		res.setPositionData(pd2);

		return this.curriculaRepository.save(res);

	}
	public void delete(final Curricula curricula) {
		Rookie principal;
		Collection<Curricula> result;
		Assert.isTrue(curricula.getId() != 0);
		principal = this.rookieService.findByPrincipal();
		Assert.notNull(principal);
		result = this.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.contains(curricula));
		this.curriculaRepository.delete(curricula);
	}

	public Curricula findOne(final int curriculaId) {
		Curricula res;
		res = this.curriculaRepository.findOne(curriculaId);
		return res;
	}

	public Collection<Curricula> findByPrincipal() {
		final Rookie b = this.rookieService.findByPrincipal();
		Collection<Curricula> res;
		res = this.curriculaRepository.findByRookieId(b.getId());
		return res;
	}

	public void flush() {
		this.curriculaRepository.flush();
	}

	public Curricula findByPersonalDataId(final int id) {
		final Curricula h = this.curriculaRepository.findByPersonalDataId(id);
		return h;
	}

	public Collection<Curricula> findByRookieId(final int id) {
		return this.curriculaRepository.findByRookieId(id);
	}

	public Curricula findOneToEdit(final int curriculaId) {
		final Rookie principal = this.rookieService.findByPrincipal();
		Curricula res;
		res = this.curriculaRepository.findOne(curriculaId);
		Assert.isTrue(res.getRookie() == principal);
		return res;
	}

	public Curricula findByMiscellaneousDataId(final int id) {
		return this.curriculaRepository.findByMiscellaneousDataId(id);
	}

	public Curricula findByEducationDataId(final int id) {
		return this.curriculaRepository.findByEducationDataId(id);
	}

	public Collection<Curricula> findNonCopiesByPrincipal() {
		final Rookie b = this.rookieService.findByPrincipal();
		Collection<Curricula> res;
		res = this.curriculaRepository.findNonCopiesByPrincipal(b.getId());
		return res;
	}

	public Curricula findByPositionDataId(final int id) {

		return this.curriculaRepository.findByPositionDataId(id);
	}

	public void deleteByUserDropOut(Curricula curricula) {
		this.curriculaRepository.delete(curricula);
	}

}

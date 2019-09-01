
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.PositionRepository;
import domain.Company;
import domain.Finder;
import domain.Position;
import domain.Problem;
import domain.Rookie;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private MessageService		messageService;

	// Constructors ------------------------------------

	public PositionService() {
		super();
	}

	// CREATE
	public Position create() {

		Company principal;
		final Collection<Problem> problems = new ArrayList<>();

		// Comprobamos que la persona logueda es una company
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		// Create instance of the result
		final Position res = new Position();

		// Setting defaults
		res.setCompany(principal);
		res.setProblems(problems);
		res.setTicker(PositionService.generateTicker(principal));
		res.setIsDraft(true); // when created, it's set to draft
		res.setIsCancelled(false);

		return res;
	}

	// SAVE
	public Position save(final Position position) {

		final Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.isTrue(position.getCompany().getId() == principal.getId());
		//position.setIsCancelled(position.getIsCancelled());
		Position res;

		// Check if it's an updating
		if (position.getId() == 0) {

			if (principal instanceof Company)
				Assert.isTrue(position.getCompany().getId() == principal.getId());

			// Checking that the ticker is not store in the database
			Assert.isNull(this.positionRepository.findPositionByTicker(position.getTicker()));

			// Calling the repository
			res = this.positionRepository.save(position);

		} else
			// Calling the repository
			res = this.positionRepository.save(position);

		if (position.getIsDraft() == false)
			Assert.isTrue(position.getProblems().size() >= 2);

		final List<Rookie> rookies = new ArrayList<Rookie>(this.rookieService.findAll());
		for (int i = 0; i < rookies.size(); i++) {
			final Finder f = rookies.get(i).getFinder();
			final Collection<Position> pos = this.positionRepository.filter(f.getKeyword(), f.getDeadline(), f.getMinimumSalary(), f.getMaximumSalary());
			if (pos.contains(position))
				this.messageService.notificateNewOffer(position.getCompany(), rookies.get(i), position);
			this.messageService.notificateNewOffer(position.getCompany(), rookies.get(i), position);
		}
		// Return the result
		return res;
	}

	// DELETE
	public void delete(final Position position) {

		Company principal;

		// Comprobamos que la persona logueda es un company
		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(position);

		// Comprobamos que la position pertenece a la company.
		Assert.isTrue(position.getCompany().getId() == principal.getId());
		this.applicationService.deleteApplicationWithoutPosition(position.getId());
		this.positionRepository.delete(position);
	}

	// FINDALL
	public Collection<Position> findAll() {
		// Create instance of the result
		Collection<Position> res = new ArrayList<Position>();

		// Call the repository
		res = this.positionRepository.findAll();

		// Return the results in the collection
		return res;
	}

	// FINDONE
	public Position findOne(final int positionID) {

		// Create instance of the result
		Position res;

		// Call the repository
		res = this.positionRepository.findOne(positionID);

		// Check the res is not null
		Assert.notNull(res);

		// Return the result
		return res;
	}

	// FINDONE TO FAIL
	public Position findOneToFail(final int positionID) {

		// Create instance of the result
		Position res;

		// Call the repository
		res = this.positionRepository.findOne(positionID);

		// Return the result
		return res;
	}

	/*
	 * This method generates an alphanumeric code of 6 digits
	 */

	/*
	 * This is a method to generate a unique Ticker for the system
	 */
	public static String generateTicker(final Company c) {

		final Integer numero = (int) (Math.random() * 8999) + 1000;

		// Combining abecedario and numbers into alphanumeric
		String numeric = numero.toString();
		numeric = c.getName().substring(0, 4) + "-" + numeric;

		return numeric;

	}

	public int positionProblemCounter(final Position position) {

		int res = 0;
		Collection<Problem> problemas;

		problemas = position.getProblems();

		res = problemas.size();

		return res;
	}

	public Collection<Position> searchPositionAnonymous(final String keyword) {
		Collection<Position> res;

		res = this.positionRepository.filter(keyword);
		return res;

	}

	public Collection<Position> findPositionsByCompanyId(final int companyId) {
		Collection<Position> res;
		res = this.positionRepository.findPositionsByCompanyId(companyId);
		return res;
	}

	public Collection<Position> findPositionsFinalModeByCompany(final int companyId) {
		Collection<Position> res;
		res = this.positionRepository.findPositionsFinalModeByCompany(companyId);
		return res;
	}

	public Collection<Position> findPositionsFinalMode() {
		Collection<Position> res;
		res = this.positionRepository.findPositionsFinalMode();
		return res;
	}

	public Collection<Position> findPositionsNotCancelled(final int companyId) {
		Collection<Position> res;
		res = this.positionRepository.findPositionsNotCancelled(companyId);
		return res;
	}

	public Collection<Position> findPositionsNotCancelled() {
		Collection<Position> res;
		res = this.positionRepository.findPositionsNotCancelled();
		return res;
	}

	public Collection<Position> findPositionsNotCancelledFinalMode() {
		Collection<Position> res;
		res = this.positionRepository.findPositionsNotCancelledFinalMode();
		return res;
	}

	// Construct a PositionForm Object from a Position Object
	public PositionForm construct(final Position position) {

		final PositionForm res = new PositionForm();

		res.setId(position.getId());
		res.setVersion(position.getVersion());
		res.setTitle(position.getTitle());
		res.setDescription(position.getDescription());
		res.setTicker(position.getTicker());
		res.setDeadline(position.getDeadline());
		res.setProfile(position.getProfile());
		res.setSkills(position.getSkills());
		res.setTechnologies(position.getTechnologies());
		res.setSalary(position.getSalary());
		res.setIsDraft(position.getIsDraft());
		res.setIsCancelled(position.getIsCancelled());
		res.setProblems(position.getProblems());

		return res;
	}

	// reconstruct the Position Object from the PositionForm
	public Position reconstruct(final PositionForm positionForm, final BindingResult bindingResult) {

		// Set Position properties from PositionForm
		Position res;

		if (positionForm.getId() == 0) {
			res = this.create();
			res.setId(positionForm.getId());
		} else {
			res = new Position();
			final Position aux = this.findOne(positionForm.getId());

			res.setCompany(aux.getCompany());
			res.setId(positionForm.getId());
			res.setTicker(aux.getTicker());
			res.setProblems(aux.getProblems());
			res.setIsDraft(aux.getIsDraft());
			res.setIsCancelled(aux.getIsCancelled());
		}
		if (positionForm.getId() != 0)
			res.setProblems(positionForm.getProblems());
		if (!(positionForm.getProblems() == null))
			res.setProblems(positionForm.getProblems());
		res.setDescription(positionForm.getDescription());
		res.setTitle(positionForm.getTitle());
		res.setProfile(positionForm.getProfile());
		res.setSkills(positionForm.getSkills());
		res.setTechnologies(positionForm.getTechnologies());
		res.setSalary(positionForm.getSalary());
		res.setIsDraft(positionForm.getIsDraft());
		res.setIsCancelled(positionForm.getIsCancelled());
		res.setDeadline(positionForm.getDeadline());
		res.setVersion(positionForm.getVersion());

		// Checking that the terms are accepted

		// return Position
		return res;

	}
	public void flush() {
		this.positionRepository.flush();

	}

	public void deleteByUserDropOut(Position position) {
		this.positionRepository.delete(position);
	}
}

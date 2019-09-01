
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Administrator;
import domain.Enrolment;
import domain.Position;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PositionRepository		positionRepository;

	//Validator
	@Autowired
	private Validator				validator;

	// Supporting services ----------------------------------------------------

	@Autowired
	private EnrolmentService		enrolmentService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructor ------------------------------------------------------------
	public PositionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// FINDALL
	public Collection<Position> findAll() {
		Collection<Position> res = new ArrayList<>();
		res = this.positionRepository.findAll();
		return res;
	}
	// FINDONE
	public Position findOne(final int positionId) {
		Position res;
		res = this.positionRepository.findOne(positionId);
		Assert.notNull(res);
		return res;
	}

	// FINDONE TO FAIL
	public Position findOneToFail(final int positionId) {
		Position res;
		res = this.positionRepository.findOne(positionId);
		return res;
	}

	// CREATE
	public Position create() {
		// Create a position
		Position res;
		// Create an instance
		res = new Position();
		// Return the position
		return res;
	}

	// DELETE
	public void delete(final Position position) {
		//Inicializamos las variables
		Collection<Enrolment> aux;

		//Comprobamos que es un admin quien la borra
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);

		//Comprobamos que la posicion no es nula y que nadie esta enrolado a ella
		Assert.notNull(position);

		//Comprobamos que no hay ningun enrolment con esa posicion asignada y borramos
		aux = this.enrolmentService.findByPosition(position);
		Assert.isTrue(aux.isEmpty());

		this.positionRepository.delete(position);

	}

	// SAVE
	public Position save(final Position position) {
		// An administrator must be logged
		Assert.notNull(this.administratorService.findByPrincipal());

		// Check the input is not null
		Assert.notNull(position);
		// Create a result
		Position res;
		// Call the repository
		res = this.positionRepository.save(position);

		// Return
		return res;
	}

	public PositionForm construct(final Position position) {

		final PositionForm res = new PositionForm();
		if (position.getName() != null) {
			res.setNameEn(position.getName().get("en"));
			res.setNameEs(position.getName().get("es"));
			res.setId(position.getId());
			res.setVersion(position.getVersion());
		}

		return res;
	}

	public Position reconstruct(final PositionForm x, final BindingResult binding) {
		final Position res = new Position();

		final Map<String, String> mapito = new HashMap<String, String>();
		mapito.put("es", x.getNameEs());
		mapito.put("en", x.getNameEn());
		res.setName(mapito);
		res.setId(x.getId());
		res.setVersion(x.getVersion());
		this.validator.validate(res, binding);
		return res;
	}
	// Other business methods -------------------------------------------------

}

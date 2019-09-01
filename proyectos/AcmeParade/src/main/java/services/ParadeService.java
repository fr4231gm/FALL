
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.Parade;
import forms.ParadeForm;

@Service
@Transactional
public class ParadeService {

	// Constructor ------------------------------------------------------------
	public ParadeService() {
		super();
	}


	// Managed repository ------------------------------------------------------

	@Autowired
	private ParadeRepository	paradeRepository;

	// Supporting Services -----------------------------------------------------
	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	// Simple CRUD methods -----------------------------------------------------

	// FINDALL
	public Collection<Parade> findAll() {
		// Create instance of the result
		Collection<Parade> res = new ArrayList<Parade>();

		// Call the repository
		res = this.paradeRepository.findAll();

		// Return the results in the collection
		return res;
	}

	// FINDONE
	public Parade findOne(final int paradeID) {

		// Create instance of the result
		Parade res;

		// Call the repository
		res = this.paradeRepository.findOne(paradeID);

		//Check the res is not null
		Assert.notNull(res);
		// Return the result
		return res;
	}

	// FINDONE
	public Parade findOneToFail(final int paradeID) {

		// Create instance of the result
		Parade res;

		// Call the repository
		res = this.paradeRepository.findOne(paradeID);

		// Return the result
		return res;
	}

	// CREATE
	public Parade create() {

		Brotherhood principal;
		final Collection<domain.Float> floats = new ArrayList<>();

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		// Create instance of the result
		final Parade res = new Parade();

		// Setting defaults
		res.setBrotherhood(principal);
		res.setFloats(floats);
		res.setTicker(ParadeService.generateTicker());
		res.setIsDraft(true); // when created, it's set to draft

		final Collection<String> locations = new ArrayList<String>();

		res.setLocations(locations);

		// Return the result to show
		return res;

	}

	// SAVE
	public Parade save(final Parade parade) {

		final Actor principal;

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		// Creating the result
		Parade res;

		if (parade.getStatus() == "APPROVED")
			this.messageService.notificatePublication(parade.getBrotherhood(), parade.getTitle());

		// Check if it's an updating
		if (parade.getId() == 0) {
			// Saving by the first time

			// Checking that the ticker is not store in the database
			Assert.isNull(this.paradeRepository.findParadeByTicker(parade.getTicker()));

			for (int i = 1; i <= parade.getRows(); i++) {
				final String row = i + "#" + "0";
				parade.getLocations().add(row);
			}

			// Calling the repository
			res = this.paradeRepository.save(parade);

		} else {
			if (!(principal instanceof Chapter))
				Assert.isTrue(parade.getBrotherhood().getId() == principal.getId());
			if(parade.getStatus() == "REJECTED"){
				Assert.isTrue(parade.getRejectReason().trim().length() > 0);
			}
			// Calling the repository
			res = this.paradeRepository.save(parade);
		}
		// Return the result
		return res;

	}

	// DELETE
	public void delete(final Parade parade) {

		Brotherhood principal;

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(parade);

		// Comprobamos que la parade pertenece al brotherhood.
		Assert.isTrue(parade.getBrotherhood().getId() == principal.getId());
		this.requestService.deleteRequestWithoutParade(parade.getId());
		this.paradeRepository.delete(parade);
	}

	// Other business methods -------------------------------------------------

	public Collection<Parade> findParadesByMemberId(final int memberId) {
		Collection<Parade> res = new ArrayList<>();

		Assert.notNull(memberId);

		res = this.findParadesByMemberId(memberId);

		return res;
	}

	/*
	 * This method generates an alphanumeric code of 6 digits
	 */
	private static String generateAlphaNumericCode() {
		// Create an empty string
		String res = "";

		// Create abecedario variblae with international capital letters
		final String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// Create numeros variable
		final String numbers = "0123456789";

		// Combining abecedario and numbers into alphanumeric
		final String alphanumeric = abecedario + numbers;

		// Keep length of alphanumeric
		final int top_alphanumeric = alphanumeric.length() - 1;

		// Variable to obtain the character of th alphanumeric string
		char c;

		// Method to generate 6 alphanumeric random characters
		for (int i = 0; i < 6; i++) {
			// Generate a random number
			final int randomNumber = (int) Math.floor(Math.random() * top_alphanumeric);
			// Keeping the char value for randomNumber of the alphanumeric
			c = alphanumeric.charAt(randomNumber);
			// Save in res actual value + char of c value generated
			res += c;
		}
		// Return the result
		return res;
	}

	/*
	 * This is a method to generate a unique Ticker for the system
	 */
	public static String generateTicker() {
		// Create result variable
		String res = null;

		// Create date instance
		final Date fecha = new Date();
		final DateFormat df = new SimpleDateFormat("yyMMdd");
		final String formattedDate = df.format(fecha);

		// Generating alphanumeric code
		final String alphanumericCode = ParadeService.generateAlphaNumericCode();

		// Combining date + alphanumeric code
		res = formattedDate + "-" + alphanumericCode;

		// Returning ticker
		return res;
	}

	public Collection<Parade> findParadesByBrotherhoodId(final int brotherhoodId) {

		// Initialize variable
		Collection<Parade> res;

		res = this.paradeRepository.findParadesByBrotherhoodId(brotherhoodId);

		return res;

	}

	// Construct a ParadeForm Object from a Parade Object
	public ParadeForm construct(final Parade parade) {
		// initialize
		final ParadeForm res = new ParadeForm();

		// Set Parade properties into ParadeForm
		res.setRejectReason(parade.getRejectReason());
		res.setStatus(parade.getStatus());
		res.setId(parade.getId());
		res.setVersion(parade.getVersion());
		res.setDescription(parade.getDescription());
		res.setMoment(parade.getMoment());
		res.setTitle(parade.getTitle());
		res.setIsDraft(parade.getisDraft());
		res.setTicker(parade.getTicker());
		//	res.setFloats(parade.getFloats());
		res.setRows(parade.getRows());

		//Return the form
		return res;

	}

	// reconstruct the Parade Object from the ParadeForm
	public Parade reconstruct(final ParadeForm paradeForm, final BindingResult bindingResult) {

		// Set Parade properties from ParadeForm
		Parade res;

		if (paradeForm.getId() == 0) {
			res = this.create();
			res.setId(paradeForm.getId());
			res.setRows(paradeForm.getRows());
		} else {
			res = new Parade();
			final Parade aux = this.findOne(paradeForm.getId());

			res.setBrotherhood(aux.getBrotherhood());
			res.setId(paradeForm.getId());
			res.setTicker(aux.getTicker());
			res.setRows(aux.getRows());
			res.setLocations(aux.getLocations());
		}
		res.setRejectReason(paradeForm.getRejectReason());
		res.setStatus(paradeForm.getStatus());
		if (paradeForm.getId() != 0)
			res.setFloats(paradeForm.getFloats());
		if (!(paradeForm.getFloats() == null))
			res.setFloats(paradeForm.getFloats());
		res.setDescription(paradeForm.getDescription());
		res.setTitle(paradeForm.getTitle());
		res.setIsDraft(paradeForm.getisDraft());
		res.setMoment(paradeForm.getMoment());
		res.setVersion(paradeForm.getVersion());
		this.validator.validate(paradeForm, bindingResult);

		// return Parade
		return res;

	}
	public Collection<Parade> findParadesNoDraftByBrotherhood(final int brotherhoodId) {
		Collection<Parade> res;

		res = this.paradeRepository.findParadesNoDraftByBrotherhood(brotherhoodId);

		return res;
	}

	public void flush() {
		this.paradeRepository.flush();
	}

	public Collection<Parade> findParadeRequestablesByMemberId(final int memberId) {
		Collection<Parade> res;

		res = this.paradeRepository.findParadesRequestablesByMemberId(memberId);

		return res;
	}

	public Parade addColumn(final Parade parade) {
		final List<String> row = new ArrayList<String>(parade.getLocations());
		final Collection<String> locations = new ArrayList<String>();
		for (int i = 0; i < parade.getRows(); i++) {
			final String row2 = row.get(i) + "0" + "#";
			locations.add(row2);
		}
		parade.setLocations(locations);
		final Parade saved = this.paradeRepository.save(parade);
		return saved;
	}

	public Parade updateLocations(final Parade parade) {
		return this.paradeRepository.save(parade);
	}

	public Parade addColumnWithoutSave(final Parade parade) {
		final List<String> row = new ArrayList<String>(parade.getLocations());
		final Collection<String> locations = new ArrayList<String>();
		for (int i = 0; i < parade.getRows(); i++) {
			final String row2 = row.get(i) + "0" + "#";
			locations.add(row2);
		}
		parade.setLocations(locations);
		return parade;
	}

	public Parade copy(final Parade originalParade) {

		//Comprobamos que la parade original no es nula
		Assert.notNull(originalParade);

		//Creacion de la Parade que devolveremos copiada
		final Parade res = new Parade();

		//Adjudicamos a la copia los valores de la original
		res.setMoment(originalParade.getMoment());
		res.setTitle(originalParade.getTitle());
		res.setDescription(originalParade.getDescription());
		res.setRows(originalParade.getRows());
		res.setLocations(originalParade.getLocations());
		res.setSegments(originalParade.getSegments());
		res.setTicker(ParadeService.generateTicker());
		res.setIsDraft(true);
		res.setBrotherhood(originalParade.getBrotherhood());
		res.setFloats(originalParade.getFloats());
		res.setStatus("");
		res.setRejectReason("");

		return res;
	}

	public Collection<Parade> findByArea(final Area a) {

		Collection<Parade> res = new ArrayList<Parade>();

		// Call the repository
		res = this.paradeRepository.findByArea(a.getId());
		//Assert.notNull(res);
		// Return the results in the collection
		return res;

	}

	public Collection<Parade> findParadesWithStatusAccepted(final int brotherhoodId) {

		Collection<Parade> res = new ArrayList<>();

		res = this.paradeRepository.findParadesWithStatusAccepted(brotherhoodId);

		return res;
	}

	public Collection<Parade> findParadesAccepted() {

		Collection<Parade> res = new ArrayList<>();

		res = this.paradeRepository.findParadesAccepted();

		return res;

	}
}

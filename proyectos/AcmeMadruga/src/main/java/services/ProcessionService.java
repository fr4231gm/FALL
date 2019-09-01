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

import repositories.ProcessionRepository;
import domain.Brotherhood;
import domain.Procession;
import forms.ProcessionForm;

@Service
@Transactional
public class ProcessionService {

	// Constructor ------------------------------------------------------------
	public ProcessionService() {
		super();
	}

	// Managed repository ------------------------------------------------------

	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting Services -----------------------------------------------------
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired 
	private Validator validator;

	// Simple CRUD methods -----------------------------------------------------

	// FINDALL
	public Collection<Procession> findAll() {
		// Create instance of the result
		Collection<Procession> res = new ArrayList<Procession>();

		// Call the repository
		res = this.processionRepository.findAll();

		// Return the results in the collection
		return res;
	}

	// FINDONE
	public Procession findOne(final int processionID) {

		// Create instance of the result
		Procession res;

		// Call the repository
		res = this.processionRepository.findOne(processionID);
		
		//Check the res is not null
		Assert.notNull(res);
		// Return the result
		return res;
	}

	// FINDONE
		public Procession findOneToFail(final int processionID) {

			// Create instance of the result
			Procession res;

			// Call the repository
			res = this.processionRepository.findOne(processionID);

			// Return the result
			return res;
		}
		
	// CREATE
	public Procession create() {

		Brotherhood principal;
		Collection<domain.Float> floats = new ArrayList<>();

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		

		// Create instance of the result
		final Procession res = new Procession();

		// Setting defaults
		res.setBrotherhood(principal);
		res.setFloats(floats);
		res.setTicker(ProcessionService.generateTicker());
		res.setIsDraft(true); // when created, it's set to draft
		
		Collection<String> locations = new ArrayList<String>();
		
		res.setLocations(locations);

		// Return the result to show
		return res;

	}

	// SAVE
	public Procession save(final Procession procession) {

		Brotherhood principal;

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		// Creating the result
		Procession res;

		// Check if it's an updating
		if (procession.getId() == 0) {
			// Saving by the first time

			// Checking that the ticker is not store in the database
			Assert.isNull(this.processionRepository
					.findProcessionByTicker(procession.getTicker()));
			
			for(int i=1; i<=procession.getRows(); i++){
				String row = i + "#" + "0";
				procession.getLocations().add(row);
			}

			// Calling the repository
			res = this.processionRepository.save(procession);

		} else {
			Assert.isTrue(procession.getBrotherhood().getId() == principal
					.getId());

			// Calling the repository
			res = this.processionRepository.save(procession);
		}
		// Return the result
		return res;

	}

	// DELETE
	public void delete(final Procession procession) {

		Brotherhood principal;

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		

		Assert.notNull(procession);

		// Comprobamos que la procession pertenece al brotherhood.
		Assert.isTrue(procession.getBrotherhood().getId() == principal.getId());
		this.requestService.deleteRequestWithoutProcession(procession.getId());
		this.processionRepository.delete(procession);
	}

	// Other business methods -------------------------------------------------

	public Collection<Procession> findProcessionsByMemberId(int memberId) {
		Collection<Procession> res = new ArrayList<>();

		Assert.notNull(memberId);

		res = this.findProcessionsByMemberId(memberId);

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
			final int randomNumber = (int) Math.floor(Math.random()
					* top_alphanumeric);
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
		final String alphanumericCode = ProcessionService
				.generateAlphaNumericCode();

		// Combining date + alphanumeric code
		res = formattedDate + "-" + alphanumericCode;

		// Returning ticker
		return res;
	}

	

	public Collection<Procession> findProcessionsByBrotherhoodId(
			final int brotherhoodId) {

		// Initialize variable
		Collection<Procession> res;

		res = this.processionRepository
				.findProcessionsByBrotherhoodId(brotherhoodId);

		return res;

	}
	
	// Construct a ProcessionForm Object from a Procession Object
	public ProcessionForm construct (final Procession procession){
		// initialize
		ProcessionForm res = new ProcessionForm();
		 
		// Set Procession properties into ProcessionForm
		res.setId(procession.getId());
		res.setVersion(procession.getVersion());
		res.setDescription(procession.getDescription());
		res.setMoment(procession.getMoment());
		res.setTitle(procession.getTitle());
		res.setIsDraft(procession.getisDraft());
		res.setTicker(procession.getTicker());
		res.setFloats(procession.getFloats());
		res.setRows(procession.getRows());
	
		//Return the form
		return res;
		
	}
	
	// reconstruct the Procession Object from the ProcessionForm
	public Procession reconstruct (final ProcessionForm processionForm, final BindingResult bindingResult){
		
		// Set Procession properties from ProcessionForm
		Procession res;
		
		if (processionForm.getId()== 0){
			res = this.create();
			res.setId(processionForm.getId());
			res.setRows(processionForm.getRows());
		}
		else {
			res = new Procession();
			Procession aux = this.findOne(processionForm.getId());
			
			res.setBrotherhood(aux.getBrotherhood());
			res.setId(processionForm.getId());
			res.setTicker(aux.getTicker());
			res.setRows(aux.getRows());
			res.setLocations(aux.getLocations());
		}
		
		
		res.setFloats(processionForm.getFloats());
		res.setDescription(processionForm.getDescription());
		res.setTitle(processionForm.getTitle());
		res.setIsDraft(processionForm.getisDraft());
		res.setMoment(processionForm.getMoment());
		res.setVersion(processionForm.getVersion());
		
		this.validator.validate(processionForm, bindingResult);
		
		
		// return Procession
		return res;
		
	}
	
	public Collection<Procession> findProcessionsNoDraftByBrotherhood(final int brotherhoodId){
		Collection<Procession> res;
		
		res = this.processionRepository.findProcessionsNoDraftByBrotherhood(brotherhoodId);
		
		return res;
	}
	
	public void flush(){
			this.processionRepository.flush();
	}
	
	public Collection<Procession> findProcessionRequestablesByMemberId(final int memberId){
		Collection<Procession> res;
		
		res = this.processionRepository.findProcessionsRequestablesByMemberId(memberId);
		
		return res;
	}

	public Procession addColumn(Procession procession) {
		List<String> row = new ArrayList<String> (procession.getLocations());
		Collection<String> locations = new ArrayList<String> ();
		for(int i=0; i<procession.getRows(); i++){
			String row2 = row.get(i) + "0" + "#" ;
			locations.add(row2);
		}
		procession.setLocations(locations);
		Procession saved = this.processionRepository.save(procession);
		return saved;
	}
	
	public Procession updateLocations(final Procession procession) {
		return this.processionRepository.save(procession);
	}

	public Procession addColumnWithoutSave(Procession procession) {
		List<String> row = new ArrayList<String> (procession.getLocations());
		Collection<String> locations = new ArrayList<String> ();
		for(int i=0; i<procession.getRows(); i++){
			String row2 = row.get(i) + "0" + "#";
			locations.add(row2);
		}
		procession.setLocations(locations);
		return procession;
	}

}

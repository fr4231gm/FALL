package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatRepository;
import domain.Brotherhood;
import forms.FloatForm;

@Service
@Transactional
public class FloatService {
	// Managed repository -----------
	@Autowired
	private FloatRepository floatRepository;

	@Autowired
	private Validator validator;

	// Supporting services ----------

	@Autowired
	private BrotherhoodService brotherhoodService;
	

	// Constructors -----------------

	public FloatService() {
		super();
	}

	// Simple CRUDs methods ----------
	public domain.Float create() {
		// Comprobamos que la persona logueada es un brotherhood
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		// Nuevo Floatt
		final domain.Float res = new domain.Float();

		// final Brotherhood bh = new Brotherhood();

		res.setBrotherhood(principal);

		return res;
	}

	public domain.Float save(final domain.Float floatt) {
		// Comprobamos que la persona logueada es un brotherhood
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		// New Floatt
		domain.Float res = null;

		if (floatt.getId() == 0) {
			res = this.floatRepository.save(floatt);
		} else {
			Assert.isTrue(floatt.getBrotherhood().getId() == principal.getId());
			res = this.floatRepository.save(floatt);
		}
		
		return res;

	}

	/*
	 * public void delete (final int floatId){ //Comprobamos que la persona
	 * logueada es un brotherhood Brotherhood principal; principal =
	 * this.brotherhoodService.findByPrincipal(); domain.Float x =
	 * this.floatRepository.findOne(floatId); Assert.notNull(principal);
	 * Assert.notNull(floatId);
	 * 
	 * 
	 * //Comprobamos que la float pertenece al brotherhood.
	 * Assert.isTrue(x.getBrotherhood().getId() == principal.getId());
	 * this.floatRepository.delete(floatId);
	 * 
	 * }
	 */

	public void delete(domain.Float floatt) {
		// Comprobamos que la persona logueada es un brtoherhood
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(floatt);

		// Comprobamos que la float pertenece al brotherhood.
		Assert.isTrue(floatt.getBrotherhood().getId() == principal.getId());
		this.floatRepository.delete(floatt);

	}

	public domain.Float findOne(final int floatId) {
		domain.Float res;

		res = this.floatRepository.findOne(floatId);
		Assert.notNull(res);

		return res;

	}
	
	public domain.Float findOneToFail(final int floatId) {
		domain.Float res;

		res = this.floatRepository.findOne(floatId);
		//Assert.notNull(res);

		return res;

	}

	public Collection<domain.Float> findAll() {
		Collection<domain.Float> res;

		res = this.floatRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	// Other business methods---------

	public Collection<domain.Float> findFloatsByBrotherhoodId(final int bthid) {

		// Variable iniciada
		Collection<domain.Float> res;

		res = this.floatRepository.findFloatsByBrotherhoodId(bthid);

		return res;
	}

	public domain.Float reconstruct(FloatForm form, BindingResult binding) {
		domain.Float res;

		// Creating a new Float
		if (form.getId() == 0) {
			res = this.create();
		} else {
			res = new domain.Float();
			res.setBrotherhood(this.findOne(form.getId()).getBrotherhood());
		}

		// Float Atributes
		res.setId(form.getId());
		res.setTitle(form.getTitle());
		res.setDescription(form.getDescription());
		res.setPictures(form.getPictures());
		res.setVersion(form.getVersion());

		// Validating the form
		this.validator.validate(form, binding);

		// Checking that all the pictures URL are valid
		if (checkPictures(form.getPictures())) {
			binding.rejectValue("pictures", "brotherhood.pictures.invalid");
		}
		return res;
	}

	private boolean checkPictures(String pictures) {
		boolean res = false;
		pictures.replace(" ","");
		String[] aux = pictures.split("\r\n");
		Pattern p = Pattern.compile("(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");
		Matcher m;
		for (int i = 0; i< aux.length; i++){
			m=p.matcher(aux[i]);
			if(!m.find() && aux[i].length() > 0){
				res = true;
			}
		}
		return res;
	}

	// Construct : Float --> FloatForm
	public FloatForm construct(domain.Float floatt) {

		FloatForm res = new FloatForm();

		res.setId(floatt.getId());
		res.setTitle(floatt.getTitle());
		res.setDescription(floatt.getDescription());
		res.setPictures(floatt.getPictures());
		res.setVersion(floatt.getVersion());

		return res;
	}

}

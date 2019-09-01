
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import domain.Administrator;
import domain.Area;

@Service
@Transactional
public class AreaService {

	//Managed repository ------------------
	@Autowired
	private AreaRepository			areaRepository;

	// Supporting services -----------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors -------------------

	public AreaService() {
		super();
	}
	// Simple CRUDs methods -----------

	public Area create() {
		//Comprobamos que la persona logueada es administrador
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		//Nueva Area
		final Area res = new Area();
		return res;
	}

	public Area save(final Area area) {
		//Comprobamos que la persona logueada es administrador
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		//Declaramos la area.
		Area res;

		//Si la area no se encuentra en la base de datos (es una creacion)
		if (area.getId() == 0)
			res = this.areaRepository.save(area);
		else
			res = this.areaRepository.save(area);
		return res;
	}

	public Area findOne(final int areaId) {
		Area res;

		Assert.notNull(areaId);
		res = this.areaRepository.findOne(areaId);
		Assert.notNull(res);

		return res;
	}
	
	public Area findOneToFail(final int areaId) {
		Area res;

		res = this.areaRepository.findOne(areaId);

		return res;
	}

	public Collection<Area> findAll() {
		Collection<Area> res;

		res = this.areaRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void delete(final Area area) {
		//Comprobamos que la persona logueada es administrador
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(area);
		this.areaRepository.delete(area);

	}
	//Other business methods ----------

}

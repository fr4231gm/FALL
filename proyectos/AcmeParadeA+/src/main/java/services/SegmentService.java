
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.SegmentRepository;
import domain.Brotherhood;
import domain.Coordinate;
import domain.Parade;
import domain.Segment;
import forms.SegmentForm;

@Service
@Transactional
public class SegmentService {

	// Constructor ------------------------------------------------------------
	public SegmentService() {
		super();
	}


	// Managed repository ------------------------------------------------------

	@Autowired
	private SegmentRepository	segmentRepository;

	// Supporting Services -----------------------------------------------------
	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Simple CRUD methods -----------------------------------------------------

	//CREATE
	public Segment create() {
		final Segment s = new Segment();
		final Coordinate destination = new Coordinate();
		final Coordinate origin = new Coordinate();

		s.setDestination(destination);
		s.setOrigin(origin);

		return s;
	}

	//SAVE

	public Segment save(final Segment segment) {
		Segment result;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		result = this.segmentRepository.save(segment);
		Assert.notNull(result);
		return result;
	}

	//FINDONE
	public Segment findOne(final int segmentId) {

		//Declaracion de variables
		Segment res;

		//Obtener Segmento del repositorio
		res = this.segmentRepository.findOne(segmentId);

		//Comprobamos que el segmento obtenido no es nulo
		Assert.notNull(res);

		return res;
	}

	//FINDALL
	public Collection<Segment> findAll() {

		//Declaracion de variables
		Collection<Segment> res = new ArrayList<>();

		//Obtener coleccion de segmentos del repositorio
		res = this.segmentRepository.findAll();

		return res;
	}

	// DELETE
	public void delete(final int paradeId, final int segmentId) {

		// Declaracion de variables
		Brotherhood principal;
		Segment segment;
		Parade parade;

		segment = this.findOne(segmentId);
		parade = this.paradeService.findOne(paradeId);

		// Comprobamos que el segmento no es nulo y
		// y que ya existe en la BBDD
		Assert.notNull(segment);
		Assert.isTrue(segment.getId() != 0);

		// Comprobamos que el actor logeado es un Brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(parade.getBrotherhood().getId() == principal.getId());
		Assert.notNull(principal);

		// Obtenemos todos los segmentos de una parade
		final Collection<Segment> listSegment = this.paradeService.findOne(paradeId).getSegments();

		// Transformar coleccion en array
		final List<Segment> segments = new ArrayList<>(listSegment);

		// Si el segmento que intento borrar es el primero o el ultimo de la
		// lista borra sin problema
		if (segments.get(0).getId() == segment.getId() || segments.get(segments.size() - 1).getId() == segment.getId()) {

			final Parade p = this.paradeService.findOne(paradeId);
			segments.remove(segment);
			p.setSegments(segments);
			this.paradeService.save(p);

			this.segmentRepository.delete(segmentId);

			// Si el segmento que intento borrar est� 'dentro' de la lista
		} else {

			final Coordinate destinoAnterior = segments.get(segments.indexOf(segment) - 1).getDestination();
			final Date fechaAnterior = segments.get(segments.indexOf(segment) - 1).getEndTime();
			final Segment nuevoSegmento = segments.get(segments.indexOf(segment) + 1);

			final Parade p = this.paradeService.findOne(paradeId);
			segments.remove(segment);
			p.setSegments(segments);
			this.paradeService.save(p);

			this.segmentRepository.delete(segment);

			nuevoSegmento.setOrigin(destinoAnterior);
			nuevoSegmento.setStartTime(fechaAnterior);

			this.segmentRepository.save(nuevoSegmento);

		}

		this.segmentRepository.flush();
	}
	// Other methods ------------------------

	//	public SegmentForm construct(final Segment segment) {
	//
	//		final SegmentForm res = new SegmentForm();
	//		if (!(segment.getId() == 0)) {
	//			res.setStartTimeMinutes(segment.getStartTime().getMinutes());
	//			res.setStartTimeHour(segment.getStartTime().getHours());
	//			res.setEndTimeMinutes(segment.getEndTime().getMinutes());
	//			res.setEndTimeHour(segment.getEndTime().getHours());
	//
	//		}
	//		res.setDestination(segment.getDestination());
	//		res.setOrigin(segment.getOrigin());
	//		res.setVersion(segment.getVersion());
	//		res.setId(segment.getId());
	//		return res;
	//	}
	@SuppressWarnings("deprecation")
	public Segment reconstruct(final SegmentForm x, final BindingResult binding) {
		final Segment res = new Segment();

		final Date startTime = new Date();
		if (!(x.getStartTimeHour() == null))
			startTime.setHours(x.getStartTimeHour());
		if (!(x.getStartTimeMinutes() == null))
			startTime.setMinutes(x.getStartTimeMinutes());

		final Date endTime = new Date();
		if (!(x.getEndTimeHour() == null))
			endTime.setHours(x.getEndTimeHour());

		if (!(x.getEndTimeMinutes() == null))
			endTime.setMinutes(x.getEndTimeMinutes());

		res.setDestination(x.getDestination());
		res.setOrigin(x.getOrigin());
		res.setEndTime(endTime);
		res.setStartTime(startTime);
		res.setVersion(x.getVersion());
		res.setId(x.getId());

		return res;
	}
	//Other methods ------------------------

	public void flush() {

		this.segmentRepository.flush();

	}

}

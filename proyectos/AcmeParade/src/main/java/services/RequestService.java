
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Actor;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed repository
	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services -----------------------------
	@Autowired
	private MemberService		memberService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private MessageService	messageService;


	// Constructors ------------------------------------

	public RequestService() {
		super();
	}

	// Simple CRUDs methods

	// CREATE
	public Request create() {

		Member principal;

		// YOU HAVE TO VERIFY THAT A MEMBER CREATES A REQUEST
		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		// inicializar atributos
		final Request res = new Request();
		res.setStatus("PENDING");
		res.setMember(principal);

		return res;
	}

	public Integer[] findfreelocations(final Request request) {
		Integer[] res = {
			0, 0
		};
		final List<String> locations = new ArrayList<String>(request.getParade().getLocations());
		Collections.sort(locations);
		bucle: for (int i = 0; i < locations.size(); i++) {
			final String[] column = locations.get(i).split("#");
			for (int j = 1; j < column.length; j++)
				if (Integer.parseInt(column[j]) == 0) {
					res = new Integer[] {
						i + 1, j
					};
					break bucle;
				}

		}

		return res;
	}
	// SAVE
	public Request save(final Request request) {
		final Actor principal = this.actorService.findByPrincipal();

		// comprobar que la request no es nula, y su parade asociada tampoco
		Assert.notNull(request);
		Assert.notNull(request.getParade());

		Request res = null;
		// si el id es 0 y el usuario loggeado es un miembro guarda la request
		if (request.getId() == 0 && principal instanceof Member) {
			Assert.isTrue(request.getMember().getId() == principal.getId());
			final Integer[] freelocation = this.findfreelocations(request);
			Integer row = Integer.valueOf(freelocation[0]);
			Integer column = Integer.valueOf(freelocation[1]);
			
			if (row == 0 || column == 0) {
				
				final Parade newparade = this.paradeService.addColumn(request.getParade());
				final List<String> locations = new ArrayList<String>(newparade.getLocations());
				final String[] col = locations.get(0).split("#");
				column = col.length - 1;
				row = 1;
				request.setParade(newparade);
				
			}
			request.setRowRequest(row);
			request.setColumnRequest(column);
			final Parade p = request.getParade();
			final List<String> locations = new ArrayList<String>(p.getLocations());
			Collections.sort(locations);
			final String[] col = locations.get(row - 1).split("#");
			col[column] = String.valueOf(request.getMember().getId());

			locations.remove(row - 1);
			String aux2 = "";
			for (int i = 0; i < col.length; i++)
				aux2 = aux2 + col[i] + "#";
			locations.add(aux2);
			p.setLocations(locations);
			this.paradeService.updateLocations(p);
			res = this.requestRepository.save(request);

		}
		// si el id no es 0 y el usuario loggeado es una hermandad pasamos a
		// comprobar el status
		else if (request.getId() != 0 && principal instanceof Brotherhood) {


			// Comprobamos que la request pertenece a una de las procesiones de
			// la hermandad logeada
			Assert.isTrue(request.getParade().getBrotherhood().getId() == (principal.getId()));
			final Request aux = this.findOne(request.getId());
			
			//Si el estatus cambia enviamos mensaje
			if(request.getStatus() != aux.getStatus()){
				this.messageService.notificateRequestStatusChange(aux.getParade().getBrotherhood(), aux.getMember(), aux.getStatus(), request.getStatus(), aux.getParade().getTitle());
			}
			
			// Si el estado es rejected y el campo rejectReason no es nulo
			// guardamos la request
			if (request.getStatus().equals("REJECTED")) {
				Parade p = aux.getParade();
				final List<String> unchanged = new ArrayList<String>(aux.getParade().getLocations());
				Collections.sort(unchanged);
				final String[] unchangedcolumn = unchanged.get(aux.getRowRequest() - 1).split("#");

				unchanged.remove(unchanged.get(aux.getRowRequest() - 1));
				String aux3 = "";
				for (int i = 0; i < unchangedcolumn.length; i++)
					if (i == aux.getColumnRequest())
						aux3 = aux3 + "0#";
					else
						aux3 = aux3 + unchangedcolumn[i] + "#";
				unchanged.add(aux3);
				p.setLocations(unchanged);
				p = this.paradeService.updateLocations(p);
				Assert.notNull(request.getRejectReason());
				res = this.requestRepository.save(request);

			}
			// Si el estado es approved o pending y los campos row y column no son nulos
			// guardamos la request
			else if (request.getStatus().equals("APPROVED") || request.getStatus().equals("PENDING")) {
				if (request.getRowRequest() != aux.getRowRequest() || request.getColumnRequest() != aux.getColumnRequest()) {
					final List<String> locations = new ArrayList<String>(aux.getParade().getLocations());
					final String[] col = locations.get(0).split("#");
					Parade p = aux.getParade();
					if (request.getColumnRequest() >= (col.length - 1)) {
						final int difference = request.getColumnRequest() - (col.length - 1);
						for (int i = 0; i <= difference; i++)
							p = this.paradeService.addColumnWithoutSave(p);
						p = this.paradeService.updateLocations(p);
						request.setParade(p);
					}

					final List<String> unchanged = new ArrayList<String>(aux.getParade().getLocations());
					Collections.sort(unchanged);
					final String[] unchangedcolumn = unchanged.get(aux.getRowRequest() - 1).split("#");

					unchanged.remove(unchanged.get(aux.getRowRequest() - 1));
					String aux3 = "";
					for (int i = 0; i < unchangedcolumn.length; i++)
						if (i == aux.getColumnRequest())
							aux3 = aux3 + "0#";
						else
							aux3 = aux3 + unchangedcolumn[i] + "#";
					unchanged.add(aux3);
					p.setLocations(unchanged);
					p = this.paradeService.updateLocations(p);

					final List<String> locations2 = new ArrayList<String>(p.getLocations());
					Collections.sort(locations2);
					final String[] col2 = locations2.get(request.getRowRequest() - 1).split("#");
					for (int i = 0; i < col2.length; i++)
						
					col2[request.getColumnRequest()] = String.valueOf(request.getMember().getId());
					locations2.remove(locations2.get(request.getRowRequest() - 1));

					String aux2 = "";
					for (int i = 0; i < col2.length; i++)
						aux2 = aux2 + col2[i] + "#";
					locations2.add(aux2);
					p.setLocations(locations2);
					p = this.paradeService.updateLocations(p);
					request.setParade(p);

				}
				Assert.notNull(request.getRowRequest());
				Assert.notNull(request.getColumnRequest());
				res = this.requestRepository.save(request);
			} else
				this.requestRepository.save(request);
		}
		return res;
	}

	// DELETE
	public void delete(final Request request) {

		Member principal;

		// YOU HAVE TO VERIFY THAT A MEMBER CREATES A REQUEST
		principal = this.memberService.findByPrincipal();
		Assert.notNull(request);

		Assert.isTrue(request.getMember().getId() == principal.getId());

		// 11.1 requests cannot be updated, but they can be deleted as
		// long as they are in the pending status
		Assert.isTrue(request.getStatus().equals("PENDING"));

		this.requestRepository.delete(request);
	}

	// FINDALL
	public Collection<Request> findAll() {
		// Create instance of the result
		Collection<Request> res = new ArrayList<Request>();

		// Call the repository
		res = this.requestRepository.findAll();

		// Return the results in the collection
		return res;
	}

	// FINDONE
	public Request findOne(final int requestID) {

		// Create instance of the result
		Request res;

		// Call the repository
		res = this.requestRepository.findOne(requestID);

		// Return the result
		return res;
	}

	// FIND BY PRINCIPAL
	public Collection<Request> findByPrincipal() {
		// Create instance of the result
		Collection<Request> res = new ArrayList<Request>();
		final Actor principal = this.actorService.findByPrincipal();

		// Call the repository
		if (principal instanceof Member)
			res = this.requestRepository.findByMember(principal.getId());
		else if (principal instanceof Brotherhood)
			res = this.requestRepository.findByBrotherhood(principal.getId());

		// Return the results in the collection
		return res;
	}

	public Collection<Request> findByParade(final int paradeId) {
		Collection<Request> res;

		res = this.requestRepository.findByParade(paradeId);

		return res;
	}

	public void deleteRequestWithoutParade(final int paradeId) {

		Brotherhood principal;

		// Comprobamos que la persona logueda es un brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		for (final Request r : this.findByParade(paradeId))
			this.requestRepository.delete(r);
		Assert.isTrue(this.findByParade(paradeId).isEmpty());
	}
	public void deleteByUserDropOut(final Request request) {

		this.requestRepository.delete(request);
	}

}

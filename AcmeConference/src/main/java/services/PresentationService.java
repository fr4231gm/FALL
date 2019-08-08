package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PresentationRepository;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationRepository presentationRepository;

	public Presentation create() {
		Presentation res;

		res = new Presentation();

		return res;
	}

	public Presentation save(Presentation presentation) {
		Presentation res;

		res = this.presentationRepository.save(presentation);

		return res;
	}

	public Presentation findOne(int presentationId) {
		return this.presentationRepository.findOne(presentationId);
	}

	public Collection<Presentation> findAll() {
		Collection<Presentation> result;

		result = this.presentationRepository.findAll();

		return result;
	}
	
	public Collection<Presentation> findPresentationsByConferenceId(int conferenceId){
		return this.presentationRepository.findPresentationsByConferenceId(conferenceId);
	}
	
	public void delete(Presentation presentation){
		this.presentationRepository.delete(presentation);
	}
}

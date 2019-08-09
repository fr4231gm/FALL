package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PanelRepository;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	@Autowired
	private PanelRepository panelRepository;

	public Panel create() {
		Panel res;

		res = new Panel();

		return res;
	}

	public Panel save(Panel panel) {
		Panel res;

		res = this.panelRepository.save(panel);

		return res;
	}

	public Panel findOne(int panel) {
		return this.panelRepository.findOne(panel);
	}

	public Collection<Panel> findAll() {
		Collection<Panel> result;

		result = this.panelRepository.findAll();

		return result;
	}
	
	public Collection<Panel> findPanelsByConferenceId(int conferenceId){
		return this.panelRepository.findPanelsByConferenceId(conferenceId);
	}
	
	public void delete(Panel panel){
		this.panelRepository.delete(panel);
	}
}

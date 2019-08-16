package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelRepository;
import domain.Administrator;
import domain.Conference;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	@Autowired
	private PanelRepository panelRepository;
	
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
    private UtilityService utilityService;
	
	public Panel create(int conferenceId) {
		Administrator principal;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(conference));
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Panel res;

		res = new Panel();
		res.setConference(conference);

		return res;
	}

	public Panel save(Panel panel) {
		Panel res;

		Assert.isTrue(!this.utilityService.checkUrls(panel.getAttachments()));
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

	public Collection<Panel> findPanelsByConferenceId(int conferenceId) {
		return this.panelRepository.findPanelsByConferenceId(conferenceId);
	}

	public void delete(Panel panel) {
		Administrator principal;
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		this.panelRepository.delete(panel);
	}
}

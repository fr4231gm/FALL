package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Section;
import domain.Tutorial;
import repositories.SectionRepository;

@Service
@Transactional
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;


    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private ConferenceService conferenceService;

    public Section create(int tutorialId) {
    
        Administrator principal;
        Tutorial tutorial;
        Collection<Section> sections;

        tutorial = this.tutorialService.findOne(tutorialId);
        Assert.isTrue(!this.conferenceService.findPastConferences().contains(tutorial.getConference()));

        sections = tutorial.getSections();

        principal = this.administratorService.findByPrincipal();
        Assert.notNull(principal);

        Section res;

        res = new Section();
        sections.add(res);
        tutorial.setSections(sections);

        return res;
    }

    public Section save(Section section) {
    
        Section res;

        res = this.sectionRepository.save(section);     

        return res;
    }

    public Section findOne(int sectionId) {
    
        return this.sectionRepository.findOne(sectionId);
    }

    public Collection<Section> findAll() {
    
        Collection<Section> result;
    

        result = this.sectionRepository.findAll();

        return result;
    }

    public void delete(Section section) {
    
        Administrator principal;

        principal = this.administratorService.findByPrincipal();
        Assert.notNull(principal);

        this.sectionRepository.delete(section);
    }
}

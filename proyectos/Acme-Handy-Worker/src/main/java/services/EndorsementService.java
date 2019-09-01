
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Actor;
import domain.Configuration;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private EndorsementRepository	endorsementRepository;
	
	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	public EndorsementService() {
		super();
	}

	public Endorsement create() {
		Endorsement result = new Endorsement();
		
		Actor c;
		c = this.actorService.findByPrincipal();
		Assert.isTrue(c instanceof Customer || c instanceof HandyWorker);
		if(c instanceof Customer){
			Customer c2 = this.customerService.findByPrincipal();
			result.setCreator(c2);
		}
		if(c instanceof HandyWorker){
			HandyWorker c2 = this.handyWorkerService.findByPrincipal();
			result.setCreator(c2);
		}
		Assert.notNull(result);
		result.setMoment(new Date(System.currentTimeMillis()-1));
		return result;
	}

	public Collection<Endorsement> findPerCreator(final Integer id) {
		Assert.isTrue(id != 0);
		return this.endorsementRepository.findEndorsementCreator(id);
	}

	public Collection<Endorsement> findPerReceiver(final Integer id) {
		Assert.isTrue(id != 0);
		return this.endorsementRepository.findEndorsementReceiver(id);
	}

	public Endorsement findOne(final int id) {
		Assert.isTrue(id != 0);
		return this.endorsementRepository.findOne(id);
	}

	public Endorsement save(final Endorsement e) {
		Assert.notNull(e);
		Actor principal = this.actorService.findByPrincipal();
		if(this.actorService.checkSpam(e.getComments())){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		
		return this.endorsementRepository.save(e);
	}

	public void delete(final Endorsement e) {
		Assert.notNull(e);
		this.endorsementRepository.delete(e);
	}

	public Collection<Endorsement> findAll() {
		return this.endorsementRepository.findAll();
	}
	
    public Collection<Integer> positiveAndNegativeWords(final Endorsement endorsement) {
        Assert.notNull(endorsement);
        // indexOf(...) is case sensitive
        final String text = endorsement.getComments().toLowerCase();

        // The number of positive words will be stored in the element with index 0, and negative ones in the element with index 1
        final List<Integer> posAndNegWords = new ArrayList<Integer>();
        posAndNegWords.add(0);
        posAndNegWords.add(0);

        Integer countPos = 0;
        Integer countNeg = 0;
        List<Configuration> config = new ArrayList<Configuration>(this.configurationService.findAll());
        final String posWords = "";
        final String negWords = "";
        for(int i = 0; i< config.size(); i++){
        	posWords.concat(config.get(i).getPositiveWords() + "/n");
        	negWords.concat(config.get(i).getNegativeWords() + "/n");
        }

        final String[] arrayPosWords = posWords.toLowerCase().split("\n");
        final String[] arrayNegWords = negWords.toLowerCase().split("\n");

        for (final String posWord : arrayPosWords) {
            final String[] split = text.toLowerCase().split(posWord);
            countPos += split.length - 1;
        }

        for (final String negWord : arrayNegWords) {
            final String[] split = text.toLowerCase().split(negWord);
            countNeg += split.length - 1;
        }

        posAndNegWords.set(0, countPos);
        posAndNegWords.set(1, countNeg);
        return posAndNegWords;
    }

}

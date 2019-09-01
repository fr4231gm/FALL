
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Endorsement;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	@Autowired
	private EndorsementService	endorsementService;


	public EndorserService() {
		super();
	}

	public Collection<Integer> countTotalPositiveAndNegativeWordsPerEndorser(final Endorser endorser) {
		Assert.notNull(endorser);
		Integer p = 0;
		Integer n = 0;
		final List<Integer> posNegWords = new ArrayList<Integer>();
		final Collection<Endorsement> endorsements = this.endorsementService.findPerReceiver(endorser.getId());
		for (final Endorsement e : endorsements) {
			final List<Integer> l = new ArrayList<Integer>(this.endorsementService.positiveAndNegativeWords(e));
			p += l.get(0);
			n += l.get(1);
		}
		posNegWords.add(p);
		posNegWords.add(n);
		return posNegWords;
	}

}

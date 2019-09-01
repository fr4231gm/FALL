
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoutRepository;
import security.LoginService;
import security.UserAccount;
import domain.Shout;

@Service
@Transactional
public class ShoutService {

	// Managed Repository
	@Autowired
	private ShoutRepository	shoutRepository;


	// Constructors
	public ShoutService() {
		super();
	}

	// Simple CRUD methods
	public Shout create() {
		Shout result;
		final UserAccount userAccount;
		final String username;
		userAccount = LoginService.getPrincipal();
		username = userAccount.getUsername();
		result = new Shout();
		result.setUsername(username);
		result.setLink("");
		result.setText("");
		return result;
	}

	public void save(final Shout Shout) {
		this.shoutRepository.save(Shout);
	}

	public Shout findOne(final int Id) {
		Shout result;
		result = this.shoutRepository.findOne(Id);
		return result;
	}

	public Collection<Shout> findAll() {
		Collection<Shout> result;
		result = this.shoutRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Map<String, Integer> computeStatistics() {
		final Map<String, Integer> res;
		final int all, countLong, countShort;

		all = this.shoutRepository.countAllShouts();
		countShort = this.shoutRepository.countShortShouts();
		countLong = this.shoutRepository.countLongShouts();

		res = new HashMap<String, Integer>();
		res.put("count.all.shouts", all);
		res.put("count.short.shouts", countShort);
		res.put("count.long.shouts", countLong);

		return res;
	}

	/*
	 * public Shout longestShout() {
	 * final Shout longestShout;
	 * longestShout = this.shoutRepository.MaxShout();
	 * return longestShout;
	 * }
	 */
}

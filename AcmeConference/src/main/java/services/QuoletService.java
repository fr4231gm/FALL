
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.QuoletRepository;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository	quoletRepo;


	public Quolet findOne(final int id) {
		final Quolet q = this.quoletRepo.findOne(id);
		return q;
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepo.findAll();
	}

}

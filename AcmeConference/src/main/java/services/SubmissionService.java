
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Author;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private AuthorService			authorService;


	public Collection<Submission> findByAuthor() {
		final Author a = this.authorService.findByPrincipal();
		Assert.notNull(a);

		final Collection<Submission> subs = this.submissionRepository.findByAuthorId(a.getId());

		return subs;
	}

}

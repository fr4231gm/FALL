
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Author;
import domain.Paper;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ConferenceService		conferenceService;


	public Collection<Submission> findByAuthor() {
		final Author a = this.authorService.findByPrincipal();
		Assert.notNull(a);

		final Collection<Submission> subs = this.submissionRepository.findByAuthorId(a.getId());

		return subs;
	}

	public Submission findOne(final int submissionId) {
		Submission s;
		s = this.submissionRepository.findOne(submissionId);
		Assert.notNull(s);
		return s;
	}

	public Submission create(final Integer conferenceId) {
		final Submission s = new Submission();
		s.setAuthor(this.authorService.findByPrincipal());
		s.setConference(this.conferenceService.findOne(conferenceId));
		s.setTicker(this.generateTicker(s));
		s.setStatus("UNDER-REVIEW");
		s.setMoment(new Date(System.currentTimeMillis() + 1));
		final Paper p = new Paper();
		p.setCameraReadyPaper(false);
		s.setPaper(p);

		return s;
	}
	private String generateTicker(final Submission s) {
		String res = "";

		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i < 4; i++) {
			final int randomNumber = (int) Math.floor(Math.random() * (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}
		String middleName;
		final String name = s.getAuthor().getName();
		if (s.getAuthor().getMiddleName() != null)
			middleName = s.getAuthor().getMiddleName();
		else
			middleName = "X";

		final String surname = s.getAuthor().getSurname();

		// Adding formatted date to alphanumeric code
		res = name.charAt(0) + middleName.charAt(0) + surname.charAt(0) + "-" + res;

		return res;
	}

	public Submission save(final Submission s) {
		Submission result;
		Assert.isTrue(s.getAuthor().getId() == this.authorService.findByPrincipal().getId());
		final Date actual = new Date(System.currentTimeMillis() - 1);
		Assert.isTrue(s.getConference().getSubmissionDeadline().after(actual));
		s.setMoment(actual);
		result = this.submissionRepository.save(s);
		return result;
	}
}

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import utilities.internal.DatabaseUtil;
import domain.Comment;
import domain.Conference;
import domain.Reviewer;

@Service
@Transactional
public class ConferenceService {

	// Repositorios
	@Autowired
	private ConferenceRepository conferenceRepository;

	public Conference update(Conference conference){
		Conference res;
		
		res = this.conferenceRepository.save(conference);
		
		return res;
	}
	
	public Collection<Conference> findRunningConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository.findRunningConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Collection<Conference> findForthcomingConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository
				.findForthcomingConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Collection<Conference> findPastConferences() {
		Collection<Conference> conferences;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		conferences = this.conferenceRepository.findPastConferences(actual);
		Assert.notNull(conferences);

		return conferences;
	}

	public Conference findOne(final int conferenceId) {
		final Conference c = this.conferenceRepository.findOne(conferenceId);
		return c;
	}

	public Collection<Conference> searchConferenceAnonymousRunning(
			final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterRunning(keyword, actual);
		return res;
	}

	public Collection<Conference> searchConferenceAnonymousForthcomming(
			final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterForthcomming(keyword, actual);
		return res;
	}

	public Collection<Conference> searchConferenceAnonymousPast(
			final String keyword) {
		Collection<Conference> res;
		final Date actual = new Date(System.currentTimeMillis() - 1);
		res = this.conferenceRepository.filterPast(keyword, actual);
		return res;
	}

	public Collection<Conference> findAll() {
		Collection<Conference> result;
		result = this.conferenceRepository.findAll();
		return result;
	}
	
	public Collection<Reviewer> getCompatibleReviewers(Conference conference) {
		List<Reviewer> res;
		String text = conference.getTitle() + " " + conference.getSummary();
		
		try {
			final DatabaseUtil databaseUtil = new DatabaseUtil();
			databaseUtil.initialise();
			final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(databaseUtil.getEntityManager());
			databaseUtil.getEntityManager().getTransaction().begin();
			fullTextEntityManager.createIndexer().startAndWait();
			res = this.reviewersKeywordsSearch(text, fullTextEntityManager);
			res =  res.subList(0, Math.min(res.size(), 3));
		} catch (final Throwable oops) {
			res = new ArrayList<Reviewer>();
		}
	
		return res;
		
	}

	@SuppressWarnings("unchecked")
	public List<Reviewer> reviewersKeywordsSearch(final String keywordSearch, final FullTextEntityManager fullTextEntityManager) {
		List<Reviewer> result;
		
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Reviewer.class).get();
		final org.apache.lucene.search.Query query = qb.keyword().onFields("keywords").matching(keywordSearch).createQuery();
		final Query fullSearchQuery = fullTextEntityManager.createFullTextQuery(query, Reviewer.class);
		result = fullSearchQuery.getResultList();
		
		return result;
	}

	public Collection<Conference> findByCategoryId(int categoryId) {
		return this.conferenceRepository.findConferencesByCategoryId(categoryId);
	}

	public void flush() {
		this.conferenceRepository.flush();
		
	}

	public void addCommentToConference(Conference conference, Comment comment){
		Collection<Comment> comments;
		
		comments = conference.getComments();
		System.out.println("Conference: " + conference);
		comments.add(comment);
		conference.setComments(comments);
		
		this.update(conference);
	}

}


package services;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Author;
import domain.Conference;
import domain.Configuration;
import domain.Paper;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ConferenceService		conferenceService;


	public Administrator save(final Administrator admin) {
		Administrator result, principal;
		Assert.notNull(admin);

		// Comprobamos que la persona logueda es un admin
		// Solo un admin crea otros admin
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		// Si el administrador no esta en la base de datos (es una creacion, no
		// una actualizacion)
		// codificamos la contrasena de su cuenta de usuario
		if (admin.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

			admin.getUserAccount().setPassword(passwordEncoder.encodePassword(admin.getUserAccount().getPassword(), null));
		} else
			Assert.isTrue(principal.getUserAccount() == admin.getUserAccount());
		// Guardamos en la bbdd
		result = this.administratorRepository.save(admin);

		return result;
	}

	// Método para encontrar un administrador a traves de su ID
	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;

	}

	// Devuelve todos los administradores de la bbdd
	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	// Metodo que devuelve el administrador logueado en el sistema
	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findAdministratorByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	// Metodo que devuelve un administrador a traves del ID de su cuenta de
	// usuario Servira para el metodo findByPrincipal()
	public Administrator findAdministratorByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Administrator result;

		result = this.administratorRepository.findAdministratorByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {
		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
	}

	public void flush() {
		this.administratorRepository.flush();
	}

	public Double[] SubmissionsPerConference() {
		return this.administratorRepository.SubmissionsPerConference();
	}

	public Double[] RegistrationsPerConference() {
		return this.administratorRepository.RegistrationsPerConference();
	}

	public Double[] ConferencesFeesStats() {
		return this.administratorRepository.ConferencesFeesStats();
	}

	public Double[] ConferencesDaysStats() {
		return this.administratorRepository.ConferencesDaysStats();
	}

	public Double[] ConferencesPerCategory() {
		return this.administratorRepository.ConferencesPerCategory();
	}

	public Double[] CommentsPerConference() {
		return this.administratorRepository.CommentsPerConference();
	}

	public Double[] CommentsPerActivity() {
		return this.administratorRepository.CommentsPerActivity();
	}

	public Double DraftQulpsRatio() {
		final Double d = this.administratorRepository.DraftQulpsRatio();
		return d;
	}

	public Double qulpsPerAdministratorRatio() {
		final Double d = this.administratorRepository.QulpsPerAdministratorRatio();
		return d;
	}

	public Double[] qulpsPerConferenceStats() {
		return this.administratorRepository.QulpsPerConferenceStats();
	}

	public void computeScore() {
		final Collection<Author> autores = this.authorService.findAll();
		final HashMap<Author, Double> map = new HashMap<>();
		Double maxCount = 1.;
		final String[] buzzWords = this.computeBuzzWords().split(",");
		for (final Author autor : autores) {
			final Collection<Paper> papers = this.submissionService.findCameraReadyPapersByAuthorId(autor.getId());
			String allWords = "";
			for (final Paper paper : papers)
				allWords += paper.getTitle() + " " + paper.getSummary() + " ";
			final String[] wordsArray = allWords.split(" ");
			for (final String word : wordsArray)
				if (Arrays.asList(buzzWords).contains(word))
					if (map.containsKey(word)) {
						final Double count = map.get(word);
						map.put(autor, count + 1);
						if (count >= maxCount)
							maxCount = count;
					} else
						map.put(autor, 1.);
		}
		for (final Entry<Author, Double> entry : map.entrySet()) {
			final Author autor = entry.getKey();
			autor.setScore(map.get(autor) / maxCount);
			this.authorService.updateScore(autor);
		}
	}

	public String computeBuzzWords() {
		final Administrator principal = this.findByPrincipal();
		Assert.notNull(principal);
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		final Date d = cal.getTime();
		final Collection<Conference> conferences = this.conferenceService.findConferencesAfter(d);
		String allWords = "";
		//Obtenemos todas las palabras
		for (final Conference conference : conferences)
			allWords += conference.getTitle() + " " + conference.getSummary() + " ";
		//Borramos las voidWords
		final Collection<Configuration> configurations = this.configurationService.findAll();
		for (final Configuration config : configurations) {
			final String[] voidWords = config.getVoidWords().split(",");
			for (final String voidWord : voidWords)
				allWords = this.removeWord(allWords, voidWord);
		}
		final String[] wordsArray = allWords.split(" ");
		int maxCount = 1;
		final HashMap<String, Integer> map = new HashMap<>();
		for (String word : wordsArray) {
			word = word.toLowerCase();
			if (map.containsKey(word)) {
				final int count = map.get(word);
				map.put(word, count + 1);
				if (count >= maxCount)
					maxCount = count;
			} else
				map.put(word, 1);
		}

		final Double corte = Math.max(1.1, maxCount * 0.8);
		String buzzWords = "";
		for (final Entry<String, Integer> entry : map.entrySet())
			if (entry.getValue() >= corte)
				buzzWords += (entry.getKey()) + ", ";

		for (final Configuration config : configurations) {
			config.setBuzzWords(buzzWords);
			this.configurationService.save(config);
		}
		return buzzWords;

	}

	public String removeWord(String string, String word) {
		if (string.contains(word)) {
			word = " " + word + " ";
			string = string.replaceAll(word, " ");
		}
		return string;
	}

}

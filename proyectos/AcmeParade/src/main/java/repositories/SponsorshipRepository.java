package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {
	//Find Sponsorship from Sponsor
	@Query("select s from Sponsorship s where s.sponsor.id = ?1")
	Collection<Sponsorship> findSponsorshipsBySponsorId (int sponsorId);
	
	//Find Sponsorship from CreditCard
	@Query("select s from Sponsorship s where s.creditCard.id = ?1")
	Collection<Sponsorship> findSponsorshipsByCreditCardId (int creditcardId);
	
	//Find Sponsorship from Parade
	@Query("select s from Sponsorship s where s.parade.id = ?1")
	Collection<Sponsorship> findSponsorshipsByParadeId (int paradeId);
	/*
	if (sponsorship.getCreditCard().getExpirationYear() > anyo 
			|| (sponsorship.getCreditCard().getExpirationYear()==anyo && sponsorship.getCreditCard().getExpirationMonth() > mes )){
*/
	//Find Sponsorships with expired creditcards
	@Query("select s from Sponsorship s where (s.isEnabled = true and (s.creditCard.expirationYear < ?1 or (s.creditCard.expirationYear = ?1 and s.creditCard.expirationMonth < ?2)))")
	Collection<Sponsorship> findSponsorshipsWithExpiredCreditCards(int anyo, int mes);
	
}

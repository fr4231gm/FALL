package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;
import forms.EnrolmentForm;

@Service
@Transactional
public class EnrolmentService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private EnrolmentRepository enrolmentRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired 
	private MemberService memberService;
	
	@Autowired 
	private ActorService actorService;
	
	
	// Constructor ------------------------------------------------------------
	public EnrolmentService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	// CREATE
	public Enrolment create(int brotherHoodId){
		//checking that the actor logged is a member
		Member principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);
		
		//Creating the new enrolment
		Enrolment res;
		res = new Enrolment();
		
		//setting the parameters
		res.setBrotherhood(this.brotherhoodService.findOne(brotherHoodId));
		res.setMember(principal);
		res.setMoment(new Date(System.currentTimeMillis()-1)); // rest 1 millisecond to avoid problems with @Past annotation
		
		
		//Return the created enrolment
		return res;
	}
	
	// SAVE
	public Enrolment save(Enrolment enrolment){
		//Actor logged
		Actor principal = this.actorService.findByPrincipal();
		
		//Checking that the actor logged is part of the enrolment
		//A brotherhood only can change the enrolment if dropOutMoment is null
		Assert.isTrue(enrolment.getMember().equals(principal) || enrolment.getBrotherhood().equals(principal));
		
		//Checking if its new
		if(enrolment.getId() == 0){
			//creation date its actual date
			enrolment.setMoment(new Date(System.currentTimeMillis()-1)); // rest 1 millisecond to avoid problems with @Past annotation
		}
		
		//Save the enrolment
		Enrolment res = this.enrolmentRepository.save(enrolment);
		
		return res;
	}
	
	// Other business methods -------------------------------------------------
	
	// FIND ALL
	public Collection<Enrolment> findAll(){
		//Initialize variable
		Collection<Enrolment> res;
		
		//Actor logged
		Actor principal = this.actorService.findByPrincipal();
		
		//Checking that the actor logged is a brotherhood or a member
		Assert.isTrue(principal instanceof Member || principal instanceof Brotherhood || principal instanceof Administrator);
		
		//Returns all the enrolments
		res = this.enrolmentRepository.findAll();
		return res;
	}
	
	// FIND ONE
	public Enrolment findOne(int enrolmentId){
		//Initialize variable
		Enrolment res;
		
		//Actor logged
		Actor principal = this.actorService.findByPrincipal();
		
		//Checking that the actor logged is a brotherhood or a member
		Assert.isTrue(principal instanceof Member || principal instanceof Brotherhood);
		
		res = this.enrolmentRepository.findOne(enrolmentId);
		return res;
	}
	
	//FIND ONE TO FAIL
	public Enrolment findOneToFail(final int enrolmentId){
		Enrolment res;
		
		res = this.enrolmentRepository.findOne(enrolmentId);
		
		return res;
	}
	
	// FIND ALL ENROLMENTS BY PRINCIPAL
	public Collection<Enrolment> findAllEnrolmentsByPrincipal(){
		//Initialize variable
		Collection<Enrolment> res;
		
		//Actor logged
		Actor principal = this.actorService.findByPrincipal();
		
		//Checking that the actor logged is a brotherhood or a member
		Assert.isTrue(principal instanceof Member || principal instanceof Brotherhood);
		
		//if the actor logged is a member return all his enrolments
		if(principal instanceof Member){
			res = this.enrolmentRepository.findByMember(principal.getId());
		}
		//if the actor logged is a brotherhood return all the enrolments that belongs to this brotherhood
		//Only returns the enrolments which the drop out moments its null
		else {
			res = this.enrolmentRepository.findByBrotherhood(principal.getId());
		}
		return res;
	}
	
	// FIND ENROLMENTS WITHOUT POSITION BY BROTHERHOOD
	public Collection<Enrolment> findEnrolmentsWhithoutPositionByPrincipal(){
		//Initialize variable
		Collection<Enrolment> res;
		
		//Brotherhood logged
		Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		
		//Gets the enrolments without position that belongs to this brotherhood from the repository
			res = this.enrolmentRepository.findEnrolmentsWithoutPositionByBrotherhood(principal.getId());
		return res;
	}
	
	// FIND ENROLMENTS WITH POSITION BY BROTHERHOOD
	public Collection<Enrolment> findEnrolmentsWhithPositionByPrincipal(){
		//Initialize variable
		Collection<Enrolment> res;
		
		//Brotherhood logged
		Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		
		//Gets the enrolments without position that belongs to this brotherhood from the repository
			res = this.enrolmentRepository.findEnrolmentsWithPositionByBrotherhood(principal.getId());
		return res;
	}
	
	//Drop Out
	public Enrolment dropOut(int enrolmentId){
		//Initialize variable
		Enrolment res;
		Enrolment enrolment;
		
		//Brotherhood logged
		Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		
		//Gets the enrolment
		enrolment = this.findOne(enrolmentId);
		enrolment.setDropOutMoment(new Date(System.currentTimeMillis()-1));
		enrolment.setPosition(null);
		res = this.save(enrolment);
		
		return res;
	}
	
	//Leave
	public Enrolment leave(int enrolmentId){
		//Initialize variable
		Enrolment res;
		Enrolment enrolment;
		
		//Brotherhood logged
		Member principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);
		
		//Gets the enrolment
		enrolment = this.findOne(enrolmentId);
		enrolment.setDropOutMoment(new Date(System.currentTimeMillis()-1));
		enrolment.setPosition(null);
		res = this.save(enrolment);
		
		return res;
	}
	
	// ENROLL
	// creates the enrolment or re-enroll a member if the enrolment its created
	public Enrolment enroll(int brotherhoodId){
		//Initialize variable
		Enrolment res;

		//checking that the actor logged is a member
		Member principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);
		
		//check if the member is enrolling for first time or if it is re-enrolling
		Enrolment aux = this.enrolmentRepository.findByActorAndBrotherhood(principal.getId(), brotherhoodId);
		if(aux == null){
			// if its the first enrolment to this brotherhoods creates it
			Enrolment created = this.create(brotherhoodId);
			res = this.save(created);
		} else {
			// if its not the first enrolment to this brotherhoods re-enrolls on it deleting the drop out date
			aux.setDropOutMoment(null);
			res = this.save(aux);
		}
		return res;
	}
	
	//Constructs a EnrolmentForm Object from a Enrolment object
	public EnrolmentForm construct(int enrolmentId){
		//Initialize variable
		EnrolmentForm res = new EnrolmentForm();
		Enrolment enrolment = this.findOne(enrolmentId);
		
		//Set Enrolment properties into EnrolmentForm
		res.setId(enrolment.getId());
		res.setPosition(enrolment.getPosition());
		
		//Return the form
		return res;
	}
	
	//Reconstructs the Enrolment Object from the EnrolmentForm
	public Enrolment reconstruct(EnrolmentForm enrolmentForm){
		//Initialize variable
		Enrolment res;
		
		//Set Enrolment properties into EnrolmentForm
		res = this.findOne(enrolmentForm.getId());
		res.setPosition(enrolmentForm.getPosition());
		
		//Return the Enrolment
		return res;
	}

	public Collection<Enrolment> findByPosition(Position position) {
		// Initialize variables
		Collection<Enrolment> res;
		
		//Call to the repostiory
		res = this.enrolmentRepository.findByPositionId(position.getId());
		
		return res;
	}

	public void deleteByUserDropOut(Enrolment enrolment) {
		this.enrolmentRepository.delete(enrolment);
	}
	
	
	
}
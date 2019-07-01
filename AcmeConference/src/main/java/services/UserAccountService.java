package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Consturctor ------------------------------------------------------------
	public UserAccountService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	//CREATE
	public UserAccount create (final String authority){
		
		// Create return object
		UserAccount res = new UserAccount();
		
		// Creating a new authority 
		Authority auth = new Authority();
		
		// Creating a collection of authorities
		final Collection<Authority> authorities = new ArrayList<Authority>();
		
		// Inserting the new authority
		authorities.add(auth);
		
		// Setting the new authority
		res.setAuthorities(authorities);
		
		// Return result
		return res;
	}
	
	//FINDONE
	public UserAccount findOne (final int userAccountId){
		UserAccount res;
		res = this.userAccountRepository.findOne(userAccountId);
		return res;
	}
	
	//FINDALL
	public Collection<UserAccount> findAll (){
		Collection <UserAccount> res = new ArrayList<>();
		res.addAll(this.userAccountRepository.findAll());
		return res;
	}
	
	//SAVE
	public UserAccount save(UserAccount userAccount){
		
		Assert.notNull(userAccount);
		
		UserAccount res, encoded;
		
		if (userAccount.getId() == 0){
			// Saving by the first time
			
			// Encoding the userAccount
			encoded = UserAccountService.encodedPassword(userAccount);
			
		}else{
			// Updating the userAccount
			
			// Encoding the userAccount
			encoded = UserAccountService.encodedPassword(userAccount);
			
		}
		// Calling the repository to save
		res = this.userAccountRepository.save(encoded);
		
		return res;
	}
	
	// Other business  methods ------------------------------------------------

	// Encode password - it's a method to encode password
	public static UserAccount encodedPassword (final UserAccount in){
		// Parameters
		Md5PasswordEncoder encoder;
		String encodedPassword;
		
		// Create a new instance of encoder
		encoder = new Md5PasswordEncoder();
		
		// Encoding
		encodedPassword = encoder.encodePassword(in.getPassword(), null);
		
		// Updating input's password
		in.setPassword(encodedPassword);
		
		// Returning the modified input
		return in;
	}
	
	// Find UserAccount by Username
	public UserAccount findByUsername(final String username){
		UserAccount res;
		res = this.userAccountRepository.findByUsername(username);
		return res;
	}
	
}

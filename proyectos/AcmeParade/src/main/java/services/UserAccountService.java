
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	// Constructors -----------------------------------------------------------	
	
	public UserAccountService() {
		super();		
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public UserAccount create(final String authorityName) {
		Assert.notNull(authorityName);
		UserAccount result = null;
		result = new UserAccount();
		result.setAuthorities(new ArrayList<Authority>());
		final Authority authority = new Authority();
		authority.setAuthority(authorityName);
		result.getAuthorities().add(authority);
		result.setEnabled(true);
		return result;
	}

	
	public UserAccount findOne(final int UserAccountId) {
		UserAccount result = null;
		result = this.userAccountRepository.findOne(UserAccountId);
		return result;	
	}
	
	public Collection<UserAccount> findAll(){
		Collection<UserAccount> result = null;
		result = this.userAccountRepository.findAll();
		return result;
	}
	
	public UserAccount save(final UserAccount userAccount){
		Assert.notNull(userAccount);
		return this.userAccountRepository.save(userAccount);
	}
	
	public void delete(final int id) {
		this.userAccountRepository.delete(id);
	}

	public Object findByUserName(String username) {
		UserAccount result = null;
		result = this.userAccountRepository.findByUsername(username);
		return result;	
	}
	
}
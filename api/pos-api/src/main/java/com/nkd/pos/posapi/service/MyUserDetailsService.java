package com.nkd.pos.posapi.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.entity.MyUserDetails;
import com.nkd.pos.posapi.entity.UserRole;
import com.nkd.pos.posapi.entity.Usr;

@Service
public class MyUserDetailsService implements UserDetailsService{

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
		Optional<Usr> Usr = userService.getByUsername(username);
		
		Usr.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		Usr user = Usr.get();
		List<UserRole> userRoles = userRoleService.findByUser(Usr.get());
		String rolesStr = "";
		Iterator<UserRole> userRolesIter = userRoles.iterator();
		while(userRolesIter.hasNext()) {
	    	rolesStr += (userRolesIter.next()).getRole().getRole();
			if(userRolesIter.hasNext()) {
				rolesStr +=",";
			}
		}
		
		Usr.get().setRoles(rolesStr);
		
		
		return Usr.map(MyUserDetails::new).get();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}

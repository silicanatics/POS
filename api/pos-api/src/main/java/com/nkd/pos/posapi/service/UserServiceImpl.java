package com.nkd.pos.posapi.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.EntitiyRepository;
import com.nkd.pos.posapi.dao.EntityAddressRepository;
import com.nkd.pos.posapi.dao.UserRepository;
import com.nkd.pos.posapi.dao.UserRoleRepository;
import com.nkd.pos.posapi.entity.Customer;
import com.nkd.pos.posapi.entity.DBEntity;
import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.EntityType;
import com.nkd.pos.posapi.entity.Role;
import com.nkd.pos.posapi.entity.UserRole;
import com.nkd.pos.posapi.entity.Usr;

@Service
public class UserServiceImpl implements UserService{

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	EntityManager entityManager;
	@Autowired
	UserRepository usrRepository;
	@Autowired
	EntitiyRepository entitiyRepository;
	@Autowired
	EntityAddressRepository entityAddressRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public Usr getById(int id) {
		
		return usrRepository.getById(id);
	}

	@Override
	public Optional<Usr> getByUsername(String username) {
		// TODO Auto-generated method stub
		return usrRepository.findByusername(username);
	}

	@Override
	public Usr save(Usr user) {
		// TODO Auto-generated method stub
		EntityType entityType = new EntityType();
		entityType.setId(3);
		DBEntity entity = user.getdBEntity();
		entity.setEntityType(entityType);
		entity = entitiyRepository.saveAndFlush(entity);
		EntityAddress EA = new EntityAddress();
		EA.setDbEntity(entity);
		EA.setAddressText("Shop....");
		
		entityAddressRepository.saveAndFlush(EA);
		user.setdBEntity(entity);
		user.setPassword("{bcrypt}"+encoder.encode(user.getPassword()));
		//user.setRoles(roles);
		Usr usr = usrRepository.saveAndFlush(user);
		UserRole ur = new UserRole();
		ur.setUser(usr);
		Role role = new Role();
		role.setId(2);
		ur.setRole(role);
		userRoleRepository.saveAndFlush(ur);
		return usr;
	}

	@Override
	public EntityAddress getUserAdress(Usr User) {
		
		String qlString = "select ea from EntityAddress ea where ea.dbEntity.id = " + User.getdBEntity().getId();
		EntityAddress address = entityManager.createQuery(qlString, EntityAddress.class)
				.getSingleResult();
		
		return address;
	}

}

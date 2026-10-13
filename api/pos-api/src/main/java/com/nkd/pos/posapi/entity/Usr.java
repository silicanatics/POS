package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="Users")
public class Usr implements UserDetails, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@OneToOne
	@JoinColumn(name="entityId")
	private DBEntity dBEntity;
	@Transient
	private EntityAddress entityAddress;
	@Column(nullable = true)
	private String userId;
	@Column(unique = true)
	private String username;
	private String password;
	private boolean enabled;
	private String roles;
	@Transient
	private String token;
	public Usr() {
		super();
	}

	public Usr(int id, DBEntity dBEntity, String userId, String username, String password, boolean enabled,
			String roles) {
		super();
		this.id = id;
		this.dBEntity = dBEntity;
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DBEntity getdBEntity() {
		return dBEntity;
	}

	public void setdBEntity(DBEntity dBEntity) {
		this.dBEntity = dBEntity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

	public EntityAddress getEntityAddress() {
		return entityAddress;
	}

	public void setEntityAddress(EntityAddress entityAddress) {
		this.entityAddress = entityAddress;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return enabled;
	}
	@Override
	public boolean isAccountNonLocked() {
	
		return enabled;
	}
	@Override
	public boolean isCredentialsNonExpired() {

		return enabled;
	}
	@Override
	public boolean isEnabled() {
	
		return enabled;
	}

	@Override
	public String toString() {
		return "Usr [id=" + id + ", dBEntity=" + dBEntity + ", entityAddress=" + entityAddress + ", userId=" + userId
				+ ", username=" + username + ", password=" + password + ", enabled=" + enabled + ", roles=" + roles
				+ ", token=" + token + "]";
	}
	


	
		
}

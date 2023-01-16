package com.org.onehome.login.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.org.onehome.model.Role;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@Getter
@Setter
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	public String getUserName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	@Column(nullable = false)
	public String name;

	@Column(nullable = false)
	public String password;
	
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "user_role_id")	
	public Role role;

	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}


	@CreationTimestamp
	public LocalDateTime createdAt;

	@UpdateTimestamp
	LocalDateTime updatedAt;

}

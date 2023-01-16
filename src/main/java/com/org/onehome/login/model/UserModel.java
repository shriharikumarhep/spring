package com.org.onehome.login.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_user",uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name" })
})
@Getter
@Setter
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(nullable = false)
	public String name;

	@Column(nullable = false)
	public String password;
	
	@CreationTimestamp
	LocalDateTime createdAt;
	
	@UpdateTimestamp
	LocalDateTime updatedAt;
	
	
}

package com.org.onehome.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.onehome.login.model.*;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	public UserModel findByName(String username);
	public UserModel getUserByNameAndPassword(String userName, String password);
}

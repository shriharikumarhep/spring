package com.org.onehome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.org.onehome.model.Authority;
import com.org.onehome.model.Role;

public interface AuthRepository extends JpaRepository<Authority, Long>{

}

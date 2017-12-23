package com.simpleproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleproj.model.SPUser;

@Repository
public interface SPUserRepository extends JpaRepository<SPUser, Long> {

	public SPUser findByLogin(String login);
}

package com.skooltest.entities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.auth.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	public Optional<User> findByUserNameAndIsActive(String userName, byte active);
	
	public Optional<User> findByIdAndIsActive(Integer userId, Byte active);
	

}

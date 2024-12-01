package com.skooltest.entities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.masters.MasterSubject;

@Repository
public interface MasterSubjectRepository extends JpaRepository<MasterSubject, Integer>{

	Optional<MasterSubject> findByName(String subject);

}

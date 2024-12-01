package com.skooltest.entities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.masters.MasterStatus;

@Repository
public interface MasterStatusRepository extends JpaRepository<MasterStatus, Integer>{

	Optional<MasterStatus> findByCode(String stage);

}

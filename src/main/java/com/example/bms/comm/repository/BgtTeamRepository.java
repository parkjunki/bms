package com.example.bms.comm.repository;

import com.example.bms.comm.entity.BgtTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgtTeamRepository extends JpaRepository<BgtTeam, String> { }

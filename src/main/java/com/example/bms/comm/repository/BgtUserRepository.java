package com.example.bms.comm.repository;

import com.example.bms.comm.entity.BgtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgtUserRepository extends JpaRepository<BgtUser, String> {
}

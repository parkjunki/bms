package com.example.bms.prfr.repository;

import com.example.bms.prfr.entity.BgtTcrd;
import com.example.bms.prfr.entity.BgtTcrdId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgtTcrdRepository extends JpaRepository<BgtTcrd, BgtTcrdId> {
}

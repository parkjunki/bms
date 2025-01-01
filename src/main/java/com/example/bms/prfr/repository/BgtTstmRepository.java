package com.example.bms.prfr.repository;

import com.example.bms.prfr.entity.BgtTstm;
import com.example.bms.prfr.entity.BgtTstmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgtTstmRepository extends JpaRepository<BgtTstm, BgtTstmId> {
}

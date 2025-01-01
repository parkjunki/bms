package com.example.bms.prfr.repository;

import com.example.bms.prfr.entity.BgtCbng;
import com.example.bms.prfr.entity.BgtCbngId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgtCbngRepository extends JpaRepository<BgtCbng, BgtCbngId> {
}

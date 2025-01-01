package com.example.bms.bdgt.repository;

import com.example.bms.bdgt.entity.BgtLimt;
import com.example.bms.bdgt.entity.BgtLimtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BgtLimtRepository extends JpaRepository<BgtLimt, BgtLimtId> {
    List<BgtLimt> findByBgtLimtIdBsnsYyAndBgtLimtIdTeamCd(String bsnsYy, String teamCd);
}

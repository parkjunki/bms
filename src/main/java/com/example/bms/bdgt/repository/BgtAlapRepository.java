package com.example.bms.bdgt.repository;

import com.example.bms.bdgt.entity.BgtAlap;
import com.example.bms.bdgt.entity.BgtAlapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BgtAlapRepository extends JpaRepository<BgtAlap, BgtAlapId> {
    List<BgtAlap> findByBgtAlapIdBsnsYyAndTeamCd(String bsnsYy, String teamCd);

    BgtAlap findTopByBgtAlapIdBsnsYyOrderByBgtAlapIdAlapNoDesc(String bsnsYy);
}

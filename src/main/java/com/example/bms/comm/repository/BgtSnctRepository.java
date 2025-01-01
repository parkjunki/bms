package com.example.bms.comm.repository;

import com.example.bms.comm.entity.BgtSnct;
import com.example.bms.comm.entity.BgtSnctId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BgtSnctRepository extends JpaRepository<BgtSnct, BgtSnctId> {
    List<BgtSnct> findByBgtSnctIdBsnsYyAndTeamCd(String bsnsYy, String teamCd);
}

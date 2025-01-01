package com.example.bms.bdgt.repository;

import com.example.bms.bdgt.entity.BgtTran;
import com.example.bms.bdgt.entity.BgtTranId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BgtTranRepository extends JpaRepository<BgtTran, BgtTranId> {
    BgtTran findTopByBgtTranIdBsnsYyOrderByBgtTranIdTranSrnDesc(String bsnsYy);

    List<BgtTran> findByBgtTranIdBsnsYyAndTeamCd(String bsnsYy, String teamCd);
}

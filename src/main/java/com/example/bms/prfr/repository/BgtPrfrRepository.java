package com.example.bms.prfr.repository;

import com.example.bms.prfr.entity.BgtPrfr;
import com.example.bms.prfr.entity.BgtPrfrId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BgtPrfrRepository extends JpaRepository<BgtPrfr, BgtPrfrId> {
    @Query("SELECT COALESCE(MAX(b.bgtPrfrId.prfrNo), 0) FROM BgtPrfr b WHERE b.bgtPrfrId.prfrYm = :prfrYm")
    Integer findMaxPrfrNoByPrfrYm(String prfrYm);

    @Query("SELECT b FROM BgtPrfr b WHERE b.bsnsYy = :bsnsYy AND b.bgtPrfrId.teamCd = :teamCd")
    Page<BgtPrfr> findByBsnsYyAndTeamCd(String bsnsYy, String teamCd, Pageable pageable);

    BgtPrfr findByBgtPrfrId(BgtPrfrId bgtPrfrId);
}

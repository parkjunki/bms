package com.example.bms.bdgt.repository;

import com.example.bms.bdgt.entity.BgtBdgt;
import com.example.bms.bdgt.entity.BgtBdgtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BgtBdgtRepository extends JpaRepository<BgtBdgt, BgtBdgtId> {
    // bsnsYy, qtrDcd 조건으로 목록 조회
    List<BgtBdgt> findByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(String bsnsYy, String qtrDcd);

    void deleteAllByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(String bsnsYy, String qtrDcd);
}

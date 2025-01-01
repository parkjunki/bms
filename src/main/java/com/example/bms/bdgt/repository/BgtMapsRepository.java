package com.example.bms.bdgt.repository;

import com.example.bms.bdgt.entity.BgtMaps;
import com.example.bms.bdgt.entity.BgtMapsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BgtMapsRepository extends JpaRepository<BgtMaps, BgtMapsId> {
    List<BgtMaps> findByBgtMapsIdBsnsYyAndBgtMapsIdBaseMmAndBgtMapsIdTeamCd(String bsnSyy, String baseMm, String teamCd);
}

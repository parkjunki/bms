package com.example.bms.bdgt.service;

import com.example.bms.bdgt.dto.BgtLimtDto;
import com.example.bms.bdgt.entity.BgtLimt;
import com.example.bms.bdgt.entity.BgtLimtId;
import com.example.bms.bdgt.repository.BgtLimtRepository;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BgtLimtService {
    private final BgtLimtRepository bgtLimtRepository;

    public BgtLimtService(BgtLimtRepository bgtLimtRepository) {
        this.bgtLimtRepository = bgtLimtRepository;
    }

    // 조건에 따른 목록 조회
    public List<BgtLimtDto> findByBsnsYyAndTeamCd(String bsnsYy, String teamCd) {
        List<BgtLimt> bgtLimts = bgtLimtRepository.findByBgtLimtIdBsnsYyAndBgtLimtIdTeamCd(bsnsYy, teamCd);
        return bgtLimts.stream().map(BgtLimtDto::fromEntity).collect(Collectors.toList());
    }

    // 금액별 증감 수정
    public BgtLimtDto updateAmounts(BgtLimtId bgtLimtId, BigDecimal bgtLmtBalDelta, BigDecimal prfrScdlAmtDelta, BigDecimal ttlBgtAlctAmtDelta, BigDecimal ttlPrfrAmtDelta) {
        BgtLimt bgtLimt = bgtLimtRepository.findById(bgtLimtId)
                .orElseThrow(() -> new CustomNotFoundException("예산현황을 찾을 수 없습니다."));

        bgtLimt.setBgtLmtBal(bgtLimt.getBgtLmtBal().add(bgtLmtBalDelta));
        bgtLimt.setPrfrScdlAmt(bgtLimt.getPrfrScdlAmt().add(prfrScdlAmtDelta));
        bgtLimt.setTtlBgtAlctAmt(bgtLimt.getTtlBgtAlctAmt().add(ttlBgtAlctAmtDelta));
        bgtLimt.setTtlPrfrAmt(bgtLimt.getTtlPrfrAmt().add(ttlPrfrAmtDelta));

        BgtLimt updatedEntity = bgtLimtRepository.save(bgtLimt);
        return BgtLimtDto.fromEntity(updatedEntity);
    }
}

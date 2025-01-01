package com.example.bms.bdgt.dto;

import com.example.bms.bdgt.entity.BgtLimt;
import com.example.bms.bdgt.entity.BgtLimtId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgtLimtDto {
    private String bsnsYy; // 사업 연도
    private String teamCd; // 팀 코드
    private String bgtItexCd; // 예산 항목 코드

    private BigDecimal bgtLmtBal; // 예산 한도 잔액
    private BigDecimal prfrScdlAmt; // 집행 예정 금액
    private BigDecimal ttlBgtAlctAmt; // 총 예산 배정 금액
    private BigDecimal ttlPrfrAmt; // 총 집행 금액

    private LocalDateTime fsDt; // 생성 일시
    private LocalDateTime lsDt; // 최종 수정 일시

    public static BgtLimtDto fromEntity(BgtLimt bgtLimt) {
        return BgtLimtDto.builder()
                .bsnsYy(bgtLimt.getBgtLimtId().getBsnsYy())
                .teamCd(bgtLimt.getBgtLimtId().getTeamCd())
                .bgtItexCd(bgtLimt.getBgtLimtId().getBgtItexCd())
                .bgtLmtBal(bgtLimt.getBgtLmtBal())
                .prfrScdlAmt(bgtLimt.getPrfrScdlAmt())
                .ttlBgtAlctAmt(bgtLimt.getTtlBgtAlctAmt())
                .ttlPrfrAmt(bgtLimt.getTtlPrfrAmt())
                .fsDt(bgtLimt.getFsDt())
                .lsDt(bgtLimt.getLsDt())
                .build();
    }

    public BgtLimt toEntity() {
        return BgtLimt.builder()
                .bgtLimtId(new BgtLimtId(bsnsYy, teamCd, bgtItexCd))
                .bgtLmtBal(bgtLmtBal)
                .prfrScdlAmt(prfrScdlAmt)
                .ttlBgtAlctAmt(ttlBgtAlctAmt)
                .ttlPrfrAmt(ttlPrfrAmt)
                .fsDt(fsDt)
                .lsDt(lsDt)
                .build();
    }
}

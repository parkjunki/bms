package com.example.bms.bdgt.dto;

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
public class BgtMapsDto {
    private String bsnsYy;  // 사업 연도
    private String baseMm;  // 기준 월
    private String teamCd;  // 팀 코드
    private String bgtItexCd;  // 예산 항목 코드

    private BigDecimal drmnAlctAmt;  // 월중 배정 금액
    private BigDecimal drmnPrfrAmt;  // 월중 집행 금액
    private BigDecimal alctRdmpAmt;  // 월중 배정환수 금액
    private BigDecimal drmnPrfrCcAmt;  // 월중 집행 취소 금액

    private LocalDateTime fsDt;  // 생성 일시
    private LocalDateTime lsDt;  // 최종 수정 일시
}

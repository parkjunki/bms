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
public class BgtBdgtDto {
    private String bsnSyy;   // 사업 연도
    private String qtrDcd;   // 분기 코드
    private String teamCd;   // 팀 코드
    private String bgtItexCd; // 예산 항목 코드
    private String statCd;   // 상태 코드
    private BigDecimal bdgtMubAmt; // 편성 금액
    private BigDecimal rginAlctAmt; // 조정 금액
    private String bdalFnsYn; // 완료 여부
    private LocalDateTime fsDt; // 생성 일시
    private LocalDateTime lsDt; // 최종 수정 일시
}

package com.example.bms.bdgt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgtTranDto {

    private String bsnsYy; // 사업 연도
    private Integer tranSrn; // 거래 일련번호
    private String tranDcd; // 거래 코드
    private String statCd; // 상태 코드
    private String teamCd; // 팀 코드
    private String bgtItexCd; // 예산 항목 코드
    private String tranYmd; // 거래 일자
    private String tranHms; // 거래 시간
    private String tranUserNo; // 거래 사용자 번호
    private Integer alapNo; // 관련 번호
    private String prfrYm; // 수행 연월
    private Integer prfrNo; // 수행 번호
    private BigDecimal tranAmt; // 거래 금액
    private LocalDate fsDt; // 생성 일시
    private LocalDate lsDt; // 최종 수정 일시

}

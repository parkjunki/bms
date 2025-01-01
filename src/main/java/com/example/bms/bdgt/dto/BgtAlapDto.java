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
public class BgtAlapDto {
    private String bsnsYy;
    private Integer alapNo;
    private String statCd;
    private String alapStatCd;
    private String teamCd;
    private String alapCon;
    private String aplcYmd;
    private String aplcSnctScd;
    private String aplcUserNo;
    private Integer aplcSnctNo;
    private String bgtItexCd;
    private BigDecimal bdalAplcAmt;
    private String bdalYmd;
    private String bdalFnsgYn;
    private LocalDateTime fsDt;
    private LocalDateTime lsDt;
}

package com.example.bms.prfr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgtPrfrDto {
    private String teamCd;
    private String prfrYm;
    private Integer prfrNo;
    private String bsnsYy;
    private String bgtItexCd;
    private String statCd;
    private Integer prfrSnctNo;
    private String prfrSnctScd;
    private LocalDate bgtPrfrYmd;
    private LocalTime bgtPrfrHms;
    private LocalDate acitPcsnYmd;
    private Integer rgsnUserNo;
    private String cnclSnctScd;
    private LocalDate ccYmd;
    private LocalTime ccHms;
    private LocalDate ccAcitYmd;
    private Integer ccUserNo;
    private BigDecimal prfrAmt;
    private BigDecimal ccAmt;
    private String tstmDcd;
    private Integer tstmNo;
    private String crdNo;
    private LocalDate crahYmd;
    private String cardApn;
    private String prfrRsnCon;
    private LocalDateTime fsDt;
    private LocalDateTime lsDt;
}

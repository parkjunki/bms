package com.example.bms.prfr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgtCbngDto {
    private String tstmYmd;
    private Integer tstmNo;
    private String crdBngYmd;
    private String amslSln;
    private String crdNo;
    private String crahYmd;
    private String athzHms;
    private String cardApn;
    private String statCd;
    private String stlmYmd;
    private BigDecimal amslAmt;
    private BigDecimal bilgAmt;
    private String afstNm;
    private String afstTpbCd;
    private String afstRpprNm;
    private String afstDtlAdr;
    private String afstTpn;
    private BigDecimal prfrAmt;
    private BigDecimal prfrCcAmt;
    private BigDecimal bngCnclAmt;
    private String cnclAmslSln;
    private String delRsnCon;
    private BigDecimal cardAhstAmt;
    private LocalDate cardAhstYmd;
    private BigDecimal rfndArngAmt;
    private LocalDate rfndArngYmd;
    private LocalDateTime fsDt;
    private LocalDateTime lsDt;
}

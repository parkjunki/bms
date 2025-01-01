package com.example.bms.prfr.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BgtCbngVo {
    private String tstmYmd;
    private Integer tstmNo;
    private String crdBngYmd;
    private String crdNo;
    private String crahYmd;
    private String athzHms;
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
    private BigDecimal cardAhstAmt;
    private LocalDate cardAhstYmd;
    private BigDecimal rfndArngAmt;
    private LocalDate rfndArngYmd;
}

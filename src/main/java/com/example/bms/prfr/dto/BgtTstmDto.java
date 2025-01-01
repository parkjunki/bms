package com.example.bms.prfr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BgtTstmDto {
    private String tstmYmd;
    private String tstmNo;
    private String teamCd;
    private String rcppDcd;
    private String statCd;
    private BigDecimal issAmt;
    private String bprSrn;
    private String frm;
    private String rpprNm;
    private BigDecimal prfrAmt;
    private BigDecimal prfrCcAmt;
    private String etcCon;
    private LocalDate fsDt;
    private LocalDate lsDt;
}

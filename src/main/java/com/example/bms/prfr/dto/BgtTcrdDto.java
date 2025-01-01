package com.example.bms.prfr.dto;

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
public class BgtTcrdDto {
    private String crdNo;
    private Integer teamCrdSrn;
    private String teamCd;
    private String statCd;
    private String stlmDd;
    private String valdYm;
    private String stlmAcn;
    private BigDecimal useLmtAmt;
    private String crdsYmd;
    private String userNo;
    private LocalDate fsDt;
    private LocalDate lsDt;
}

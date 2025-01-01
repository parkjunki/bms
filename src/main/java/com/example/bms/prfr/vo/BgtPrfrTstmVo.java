package com.example.bms.prfr.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BgtPrfrTstmVo {
    private String tstmYmd;
    private Integer tstmNo;
    private String tstmDcd;
    private BigDecimal bilgAmt;
    private String frm;
}

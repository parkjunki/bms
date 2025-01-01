package com.example.bms.prfr.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_BGT_PRFR")
public class BgtPrfr {
    @EmbeddedId
    private BgtPrfrId bgtPrfrId;
    private String bsnsYy;
    private String bgtItexCd;
    private String statCd;
    private Integer prfrSnctNo;
    private String prfrSnctScd;
    private Date bgtPrfrYmd;
    private Date bgtPrfrHms;
    private Date acitPcsnYmd;
    private Integer rgsnUserNo;
    private String cnclSnctScd;
    private Date ccYmd;
    private Date ccHms;
    private Date ccAcitYmd;
    private Integer ccUserNo;
    private BigDecimal prfrAmt;
    private BigDecimal ccAmt;
    private String tstmDcd;
    private Integer tstmNo;
    private String crdNo;
    private Date crahYmd;
    private String cardApn;
    private String prfrRsnCon;
    private Date fsDt;
    private Date lsDt;
}

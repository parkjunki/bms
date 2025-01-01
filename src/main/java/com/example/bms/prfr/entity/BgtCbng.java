package com.example.bms.prfr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_BGT_CBNG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgtCbng {

    @EmbeddedId
    private BgtCbngId bgtCbngId;

    @Column(name = "CRD_BNG_YMD", length = 8)
    private String crdBngYmd;

    @Column(name = "AMSL_SLN", length = 50)
    private String amslSln;

    @Column(name = "CRD_NO", length = 50)
    private String crdNo;

    @Column(name = "CRAH_YMD", length = 8)
    private String crahYmd;

    @Column(name = "ATHZ_HMS", length = 6)
    private String athzHms;

    @Column(name = "CARD_APN", length = 50)
    private String cardApn;

    @Column(name = "STAT_CD", length = 2)
    private String statCd;

    @Column(name = "STLM_YMD", length = 8)
    private String stlmYmd;

    @Column(name = "AMSL_AMT", precision = 15, scale = 2)
    private BigDecimal amslAmt;

    @Column(name = "BILG_AMT", precision = 15, scale = 2)
    private BigDecimal bilgAmt;

    @Column(name = "AFST_NM", length = 100)
    private String afstNm;

    @Column(name = "AFST_TPB_CD", length = 4)
    private String afstTpbCd;

    @Column(name = "AFST_RPPR_NM", length = 100)
    private String afstRpprNm;

    @Column(name = "AFST_DTL_ADR", length = 255)
    private String afstDtlAdr;

    @Column(name = "AFST_TPN", length = 20)
    private String afstTpn;

    @Column(name = "PRFR_AMT", precision = 15, scale = 2)
    private BigDecimal prfrAmt;

    @Column(name = "PRFR_CC_AMT", precision = 15, scale = 2)
    private BigDecimal prfrCcAmt;

    @Column(name = "BNG_CNCL_AMT", precision = 15, scale = 2)
    private BigDecimal bngCnclAmt;

    @Column(name = "CNCL_AMSL_SLN", length = 50)
    private String cnclAmslSln;

    @Column(name = "DEL_RSN_CON", columnDefinition = "TEXT")
    private String delRsnCon;

    @Column(name = "CARD_AHST_AMT", precision = 15, scale = 2)
    private BigDecimal cardAhstAmt;

    @Column(name = "CARD_AHST_YMD")
    private LocalDate cardAhstYmd;

    @Column(name = "RFND_ARNG_AMT", precision = 15, scale = 2)
    private BigDecimal rfndArngAmt;

    @Column(name = "RFND_ARNG_YMD")
    private LocalDate rfndArngYmd;

    @Column(name = "FS_DT")
    private LocalDateTime fsDt;

    @Column(name = "LS_DT")
    private LocalDateTime lsDt;
}
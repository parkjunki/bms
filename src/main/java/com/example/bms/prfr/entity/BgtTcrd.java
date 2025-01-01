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
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TB_BGT_TCRD")
@Data
public class BgtTcrd {
    @EmbeddedId
    private BgtTcrdId bgtTcrdId;

    @Column(name = "TEAM_CD", length = 4)
    private String teamCd;

    @Column(name = "STAT_CD", length = 2)
    private String statCd;

    @Column(name = "STLM_DD", length = 2)
    private String stlmDd;

    @Column(name = "VALD_YM", length = 6)
    private String valdYm;

    @Column(name = "STLM_ACN", length = 50)
    private String stlmAcn;

    @Column(name = "USE_LMT_AMT", precision = 15, scale = 2)
    private BigDecimal useLmtAmt;

    @Column(name = "CRDS_YMD", length = 8)
    private String crdsYmd;

    @Column(name = "USER_NO", length = 5)
    private String userNo;

    @Column(name = "FS_DT")
    private LocalDate fsDt;

    @Column(name = "LS_DT")
    private LocalDate lsDt;

}

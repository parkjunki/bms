package com.example.bms.prfr.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "TB_BGT_TSTM")
public class BgtTstm {
    @EmbeddedId
    private BgtTstmId bgtTstmId;

    @Column(name = "TEAM_CD", length = 4)
    private String teamCd;

    @Column(name = "RCPP_DCD", length = 2)
    private String rcppDcd;

    @Column(name = "STAT_CD", length = 2)
    private String statCd;

    @Column(name = "ISS_AMT", precision = 15, scale = 2)
    private BigDecimal issAmt;

    @Column(name = "BPR_SRN", length = 100)
    private String bprSrn;

    @Column(name = "FRM", length = 100)
    private String frm;

    @Column(name = "RPPR_NM", length = 50)
    private String rpprNm;

    @Column(name = "PRFR_AMT", precision = 15, scale = 2)
    private BigDecimal prfrAmt;

    @Column(name = "PRFR_CC_AMT", precision = 15, scale = 2)
    private BigDecimal prfrCcAmt;

    @Column(name = "ETC_CON", length = 500)
    private String etcCon;

    @Column(name = "FS_DT")
    @Temporal(TemporalType.DATE)
    private LocalDate fsDt;

    @Column(name = "LS_DT")
    @Temporal(TemporalType.DATE)
    private LocalDate lsDt;
}

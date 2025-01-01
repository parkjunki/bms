package com.example.bms.bdgt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_BGT_ALAP")
public class BgtAlap {

    @EmbeddedId
    private BgtAlapId bgtAlapId;

    @Column(name = "STAT_CD", length = 2)
    private String statCd;

    @Column(name = "ALAP_STAT_CD", length = 2)
    private String alapStatCd;

    @Column(name = "TEAM_CD", length = 4)
    private String teamCd;

    @Column(name = "ALAP_CON", columnDefinition = "TEXT")
    private String alapCon;

    @Column(name = "APLC_YMD", length = 8)
    private String aplcYmd;

    @Column(name = "APLC_SNCT_SCD", length = 2)
    private String aplcSnctScd;

    @Column(name = "APLC_USER_NO", length = 5)
    private String aplcUserNo;

    @Column(name = "APLC_SNCT_NO")
    private Integer aplcSnctNo;

    @Column(name = "BGT_ITEX_CD", length = 6)
    private String bgtItexCd;

    @Column(name = "BDAL_APLC_AMT", precision = 15, scale = 2)
    private BigDecimal bdalAplcAmt;

    @Column(name = "BDAL_YMD", length = 8)
    private String bdalYmd;

    @Column(name = "BDAL_FNSG_YN", length = 1)
    private String bdalFnsgYn;

    @Column(name = "FS_DT")
    private LocalDateTime fsDt;

    @Column(name = "LS_DT")
    private LocalDateTime lsDt;
}

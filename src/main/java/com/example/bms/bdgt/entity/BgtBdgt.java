package com.example.bms.bdgt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "TB_BGT_BGDT")
public class BgtBdgt {
    @EmbeddedId
    private BgtBdgtId bgtBdgtId;

    @Column(name = "STAT_CD")
    private String statCd;   // 상태 코드

    @Column(name = "BDGT_MUB_AMT")
    private BigDecimal bdgtMubAmt; // 편성 금액

    @Column(name = "RGIN_ALCT_AMT")
    private BigDecimal rginAlctAmt; // 조정 금액

    @Column(name = "BDAL_FNSG_YN")
    private String bdalFnsYn; // 완료 여부

    @Column(name = "FS_DT")
    private LocalDateTime fsDt; // 생성 일시

    @Column(name = "LS_DT")
    private LocalDateTime lsDt; // 최종 수정 일시
}

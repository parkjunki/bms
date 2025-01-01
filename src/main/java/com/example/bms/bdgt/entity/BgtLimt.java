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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MGT_LIMT")
public class BgtLimt {
    @EmbeddedId
    private BgtLimtId bgtLimtId;

    @Column(name = "BGT_LMT_BAL", precision = 15, scale = 2)
    private BigDecimal bgtLmtBal; // 예산 한도 잔액

    @Column(name = "PRFR_SCDL_AMT", precision = 15, scale = 2)
    private BigDecimal prfrScdlAmt; // 집행 예정 금액

    @Column(name = "TTL_BGT_ALCT_AMT", precision = 15, scale = 2)
    private BigDecimal ttlBgtAlctAmt; // 총 예산 배정 금액

    @Column(name = "TTL_PRFR_AMT", precision = 15, scale = 2)
    private BigDecimal ttlPrfrAmt; // 총 집행 금액

    @Column(name = "FS_DT")
    private LocalDateTime fsDt; // 생성 일시

    @Column(name = "LS_DT")
    private LocalDateTime lsDt; // 최종 수정 일시
}

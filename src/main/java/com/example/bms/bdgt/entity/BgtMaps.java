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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_BGT_MAPS")
public class BgtMaps {
    @EmbeddedId
    private BgtMapsId bgtMapsId;

    @Column(name = "DRMN_ALCT_AMT")
    private BigDecimal drmnAlctAmt;  //월중 배정 금액

    @Column(name = "DRMN_PRFR_AMT")
    private BigDecimal drmnPrfrAmt;  // 월중 집행 금액

    @Column(name = "ALCT_RDMP_AMT")
    private BigDecimal alctRdmpAmt;  // 월중 배정 환수 금액

    @Column(name = "DRMN_PRFR_CC_AMT")
    private BigDecimal drmnPrfrCcAmt;  // 월중 집행 취소 금액

    @Column(name = "FS_DT")
    private LocalDateTime fsDt;  // 생성 일시

    @Column(name = "LS_DT")
    private LocalDateTime lsDt;  // 최종 수정 일시
}

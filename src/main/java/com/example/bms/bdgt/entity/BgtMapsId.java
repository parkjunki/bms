package com.example.bms.bdgt.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BgtMapsId implements Serializable {
    private String bsnsYy;  // 사업 연도
    private String baseMm;  // 기준 월
    private String teamCd;  // 팀 코드
    private String bgtItexCd;  // 예산 항목 코드
}

package com.example.bms.prfr.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class BgtPrfrId implements Serializable {
    private String teamCd;          // 팀 코드
    private String prfrYm;          // 수행 연월
    private int prfrNo;             // 수행 번호
}

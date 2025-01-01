package com.example.bms.bdgt.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class BgtTranId implements Serializable {
    private String bsnsYy; // 사업 연도
    private Integer tranSrn; // 거래 일련번호
}

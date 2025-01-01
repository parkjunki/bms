package com.example.bms.prfr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BgtCbngId implements Serializable {
    @Column(name = "TSTM_YMD", length = 8)
    private String tstmYmd;

    @Column(name = "TSTM_NO")
    private Integer tstmNo;
}

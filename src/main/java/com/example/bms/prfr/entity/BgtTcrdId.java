package com.example.bms.prfr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class BgtTcrdId implements Serializable {
    @Column(name = "CRD_NO", length = 16)
    private String crdNo;

    @Column(name = "TEAM_CRD_SRN")
    private Integer teamCrdSrn;
}

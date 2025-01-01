package com.example.bms.comm.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class BgtSnctId implements Serializable {
    private String bsnsYy;
    private Integer snctNo;
}

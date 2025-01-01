package com.example.bms.comm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_BGT_ITEX")
@Data
public class BgtItex {
    @Id
    private String bgtItexCd;
    private String bgtItexNm;
    private String accd;
    private LocalDate fsDt;
    private LocalDate lsDt;
}

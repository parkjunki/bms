package com.example.bms.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BgtItexDto {
    private String bgtItexCd;
    private String bgtItexNm;
    private String accd;
    private LocalDate fsDt;
    private LocalDate lsDt;
}

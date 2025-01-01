package com.example.bms.comm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TB_BGT_TEAM")
public class BgtTeam {
    @Id
    @NotNull
    @Column(length = 4)
    private String teamCd;

    @NotNull
    @Column(length = 50)
    private String teamNm;
    private LocalDate fsDt;
    private LocalDate lsDt;

    public String getTeamCd() {
        return teamCd;
    }

    public void setTeamCd(String teamCd) {
        this.teamCd = teamCd;
    }

    public String getTeamNm() {
        return teamNm;
    }

    public void setTeamNm(String teamNm) {
        this.teamNm = teamNm;
    }

    public LocalDate getFsDt() {
        return fsDt;
    }

    public void setFsDt(LocalDate fsDt) {
        this.fsDt = fsDt;
    }

    public LocalDate getLsDt() {
        return lsDt;
    }

    public void setLsDt(LocalDate lsDt) {
        this.lsDt = lsDt;
    }
}

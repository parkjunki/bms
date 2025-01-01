package com.example.bms.comm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class BgtTeamDto {

        @NotNull(message = "팀코드를 입력해주세요.")
        private String teamCd;

        @NotNull(message = "팀이름을 입력해주세요.")
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

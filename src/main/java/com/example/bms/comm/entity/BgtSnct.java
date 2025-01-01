package com.example.bms.comm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_BGT_SNCT")
public class BgtSnct {
    @EmbeddedId
    private BgtSnctId bgtSnctId;

    @Column(name = "SNCT_RQST_YMD", length = 8)
    private String snctRqstYmd; // 결재 요청 일자

    @Column(name = "RQST_USER_NO", length = 5)
    private String rqstUserNo; // 요청 사용자 번호

    @Column(name = "STAT_CD", length = 2)
    private String statCd; // 상태 코드

    @Column(name = "ALAP_NO")
    private Integer alapNo; // 추가배정신청번호

    @Column(name = "TEAM_CD", length = 4)
    private String teamCd; // 팀 코드

    @Column(name = "PRFR_YM", length = 6)
    private String prfrYm; // 집행 년월

    @Column(name = "PRFR_NO")
    private Integer prfrNo; // 집행 번호

    @Column(name = "SNCT_RQST_HMS", length = 6)
    private String snctRqstHms; // 결재 요청 시간

    @Column(name = "SNCT_CON")
    private String snctCon; // 결재 내용

    @Column(name = "SNLN_USER1", length = 5)
    private String snlnUser1; // 결재자 1

    @Column(name = "SNLN_USER2", length = 5)
    private String snlnUser2; // 결재자 2

    @Column(name = "SNLN_USER3", length = 5)
    private String snlnUser3; // 결재자 3

    @Column(name = "CUR_STG_USER", length = 5)
    private String curStgUser; // 현재 결재 사용자

    @Column(name = "PRE_STG_USER", length = 5)
    private String preStgUser; // 이전 결재 사용자

    @Column(name = "LAST_SNLV", length = 1)
    private String lastSnlv; // 최종 결재 레벨

    @Column(name = "FS_DT")
    private LocalDateTime fsDt; // 생성 일시

    @Column(name = "LS_DT")
    private LocalDateTime lsDt; // 최종 수정 일시
}

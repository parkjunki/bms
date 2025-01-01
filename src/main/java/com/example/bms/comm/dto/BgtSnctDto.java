package com.example.bms.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgtSnctDto {
    private String bsnsYy; // 사업 연도
    private Integer snctNo; // 결재 번호
    private String snctRqstYmd; // 결재 요청 일자
    private String rqstUserNo; // 요청 사용자 번호
    private String statCd; // 상태 코드
    private Integer alapNo; // 추가배정신청번호
    private String teamCd; // 팀 코드
    private String prfrYm; // 집행 년월
    private Integer prfrNo; // 집행 번호
    private String snctRqstHms; // 결재 요청 시간
    private String snctCon; // 결재 내용
    private String snlnUser1; // 결재자 1
    private String snlnUser2; // 결재자 2
    private String snlnUser3; // 결재자 3
    private String curStgUser; // 현재 결재 사용자
    private String preStgUser; // 이전 결재 사용자
    private String lastSnlv; // 최종 결재 레벨
    private LocalDateTime fsDt; // 생성 일시
    private LocalDateTime lsDt; // 최종 수정 일시
}

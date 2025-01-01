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
public class BgtUserDto {
    private String userNo; // 사용자 번호 (PK)
    private String userNm; // 이름
    private String nickNm; // 닉네임
    private String teamCd; // 팀 코드
    private String authRole; // 권한/역할
    private LocalDateTime fsDt; // 생성 일시
    private LocalDateTime lsDt; // 최종 수정 일시
}

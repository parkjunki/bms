package com.example.bms.comm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "TB_BGT_USER")
public class BgtUser {
    @Id
    private String userNo; // 사용자 번호 (PK)
    private String pwd;    // 비밀번호
    private String userNm; // 이름
    private String nickNm; // 닉네임
    private String teamCd; // 팀 코드
    private String authRole; // 권한/역할
    private LocalDateTime fsDt; // 생성 일시
    private LocalDateTime lsDt; // 최종 수정 일시
}

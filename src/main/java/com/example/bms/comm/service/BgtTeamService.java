package com.example.bms.comm.service;

import com.example.bms.comm.dto.BgtTeamDto;
import com.example.bms.comm.entity.BgtTeam;
import com.example.bms.comm.repository.BgtTeamRepository;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BgtTeamService {
    private final BgtTeamRepository bgtTeamRepository;

    public BgtTeamService(BgtTeamRepository bgtTeamRepository) {
        this.bgtTeamRepository = bgtTeamRepository;
    }

    /* 팀 목록 조회 */
    public List<BgtTeamDto> getAllTeams() {
        return bgtTeamRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /* 팀 상세 조회 */
    public BgtTeamDto getTeamByCd(String teamCd) {
        BgtTeam bgtTeam = bgtTeamRepository.findById(teamCd)
                .orElseThrow(() -> new CustomNotFoundException("팀 정보를 찾을 수 없습니다. : " + teamCd));
        return toDto(bgtTeam);
    }

    /* 팀 등록 */
    public BgtTeamDto saveTeam(BgtTeamDto bgtTeamDto) {
        BgtTeam bgtTeam = toEntity(bgtTeamDto);
        bgtTeam.setFsDt(LocalDate.now());
        bgtTeam.setLsDt(LocalDate.now());
        BgtTeam savedTeam = bgtTeamRepository.save(bgtTeam);
        return toDto(savedTeam);
    }

    /* 팀 삭제 */
    public void deleteTeam(String teamCd) {
        boolean result = bgtTeamRepository.existsById(teamCd);
        if (result) bgtTeamRepository.deleteById(teamCd);
        else throw new CustomNotFoundException("팀 정보를 찾을 수 없습니다. : " + teamCd);
    }

    /* 팀 이름 수정 */
    public BgtTeamDto updateTeamNm(String teamCd, String teamNewNm) {
        return bgtTeamRepository.findById(teamCd)
                .map(bgtTeam -> {
                    bgtTeam.setTeamNm(teamNewNm);
                    bgtTeam.setLsDt(LocalDate.now());
                    BgtTeam updatedTeam = bgtTeamRepository.save(bgtTeam);
                    return toDto(updatedTeam);
                })
                .orElseThrow(() -> new CustomNotFoundException("팀 정보를 찾을 수 없습니다. : " + teamCd));
    }


    private BgtTeamDto toDto(BgtTeam bgtTeam) {
        BgtTeamDto dto = new BgtTeamDto();
        dto.setTeamCd(bgtTeam.getTeamCd());
        dto.setTeamNm(bgtTeam.getTeamNm());
        dto.setFsDt(bgtTeam.getFsDt());
        dto.setLsDt(bgtTeam.getLsDt());
        return dto;
    }

    private BgtTeam toEntity(BgtTeamDto bgtTeamDto) {
        BgtTeam team = new BgtTeam();
        team.setTeamCd(bgtTeamDto.getTeamCd());
        team.setTeamNm(bgtTeamDto.getTeamNm());
        team.setFsDt(bgtTeamDto.getFsDt());
        team.setLsDt(bgtTeamDto.getLsDt());
        return team;
    }
}

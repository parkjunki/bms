package com.example.bms.comm.controller;

import com.example.bms.comm.dto.BgtTeamDto;
import com.example.bms.comm.service.BgtTeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BgtTeamController {
    private final BgtTeamService bgtTeamService;

    public BgtTeamController(BgtTeamService bgtTeamService) {
        this.bgtTeamService = bgtTeamService;
    }

    @GetMapping("/teams")
    public List<BgtTeamDto> getAllTeam() {
        return bgtTeamService.getAllTeams();
    }

    @PostMapping("/teams")
    public BgtTeamDto createTeam(@RequestBody BgtTeamDto bgtTeamDto) {
        return bgtTeamService.saveTeam(bgtTeamDto);
    }

    @DeleteMapping("/teams/{teamCd}")
    public void deleteTeam(@PathVariable String teamCd) {
        bgtTeamService.deleteTeam(teamCd);
    }

    @PutMapping("/teams/{teamCd}")
    public BgtTeamDto updateTeamNewNm(@PathVariable String teamCd, @RequestBody String teamNewNm) {
        return bgtTeamService.updateTeamNm(teamCd, teamNewNm);
    }
}

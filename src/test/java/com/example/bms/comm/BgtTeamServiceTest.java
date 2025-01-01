package com.example.bms.comm;

import com.example.bms.comm.dto.BgtTeamDto;
import com.example.bms.comm.entity.BgtTeam;
import com.example.bms.comm.repository.BgtTeamRepository;
import com.example.bms.comm.service.BgtTeamService;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtTeamServiceTest {

    @Mock
    private BgtTeamRepository bgtTeamRepository;

    @InjectMocks
    private BgtTeamService bgtTeamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("모든 팀 조회")
    void testGetAllTeams() {
        BgtTeam bgtTeam1 = BgtTeam.builder().teamCd("0001").teamNm("Test1").fsDt(LocalDate.now()).lsDt(LocalDate.now()).build();
        BgtTeam bgtTeam2 = BgtTeam.builder().teamCd("0002").teamNm("Test2").fsDt(LocalDate.now()).lsDt(LocalDate.now()).build();
        List<BgtTeam> teams = Arrays.asList(bgtTeam1, bgtTeam2);

        when(bgtTeamRepository.findAll()).thenReturn(teams);

        List<BgtTeamDto> result = bgtTeamService.getAllTeams();

        result.forEach(team -> System.out.println("Teams --> " + team.getTeamCd() + " // " + team.getTeamNm()));

        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getTeamNm());
        assertEquals("Test2", result.get(1).getTeamNm());
        verify(bgtTeamRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("팀 코드로 조회")
    void testGetTeamByCd_Found() {
        // Arrange
        String teamCd = "0001";
        BgtTeam team = new BgtTeam(teamCd, "Test1", LocalDate.now(), LocalDate.now());
        when(bgtTeamRepository.findById(teamCd)).thenReturn(Optional.of(team));

        // Act
        BgtTeamDto result = bgtTeamService.getTeamByCd(teamCd);

        // Assert
        assertNotNull(result);
        assertEquals("Test1", result.getTeamNm());
        verify(bgtTeamRepository, times(1)).findById(teamCd);
    }

    @Test
    @DisplayName("없는 팀 코드 조회")
    void testGetTeamByCd_NotFound() {
        // Arrange
        String teamCd = "9999";
        when(bgtTeamRepository.findById(teamCd)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(CustomNotFoundException.class, () -> bgtTeamService.getTeamByCd(teamCd));
        assertEquals("팀 정보를 찾을 수 없습니다. : 9999", exception.getMessage());
        verify(bgtTeamRepository, times(1)).findById(teamCd);
    }

    @Test
    @DisplayName("팀 등록")
    void testSaveTeam() {

        BgtTeamDto bgtTeamDto =  new BgtTeamDto();
        bgtTeamDto.setTeamCd("0001");
        bgtTeamDto.setTeamNm("Dev 2 Team");

        BgtTeam bgtTeam = new BgtTeam("0001", "Dev 2 Team", null, null);
        when(bgtTeamRepository.save(any(BgtTeam.class))).thenAnswer(invocation -> {
            BgtTeam savedTeam = invocation.getArgument(0);
            savedTeam.setFsDt(LocalDate.now());
            savedTeam.setLsDt(LocalDate.now());
            return savedTeam;
        });

        BgtTeamDto result = bgtTeamService.saveTeam(bgtTeamDto);

        System.out.println("Team Saved : " + result.getTeamCd() + " : " + result.getTeamNm());
        assertNotNull(result);
        assertEquals("Dev 2 Team", result.getTeamNm());
        assertNotNull(result.getFsDt());
        assertNotNull(result.getLsDt());
        verify(bgtTeamRepository, times(1)).save(any(BgtTeam.class));
    }

    @Test
    @DisplayName("팀 삭제 성공")
    void testDeleteTeam_Success() {
        String teamCd = "0001";
        when(bgtTeamRepository.existsById(teamCd)).thenReturn(true);

        bgtTeamService.deleteTeam(teamCd);

        System.out.println("Team deleted success!!!! " + teamCd);
        verify(bgtTeamRepository, times(1)).existsById(teamCd);
        verify(bgtTeamRepository, times(1)).deleteById(teamCd);
    }

    @Test
    @DisplayName("팀 삭제 실패")
    void testDeleteTeam_NotFound() {
        String teamCd = "9999";
        when(bgtTeamRepository.existsById(teamCd)).thenReturn(false);

        Exception exception = assertThrows(CustomNotFoundException.class
                , () -> bgtTeamService.deleteTeam(teamCd));
        System.out.println("Exception Message: " + exception.getMessage());
        assertEquals("팀 정보를 찾을 수 없습니다. : 9999", exception.getMessage());
        verify(bgtTeamRepository, times(1)).existsById(teamCd);
    }

    @Test
    @DisplayName("팀 정보 수정 성공")
    void testUpdateTeam_Success() {
        String teamCd = "0001";
        String newTeamNm = "FinanceTeam";
        BgtTeam bgtTeam = new BgtTeam(teamCd, "DevTeam", LocalDate.now(), LocalDate.now());

        when(bgtTeamRepository.findById(teamCd)).thenReturn(Optional.of(bgtTeam));
        when(bgtTeamRepository.save(any(BgtTeam.class))).thenReturn(bgtTeam);

        BgtTeamDto result = bgtTeamService.updateTeamNm(teamCd, newTeamNm);

        System.out.println("Update Success !! " + result.getTeamNm());
        assertNotNull(result);
        assertEquals(newTeamNm, result.getTeamNm());
        verify(bgtTeamRepository, times(1)).findById(teamCd);
        verify(bgtTeamRepository, times(1)).save(any(BgtTeam.class));

    }

    @Test
    @DisplayName("팀 정보 수정 실패")
    void testUpdateTeam_NotFound() {
        String teamCd = "9999";
        String newTeamNm = "DevDev";

        when(bgtTeamRepository.findById(teamCd)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomNotFoundException.class
                , () -> bgtTeamService.updateTeamNm(teamCd, "TestTeam"));
        System.out.println("Exception message : " + exception.getMessage());
        assertEquals("팀 정보를 찾을 수 없습니다. : 9999", exception.getMessage());
        verify(bgtTeamRepository, times(1)).findById(teamCd);
    }
}

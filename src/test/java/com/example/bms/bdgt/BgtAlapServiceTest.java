package com.example.bms.bdgt;

import com.example.bms.bdgt.dto.BgtAlapDto;
import com.example.bms.bdgt.entity.BgtAlap;
import com.example.bms.bdgt.entity.BgtAlapId;
import com.example.bms.bdgt.repository.BgtAlapRepository;
import com.example.bms.bdgt.service.BgtAlapService;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtAlapServiceTest {
    @Mock
    private BgtAlapRepository bgtAlapRepository;

    @InjectMocks
    private BgtAlapService bgtAlapService;

    private BgtAlapId bgtAlapId;
    private BgtAlap bgtAlap;

    private BgtAlapDto bgtAlapDto;

    private List<BgtAlap> bgtAlapList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bgtAlapId = new BgtAlapId("2024",1);

        bgtAlap = BgtAlap.builder()
                .bgtAlapId(bgtAlapId)
                .bgtItexCd("221099")
                .alapCon("추가배정 신청합니다.")
                .aplcUserNo("82062")
                .bdalAplcAmt(new BigDecimal(250000.0))
                .build();

        bgtAlapDto = BgtAlapDto.builder()
                .bsnsYy(bgtAlapId.getBsnsYy())
                .alapNo(bgtAlapId.getAlapNo())
                .bgtItexCd("221099")
                .alapCon("추가배정 신청합니다.")
                .aplcUserNo("82062")
                .bdalAplcAmt(new BigDecimal(250000.0))
                .build();

        bgtAlapList = List.of(
                bgtAlap = BgtAlap.builder()
                        .bgtAlapId(new BgtAlapId("2024",1))
                        .bgtItexCd("221099")
                        .alapCon("221099 추가배정 신청합니다.")
                        .teamCd("0001")
                        .aplcUserNo("82062")
                        .bdalAplcAmt(new BigDecimal(250000.0))
                        .build(),
                bgtAlap = BgtAlap.builder()
                        .bgtAlapId(new BgtAlapId("2024",2))
                        .bgtItexCd("223099")
                        .alapCon("223099 추가배정 신청합니다.")
                        .teamCd("0001")
                        .aplcUserNo("82062")
                        .bdalAplcAmt(new BigDecimal(950000.0))
                        .build());
    }

    @Test
    @DisplayName("추가배정신청내역 목록 조회")
    void testGetAlapList_Succcess() {
        String bsnsYy = "2024";
        String teamCd = "0001";
        when(bgtAlapRepository.findByBgtAlapIdBsnsYyAndTeamCd(bsnsYy,teamCd)).thenReturn(bgtAlapList);

        List<BgtAlapDto> result = bgtAlapService.getAlapList(bsnsYy,teamCd);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("추가배정신청내역 상세조회")
    void testGetAlapDetail_Success() {
        when(bgtAlapRepository.findById(bgtAlapId)).thenReturn(Optional.of(bgtAlap));

        BgtAlapDto result = bgtAlapService.getAlapDetail(bgtAlapId);

        assertNotNull(result);
        assertEquals("2024", result.getBsnsYy());
        assertEquals(1, result.getAlapNo());
        verify(bgtAlapRepository, times(1)).findById(bgtAlapId);
    }

    @Test
    @DisplayName("추가배정신청내역 상세조회 not found")
    void testGetAlapDetail_NotFound() {
        when(bgtAlapRepository.findById(bgtAlapId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> bgtAlapService.getAlapDetail(bgtAlapId));
        assertEquals("추가배정신청내역 정보를 찾을 수 없습니다.", exception.getMessage());
        verify(bgtAlapRepository, times(1)).findById(bgtAlapId);
    }

    @Test
    @DisplayName("추가배정신청내역 등록")
    void testRegisterAlap() {

        when(bgtAlapRepository.findTopByBgtAlapIdBsnsYyOrderByBgtAlapIdAlapNoDesc("2024")).thenReturn(null);

        when(bgtAlapRepository.save(any(BgtAlap.class))).thenReturn(bgtAlap);

        BgtAlapDto result = bgtAlapService.registerAlap(bgtAlapDto);

        assertNotNull(result);
        assertEquals("2024", result.getBsnsYy());
        assertEquals(1, result.getAlapNo());
        verify(bgtAlapRepository, times(1)).save(any(BgtAlap.class));
    }

    @Test
    @DisplayName("추가배정신청내역 수정")
    void testUpdateAlap_Success() {
        when(bgtAlapRepository.findById(bgtAlapId)).thenReturn(Optional.of(bgtAlap));

        bgtAlapDto.setAlapCon("추가배정신청내역 내용 변경 기능 확인");
        bgtAlapDto.setBdalAplcAmt(new BigDecimal(15000));

        when(bgtAlapRepository.save(any(BgtAlap.class))).thenReturn(bgtAlap);

        BgtAlapDto result = bgtAlapService.updateAlap(bgtAlapId, bgtAlapDto);

        assertNotNull(result);
        assertEquals("추가배정신청내역 내용 변경 기능 확인", result.getAlapCon());
        assertEquals(new BigDecimal(15000), result.getBdalAplcAmt());
        verify(bgtAlapRepository, times(1)).save(any(BgtAlap.class));
    }

    @Test
    @DisplayName("추가배정신청내역 수정 not found")
    void testUpdateAlap_NotFound() {
        when(bgtAlapRepository.findById(bgtAlapId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> bgtAlapService.updateAlap(bgtAlapId, bgtAlapDto));
        assertEquals("추가배정신청내역 정보를 찾을 수 없습니다.", exception.getMessage());
        verify(bgtAlapRepository, times(1)).findById(bgtAlapId);
    }

    @Test
    @DisplayName("추가배정신청내역 삭제")
    void testDeleteAlap_Success() {
        when(bgtAlapRepository.findById(bgtAlapId)).thenReturn(Optional.of(bgtAlap));

        bgtAlapService.deleteAlap(bgtAlapId);

        verify(bgtAlapRepository, times(1)).delete(bgtAlap);
    }

    @Test
    @DisplayName("추가배정신청내역 삭제 not found")
    void testDeleteAlap_NotFound() {
        when(bgtAlapRepository.findById(bgtAlapId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> bgtAlapService.deleteAlap(bgtAlapId));
        assertEquals("추가배정신청내역 정보를 찾을 수 없습니다.", exception.getMessage());
        verify(bgtAlapRepository, times(1)).findById(bgtAlapId);
    }


}

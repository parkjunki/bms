package com.example.bms.bdgt;

import com.example.bms.bdgt.dto.BgtLimtDto;
import com.example.bms.bdgt.entity.BgtLimt;
import com.example.bms.bdgt.entity.BgtLimtId;
import com.example.bms.bdgt.repository.BgtLimtRepository;
import com.example.bms.bdgt.service.BgtLimtService;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtLimtServiceTest {
    @Mock
    private BgtLimtRepository bgtLimtRepository;

    @InjectMocks
    private BgtLimtService bgtLimtService;

    private BgtLimtId bgtLimtId;
    private BgtLimt bgtLimt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bgtLimtId = new BgtLimtId("2024", "0001", "223099");
        bgtLimt = new BgtLimt(bgtLimtId
                , new BigDecimal("1000.00")
                , new BigDecimal("200.00")
                , new BigDecimal("1500.00")
                , new BigDecimal("800.00")
                , LocalDateTime.now()
                , LocalDateTime.now());
    }

    @Test
    @DisplayName("예산현황 조회")
    void testFindByBsnsYyAndTeamCd() {

        when(bgtLimtRepository.findByBgtLimtIdBsnsYyAndBgtLimtIdTeamCd("2024", "0001"))
                .thenReturn(Arrays.asList(bgtLimt));


        List<BgtLimtDto> result = bgtLimtService.findByBsnsYyAndTeamCd("2024", "0001");


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2024", result.get(0).getBsnsYy());
        assertEquals("0001", result.get(0).getTeamCd());
        verify(bgtLimtRepository, times(1)).findByBgtLimtIdBsnsYyAndBgtLimtIdTeamCd("2024", "0001");
    }

    @Test
    @DisplayName("예산 한도 증감")
    void testUpdateAmounts() {

        BigDecimal bgtLmtBalDelta = new BigDecimal("100.00");
        BigDecimal prfrScdlAmtDelta = new BigDecimal("50.00");
        BigDecimal ttlBgtAlctAmtDelta = new BigDecimal("-200.00");
        BigDecimal ttlPrfrAmtDelta = new BigDecimal("150.00");

        when(bgtLimtRepository.findById(bgtLimtId)).thenReturn(Optional.of(bgtLimt));
        when(bgtLimtRepository.save(any(BgtLimt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BgtLimtDto updated = bgtLimtService.updateAmounts(bgtLimtId, bgtLmtBalDelta, prfrScdlAmtDelta, ttlBgtAlctAmtDelta, ttlPrfrAmtDelta);

        assertNotNull(updated);
        assertEquals(new BigDecimal("1100.00"), updated.getBgtLmtBal());
        assertEquals(new BigDecimal("250.00"), updated.getPrfrScdlAmt());
        assertEquals(new BigDecimal("1300.00"), updated.getTtlBgtAlctAmt());
        assertEquals(new BigDecimal("950.00"), updated.getTtlPrfrAmt());

        verify(bgtLimtRepository, times(1)).findById(bgtLimtId);
        verify(bgtLimtRepository, times(1)).save(any(BgtLimt.class));
    }

    @Test
    @DisplayName("예산 증감 실패")
    void testUpdateAmounts_NotFound() {

        when(bgtLimtRepository.findById(bgtLimtId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () ->
                bgtLimtService.updateAmounts(bgtLimtId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

        assertEquals("예산현황을 찾을 수 없습니다.", exception.getMessage());
        verify(bgtLimtRepository, times(1)).findById(bgtLimtId);
        verify(bgtLimtRepository, times(0)).save(any(BgtLimt.class));
    }
}

package com.example.bms.bdgt;

import com.example.bms.bdgt.entity.BgtMaps;
import com.example.bms.bdgt.entity.BgtMapsId;
import com.example.bms.bdgt.repository.BgtMapsRepository;
import com.example.bms.bdgt.service.BgtMapsService;
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
public class BgtMapsServiceTest {
    @Mock
    private BgtMapsRepository bgtMapsRepository;

    @InjectMocks
    private BgtMapsService bgtMapsService;


    private BgtMapsId bgtMapsId;
    private BgtMaps bgtMaps;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트용 데이터 준비
        bgtMapsId = new BgtMapsId("2024","12","0001","221099");

        bgtMaps = new BgtMaps();
        bgtMaps.setBgtMapsId(bgtMapsId);
        bgtMaps.setDrmnAlctAmt(BigDecimal.valueOf(1000));
        bgtMaps.setDrmnPrfrAmt(BigDecimal.valueOf(500));
        bgtMaps.setAlctRdmpAmt(BigDecimal.valueOf(300));
        bgtMaps.setDrmnPrfrCcAmt(BigDecimal.valueOf(200));
    }

    @Test
    @DisplayName("월보집계내역 조회")
    void testGetBgtMapsList() {

        String bsnSyy = "2024";
        String baseMm = "12";
        String teamCd = "0001";

        when(bgtMapsRepository.findByBgtMapsIdBsnsYyAndBgtMapsIdBaseMmAndBgtMapsIdTeamCd(bsnSyy, baseMm, teamCd))
                .thenReturn(List.of(bgtMaps));

        List<BgtMaps> result = bgtMapsService.getBgtMapsList(bsnSyy, baseMm, teamCd);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bgtMaps, result.get(0));

        verify(bgtMapsRepository, times(1)).findByBgtMapsIdBsnsYyAndBgtMapsIdBaseMmAndBgtMapsIdTeamCd(bsnSyy, baseMm, teamCd);
    }

    @Test
    @DisplayName("월보집계 처리")
    void testUpdateBgtMaps() {

        BigDecimal additionalAlctAmt = BigDecimal.valueOf(500);
        BigDecimal additionalPrfrAmt = BigDecimal.valueOf(200);
        BigDecimal additionalRdmpAmt = BigDecimal.valueOf(100);
        BigDecimal additionalPrfrCcAmt = BigDecimal.valueOf(50);

        when(bgtMapsRepository.findById(bgtMapsId)).thenReturn(Optional.of(bgtMaps));

        when(bgtMapsRepository.save(any(BgtMaps.class))).thenReturn(bgtMaps);

        BgtMaps updatedBgtMaps = bgtMapsService.updateBgtMaps(bgtMapsId,
                additionalAlctAmt, additionalPrfrAmt, additionalRdmpAmt, additionalPrfrCcAmt);

        assertNotNull(updatedBgtMaps);
        assertEquals(BigDecimal.valueOf(1500), updatedBgtMaps.getDrmnAlctAmt());  // 1000 + 500
        assertEquals(BigDecimal.valueOf(700), updatedBgtMaps.getDrmnPrfrAmt());    // 500 + 200
        assertEquals(BigDecimal.valueOf(400), updatedBgtMaps.getAlctRdmpAmt());    // 300 + 100
        assertEquals(BigDecimal.valueOf(250), updatedBgtMaps.getDrmnPrfrCcAmt());  // 200 + 50

        verify(bgtMapsRepository, times(1)).findById(bgtMapsId);
        verify(bgtMapsRepository, times(1)).save(updatedBgtMaps);
    }

    @Test
    @DisplayName("월보집계 처리 실패")
    void testUpdateBgtMapsNotFound() {

        BigDecimal additionalAlctAmt = BigDecimal.valueOf(500);
        BigDecimal additionalPrfrAmt = BigDecimal.valueOf(200);
        BigDecimal additionalRdmpAmt = BigDecimal.valueOf(100);
        BigDecimal additionalPrfrCcAmt = BigDecimal.valueOf(50);

        when(bgtMapsRepository.findById(bgtMapsId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            bgtMapsService.updateBgtMaps(bgtMapsId,
                    additionalAlctAmt, additionalPrfrAmt, additionalRdmpAmt, additionalPrfrCcAmt);
        });

        assertEquals("해당 예산 월보 내역을 확인할 수 없습니다.", exception.getMessage());
    }

}

package com.example.bms.prfr;

import com.example.bms.glob.exception.CustomNotFoundException;
import com.example.bms.prfr.entity.BgtCbng;
import com.example.bms.prfr.entity.BgtCbngId;
import com.example.bms.prfr.repository.BgtCbngRepository;
import com.example.bms.prfr.service.BgtCbngService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtCbngServiceTest {

    @Mock
    private BgtCbngRepository bgtCbngRepository;

    @InjectMocks
    private BgtCbngService bgtCbngService;

    private BgtCbngId bgtCbngId;
    private BgtCbng bgtCbng;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bgtCbngId = BgtCbngId.builder()
                .tstmYmd("20241215")
                .tstmNo(1)
                .build();

        bgtCbng = BgtCbng.builder()
                .bgtCbngId(bgtCbngId)
                .prfrAmt(BigDecimal.valueOf(1000))
                .prfrCcAmt(BigDecimal.valueOf(200))
                .statCd("01")
                .delRsnCon(null)
                .build();
    }

    @Test
    @DisplayName("카드 매입 내역 상세 조회 - 존재할 때")
    void getById_shouldReturnEntity_whenEntityExists() {
        when(bgtCbngRepository.findById(bgtCbngId)).thenReturn(Optional.of(bgtCbng));

        Optional<BgtCbng> result = bgtCbngService.getById(bgtCbngId);

        assertTrue(result.isPresent());
        assertEquals(bgtCbng, result.get());
        verify(bgtCbngRepository, times(1)).findById(bgtCbngId);
    }

    @Test
    @DisplayName("카드 매입 내역 상세 조회 - 존재하지 않을 때")
    void getById_shouldReturnEmpty_whenEntityDoesNotExist() {
        when(bgtCbngRepository.findById(bgtCbngId)).thenReturn(Optional.empty());

        Optional<BgtCbng> result = bgtCbngService.getById(bgtCbngId);

        assertFalse(result.isPresent());
        verify(bgtCbngRepository, times(1)).findById(bgtCbngId);
    }

    @Test
    @DisplayName("카드 매입 내역 수정 - 존재할 때")
    void updateExecutionAmounts_shouldUpdateEntity_whenEntityExists() {
        when(bgtCbngRepository.findById(bgtCbngId)).thenReturn(Optional.of(bgtCbng));

        BigDecimal newPrfrAmt = BigDecimal.valueOf(1500);
        BigDecimal newPrfrCcAmt = BigDecimal.valueOf(300);
        bgtCbngService.updateExecutionAmounts(bgtCbngId, newPrfrAmt, newPrfrCcAmt);

        assertEquals(newPrfrAmt, bgtCbng.getPrfrAmt());
        assertEquals(newPrfrCcAmt, bgtCbng.getPrfrCcAmt());
        verify(bgtCbngRepository, times(1)).findById(bgtCbngId);
        verify(bgtCbngRepository, times(1)).save(bgtCbng);
    }

    @Test
    @DisplayName("카드 매입 내역 수정 - 존재하지 않을 때")
    void updateExecutionAmounts_shouldThrowException_whenEntityDoesNotExist() {
        when(bgtCbngRepository.findById(bgtCbngId)).thenReturn(Optional.empty());

        BigDecimal newPrfrAmt = BigDecimal.valueOf(1500);
        BigDecimal newPrfrCcAmt = BigDecimal.valueOf(300);

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () ->
                bgtCbngService.updateExecutionAmounts(bgtCbngId, newPrfrAmt, newPrfrCcAmt));

        assertEquals("카드 매입 내역을 찾을 수 없습니다.", exception.getMessage());
        verify(bgtCbngRepository, times(1)).findById(bgtCbngId);
    }

    @Test
    @DisplayName("카드 매입 내역 삭제 - 존재할 때")
    void markAsDeleted_shouldUpdateEntity_whenEntityExists() {
        when(bgtCbngRepository.findById(bgtCbngId)).thenReturn(Optional.of(bgtCbng));

        String delRsnCon = "Test deletion reason";
        String statCd = "99";
        bgtCbngService.markAsDeleted(bgtCbngId, delRsnCon, statCd);

        assertEquals(delRsnCon, bgtCbng.getDelRsnCon());
        assertEquals(statCd, bgtCbng.getStatCd());
        verify(bgtCbngRepository, times(1)).findById(bgtCbngId);
        verify(bgtCbngRepository, times(1)).save(bgtCbng);
    }

    @Test
    @DisplayName("카드 매입 내역 삭제 - 존재하지 않을 때")
    void markAsDeleted_shouldThrowException_whenEntityDoesNotExist() {
        when(bgtCbngRepository.findById(bgtCbngId)).thenReturn(Optional.empty());

        String delRsnCon = "카드 매입내역 삭제 사유 입력 테스트.";
        String statCd = "99";

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () ->
                bgtCbngService.markAsDeleted(bgtCbngId, delRsnCon, statCd));

        assertEquals("카드 매입 내역을 찾을 수 없습니다.", exception.getMessage());
        verify(bgtCbngRepository, times(1)).findById(bgtCbngId);
    }
}

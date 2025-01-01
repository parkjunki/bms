package com.example.bms.comm;

import com.example.bms.comm.dto.BgtSnctDto;
import com.example.bms.comm.entity.BgtSnct;
import com.example.bms.comm.entity.BgtSnctId;
import com.example.bms.comm.repository.BgtSnctRepository;
import com.example.bms.comm.service.BgtSnctService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtSnctServiceTest {
    @Mock
    private BgtSnctRepository bgtSnctRepository;

    @InjectMocks
    private BgtSnctService bgtSnctService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("결재내역정보 조회")
    public void testGetBgtSnctsByBsnsYyAndTeamCd() {

        String bsnsYy = "2024";
        String teamCd = "0001";

        BgtSnct entity1 = BgtSnct.builder()
                .bgtSnctId(new BgtSnctId("2024", 1))
                .teamCd(teamCd)
                .snctRqstYmd("20241201")
                .rqstUserNo("82062")
                .fsDt(LocalDateTime.now())
                .build();

        BgtSnct entity2 = BgtSnct.builder()
                .bgtSnctId(new BgtSnctId("2024", 2))
                .teamCd(teamCd)
                .snctRqstYmd("20241202")
                .rqstUserNo("82021")
                .fsDt(LocalDateTime.now())
                .build();

        when(bgtSnctRepository.findByBgtSnctIdBsnsYyAndTeamCd(bsnsYy, teamCd)).thenReturn(Arrays.asList(entity1, entity2));

        List<BgtSnctDto> result = bgtSnctService.getBgtSnctsByBsnsYyAndTeamCd(bsnsYy, teamCd);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("82062", result.get(0).getRqstUserNo());
        assertEquals("82021", result.get(1).getRqstUserNo());
        verify(bgtSnctRepository, times(1)).findByBgtSnctIdBsnsYyAndTeamCd(bsnsYy, teamCd);
    }

    @Test
    @DisplayName("결재내역정보 상세조회")
    public void testGetBgtSnctById() {

        BgtSnctId id = new BgtSnctId("2024", 1);
        BgtSnct entity = BgtSnct.builder()
                .bgtSnctId(new BgtSnctId("2024", 1))
                .teamCd("0001")
                .snctRqstYmd("20241201")
                .rqstUserNo("82062")
                .fsDt(LocalDateTime.now())
                .build();

        when(bgtSnctRepository.findById(id)).thenReturn(Optional.of(entity));

        BgtSnctDto result = bgtSnctService.getBgtSnctById(id);

        assertNotNull(result);
        assertEquals("82062", result.getRqstUserNo());
        verify(bgtSnctRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("결재내역정보 등록")
    public void testSaveBgtSnct() {

        BgtSnctDto dto = BgtSnctDto.builder()
                .bsnsYy("2024")
                .snctNo(1)
                .teamCd("0001")
                .snctRqstYmd("20241201")
                .rqstUserNo("82062")
                .fsDt(LocalDateTime.now())
                .build();

        BgtSnct entity = BgtSnct.builder()
                .bgtSnctId(new BgtSnctId("2024", 1))
                .teamCd("0001")
                .snctRqstYmd("20241201")
                .rqstUserNo("82062")
                .fsDt(LocalDateTime.now())
                .build();

        when(bgtSnctRepository.save(any(BgtSnct.class))).thenReturn(entity);

        BgtSnctDto result = bgtSnctService.saveBgtSnct(dto);

        assertNotNull(result);
        assertEquals("2024", result.getBsnsYy());
        assertEquals(1, result.getSnctNo());
        verify(bgtSnctRepository, times(1)).save(any(BgtSnct.class));
    }
}

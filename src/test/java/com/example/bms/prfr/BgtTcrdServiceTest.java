package com.example.bms.prfr;


import com.example.bms.prfr.dto.BgtTcrdDto;
import com.example.bms.prfr.entity.BgtTcrd;
import com.example.bms.prfr.entity.BgtTcrdId;
import com.example.bms.prfr.repository.BgtTcrdRepository;
import com.example.bms.prfr.service.BgtTcrdService;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BgtTcrdServiceTest {

    @Mock
    private BgtTcrdRepository bgtTcrdRepository;

    @InjectMocks
    private BgtTcrdService bgtTcrdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("팀 보유카드 내역 조회")
    public void testFindAll() {
        List<BgtTcrd> bgtTcrds = List.of(
                BgtTcrd.builder()
                        .bgtTcrdId(new BgtTcrdId("1234567890123456", 1))
                        .teamCd("0001")
                        .statCd("01")
                        .fsDt(LocalDate.now())
                        .build(),
                BgtTcrd.builder()
                        .bgtTcrdId(new BgtTcrdId("6543210987654321", 2))
                        .teamCd("0001")
                        .statCd("01")
                        .fsDt(LocalDate.now())
                        .build()
        );

        when(bgtTcrdRepository.findAll()).thenReturn(bgtTcrds);

        List<BgtTcrdDto> results = bgtTcrdService.findAllBgtTcrd();

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(bgtTcrdRepository).findAll();
    }

    @Test
    @DisplayName("보유카드 상세조회")
    public void testFindById() {

        BgtTcrdId bgtTcrdId = new BgtTcrdId("1234567890123456", 1);
        BgtTcrd bgtTcrd = BgtTcrd.builder()
                .bgtTcrdId(bgtTcrdId)
                .teamCd("0001")
                .statCd("01")
                .fsDt(LocalDate.now())
                .build();

        when(bgtTcrdRepository.findById(bgtTcrdId)).thenReturn(Optional.of(bgtTcrd));

        BgtTcrdDto result = bgtTcrdService.findBgtTcrdById(bgtTcrdId);

        assertNotNull(result);
        assertEquals("0001", result.getTeamCd());
        verify(bgtTcrdRepository).findById(bgtTcrdId);
    }

    @Test
    @DisplayName("보유카드 등록")
    public void testCreate() {
        BgtTcrdDto bgtTcrdDto = BgtTcrdDto.builder()
                .crdNo("1234567890123456")
                .teamCrdSrn(1)
                .teamCd("0001")
                .statCd("01")
                .build();

        BgtTcrd bgtTcrd = BgtTcrd.builder()
                .bgtTcrdId(new BgtTcrdId(bgtTcrdDto.getCrdNo(), bgtTcrdDto.getTeamCrdSrn()))
                .teamCd(bgtTcrdDto.getTeamCd())
                .statCd(bgtTcrdDto.getStatCd())
                .fsDt(LocalDate.now())
                .build();

        when(bgtTcrdRepository.save(any(BgtTcrd.class))).thenReturn(bgtTcrd);

        BgtTcrdDto result = bgtTcrdService.createBgtTcrd(bgtTcrdDto);

        assertNotNull(result);
        assertEquals("0001", result.getTeamCd());
        verify(bgtTcrdRepository).save(any(BgtTcrd.class));
    }

    @Test
    @DisplayName("보유카드 수정")
    public void testUpdate() {
        BgtTcrdId bgtTcrdId = new BgtTcrdId("1234567890123456", 1);
        BgtTcrdDto bgtTcrdDto = BgtTcrdDto.builder()
                .crdNo("1234567890123456")
                .teamCrdSrn(1)
                .teamCd("0002")
                .statCd("01")
                .build();

        when(bgtTcrdRepository.existsById(bgtTcrdId)).thenReturn(true);
        when(bgtTcrdRepository.save(any(BgtTcrd.class))).thenReturn(
                BgtTcrd.builder()
                        .bgtTcrdId(bgtTcrdId)
                        .teamCd(bgtTcrdDto.getTeamCd())
                        .statCd(bgtTcrdDto.getStatCd())
                        .lsDt(LocalDate.now())
                        .build()
        );

        BgtTcrdDto result = bgtTcrdService.updateBgtTcrd(bgtTcrdId, bgtTcrdDto);

        assertNotNull(result);
        assertEquals("0002", result.getTeamCd());
        verify(bgtTcrdRepository).existsById(bgtTcrdId);
        verify(bgtTcrdRepository).save(any(BgtTcrd.class));
    }

    @Test
    @DisplayName("보유카드 삭제")
    public void testDelete() {
        BgtTcrdId bgtTcrdId = new BgtTcrdId("1234567890123456", 1);

        bgtTcrdService.deleteBgtTcrdById(bgtTcrdId);

        verify(bgtTcrdRepository).deleteById(bgtTcrdId);
    }
}

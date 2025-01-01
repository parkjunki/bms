package com.example.bms.prfr;

import com.example.bms.prfr.dto.BgtTstmDto;
import com.example.bms.prfr.entity.BgtTstm;
import com.example.bms.prfr.entity.BgtTstmId;
import com.example.bms.prfr.repository.BgtTstmRepository;
import com.example.bms.prfr.service.BgtTstmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtTstmServiceTest {

    @Mock
    private BgtTstmRepository bgtTstmRepository;

    @InjectMocks
    private BgtTstmService bgtTstmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("모든 증빙 조회")
    public void testGetAllBgtTstms() {

        when(bgtTstmRepository.findAll()).thenReturn(Arrays.asList(
                BgtTstm.builder().bgtTstmId(new BgtTstmId("20231214","00001")).teamCd("0002").rcppDcd("01").statCd("01")
                        .issAmt(new BigDecimal("250000")).bprSrn("Az235VzijthtKey-10").frm("우리슈퍼").rpprNm("홍길동")
                        .prfrAmt(new BigDecimal("0")).prfrCcAmt(new BigDecimal("0")).etcCon("테스트증빙")
                        .fsDt(LocalDate.now()).lsDt(LocalDate.now()).build(),
                BgtTstm.builder().bgtTstmId(new BgtTstmId("20231214","00002")).teamCd("0003").rcppDcd("01").statCd("01")
                        .issAmt(new BigDecimal("150000")).bprSrn("Az235VzijthtKey-11").frm("편의점").rpprNm("김영철")
                        .prfrAmt(new BigDecimal("0")).prfrCcAmt(new BigDecimal("0")).etcCon("테스트증빙2")
                        .fsDt(LocalDate.now()).lsDt(LocalDate.now()).build()
        ));

        List<BgtTstm> list = bgtTstmService.getAllBgtTstm();

        list.forEach(bgtTstm -> System.out.println("테스트 데이터 : " + bgtTstm.getBgtTstmId().getTstmYmd() + "//" + bgtTstm.getBgtTstmId().getTstmNo()));

        assertNotNull(list);
        assertEquals(2, list.size());
        verify(bgtTstmRepository, times(1)).findAll();
    }



    @Test
    @DisplayName("증빙 세부 조회")
    public void testGetBgtTstm() {
        when(bgtTstmRepository.findById(new BgtTstmId("20231214", "00001"))).thenReturn(Optional.of(
                BgtTstm.builder().bgtTstmId(new BgtTstmId("20231214","00001")).build()
        ));

        Optional<BgtTstm> result = bgtTstmService.getBgtTstmById("20231214", "00001");
        assertTrue(result.isPresent());
        assertEquals("00001", result.get().getBgtTstmId().getTstmNo());
        verify(bgtTstmRepository, times(1)).findById(any(BgtTstmId.class));
    }

    @Test
    @DisplayName("증빙 등록")
    public void testSaveBgtTstm() {
        BgtTstm entity = BgtTstm.builder()
                .bgtTstmId(new BgtTstmId("20231214","00001"))
                .teamCd("0002")
                .build();
        when(bgtTstmRepository.save(any(BgtTstm.class))).thenReturn(entity);

        BgtTstmDto dto = BgtTstmDto.builder()
                .tstmYmd("20231214")
                .tstmNo("00001")
                .teamCd("0002")
                .build();
        BgtTstm saved = bgtTstmService.saveBgtTstm(dto);

        assertNotNull(saved);
        assertEquals("20231214", saved.getBgtTstmId().getTstmYmd());
        assertEquals("00001", saved.getBgtTstmId().getTstmNo());
        verify(bgtTstmRepository, times(1)).save(any(BgtTstm.class));
    }

    @Test
    @DisplayName("증빙 수정")
    public void testUpdateBgtTstm() {
        BgtTstm existingEntity = BgtTstm.builder()
                .bgtTstmId(new BgtTstmId("20231214","00001"))
                .teamCd("0002")
                .build();
        when(bgtTstmRepository.findById(new BgtTstmId("20231214", "00001"))).thenReturn(Optional.of(existingEntity));
        when(bgtTstmRepository.save(any(BgtTstm.class))).thenReturn(existingEntity);

        BgtTstmDto dto = BgtTstmDto.builder().teamCd("0003").build();
        BgtTstm updated = bgtTstmService.updateBgtTstm("20231214", "00001", dto);

        assertNotNull(updated);
        assertEquals("0002", updated.getTeamCd());
        verify(bgtTstmRepository, times(1)).findById(any(BgtTstmId.class));
        verify(bgtTstmRepository, times(1)).save(any(BgtTstm.class));
    }

    @Test
    @DisplayName("증빙 삭제")
    public void testDeleteBgtTstm() {
        String tstmYmd = "20231214";
        String tstmNo = "00001";

        when(bgtTstmRepository.existsById(new BgtTstmId(tstmYmd, tstmNo))).thenReturn(true);

        bgtTstmService.deleteBgtTstmById("20231214", "00001");

        verify(bgtTstmRepository, times(1)).deleteById(any(BgtTstmId.class));
    }


}

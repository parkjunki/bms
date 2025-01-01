package com.example.bms.bdgt;

import com.example.bms.bdgt.dto.BgtTranDto;
import com.example.bms.bdgt.entity.BgtTran;
import com.example.bms.bdgt.entity.BgtTranId;
import com.example.bms.bdgt.repository.BgtTranRepository;
import com.example.bms.bdgt.service.BgtTranService;
import com.example.bms.prfr.entity.BgtTcrd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BgtTranServiceTest {

    @Mock
    private BgtTranRepository bgtTranRepository;

    @InjectMocks
    private BgtTranService bgtTranService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("예산거래내역 조회")
    void getBgtTranByBsnsYyAndTeamCd() {

        String bsnsYy = "2024";
        String teamCd = "0001";

        when(bgtTranRepository.findByBgtTranIdBsnsYyAndTeamCd(bsnsYy, teamCd)).thenReturn(Arrays.asList(
                BgtTran.builder().bgtTranId(new BgtTranId("2024", 1))
                        .bgtItexCd("221099")
                        .teamCd("0001")
                        .build(),
                BgtTran.builder().bgtTranId(new BgtTranId("2024", 3))
                        .bgtItexCd("223099")
                        .teamCd("0001")
                        .build()
        ));

        List<BgtTranDto> results = bgtTranService.getBgtTransByBsnsYyAndTeamCd(bsnsYy, teamCd);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.stream().allMatch(tran -> tran.getBsnsYy().equals(bsnsYy) && tran.getTeamCd().equals(teamCd)));
    }

    @Test
    void saveBgtTran() {

        String bsnsYy = "2024";
        Integer tranSrn = 1;
        String tranDcd = "001";
        String statCd = "01";
        String teamCd = "0001";
        String bgtItexCd = "223099";
        String tranYmd = "20241214";
        String tranHms = "123456";
        String tranUserNo = "82062";
        Integer alapNo = 1;
        String prfrYm = "202412";
        Integer prfrNo = 1;
        BigDecimal tranAmt = new BigDecimal("25000.00");


        BgtTranDto bgtTranDto = BgtTranDto.builder()
                .bsnsYy(bsnsYy)
                .tranSrn(tranSrn)
                .tranDcd(tranDcd)
                .statCd(statCd)
                .teamCd(teamCd)
                .bgtItexCd(bgtItexCd)
                .tranYmd(tranYmd)
                .tranHms(tranHms)
                .tranUserNo(tranUserNo)
                .alapNo(alapNo)
                .prfrYm(prfrYm)
                .prfrNo(prfrNo)
                .tranAmt(tranAmt)
                .build();

        BgtTran bgtTran = BgtTran.builder()
                .bgtTranId(new BgtTranId(bgtTranDto.getBsnsYy(),bgtTranDto.getTranSrn()))
                .tranDcd(bgtTranDto.getTranDcd())
                .statCd(bgtTranDto.getStatCd())
                .teamCd(bgtTranDto.getTeamCd())
                .bgtItexCd(bgtTranDto.getBgtItexCd())
                .tranYmd(bgtTranDto.getTranYmd())
                .tranHms(bgtTranDto.getTranHms())
                .tranUserNo(bgtTranDto.getTranUserNo())
                .alapNo(bgtTranDto.getAlapNo())
                .prfrYm(bgtTranDto.getPrfrYm())
                .prfrNo(bgtTranDto.getPrfrNo())
                .tranAmt(bgtTranDto.getTranAmt())
                .build();


        when(bgtTranRepository.save(any(BgtTran.class))).thenReturn(bgtTran);

        BgtTranDto result = bgtTranService.saveBgtTran(bgtTranDto);

        assertNotNull(result);
        assertEquals(bsnsYy, result.getBsnsYy());
        assertNotNull(result.getTranSrn());
    }
}

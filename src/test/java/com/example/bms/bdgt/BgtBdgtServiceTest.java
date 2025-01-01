package com.example.bms.bdgt;

import com.example.bms.bdgt.entity.BgtBdgt;
import com.example.bms.bdgt.entity.BgtBdgtId;
import com.example.bms.bdgt.repository.BgtBdgtRepository;
import com.example.bms.bdgt.service.BgtBdgtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtBdgtServiceTest {
    @Mock
    private BgtBdgtRepository bgtBdgtRepository;

    @InjectMocks
    private BgtBdgtService bgtBdgtService;

    private BgtBdgt bgtBdgt1;
    private BgtBdgt bgtBdgt2;
    private List<BgtBdgt> bgtBdgtList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bgtBdgt1 = new BgtBdgt();
        bgtBdgt1.setBgtBdgtId(new BgtBdgtId("2024","1","0001","221099"));
        bgtBdgt1.setStatCd("A");
        bgtBdgt1.setBdgtMubAmt(new BigDecimal("10000"));
        bgtBdgt1.setRginAlctAmt(new BigDecimal("9000"));
        bgtBdgt1.setBdalFnsYn("N");

        bgtBdgt2 = new BgtBdgt();
        bgtBdgt2.setBgtBdgtId(new BgtBdgtId("2024","1","0001","224099"));
        bgtBdgt2.setStatCd("B");
        bgtBdgt2.setBdgtMubAmt(new BigDecimal("15000"));
        bgtBdgt2.setRginAlctAmt(new BigDecimal("14000"));
        bgtBdgt2.setBdalFnsYn("N");

        bgtBdgtList = new ArrayList<>();
        bgtBdgtList.add(bgtBdgt1);
        bgtBdgtList.add(bgtBdgt2);
    }

    @Test
    @DisplayName("실행예산 목록 조회")
    public void testGetBgtBdgtList() {
        when(bgtBdgtRepository.findByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd("2024", "1")).thenReturn(bgtBdgtList);

        List<BgtBdgt> result = bgtBdgtService.getBgtBdgtList("2024", "1");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("0001", result.get(0).getBgtBdgtId().getTeamCd());
        assertEquals("221099", result.get(0).getBgtBdgtId().getBgtItexCd());
        assertEquals("0001", result.get(1).getBgtBdgtId().getTeamCd());
        assertEquals("224099", result.get(1).getBgtBdgtId().getBgtItexCd());
    }


    @Test
    @DisplayName("실행예산 등록")
    public void testAddBgtBdgtList() {

        when(bgtBdgtRepository.saveAll(anyList())).thenReturn(bgtBdgtList);

        bgtBdgtService.addBgtBdgtList(bgtBdgtList);

        verify(bgtBdgtRepository, times(1)).saveAll(bgtBdgtList);
    }

    @Test
    @DisplayName("실행예산 수정")
    public void testUpdateBgtBdgtList() {

        //when(bgtBdgtRepository.findByBsnSyyAndQtrDcd(bsnsYy,qtrDcd)).thenReturn(bgtBdgtList);

        bgtBdgtList.get(0).setRginAlctAmt(new BigDecimal(15000));
        bgtBdgtList.get(1).setRginAlctAmt(new BigDecimal(85000));

        bgtBdgtService.updateBgtBdgtList(bgtBdgtList);

        verify(bgtBdgtRepository, times(1)).saveAll(bgtBdgtList);
    }

    @Test
    @DisplayName("실행예산 삭제")
    public void testDeleteBgtBdgt() {

        String bsnsYy = "2024";
        String qtrDcd = "1";
        when(bgtBdgtRepository.findByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(bsnsYy,qtrDcd))
                .thenReturn(bgtBdgtList);
        doNothing().when(bgtBdgtRepository).deleteAllByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(bsnsYy, qtrDcd);

        bgtBdgtService.deleteBgtBdgtList(bsnsYy,qtrDcd);

        verify(bgtBdgtRepository, times(1)).deleteAllByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(bsnsYy, qtrDcd);
    }

}

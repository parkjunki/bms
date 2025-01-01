package com.example.bms.prfr;

import com.example.bms.prfr.entity.BgtPrfr;
import com.example.bms.prfr.entity.BgtPrfrId;
import com.example.bms.prfr.repository.BgtPrfrRepository;
import com.example.bms.prfr.service.BgtPrfrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtPrfrServiceTest {
    @Mock
    private BgtPrfrRepository bgtPrfrRepository;

    @InjectMocks
    private BgtPrfrService bgtPrfrService;

    private BgtPrfr bgtPrfr;
    private BgtPrfrId bgtPrfrId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bgtPrfrId = new BgtPrfrId("0001", "202401", 1);

        bgtPrfr = new BgtPrfr();
        bgtPrfr.setBgtPrfrId(bgtPrfrId);
        bgtPrfr.setBsnsYy("2024");
        bgtPrfr.setPrfrAmt(new BigDecimal(10000.0));
    }

    @Test
    @DisplayName("지급결의서 등록")
    public void testCreateBgtPrfr() {

        String prfrYm = "202401";
        Integer maxPrfrNo = 1;

        when(bgtPrfrRepository.findMaxPrfrNoByPrfrYm(prfrYm)).thenReturn(maxPrfrNo);
        when(bgtPrfrRepository.save(any(BgtPrfr.class))).thenReturn(bgtPrfr);


        BgtPrfr result = bgtPrfrService.createBgtPrfr(bgtPrfr);

        assertNotNull(result);
        assertEquals(2, result.getBgtPrfrId().getPrfrNo());
        verify(bgtPrfrRepository, times(1)).findMaxPrfrNoByPrfrYm(prfrYm);
        verify(bgtPrfrRepository, times(1)).save(result);
    }

    @Test
    @DisplayName("결의서 목록 조회")
    public void testGetBgtPrfrList() {

        Pageable pageable = PageRequest.of(0, 10);
        List<BgtPrfr> bgtPrfrList = Arrays.asList(bgtPrfr);
        Page<BgtPrfr> page = new PageImpl<>(bgtPrfrList, pageable, bgtPrfrList.size());

        when(bgtPrfrRepository.findByBsnsYyAndTeamCd("2024", "0001", pageable)).thenReturn(page);

        Page<BgtPrfr> result = bgtPrfrService.getBgtPrfrList("2024", "0001", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(bgtPrfrRepository, times(1)).findByBsnsYyAndTeamCd("2024", "0001", pageable);
    }

    @Test
    @DisplayName("결의서 상세조회")
    public void testGetBgtPrfrDetail() {

        when(bgtPrfrRepository.findByBgtPrfrId(bgtPrfrId)).thenReturn(bgtPrfr);


        BgtPrfr result = bgtPrfrService.getBgtPrfrDetail(bgtPrfrId);


        assertNotNull(result);
        assertEquals(bgtPrfrId, result.getBgtPrfrId());
        verify(bgtPrfrRepository, times(1)).findByBgtPrfrId(bgtPrfrId);
    }

    @Test
    @DisplayName("결의서 수정")
    public void testUpdateBgtPrfr() {

        BgtPrfr updatedBgtPrfr = new BgtPrfr();
        updatedBgtPrfr.setPrfrAmt(new BigDecimal(20000.0));
        updatedBgtPrfr.setBgtItexCd("221099");

        when(bgtPrfrRepository.findByBgtPrfrId(bgtPrfrId)).thenReturn(bgtPrfr);
        when(bgtPrfrRepository.save(bgtPrfr)).thenReturn(updatedBgtPrfr);

        BgtPrfr result = bgtPrfrService.updateBgtPrfr(bgtPrfrId, updatedBgtPrfr);

        assertNotNull(result);
        assertEquals(updatedBgtPrfr.getPrfrAmt(), result.getPrfrAmt());
        assertEquals(updatedBgtPrfr.getBgtItexCd(), result.getBgtItexCd());
        verify(bgtPrfrRepository, times(1)).findByBgtPrfrId(bgtPrfrId);
        verify(bgtPrfrRepository, times(1)).save(bgtPrfr);
    }

    @Test
    @DisplayName("결의서 삭제")
    public void testDeleteBgtPrfr() {
        doNothing().when(bgtPrfrRepository).deleteById(bgtPrfrId);

        bgtPrfrService.deleteBgtPrfr(bgtPrfrId);

        verify(bgtPrfrRepository, times(1)).deleteById(bgtPrfrId);
    }
}

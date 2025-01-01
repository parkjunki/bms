package com.example.bms.comm;

import com.example.bms.comm.entity.BgtItex;
import com.example.bms.comm.repository.BgtItexRepository;
import com.example.bms.comm.service.BgtItexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BgtItexServiceTest {

    @Mock
    private BgtItexRepository bgtItexRepository;

    @InjectMocks
    private BgtItexService bgtItexService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("비목코드 전체 조회")
    void testGetAllBgtItex() {
        when(bgtItexRepository.findAll()).thenReturn(List.of(
                new BgtItex("221099", "야근식대", "IFRS1099", LocalDate.now(), LocalDate.now()),
                new BgtItex("223099", "시내교통비", "IFRS3099", LocalDate.now(), LocalDate.now())
        ));

        List<BgtItex> result = bgtItexRepository.findAll();
        result.forEach(bgtItex -> System.out.println("비목코드 명 : " + bgtItex.getBgtItexNm()));

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bgtItexRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("비목코드 상세 조회")
    void testGetBgtItexByCd() {
        String bgtItexCd = "222099";
        BgtItex mockBgtItex = new BgtItex(bgtItexCd, "연수비" , "IFRS2099", LocalDate.now(), LocalDate.now());
        when(bgtItexRepository.findById(bgtItexCd)).thenReturn(Optional.of(mockBgtItex));


        Optional<BgtItex> result = bgtItexService.getBgtItexByCd(bgtItexCd);

        System.out.println("상세 조회 코드 : " + result.get().getBgtItexCd());

        assertNotNull(result);
        assertEquals(bgtItexCd, result.get().getBgtItexCd());
        verify(bgtItexRepository, times(1)).findById(bgtItexCd);
    }

    @Test
    @DisplayName("비목코드 등록")
    void testCreateBgtItex() {

        BgtItex bgtItex = new BgtItex("224099", "용역비" , "IFRS4099", LocalDate.now(), LocalDate.now());
        BgtItex mockBgtItex = new BgtItex(bgtItex.getBgtItexCd(), bgtItex.getBgtItexNm(), bgtItex.getAccd(), bgtItex.getFsDt(), bgtItex.getLsDt());
        when(bgtItexRepository.save(any(BgtItex.class))).thenReturn(mockBgtItex);

        var result = bgtItexService.createBgtItex(bgtItex);

        // Assertions
        assertNotNull(result);
        assertEquals(bgtItex.getBgtItexCd(), result.getBgtItexCd());
        verify(bgtItexRepository, times(1)).save(any(BgtItex.class));
    }

    @Test
    @DisplayName("비목코드 수정")
    void testUpdateBgtItex() {

        String bgtItexCd = "221099";
        BgtItex existingBgtItex = new BgtItex(bgtItexCd, "야근식대","IFRS1099", LocalDate.now(), LocalDate.now());
        BgtItex updatedBgtItex = new BgtItex(bgtItexCd, "야근식비","IFRS1099", LocalDate.now(), LocalDate.now());

        when(bgtItexRepository.findById(bgtItexCd)).thenReturn(Optional.of(existingBgtItex));
        when(bgtItexRepository.save(any(BgtItex.class))).thenReturn(updatedBgtItex);


        var result = bgtItexService.updateBgtItex(bgtItexCd, updatedBgtItex);

        assertNotNull(result);
        assertEquals("야근식비", result.getBgtItexNm());
        verify(bgtItexRepository, times(1)).findById(bgtItexCd);
        verify(bgtItexRepository, times(1)).save(any(BgtItex.class));
    }

    @Test
    @DisplayName("비목코드 삭제")
    void testDeleteBgtItex() {

        String bgtItexCd = "222099";
        when(bgtItexRepository.existsById(bgtItexCd)).thenReturn(true);

        bgtItexService.deleteBgtItexByCd(bgtItexCd);

        verify(bgtItexRepository, times(1)).deleteById(bgtItexCd);
    }
}

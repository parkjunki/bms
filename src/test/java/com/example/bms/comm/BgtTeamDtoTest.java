package com.example.bms.comm;

import com.example.bms.comm.dto.BgtTeamDto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Date;

public class BgtTeamDtoTest {

    @Test
    public void dtoTest() {
        //given
        String teamCd = "0001";
        String teamNm = "Test1";
        LocalDate fsDt = LocalDate.now();
        LocalDate lsDt = LocalDate.now();

        //when
        BgtTeamDto bgtTeamDto = new BgtTeamDto(teamCd,teamNm,fsDt,lsDt);

        //then
        assertThat(bgtTeamDto.getTeamNm()).isEqualTo(teamNm);
    }
}

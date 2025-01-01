package com.example.bms.prfr;

import com.example.bms.prfr.dao.BgtCbngDao;
import com.example.bms.prfr.vo.BgtCbngVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BgtCbngDaoTest {
    @Autowired
    private BgtCbngDao bgtCbngDao;

    @Test
    public void getBgtCbngListByTeamCrd() {
        String teamCd = "0001";
        // 조인된 데이터를 조회
        List<BgtCbngVo> result = bgtCbngDao.getBgtCbngListByTeamCrd(teamCd);

        // 결과가 null이 아닌지 확인
        assertNotNull(result);
    }
}

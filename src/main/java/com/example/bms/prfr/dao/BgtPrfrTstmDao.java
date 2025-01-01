package com.example.bms.prfr.dao;

import com.example.bms.prfr.vo.BgtPrfrTstmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BgtPrfrTstmDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<BgtPrfrTstmVo> bgtPrfrTstmVoRowMapper = (rs, rowNum) -> {
        BgtPrfrTstmVo bgtPrfrTstmVo = new BgtPrfrTstmVo();
        bgtPrfrTstmVo.setTstmYmd(rs.getString("TSTM_YMD"));
        bgtPrfrTstmVo.setTstmNo(rs.getInt("TSTM_NO"));
        bgtPrfrTstmVo.setTstmDcd(rs.getString("TSTM_DCD"));
        bgtPrfrTstmVo.setBilgAmt(rs.getBigDecimal("BILG_AMT"));
        bgtPrfrTstmVo.setFrm(rs.getString("FRM"));
        return bgtPrfrTstmVo;
    };

    public List<BgtPrfrTstmVo> getBgtPrfrTstmListByTeamCd(String teamCd) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT CBNG.TSTM_YMD \n");
        sql.append("       ,CBNG.TSTM_NO \n");
        sql.append("       ,'2' AS TSTM_DCD\n");
        sql.append("       ,CBNG.BILG_AMT AS BILG_AMT\n");
        sql.append("       ,CBNG.AFST_NM AS FRM \n");
        sql.append(" FROM TB_BGT_TCRD TCRD, TB_BGT_CBNG CBNG \n");
        sql.append("WHERE TCRD.CRD_NO = CBNG.CRD_NO \n");
        sql.append("  AND TCRD.TEAM_CD = ? \n");
        sql.append("UNION ALL \n");
        sql.append("SELECT TSTM_YMD\n");
        sql.append("       ,TSTM_NO \n");
        sql.append("       ,'1' AS TSTM_DCD\n");
        sql.append("       ,ISS_AMT AS BILG_AMT\n");
        sql.append("       ,FRM \n");
        sql.append(" FROM TB_BGT_TSTM \n");
        sql.append("WHERE TEAM_CD = ? \n");

        return jdbcTemplate.query(sql.toString(), bgtPrfrTstmVoRowMapper, teamCd);
    }
}

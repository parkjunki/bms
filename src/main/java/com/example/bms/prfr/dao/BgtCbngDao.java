package com.example.bms.prfr.dao;


import com.example.bms.prfr.vo.BgtCbngVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BgtCbngDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<BgtCbngVo> bgtCbngVoRowMapper = (rs , rowNum) -> {
        BgtCbngVo bgtCbngVo = new BgtCbngVo();
        bgtCbngVo.setTstmYmd(rs.getString("TSTM_YMD"));
        bgtCbngVo.setTstmNo(rs.getInt("TSTM_NO"));
        bgtCbngVo.setCrdBngYmd(rs.getString("CRD_BNG_YMD"));
        bgtCbngVo.setCrdNo(rs.getString("CRD_NO"));
        bgtCbngVo.setCrahYmd(rs.getString("CRAH_YMD"));
        bgtCbngVo.setAthzHms(rs.getString("ATHZ_HMS"));
        bgtCbngVo.setAmslAmt(rs.getBigDecimal("AMSL_AMT"));
        bgtCbngVo.setBilgAmt(rs.getBigDecimal("BILG_AMT"));
        bgtCbngVo.setAfstNm(rs.getString("AFST_NM"));
        bgtCbngVo.setAfstTpbCd(rs.getString("AFST_TPB_CD"));
        bgtCbngVo.setAfstRpprNm(rs.getString("AFST_RPPR_NM"));
        bgtCbngVo.setAfstDtlAdr(rs.getString("AFST_DTL_ADR"));
        bgtCbngVo.setAfstTpn(rs.getString("AFST_TPN"));
        bgtCbngVo.setPrfrAmt(rs.getBigDecimal("PRFR_AMT"));
        bgtCbngVo.setPrfrCcAmt(rs.getBigDecimal("PRFR_CC_AMT"));
        bgtCbngVo.setBngCnclAmt(rs.getBigDecimal("BNG_CNCL_AMT"));
        bgtCbngVo.setCardAhstAmt(rs.getBigDecimal("CARD_AHST_AMT"));
        bgtCbngVo.setCardAhstYmd(rs.getDate("CARD_AHST_YMD").toLocalDate());
        bgtCbngVo.setRfndArngAmt(rs.getBigDecimal("RFND_ARNG_AMT"));
        bgtCbngVo.setRfndArngYmd(rs.getDate("RFND_ARNG_YMD") != null ? rs.getDate("RFND_ARNG_YMD").toLocalDate() : null);

        return bgtCbngVo;
    };

    public List<BgtCbngVo> getBgtCbngListByTeamCrd(String teamCd) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT CBNG.* \n");  //* 대신에 뽑을 컬럼들을 쭉 나열해야 함.
        sql.append(" FROM TB_BGT_TCRD TCRD, TB_BGT_CBNG CBNG \n");
        sql.append("WHERE TCRD.CRD_NO = CBNG.CRD_NO \n");
        sql.append("  AND TCRD.TEAM_CD = ? \n");

        return jdbcTemplate.query(sql.toString(), bgtCbngVoRowMapper, teamCd);
    }
}

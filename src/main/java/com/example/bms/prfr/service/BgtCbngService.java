package com.example.bms.prfr.service;

import com.example.bms.glob.exception.CustomNotFoundException;
import com.example.bms.prfr.dao.BgtCbngDao;
import com.example.bms.prfr.entity.BgtCbng;
import com.example.bms.prfr.entity.BgtCbngId;
import com.example.bms.prfr.repository.BgtCbngRepository;
import com.example.bms.prfr.vo.BgtCbngVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BgtCbngService {
    private final BgtCbngRepository bgtCbngRepository;

    private final BgtCbngDao bgtCbngDao;

    public BgtCbngService(BgtCbngRepository bgtCbngRepository, BgtCbngDao bgtCbngDao) {
        this.bgtCbngRepository = bgtCbngRepository;
        this.bgtCbngDao = bgtCbngDao;
    }

    public List<BgtCbngVo> getBgtCbngListByTeamCrd (String teamCd) {
        return bgtCbngDao.getBgtCbngListByTeamCrd(teamCd);
    }

    public Optional<BgtCbng> getById(BgtCbngId bgtCbngId) {
        return bgtCbngRepository.findById(bgtCbngId);
    }

    @Transactional
    public void updateExecutionAmounts(BgtCbngId bgtCbngId, BigDecimal prfrAmt, BigDecimal prfrCcAmt) {
        BgtCbng entity = bgtCbngRepository.findById(bgtCbngId)
                .orElseThrow(() -> new CustomNotFoundException("카드 매입 내역을 찾을 수 없습니다."));
        entity.setPrfrAmt(prfrAmt);
        entity.setPrfrCcAmt(prfrCcAmt);
        bgtCbngRepository.save(entity);
    }

    @Transactional
    public void markAsDeleted(BgtCbngId bgtCbngId, String delRsnCon, String statCd) {
        BgtCbng entity = bgtCbngRepository.findById(bgtCbngId)
                .orElseThrow(() -> new CustomNotFoundException("카드 매입 내역을 찾을 수 없습니다."));
        entity.setDelRsnCon(delRsnCon);
        entity.setStatCd(statCd);
        bgtCbngRepository.save(entity);
    }
}

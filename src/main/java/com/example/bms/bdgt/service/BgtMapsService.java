package com.example.bms.bdgt.service;

import com.example.bms.bdgt.entity.BgtMaps;
import com.example.bms.bdgt.entity.BgtMapsId;
import com.example.bms.bdgt.repository.BgtMapsRepository;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BgtMapsService {
    private final BgtMapsRepository bgtMapsRepository;

    public BgtMapsService(BgtMapsRepository bgtMapsRepository) {
        this.bgtMapsRepository = bgtMapsRepository;
    }

    public List<BgtMaps> getBgtMapsList(String bsnSyy, String baseMm, String teamCd) {
        return bgtMapsRepository.findByBgtMapsIdBsnsYyAndBgtMapsIdBaseMmAndBgtMapsIdTeamCd(bsnSyy, baseMm, teamCd);
    }

    @Transactional
    public BgtMaps updateBgtMaps(BgtMapsId bgtMapsId, BigDecimal drmnAlctAmt, BigDecimal drmnPrfrAmt, BigDecimal alctRdmpAmt, BigDecimal drmnPrfrCcAmt) {
        Optional<BgtMaps> optionalBgtMaps = bgtMapsRepository.findById(bgtMapsId);

        if (optionalBgtMaps.isPresent()) {
            BgtMaps bgtMaps = optionalBgtMaps.get();

            // 기존 값에 누적 합계를 추가
            bgtMaps.setDrmnAlctAmt(bgtMaps.getDrmnAlctAmt().add(drmnAlctAmt));
            bgtMaps.setDrmnPrfrAmt(bgtMaps.getDrmnPrfrAmt().add(drmnPrfrAmt));
            bgtMaps.setAlctRdmpAmt(bgtMaps.getAlctRdmpAmt().add(alctRdmpAmt));
            bgtMaps.setDrmnPrfrCcAmt(bgtMaps.getDrmnPrfrCcAmt().add(drmnPrfrCcAmt));

            return bgtMapsRepository.save(bgtMaps);
        } else {
            throw new CustomNotFoundException("해당 예산 월보 내역을 확인할 수 없습니다.");
        }
    }
}

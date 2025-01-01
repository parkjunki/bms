package com.example.bms.bdgt.service;

import com.example.bms.bdgt.dto.BgtTranDto;
import com.example.bms.bdgt.entity.BgtBdgt;
import com.example.bms.bdgt.entity.BgtLimtId;
import com.example.bms.bdgt.entity.BgtMapsId;
import com.example.bms.bdgt.repository.BgtBdgtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BgtBdgtService {
    private final BgtBdgtRepository bgtBdgtRepository;
    private final BgtLimtService bgtLimtService;
    private final BgtMapsService bgtMapsService;
    private final BgtTranService bgtTranService;

    public BgtBdgtService(BgtBdgtRepository bgtBdgtRepository, BgtLimtService bgtLimtService, BgtMapsService bgtMapsService, BgtTranService bgtTranService) {
        this.bgtBdgtRepository = bgtBdgtRepository;
        this.bgtLimtService = bgtLimtService;
        this.bgtMapsService = bgtMapsService;
        this.bgtTranService = bgtTranService;
    }

    public List<BgtBdgt> getBgtBdgtList(String bsnSyy, String qtrDcd) {
        return bgtBdgtRepository.findByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(bsnSyy, qtrDcd);
    }

    @Transactional
    public void addBgtBdgtList(List<BgtBdgt> bgtBdgtList) {
        bgtBdgtRepository.saveAll(bgtBdgtList);
    }

    @Transactional
    public void updateBgtBdgtList(List<BgtBdgt> bgtBdgtList) {
        bgtBdgtRepository.saveAll(bgtBdgtList);
    }

    @Transactional
    public void deleteBgtBdgtList(String bsnsYy, String qtrDcd) {
        List<BgtBdgt> bgtBdgtList = bgtBdgtRepository.findByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(bsnsYy, qtrDcd);
        if (bgtBdgtList != null) {
            bgtBdgtRepository.deleteAllByBgtBdgtIdBsnsYyAndBgtBdgtIdQtrDcd(bsnsYy, qtrDcd);
        }
    }

    /*실행예산 배정 실행
    * 1. 관리자가 배정 실행 버튼을 누를 때
    * 2. 각 목록에 있는 사업년도, 팀코드, 비목코드, 조정배정액을 기준으로 예산한도기본 업데이트
    * 3. 예산월보내역집계 업데이트
    * 4. 예산거래내역 등록
    */
    @Transactional
    public void executeBgtBdgtList(List<BgtBdgt> bgtBdgtList) {

        for(BgtBdgt bgtBdgt : bgtBdgtList) {
            String bsnsYy = bgtBdgt.getBgtBdgtId().getBsnsYy();
            String teamCd = bgtBdgt.getBgtBdgtId().getTeamCd();
            String bgtItexCd = bgtBdgt.getBgtBdgtId().getBgtItexCd();
            BigDecimal rginAlctAmt = bgtBdgt.getRginAlctAmt();

            //예산한도기본 업데이트
            bgtLimtService.updateAmounts(new BgtLimtId(bsnsYy,teamCd,bgtItexCd)
                    , rginAlctAmt
                    , new BigDecimal(0)
                    , new BigDecimal(0)
                    , new BigDecimal(0));

            LocalDate date = LocalDate.now();
            String baseMm = date.format(DateTimeFormatter.ofPattern("MM"));

            //예산월보내역집계 업데이트
            bgtMapsService.updateBgtMaps(new BgtMapsId(bsnsYy,teamCd,baseMm,bgtItexCd)
                    , rginAlctAmt
                    , new BigDecimal(0)
                    , new BigDecimal(0)
                    , new BigDecimal(0));

            //예산거래내역을 위한 DTO 생성
            BgtTranDto bgtTranDto = BgtTranDto.builder()
                    .bsnsYy(bsnsYy)
                    .bgtItexCd(bgtItexCd)
                    .teamCd(teamCd)
                    .tranAmt(rginAlctAmt).build();

            //예산거래내역 등록
            bgtTranService.saveBgtTran(bgtTranDto);
        }
    }
}

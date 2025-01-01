package com.example.bms.prfr.service;

import com.example.bms.bdgt.dto.BgtLimtDto;
import com.example.bms.bdgt.service.BgtLimtService;
import com.example.bms.comm.dto.BgtSnctDto;
import com.example.bms.comm.service.BgtSnctService;
import com.example.bms.glob.exception.CustomNotFoundException;
import com.example.bms.prfr.dao.BgtPrfrTstmDao;
import com.example.bms.prfr.entity.BgtPrfr;
import com.example.bms.prfr.entity.BgtPrfrId;
import com.example.bms.prfr.repository.BgtPrfrRepository;
import com.example.bms.prfr.vo.BgtPrfrTstmVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BgtPrfrService {
    private final BgtPrfrRepository bgtPrfrRepository;
    private final BgtLimtService bgtLimtService;
    private final BgtPrfrTstmDao bgtPrfrTstmDao;
    private final BgtSnctService bgtSnctService;

    public BgtPrfrService(BgtPrfrRepository bgtPrfrRepository, BgtLimtService bgtLimtService, BgtPrfrTstmDao bgtPrfrTstmDao, BgtSnctService bgtSnctService) {
        this.bgtPrfrRepository = bgtPrfrRepository;
        this.bgtLimtService = bgtLimtService;
        this.bgtPrfrTstmDao = bgtPrfrTstmDao;
        this.bgtSnctService = bgtSnctService;
    }

    public Page<BgtPrfr> getBgtPrfrList(String bsnsYy, String teamCd, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bgtPrfrRepository.findByBsnsYyAndTeamCd(bsnsYy, teamCd, pageable);
    }

    public BgtPrfr getBgtPrfrDetail(BgtPrfrId bgtPrfrId) {
        return bgtPrfrRepository.findByBgtPrfrId(bgtPrfrId);
    }

    /* 결의서 > 증빙 조회
     * 1. 사업년도, 팀코드에 있는 기타증빙내역과 카드매입내역 조인해서 조회
     */
    public List<BgtPrfrTstmVo> getBgtPrfrTstmListByTeamCd (String teamCd) {
        return bgtPrfrTstmDao.getBgtPrfrTstmListByTeamCd(teamCd);
    }

    /* 결의서 > 비목 조회
     * 1. 해당 사업년도, 팀코드에 있는 예산한도기본을 조회
     */
    public List<BgtLimtDto> findLimtByBsnsYyAndTeamCd (String bsnsYy, String teamCd){
        return bgtLimtService.findByBsnsYyAndTeamCd(bsnsYy, teamCd);
    }

    /* 결의서 등록
     * 1. 지급결의서내역 등록
     * 2. 증빙종류에 따른 원장 분기 처리 (1 - 기타증빙, 2 - 카드매입내역)
     *  2-1. 증빙 원장에 집행금액 업데이트
     * 3. 예산한도기본에 집행예정금액 업데이트
     */
    @Transactional
    public BgtPrfr createBgtPrfr(BgtPrfr bgtPrfr) {
        // prfrYm이 변경되었을 때 prfrNo 리셋 로직
        String prfrYm = bgtPrfr.getBgtPrfrId().getPrfrYm();
        Integer maxPrfrNo = bgtPrfrRepository.findMaxPrfrNoByPrfrYm(prfrYm);
        int newPrfrNo = maxPrfrNo + 1;  // 기존 prfrNo가 있으면 1 증가시킴

        // prfrNo 설정
        bgtPrfr.getBgtPrfrId().setPrfrNo(newPrfrNo);

        return bgtPrfrRepository.save(bgtPrfr);
    }

    @Transactional
    public BgtPrfr updateBgtPrfr(BgtPrfrId bgtPrfrId, BgtPrfr updatedBgtPrfr) {
        BgtPrfr existingBgtPrfr = bgtPrfrRepository.findByBgtPrfrId(bgtPrfrId);
        if (existingBgtPrfr != null) {
            // 필요한 필드만 업데이트
            existingBgtPrfr.setBsnsYy(updatedBgtPrfr.getBsnsYy());
            existingBgtPrfr.setBgtItexCd(updatedBgtPrfr.getBgtItexCd());
            existingBgtPrfr.setStatCd(updatedBgtPrfr.getStatCd());
            existingBgtPrfr.setPrfrSnctNo(updatedBgtPrfr.getPrfrSnctNo());
            existingBgtPrfr.setPrfrSnctScd(updatedBgtPrfr.getPrfrSnctScd());
            existingBgtPrfr.setBgtPrfrYmd(updatedBgtPrfr.getBgtPrfrYmd());
            existingBgtPrfr.setBgtPrfrHms(updatedBgtPrfr.getBgtPrfrHms());
            existingBgtPrfr.setAcitPcsnYmd(updatedBgtPrfr.getAcitPcsnYmd());
            existingBgtPrfr.setRgsnUserNo(updatedBgtPrfr.getRgsnUserNo());
            existingBgtPrfr.setCnclSnctScd(updatedBgtPrfr.getCnclSnctScd());
            existingBgtPrfr.setCcYmd(updatedBgtPrfr.getCcYmd());
            existingBgtPrfr.setCcHms(updatedBgtPrfr.getCcHms());
            existingBgtPrfr.setCcAcitYmd(updatedBgtPrfr.getCcAcitYmd());
            existingBgtPrfr.setCcUserNo(updatedBgtPrfr.getCcUserNo());
            existingBgtPrfr.setPrfrAmt(updatedBgtPrfr.getPrfrAmt());
            existingBgtPrfr.setCcAmt(updatedBgtPrfr.getCcAmt());
            existingBgtPrfr.setTstmDcd(updatedBgtPrfr.getTstmDcd());
            existingBgtPrfr.setTstmNo(updatedBgtPrfr.getTstmNo());
            existingBgtPrfr.setCrdNo(updatedBgtPrfr.getCrdNo());
            existingBgtPrfr.setCrahYmd(updatedBgtPrfr.getCrahYmd());
            existingBgtPrfr.setCardApn(updatedBgtPrfr.getCardApn());
            existingBgtPrfr.setPrfrRsnCon(updatedBgtPrfr.getPrfrRsnCon());
            existingBgtPrfr.setFsDt(updatedBgtPrfr.getFsDt());
            existingBgtPrfr.setLsDt(updatedBgtPrfr.getLsDt());

            return bgtPrfrRepository.save(existingBgtPrfr);
        }
        return null;
    }

    @Transactional
    public void deleteBgtPrfr(BgtPrfrId bgtPrfrId) {
        bgtPrfrRepository.deleteById(bgtPrfrId);
    }


    /* 지급결의서 결재요청
     * 1. 신청자가 결재요청 버튼을 누를 때
     * 2. 결재정보내역을 생성
     * 3. 지급결의서내역 상태코드 및 결재요청관련 컬럼 업데이트
     */
    @Transactional
    public void approvalBgtPrfr(BgtPrfrId bgtPrfrId) {
        BgtPrfr existingPrfr = bgtPrfrRepository.findByBgtPrfrId(bgtPrfrId);
        if(existingPrfr == null) {
            throw new CustomNotFoundException("지급결의서내역을 찾을 수 없습니다.");
        }

        String bsnsYy = String.valueOf(existingPrfr.getBsnsYy());
        String prfrYm = existingPrfr.getBgtPrfrId().getPrfrYm();
        Integer prfrNo = existingPrfr.getBgtPrfrId().getPrfrNo();
        String teamCd = existingPrfr.getBgtPrfrId().getTeamCd();

        BgtSnctDto bgtSnctDto = BgtSnctDto.builder()
                .bsnsYy(bsnsYy)
                .prfrYm(prfrYm)
                .prfrNo(prfrNo)
                .teamCd(teamCd)
                .build();
        //결재정보내역 생성
        bgtSnctService.saveBgtSnct(bgtSnctDto);

        //지급결의서내역 상태코드 및 결재요청관련 컬럼 업데이트
        existingPrfr.setBgtPrfrYmd(new Date());
        existingPrfr.setLsDt(new Date());

        bgtPrfrRepository.save(existingPrfr);
    }
}

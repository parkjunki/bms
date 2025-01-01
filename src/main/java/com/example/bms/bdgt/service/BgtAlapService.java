package com.example.bms.bdgt.service;

import com.example.bms.bdgt.dto.BgtAlapDto;
import com.example.bms.bdgt.dto.BgtTranDto;
import com.example.bms.bdgt.entity.BgtAlap;
import com.example.bms.bdgt.entity.BgtAlapId;
import com.example.bms.bdgt.entity.BgtLimtId;
import com.example.bms.bdgt.entity.BgtMapsId;
import com.example.bms.bdgt.repository.BgtAlapRepository;
import com.example.bms.comm.dto.BgtSnctDto;
import com.example.bms.comm.service.BgtSnctService;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BgtAlapService {
    private final BgtAlapRepository bgtAlapRepository;
    private final BgtLimtService bgtLimtService;
    private final BgtMapsService bgtMapsService;
    private final BgtTranService bgtTranService;
    private final BgtSnctService bgtSnctService;

    public BgtAlapService(BgtAlapRepository bgtAlapRepository, BgtLimtService bgtLimtService, BgtMapsService bgtMapsService, BgtTranService bgtTranService, BgtSnctService bgtSnctService) {
        this.bgtAlapRepository = bgtAlapRepository;
        this.bgtLimtService = bgtLimtService;
        this.bgtMapsService = bgtMapsService;
        this.bgtTranService = bgtTranService;
        this.bgtSnctService = bgtSnctService;
    }

    @Transactional(readOnly = true)
    public List<BgtAlapDto> getAlapList(String bsnsYy, String teamCd) {
        List<BgtAlap> bgtAlapList = bgtAlapRepository.findByBgtAlapIdBsnsYyAndTeamCd(bsnsYy, teamCd);
        return bgtAlapList.stream().map(this::toDto).toList();
    }


    @Transactional(readOnly = true)
    public BgtAlapDto getAlapDetail(BgtAlapId bgtAlapId) {
        BgtAlap bgtAlap = bgtAlapRepository.findById(bgtAlapId)
                .orElseThrow(() -> new CustomNotFoundException("추가배정신청내역 정보를 찾을 수 없습니다."));

        return toDto(bgtAlap);
    }


    @Transactional
    public BgtAlapDto registerAlap(BgtAlapDto bgtAlapDto) {

        BgtAlap lastAlap = bgtAlapRepository.findTopByBgtAlapIdBsnsYyOrderByBgtAlapIdAlapNoDesc(bgtAlapDto.getBsnsYy());
        int nextTranSrn = (lastAlap == null) ? 1 : lastAlap.getBgtAlapId().getAlapNo() + 1;

        bgtAlapDto.setAlapNo(nextTranSrn);
        BgtAlap savedBgtAlap = toEntity(bgtAlapDto);
        return toDto(bgtAlapRepository.save(savedBgtAlap));

    }

    @Transactional
    public BgtAlapDto updateAlap(BgtAlapId bgtAlapId, BgtAlapDto updatedBgtAlapDto) {
        Optional<BgtAlap> existingAlap = bgtAlapRepository.findById(bgtAlapId);
        if (existingAlap.isEmpty()) {
            throw new CustomNotFoundException("추가배정신청내역 정보를 찾을 수 없습니다.");
        }

        //existingAlap.get().setBgtAlapId(bgtAlapId);
        existingAlap.get().setAlapCon(updatedBgtAlapDto.getAlapCon());
        existingAlap.get().setBdalAplcAmt(updatedBgtAlapDto.getBdalAplcAmt());
        existingAlap.get().setLsDt(LocalDateTime.now());

        BgtAlap savedAlap = bgtAlapRepository.save(existingAlap.get());
        return toDto(savedAlap);
    }


    @Transactional
    public void deleteAlap(BgtAlapId bgtAlapId) {
        Optional<BgtAlap> existingAlap = bgtAlapRepository.findById(bgtAlapId);
        if (existingAlap.isEmpty()) {
            throw new CustomNotFoundException("추가배정신청내역 정보를 찾을 수 없습니다.");
        }
        bgtAlapRepository.delete(existingAlap.get());
    }

    /* 추가배정 결재요청
     * 1. 신청자가 결재요청 버튼을 누를 때
     * 2. 결재정보내역을 생성
     * 3. 추가배정신청내역 상태코드 및 결재요청관련 컬럼 업데이트
     */
    @Transactional
    public void approvalBgtAlap(BgtAlapId bgtAlapId) {
        Optional<BgtAlap> existingAlap = bgtAlapRepository.findById(bgtAlapId);
        if (existingAlap.isEmpty()) {
            throw new CustomNotFoundException("추가배정신청내역 정보를 찾을 수 없습니다.");
        }

        String bsnsYy = existingAlap.get().getBgtAlapId().getBsnsYy();
        Integer alapNo = existingAlap.get().getBgtAlapId().getAlapNo();
        String teamCd = existingAlap.get().getTeamCd();

        BgtSnctDto bgtSnctDto = BgtSnctDto.builder()
                .bsnsYy(bsnsYy)
                .alapNo(alapNo)
                .teamCd(teamCd)
                .build();
        //결재정보내역 생성
        bgtSnctService.saveBgtSnct(bgtSnctDto);

        LocalDate date = LocalDate.now();
        String aplcYmd = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        //추가배정신청내역 상태코드 및 결재요청관련 컬럼 업데이트
        existingAlap.get().setAplcYmd(aplcYmd);
        existingAlap.get().setLsDt(LocalDateTime.now());

        bgtAlapRepository.save(existingAlap.get());
    }

    /* 추가배정 실행
     * 1. 관리자가 추가배정 실행버튼을 누를 때
     * 2. 추가배정 내역의 사업년도, 팀코드, 비목코드 , 신청금액을 기준으로 예산한도기본 업데이트
     * 3. 예산월보내역집계 업데이트
     * 4. 예산거래내역 등록
     */
    @Transactional
    public void executeBgtAlap(BgtAlapId bgtAlapId) {
        Optional<BgtAlap> existingAlap = bgtAlapRepository.findById(bgtAlapId);
        if (existingAlap.isEmpty()) {
            throw new CustomNotFoundException("추가배정신청내역 정보를 찾을 수 없습니다.");
        }

        String bsnsYy = existingAlap.get().getBgtAlapId().getBsnsYy();
        String teamCd = existingAlap.get().getTeamCd();
        String bgtItexCd = existingAlap.get().getBgtItexCd();
        BigDecimal bdalAplcAmt = existingAlap.get().getBdalAplcAmt();

        //예산한도기본 업데이트
        bgtLimtService.updateAmounts(new BgtLimtId(bsnsYy,teamCd,bgtItexCd)
                , bdalAplcAmt
                , new BigDecimal(0)
                , new BigDecimal(0)
                , new BigDecimal(0));

        LocalDate date = LocalDate.now();
        String baseMm = date.format(DateTimeFormatter.ofPattern("MM"));

        //예산월보내역집계 업데이트
        bgtMapsService.updateBgtMaps(new BgtMapsId(bsnsYy,teamCd,baseMm,bgtItexCd)
                , bdalAplcAmt
                , new BigDecimal(0)
                , new BigDecimal(0)
                , new BigDecimal(0));

        //예산거래내역을 위한 DTO 생성
        BgtTranDto bgtTranDto = BgtTranDto.builder()
                .bsnsYy(bsnsYy)
                .bgtItexCd(bgtItexCd)
                .teamCd(teamCd)
                .tranAmt(bdalAplcAmt).build();

        //예산거래내역 등록
        bgtTranService.saveBgtTran(bgtTranDto);
    }

    private BgtAlapDto toDto(BgtAlap bgtAlap) {
        return BgtAlapDto.builder()
                .bsnsYy(bgtAlap.getBgtAlapId().getBsnsYy())
                .alapNo(bgtAlap.getBgtAlapId().getAlapNo())
                .statCd(bgtAlap.getStatCd())
                .alapStatCd(bgtAlap.getAlapStatCd())
                .teamCd(bgtAlap.getTeamCd())
                .alapCon(bgtAlap.getAlapCon())
                .aplcYmd(bgtAlap.getAplcYmd())
                .aplcSnctScd(bgtAlap.getAplcSnctScd())
                .aplcUserNo(bgtAlap.getAplcUserNo())
                .aplcSnctNo(bgtAlap.getAplcSnctNo())
                .bgtItexCd(bgtAlap.getBgtItexCd())
                .bdalAplcAmt(bgtAlap.getBdalAplcAmt())
                .bdalYmd(bgtAlap.getBdalYmd())
                .bdalFnsgYn(bgtAlap.getBdalFnsgYn())
                .fsDt(bgtAlap.getFsDt())
                .lsDt(bgtAlap.getLsDt())
                .build();
    }

    private BgtAlap toEntity(BgtAlapDto bgtAlapDto) {
        return BgtAlap.builder()
                .bgtAlapId(new BgtAlapId(bgtAlapDto.getBsnsYy(),bgtAlapDto.getAlapNo()))
                .statCd(bgtAlapDto.getStatCd())
                .alapStatCd(bgtAlapDto.getAlapStatCd())
                .teamCd(bgtAlapDto.getTeamCd())
                .alapCon(bgtAlapDto.getAlapCon())
                .aplcYmd(bgtAlapDto.getAplcYmd())
                .aplcSnctScd(bgtAlapDto.getAplcSnctScd())
                .aplcUserNo(bgtAlapDto.getAplcUserNo())
                .aplcSnctNo(bgtAlapDto.getAplcSnctNo())
                .bgtItexCd(bgtAlapDto.getBgtItexCd())
                .bdalAplcAmt(bgtAlapDto.getBdalAplcAmt())
                .bdalYmd(bgtAlapDto.getBdalYmd())
                .bdalFnsgYn(bgtAlapDto.getBdalFnsgYn())
                .fsDt(bgtAlapDto.getFsDt())
                .lsDt(bgtAlapDto.getLsDt())
                .build();
    }
}

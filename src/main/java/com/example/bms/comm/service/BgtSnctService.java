package com.example.bms.comm.service;

import com.example.bms.bdgt.dto.BgtTranDto;
import com.example.bms.bdgt.entity.BgtAlap;
import com.example.bms.bdgt.entity.BgtAlapId;
import com.example.bms.bdgt.entity.BgtLimtId;
import com.example.bms.bdgt.entity.BgtMapsId;
import com.example.bms.bdgt.repository.BgtAlapRepository;
import com.example.bms.bdgt.service.BgtLimtService;
import com.example.bms.bdgt.service.BgtMapsService;
import com.example.bms.bdgt.service.BgtTranService;
import com.example.bms.comm.dto.BgtSnctDto;
import com.example.bms.comm.entity.BgtSnct;
import com.example.bms.comm.entity.BgtSnctId;
import com.example.bms.comm.repository.BgtSnctRepository;
import com.example.bms.glob.exception.CustomNotFoundException;
import com.example.bms.prfr.entity.BgtPrfr;
import com.example.bms.prfr.entity.BgtPrfrId;
import com.example.bms.prfr.repository.BgtPrfrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BgtSnctService {
    private final BgtSnctRepository bgtSnctRepository;
    private final BgtAlapRepository bgtAlapRepository;
    private final BgtPrfrRepository bgtPrfrRepository;
    private final BgtLimtService bgtLimtService;
    private final BgtMapsService bgtMapsService;
    private final BgtTranService bgtTranService;

    public BgtSnctService(BgtSnctRepository bgtSnctRepository, BgtAlapRepository bgtAlapRepository, BgtPrfrRepository bgtPrfrRepository, BgtLimtService bgtLimtService, BgtMapsService bgtMapsService, BgtTranService bgtTranService) {
        this.bgtSnctRepository = bgtSnctRepository;
        this.bgtAlapRepository = bgtAlapRepository;
        this.bgtPrfrRepository = bgtPrfrRepository;
        this.bgtLimtService = bgtLimtService;
        this.bgtMapsService = bgtMapsService;
        this.bgtTranService = bgtTranService;
    }

    public List<BgtSnctDto> getBgtSnctsByBsnsYyAndTeamCd(String bsnsYy, String teamCd) {
        List<BgtSnct> bgtSnctList = bgtSnctRepository.findByBgtSnctIdBsnsYyAndTeamCd(bsnsYy, teamCd);
        return bgtSnctList.stream().map(this::toDto).toList();
    }

    public BgtSnctDto getBgtSnctById(BgtSnctId bgtSnctId) {
        Optional<BgtSnct> bgtSnct = bgtSnctRepository.findById(bgtSnctId);
        return bgtSnct.map(this::toDto).orElse(null);
    }

    public BgtSnctDto saveBgtSnct(BgtSnctDto dto) {
        BgtSnct bgtSnct = toEntity(dto);
        BgtSnct savedBgtSnct = bgtSnctRepository.save(bgtSnct);
        return toDto(savedBgtSnct);
    }

    /* 결재 승인
     * 1. 승인 레벨에 따라 로직 변경
     * 2. 최종단계가 아니라면 결재정보내역에서 결재자 관련값만 업데이트
     * 3. 최종단계라면 결의서, 추가배정신청에 따라 분기처리
     *  3-1. 결의서라면 지급결의서내역 상태, 컬럼 값 업데이트 / 예산한도기본, 예산거래내역, 예산월보집계내역 업데이트
     *  3-2. 추가배정신청이라면 상태코드만 업데이트
     */
    public void approvalBgtSnct(BgtSnctId bgtSnctId) {
        Optional<BgtSnct> existingSnct = bgtSnctRepository.findById(bgtSnctId);
        if(existingSnct.isEmpty()) {
            throw new CustomNotFoundException("결재정보내역을 찾을 수 없습니다.");
        }

        //최종단계인지 아닌지 구분하여 진행
        String lastSnlv = existingSnct.get().getLastSnlv();
        String curStgUser = existingSnct.get().getCurStgUser();
        String stgUser2 = "";
        String stgUser3 = "";
        boolean lastChk = false;

        switch (lastSnlv) {
            case "2" :
                stgUser2 = existingSnct.get().getSnlnUser2();
                if(curStgUser.equals(stgUser2)) lastChk = true;
                break;
            case "3" :
                stgUser3 = existingSnct.get().getSnlnUser3();
                if(curStgUser.equals(stgUser3)) lastChk = true;
                break;
            default:
                break;
        }

        if(!lastChk) {
            //기본 승인 단계
            //결재정보내역에서 결재자 관련값만 업데이트
            existingSnct.get().setPreStgUser(curStgUser);
            existingSnct.get().setCurStgUser(stgUser3);
            bgtSnctRepository.save(existingSnct.get());
        } else {
            //최종단계
            //결의서인지 추가배정신청인지 확인 후 분기처리
            Integer alapNo = existingSnct.get().getAlapNo();
            String bsnsYy = existingSnct.get().getBgtSnctId().getBsnsYy();

            if(alapNo != null) {
                //추가배정신청
                Optional<BgtAlap> bgtAlap = bgtAlapRepository.findById(new BgtAlapId(bsnsYy,alapNo));
                bgtAlap.get().setAlapStatCd("03");  //결재 완료 상태
                bgtAlapRepository.save(bgtAlap.get());
            } else {
                //지급결의서내역 상태, 컬럼 값 업데이트 / 예산한도기본, 예산거래내역, 예산월보집계내역 업데이트
                String teamCd = existingSnct.get().getTeamCd();
                String prfrYm = existingSnct.get().getPrfrYm();
                Integer prfrNo = existingSnct.get().getPrfrNo();

                Optional<BgtPrfr> bgtPrfr = bgtPrfrRepository.findById(new BgtPrfrId(teamCd,prfrYm,prfrNo));

                String bgtItexCd = bgtPrfr.get().getBgtItexCd();
                BigDecimal prfrAmt = bgtPrfr.get().getPrfrAmt();

                //예산한도기본 - 집행예정액을 집행금액으로 변경
                bgtLimtService.updateAmounts(new BgtLimtId(bsnsYy,teamCd,bgtItexCd)
                        , new BigDecimal(0)
                        , prfrAmt.multiply(new BigDecimal(-1))
                        , new BigDecimal(0)
                        , prfrAmt);

                //예산월보집계내역
                LocalDate date = LocalDate.now();
                String baseMm = date.format(DateTimeFormatter.ofPattern("MM"));

                bgtMapsService.updateBgtMaps(new BgtMapsId(bsnsYy,teamCd,baseMm,bgtItexCd)
                        ,new BigDecimal(0)
                        ,prfrAmt
                        ,new BigDecimal(0)
                        ,new BigDecimal(0));


                //예산거래내역을 위한 DTO 생성
                BgtTranDto bgtTranDto = BgtTranDto.builder()
                        .bsnsYy(bsnsYy)
                        .bgtItexCd(bgtItexCd)
                        .teamCd(teamCd)
                        .tranAmt(prfrAmt).build();

                //예산거래내역 등록
                bgtTranService.saveBgtTran(bgtTranDto);
            }
        }

    }


    /* 결재 취소
     * 1. 결재정보내역 상태 값 업데이트
     * 2. 결의서인지 추가배정신청인지에 따라 분기처리
     *  2-1. 각 원장에 있는 상태코드 업데이트
     */
    public void cancelBgtSnct(BgtSnctId bgtSnctId) {
        Optional<BgtSnct> existingSnct = bgtSnctRepository.findById(bgtSnctId);
        if(existingSnct.isEmpty()) {
            throw new CustomNotFoundException("결재정보내역을 찾을 수 없습니다.");
        }

        //취소 시 필요한 정보는 set해주기
        existingSnct.get().setStatCd("99");  //취소 상태
        bgtSnctRepository.save(existingSnct.get());

        //결의서인지 추가배정신청인지 확인 후 분기처리 -> 각 상태코드 업데이트
        Integer alapNo = existingSnct.get().getAlapNo();
        String bsnsYy = existingSnct.get().getBgtSnctId().getBsnsYy();

        if(alapNo != null) {
            //추가배정신청
            Optional<BgtAlap> bgtAlap = bgtAlapRepository.findById(new BgtAlapId(bsnsYy,alapNo));
            bgtAlap.get().setAlapStatCd("99");  //취소 상태
            bgtAlapRepository.save(bgtAlap.get());
        } else {
            //지급결의서
            String teamCd = existingSnct.get().getTeamCd();
            String prfrYm = existingSnct.get().getPrfrYm();
            Integer prfrNo = existingSnct.get().getPrfrNo();

            Optional<BgtPrfr> bgtPrfr = bgtPrfrRepository.findById(new BgtPrfrId(teamCd,prfrYm,prfrNo));
            bgtPrfr.get().setStatCd("99");
            bgtPrfrRepository.save(bgtPrfr.get());
        }
    }

    private BgtSnctDto toDto(BgtSnct bgtSnct) {
        return BgtSnctDto.builder()
                .bsnsYy(bgtSnct.getBgtSnctId().getBsnsYy())
                .snctNo(bgtSnct.getBgtSnctId().getSnctNo())
                .snctRqstYmd(bgtSnct.getSnctRqstYmd())
                .rqstUserNo(bgtSnct.getRqstUserNo())
                .statCd(bgtSnct.getStatCd())
                .alapNo(bgtSnct.getAlapNo())
                .teamCd(bgtSnct.getTeamCd())
                .prfrYm(bgtSnct.getPrfrYm())
                .prfrNo(bgtSnct.getPrfrNo())
                .snctRqstHms(bgtSnct.getSnctRqstHms())
                .snctCon(bgtSnct.getSnctCon())
                .snlnUser1(bgtSnct.getSnlnUser1())
                .snlnUser2(bgtSnct.getSnlnUser2())
                .snlnUser3(bgtSnct.getSnlnUser3())
                .curStgUser(bgtSnct.getCurStgUser())
                .preStgUser(bgtSnct.getPreStgUser())
                .lastSnlv(bgtSnct.getLastSnlv())
                .fsDt(bgtSnct.getFsDt())
                .lsDt(bgtSnct.getLsDt())
                .build();
    }

    private BgtSnct toEntity(BgtSnctDto bgtSnctDto) {
        return BgtSnct.builder()
                .bgtSnctId(new BgtSnctId(bgtSnctDto.getBsnsYy(),bgtSnctDto.getSnctNo()))
                .snctRqstYmd(bgtSnctDto.getSnctRqstYmd())
                .rqstUserNo(bgtSnctDto.getRqstUserNo())
                .statCd(bgtSnctDto.getStatCd())
                .alapNo(bgtSnctDto.getAlapNo())
                .teamCd(bgtSnctDto.getTeamCd())
                .prfrYm(bgtSnctDto.getPrfrYm())
                .prfrNo(bgtSnctDto.getPrfrNo())
                .snctRqstHms(bgtSnctDto.getSnctRqstHms())
                .snctCon(bgtSnctDto.getSnctCon())
                .snlnUser1(bgtSnctDto.getSnlnUser1())
                .snlnUser2(bgtSnctDto.getSnlnUser2())
                .snlnUser3(bgtSnctDto.getSnlnUser3())
                .curStgUser(bgtSnctDto.getCurStgUser())
                .preStgUser(bgtSnctDto.getPreStgUser())
                .lastSnlv(bgtSnctDto.getLastSnlv())
                .fsDt(bgtSnctDto.getFsDt())
                .lsDt(bgtSnctDto.getLsDt())
                .build();
    }
}

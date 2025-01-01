package com.example.bms.prfr.service;

import com.example.bms.glob.exception.CustomNotFoundException;
import com.example.bms.prfr.dto.BgtTstmDto;
import com.example.bms.prfr.entity.BgtTstm;
import com.example.bms.prfr.entity.BgtTstmId;
import com.example.bms.prfr.repository.BgtTstmRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BgtTstmService {

    private final BgtTstmRepository bgtTstmRepository;

    public BgtTstmService(BgtTstmRepository bgtTstmRepository) {
        this.bgtTstmRepository = bgtTstmRepository;
    }

    public List<BgtTstm> getAllBgtTstm() {
        return bgtTstmRepository.findAll();
    }

    public Optional<BgtTstm> getBgtTstmById (String tstmYmd, String tstmNo) {
        return bgtTstmRepository.findById(new BgtTstmId(tstmYmd, tstmNo));
    }

    public BgtTstm saveBgtTstm(BgtTstmDto dto) {
        BgtTstm bgtTstm = BgtTstm.builder()
                .bgtTstmId(new BgtTstmId(dto.getTstmYmd(),dto.getTstmNo()))
                .teamCd(dto.getTeamCd())
                .rcppDcd(dto.getRcppDcd())
                .statCd(dto.getStatCd())
                .issAmt(dto.getIssAmt())
                .bprSrn(dto.getBprSrn())
                .frm(dto.getFrm())
                .rpprNm(dto.getRpprNm())
                .prfrAmt(dto.getPrfrAmt())
                .prfrCcAmt(dto.getPrfrCcAmt())
                .etcCon(dto.getEtcCon())
                .fsDt(dto.getFsDt())
                .lsDt(dto.getLsDt())
                .build();
        return bgtTstmRepository.save(bgtTstm);
    }

    public BgtTstm updateBgtTstm(String tstmYmd, String tstmNo, BgtTstmDto bgtTstmDto) {
        BgtTstm bgtTstm = bgtTstmRepository.findById(new BgtTstmId(tstmYmd,tstmNo))
                .orElseThrow(() -> new CustomNotFoundException("증빙내역을 찾을 수 없습니다."));

        bgtTstm.setRcppDcd(bgtTstmDto.getRcppDcd());
        bgtTstm.setStatCd(bgtTstmDto.getStatCd());
        bgtTstm.setIssAmt(bgtTstmDto.getIssAmt());
        bgtTstm.setBprSrn(bgtTstmDto.getBprSrn());
        bgtTstm.setFrm(bgtTstmDto.getFrm());
        bgtTstm.setRpprNm(bgtTstmDto.getRpprNm());
        bgtTstm.setPrfrAmt(bgtTstmDto.getPrfrAmt());
        bgtTstm.setPrfrCcAmt(bgtTstmDto.getPrfrCcAmt());
        bgtTstm.setEtcCon(bgtTstmDto.getEtcCon());
        bgtTstm.setLsDt(LocalDate.now());

        return bgtTstmRepository.save(bgtTstm);
    }

    public void deleteBgtTstmById (String tstmYmd, String tstmNo) {
        if(bgtTstmRepository.existsById(new BgtTstmId(tstmYmd,tstmNo)) ){
            bgtTstmRepository.deleteById(new BgtTstmId(tstmYmd,tstmNo));
        } else {
            throw new CustomNotFoundException("증빙내역을 찾을 수 없습니다.");
        }
    }
}

package com.example.bms.bdgt.service;

import com.example.bms.bdgt.dto.BgtTranDto;
import com.example.bms.bdgt.entity.BgtTran;
import com.example.bms.bdgt.entity.BgtTranId;
import com.example.bms.bdgt.repository.BgtTranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BgtTranService {
    private final BgtTranRepository bgtTranRepository;

    public BgtTranService(BgtTranRepository bgtTranRepository) {
        this.bgtTranRepository = bgtTranRepository;
    }

    @Transactional(readOnly = true)
    public List<BgtTranDto> getBgtTransByBsnsYyAndTeamCd(String bsnsYy, String teamCd) {
        return bgtTranRepository.findByBgtTranIdBsnsYyAndTeamCd(bsnsYy, teamCd).stream()
                .map(tran -> toDto(tran))
                .collect(Collectors.toList());
    }

    @Transactional
    public BgtTranDto saveBgtTran(BgtTranDto bgtTranDto) {
        BgtTran lastTran = bgtTranRepository.findTopByBgtTranIdBsnsYyOrderByBgtTranIdTranSrnDesc(bgtTranDto.getBsnsYy());
        int nextTranSrn = (lastTran == null) ? 1 : lastTran.getBgtTranId().getTranSrn() + 1;

        bgtTranDto.setTranSrn(nextTranSrn);
        BgtTran bgtTran = toEntity(bgtTranDto);
        return toDto(bgtTranRepository.save(bgtTran));
    }

    private BgtTranDto toDto(BgtTran bgtTran) {
        return BgtTranDto.builder()
                .bsnsYy(bgtTran.getBgtTranId().getBsnsYy())
                .tranSrn(bgtTran.getBgtTranId().getTranSrn())
                .tranDcd(bgtTran.getTranDcd())
                .statCd(bgtTran.getStatCd())
                .teamCd(bgtTran.getTeamCd())
                .bgtItexCd(bgtTran.getBgtItexCd())
                .tranYmd(bgtTran.getTranYmd())
                .tranHms(bgtTran.getTranHms())
                .tranUserNo(bgtTran.getTranUserNo())
                .alapNo(bgtTran.getAlapNo())
                .prfrYm(bgtTran.getPrfrYm())
                .prfrNo(bgtTran.getPrfrNo())
                .tranAmt(bgtTran.getTranAmt())
                .fsDt(bgtTran.getFsDt())
                .lsDt(bgtTran.getLsDt())
                .build();
    }

    private BgtTran toEntity(BgtTranDto bgtTranDto) {
        return BgtTran.builder()
                .bgtTranId(new BgtTranId(bgtTranDto.getBsnsYy(), bgtTranDto.getTranSrn()))
                .tranDcd(bgtTranDto.getTranDcd())
                .statCd(bgtTranDto.getStatCd())
                .teamCd(bgtTranDto.getTeamCd())
                .bgtItexCd(bgtTranDto.getBgtItexCd())
                .tranYmd(bgtTranDto.getTranYmd())
                .tranHms(bgtTranDto.getTranHms())
                .tranUserNo(bgtTranDto.getTranUserNo())
                .alapNo(bgtTranDto.getAlapNo())
                .prfrYm(bgtTranDto.getPrfrYm())
                .prfrNo(bgtTranDto.getPrfrNo())
                .tranAmt(bgtTranDto.getTranAmt())
                .fsDt(bgtTranDto.getFsDt())
                .lsDt(bgtTranDto.getLsDt())
                .build();
    }
}

package com.example.bms.prfr.service;

import com.example.bms.glob.exception.CustomNotFoundException;
import com.example.bms.prfr.dto.BgtTcrdDto;
import com.example.bms.prfr.entity.BgtTcrd;
import com.example.bms.prfr.entity.BgtTcrdId;
import com.example.bms.prfr.repository.BgtTcrdRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BgtTcrdService {

    private final BgtTcrdRepository bgtTcrdRepository;

    public BgtTcrdService(BgtTcrdRepository bgtTcrdRepository) {
        this.bgtTcrdRepository = bgtTcrdRepository;
    }


    public List<BgtTcrdDto> findAllBgtTcrd() {
        return bgtTcrdRepository.findAll().stream()
                .map(bgtTcrd -> toDto(bgtTcrd))
                .collect(Collectors.toList());
    }

    public BgtTcrdDto findBgtTcrdById(BgtTcrdId bgtTcrdId) {
        return bgtTcrdRepository.findById(bgtTcrdId)
                .map(bgtTcrd -> toDto(bgtTcrd))
                .orElseThrow(() -> new CustomNotFoundException("카드정보를 찾을 수 없습니다."));
    }

    public BgtTcrdDto createBgtTcrd(BgtTcrdDto bgtTcrdDto) {
        BgtTcrd bgtTcrd = toEntity(bgtTcrdDto);
        return toDto(bgtTcrdRepository.save(bgtTcrd));
    }

    public BgtTcrdDto updateBgtTcrd(BgtTcrdId bgtTcrdId, BgtTcrdDto bgtTcrdDto) {
        if (!bgtTcrdRepository.existsById(bgtTcrdId)) {
            throw new CustomNotFoundException("카드정보를 찾을 수 없습니다.");
        }
        BgtTcrd bgtTcrd = toEntity(bgtTcrdDto);
        return toDto(bgtTcrdRepository.save(bgtTcrd));
    }

    public void deleteBgtTcrdById(BgtTcrdId bgtTcrdId) {
        bgtTcrdRepository.deleteById(bgtTcrdId);
    }

    private BgtTcrdDto toDto(BgtTcrd entity) {
        return BgtTcrdDto.builder()
                .crdNo(entity.getBgtTcrdId().getCrdNo())
                .teamCrdSrn(entity.getBgtTcrdId().getTeamCrdSrn())
                .teamCd(entity.getTeamCd())
                .statCd(entity.getStatCd())
                .stlmDd(entity.getStlmDd())
                .valdYm(entity.getValdYm())
                .stlmAcn(entity.getStlmAcn())
                .useLmtAmt(entity.getUseLmtAmt())
                .crdsYmd(entity.getCrdsYmd())
                .userNo(entity.getUserNo())
                .fsDt(entity.getFsDt())
                .lsDt(entity.getLsDt())
                .build();
    }

    private BgtTcrd toEntity(BgtTcrdDto dto) {
        return BgtTcrd.builder()
                .bgtTcrdId(new BgtTcrdId(dto.getCrdNo(), dto.getTeamCrdSrn()))
                .teamCd(dto.getTeamCd())
                .statCd(dto.getStatCd())
                .stlmDd(dto.getStlmDd())
                .valdYm(dto.getValdYm())
                .stlmAcn(dto.getStlmAcn())
                .useLmtAmt(dto.getUseLmtAmt())
                .crdsYmd(dto.getCrdsYmd())
                .userNo(dto.getUserNo())
                .fsDt(dto.getFsDt())
                .lsDt(dto.getLsDt())
                .build();
    }
}

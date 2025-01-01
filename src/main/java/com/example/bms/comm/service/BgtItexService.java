package com.example.bms.comm.service;

import com.example.bms.comm.entity.BgtItex;
import com.example.bms.comm.repository.BgtItexRepository;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BgtItexService {

    private final BgtItexRepository bgtItexRepository;

    public BgtItexService(BgtItexRepository bgtItexRepository) {
        this.bgtItexRepository = bgtItexRepository;
    }

    public List<BgtItex> getAllBgtItex() {
        return bgtItexRepository.findAll();
    }

    public Optional<BgtItex> getBgtItexByCd(String bgtItexCd) {
        return Optional.ofNullable(bgtItexRepository.findById(bgtItexCd)
                .orElseThrow(() -> new CustomNotFoundException("해당 비목코드가 존재하지 않습니다. : " + bgtItexCd)));
    }

    public BgtItex createBgtItex(BgtItex bgtItex) {
        return bgtItexRepository.save(bgtItex);
    }

    public BgtItex updateBgtItex(String bgtItexCd, BgtItex updateBgtItex) {
        return bgtItexRepository.findById(bgtItexCd)
                .map(bgtItex -> {
                    bgtItex.setBgtItexNm(updateBgtItex.getBgtItexNm());
                    bgtItex.setAccd(updateBgtItex.getAccd());
                    bgtItex.setLsDt(LocalDate.now());
                    BgtItex updatedItex = bgtItexRepository.save(bgtItex);
                    return updatedItex;
                }).orElseThrow(() -> new CustomNotFoundException("해당 비목코드가 존재하지 않습니다. : " + bgtItexCd));
    }

    public void deleteBgtItexByCd(String bgtItexCd) {
        if(bgtItexRepository.existsById(bgtItexCd)) {
            bgtItexRepository.deleteById(bgtItexCd);
        }else {
            throw new CustomNotFoundException("해당 비목코드가 존재하지 않습니다. : " + bgtItexCd);
        }
    }
}

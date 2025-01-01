package com.example.bms.comm.controller;

import com.example.bms.comm.dto.BgtItexDto;
import com.example.bms.comm.entity.BgtItex;
import com.example.bms.comm.service.BgtItexService;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bgt/itex")
public class BgtItexController {

    private final BgtItexService bgtItexService;

    public BgtItexController(BgtItexService bgtItexService) {
        this.bgtItexService = bgtItexService;
    }

    @GetMapping
    public List<BgtItex> getAllBgtItex() {
        return bgtItexService.getAllBgtItex();
    }

    @GetMapping("/{bgtItexCd}")
    public ResponseEntity<BgtItex> getBgtItexByCd(@PathVariable String bgtItexCd) {
        return bgtItexService.getBgtItexByCd(bgtItexCd)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BgtItex createBgtItex(@RequestBody BgtItexDto bgtItexDto){
        BgtItex bgtItex = new BgtItex();
        bgtItex.setBgtItexCd(bgtItexDto.getBgtItexCd());
        bgtItex.setBgtItexNm(bgtItexDto.getBgtItexNm());
        bgtItex.setAccd(bgtItexDto.getAccd());
        bgtItex.setFsDt(LocalDate.now());
        bgtItex.setLsDt(LocalDate.now());
        return bgtItexService.createBgtItex(bgtItex);
    }

    @PutMapping("/{bgtItexCd}")
    public ResponseEntity<BgtItex> updateBgtItex(@PathVariable String bgtItexCd, @RequestBody BgtItexDto bgtItexDto) {
        BgtItex bgtItex = new BgtItex();
        bgtItex.setBgtItexNm(bgtItexDto.getBgtItexNm());
        bgtItex.setAccd(bgtItexDto.getAccd());
        bgtItex.setLsDt(LocalDate.now());

        try{
            return ResponseEntity.ok(bgtItexService.updateBgtItex(bgtItexCd, bgtItex));
        }catch(CustomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bgtItexCd}")
    public ResponseEntity<Void> deleteBgtItexByCd(@PathVariable String bgtItexCd) {
        bgtItexService.deleteBgtItexByCd(bgtItexCd);
        return ResponseEntity.noContent().build();
    }
}

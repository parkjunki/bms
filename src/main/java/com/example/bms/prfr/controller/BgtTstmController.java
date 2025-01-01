package com.example.bms.prfr.controller;

import com.example.bms.prfr.dto.BgtTstmDto;
import com.example.bms.prfr.entity.BgtTstm;
import com.example.bms.prfr.service.BgtTstmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/tstm")
public class BgtTstmController {


    private final BgtTstmService bgtTstmService;

    public BgtTstmController(BgtTstmService bgtTstmService) {
        this.bgtTstmService = bgtTstmService;
    }

    @GetMapping
    public ResponseEntity<List<BgtTstm>> getAllBgtTstms() {
        List<BgtTstm> bgtTstmList = bgtTstmService.getAllBgtTstm();
        return ResponseEntity.ok(bgtTstmList);
    }

    @GetMapping("/{tstmYmd}/{tstmNo}")
    public ResponseEntity<BgtTstm> getBgtTstmById(@PathVariable String tstmYmd, @PathVariable String tstmNo) {
        return bgtTstmService.getBgtTstmById(tstmYmd, tstmNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BgtTstm> createBgtTstm(@RequestBody BgtTstmDto bgtTstmDto) {
        BgtTstm savedBgtTstm = bgtTstmService.saveBgtTstm(bgtTstmDto);
        return ResponseEntity.ok(savedBgtTstm);
    }

    @PutMapping("/{tstmYmd}/{tstmNo}")
    public ResponseEntity<BgtTstm> updateBgtTstm(@PathVariable String tstmYmd, @PathVariable String tstmNo, @RequestBody BgtTstmDto bgtTstmDto) {
        BgtTstm updatedBgtTstm = bgtTstmService.updateBgtTstm(tstmYmd,tstmNo,bgtTstmDto);
        return ResponseEntity.ok(updatedBgtTstm);
    }

    @DeleteMapping("/{tstmYmd}/{tstmNo}")
    public ResponseEntity<BgtTstm> deleteBgtTstmById(@PathVariable String tstmYmd, @PathVariable String tstmNo) {
        bgtTstmService.deleteBgtTstmById(tstmYmd,tstmNo);
        return ResponseEntity.noContent().build();
    }
}

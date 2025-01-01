package com.example.bms.bdgt.controller;

import com.example.bms.bdgt.dto.BgtLimtDto;
import com.example.bms.bdgt.entity.BgtLimtId;
import com.example.bms.bdgt.service.BgtLimtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bgt/limt")
public class BgtLimtController {
    private final BgtLimtService bgtLimtService;

    public BgtLimtController(BgtLimtService bgtLimtService) {
        this.bgtLimtService = bgtLimtService;
    }

    @GetMapping
    public ResponseEntity<List<BgtLimtDto>> getBgtLimtList(@RequestParam String bsnsYy, @RequestParam String teamCd) {
        List<BgtLimtDto> result = bgtLimtService.findByBsnsYyAndTeamCd(bsnsYy, teamCd);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{bsnsYy}/{teamCd}/{bgtItexCd}")
    public ResponseEntity<BgtLimtDto> updateBgtLimtAmounts(
            @PathVariable String bsnsYy,
            @PathVariable String teamCd,
            @PathVariable String bgtItexCd,
            @RequestParam BigDecimal bgtLmtBalDelta,
            @RequestParam BigDecimal prfrScdlAmtDelta,
            @RequestParam BigDecimal ttlBgtAlctAmtDelta,
            @RequestParam BigDecimal ttlPrfrAmtDelta) {
        BgtLimtDto updated = bgtLimtService.updateAmounts(
                new BgtLimtId(bsnsYy, teamCd, bgtItexCd),
                bgtLmtBalDelta,
                prfrScdlAmtDelta,
                ttlBgtAlctAmtDelta,
                ttlPrfrAmtDelta
        );
        return ResponseEntity.ok(updated);
    }
}

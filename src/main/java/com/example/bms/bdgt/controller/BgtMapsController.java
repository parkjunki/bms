package com.example.bms.bdgt.controller;

import com.example.bms.bdgt.entity.BgtMaps;
import com.example.bms.bdgt.entity.BgtMapsId;
import com.example.bms.bdgt.service.BgtMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bgt/maps")
public class BgtMapsController {

    private final BgtMapsService bgtMapsService;

    public BgtMapsController(BgtMapsService bgtMapsService) {
        this.bgtMapsService = bgtMapsService;
    }

    @GetMapping
    public List<BgtMaps> getBgtMapsList(@RequestParam String bsnSyy,
                                        @RequestParam String baseMm,
                                        @RequestParam String teamCd) {
        return bgtMapsService.getBgtMapsList(bsnSyy, baseMm, teamCd);
    }

    @PutMapping("/{bsnsYy}/{baseMm}/{teamCd}/{bgtItexCd}")
    @ResponseStatus(HttpStatus.OK)
    public BgtMaps updateBgtMaps(@PathVariable String bsnsYy,
                                 @PathVariable String baseMm,
                                 @PathVariable String teamCd,
                                 @PathVariable String bgtItexCd,
                                 @RequestParam BigDecimal drmnAlctAmt,
                                 @RequestParam BigDecimal drmnPrfrAmt,
                                 @RequestParam BigDecimal alctRdmpAmt,
                                 @RequestParam BigDecimal drmnPrfrCcAmt) {

        BgtMapsId bgtMapsId = new BgtMapsId(bsnsYy,baseMm,teamCd,bgtItexCd);

        return bgtMapsService.updateBgtMaps(bgtMapsId, drmnAlctAmt, drmnPrfrAmt, alctRdmpAmt, drmnPrfrCcAmt);
    }
}

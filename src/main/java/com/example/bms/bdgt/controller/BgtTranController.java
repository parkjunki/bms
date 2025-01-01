package com.example.bms.bdgt.controller;

import com.example.bms.bdgt.dto.BgtTranDto;
import com.example.bms.bdgt.service.BgtTranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/tran")
public class BgtTranController {

    private final BgtTranService bgtTranService;

    public BgtTranController(BgtTranService bgtTranService) {
        this.bgtTranService = bgtTranService;
    }


    @GetMapping
    public ResponseEntity<List<BgtTranDto>> getBgtTransByBsnsYyAndTeamCd(@RequestParam String bsnsYy, @RequestParam String teamCd) {
        List<BgtTranDto> trans = bgtTranService.getBgtTransByBsnsYyAndTeamCd(bsnsYy, teamCd);
        return ResponseEntity.ok(trans);
    }


    @PostMapping
    public ResponseEntity<BgtTranDto> saveBgtTran(@RequestBody BgtTranDto bgtTranDto) {
        BgtTranDto savedTran = bgtTranService.saveBgtTran(bgtTranDto);
        return ResponseEntity.ok(savedTran);
    }

}

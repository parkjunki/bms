package com.example.bms.comm.controller;

import com.example.bms.comm.dto.BgtSnctDto;
import com.example.bms.comm.entity.BgtSnctId;
import com.example.bms.comm.service.BgtSnctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/snct")
public class BgtSnctController {
    private final BgtSnctService bgtSnctService;

    public BgtSnctController(BgtSnctService bgtSnctService) {
        this.bgtSnctService = bgtSnctService;
    }

    @GetMapping
    public List<BgtSnctDto> getBgtSnctsByBsnsYyAndTeamCd(@RequestParam String bsnsYy, @RequestParam String teamCd) {
        return bgtSnctService.getBgtSnctsByBsnsYyAndTeamCd(bsnsYy, teamCd);
    }

    @GetMapping("/{bsnsYy}/{snctNo}")
    public BgtSnctDto getBgtSnctById(@PathVariable String bsnsYy, @PathVariable Integer snctNo) {
        BgtSnctId bgtSnctId = new BgtSnctId(bsnsYy, snctNo);
        return bgtSnctService.getBgtSnctById(bgtSnctId);
    }

    @PostMapping("/cancel/{bsnsYy}/{snctNo}")
    public ResponseEntity<Void> cancelBgtSnct (
              @PathVariable String bsnsYy
            , @PathVariable Integer snctNo) {
        bgtSnctService.cancelBgtSnct(new BgtSnctId(bsnsYy,snctNo));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approval/{bsnsYy}/{snctNo}")
    public ResponseEntity<Void> approvalBgtSnct (
              @PathVariable String bsnsYy
            , @PathVariable Integer snctNo) {
        bgtSnctService.approvalBgtSnct(new BgtSnctId(bsnsYy,snctNo));
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public BgtSnctDto saveBgtSnct(@RequestBody BgtSnctDto bgtSnctDto) {
        return bgtSnctService.saveBgtSnct(bgtSnctDto);
    }
}

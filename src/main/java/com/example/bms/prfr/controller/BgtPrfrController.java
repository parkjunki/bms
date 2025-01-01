package com.example.bms.prfr.controller;

import com.example.bms.prfr.entity.BgtPrfr;
import com.example.bms.prfr.entity.BgtPrfrId;
import com.example.bms.prfr.service.BgtPrfrService;
import com.example.bms.prfr.vo.BgtPrfrTstmVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/prfr")
public class BgtPrfrController {
    private final BgtPrfrService bgtPrfrService;

    public BgtPrfrController(BgtPrfrService bgtPrfrService) {
        this.bgtPrfrService = bgtPrfrService;
    }

    @GetMapping
    public ResponseEntity<Page<BgtPrfr>> getBgtPrfrList(
            @RequestParam String bsnsYy,
            @RequestParam String teamCd,
            @RequestParam int page,
            @RequestParam int size) {

        Page<BgtPrfr> result = bgtPrfrService.getBgtPrfrList(bsnsYy, teamCd, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tstm/{teamCd}")
    public ResponseEntity<List<BgtPrfrTstmVo>> getBgtPrfrTstmListByTeamCd (@PathVariable String teamCd){
        List<BgtPrfrTstmVo> result = bgtPrfrService.getBgtPrfrTstmListByTeamCd(teamCd);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{teamCd}/{prfrYm}/{prfrNo}")
    public ResponseEntity<BgtPrfr> getBgtPrfrDetail(
            @PathVariable String teamCd,
            @PathVariable String prfrYm,
            @PathVariable int prfrNo) {

        BgtPrfrId bgtPrfrId = new BgtPrfrId();
        bgtPrfrId.setTeamCd(teamCd);
        bgtPrfrId.setPrfrYm(prfrYm);
        bgtPrfrId.setPrfrNo(prfrNo);

        BgtPrfr result = bgtPrfrService.getBgtPrfrDetail(bgtPrfrId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/limt/{bsnsYy}/{teamCd}")
    public ResponseEntity<Void> findLimtByBsnsYyAndTeamCd(
            @PathVariable String bsnsYy,
            @PathVariable String teamCd) {
        bgtPrfrService.findLimtByBsnsYyAndTeamCd(bsnsYy,teamCd);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<BgtPrfr> createBgtPrfr(@RequestBody BgtPrfr bgtPrfr) {
        BgtPrfr createdBgtPrfr = bgtPrfrService.createBgtPrfr(bgtPrfr);
        return ResponseEntity.ok(createdBgtPrfr);
    }

    @PutMapping("/{teamCd}/{prfrYm}/{prfrNo}")
    public ResponseEntity<BgtPrfr> updateBgtPrfr(
            @PathVariable String teamCd,
            @PathVariable String prfrYm,
            @PathVariable int prfrNo,
            @RequestBody BgtPrfr updatedBgtPrfr) {

        BgtPrfrId bgtPrfrId = new BgtPrfrId();
        bgtPrfrId.setTeamCd(teamCd);
        bgtPrfrId.setPrfrYm(prfrYm);
        bgtPrfrId.setPrfrNo(prfrNo);

        BgtPrfr updated = bgtPrfrService.updateBgtPrfr(bgtPrfrId, updatedBgtPrfr);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{teamCd}/{prfrYm}/{prfrNo}")
    public ResponseEntity<Void> deleteBgtPrfr(
            @PathVariable String teamCd,
            @PathVariable String prfrYm,
            @PathVariable int prfrNo) {

        BgtPrfrId bgtPrfrId = new BgtPrfrId();
        bgtPrfrId.setTeamCd(teamCd);
        bgtPrfrId.setPrfrYm(prfrYm);
        bgtPrfrId.setPrfrNo(prfrNo);

        bgtPrfrService.deleteBgtPrfr(bgtPrfrId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/approval/{teamCd}/{prfrYm}/{prfrNo}")
    public ResponseEntity<Void> approvalBgtPrfr (
            @PathVariable String teamCd,
            @PathVariable String prfrYm,
            @PathVariable int prfrNo) {
        bgtPrfrService.approvalBgtPrfr(new BgtPrfrId(teamCd,prfrYm,prfrNo));
        return ResponseEntity.ok().build();
    }
}

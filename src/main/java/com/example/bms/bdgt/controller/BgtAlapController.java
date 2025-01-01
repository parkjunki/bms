package com.example.bms.bdgt.controller;

import com.example.bms.bdgt.dto.BgtAlapDto;
import com.example.bms.bdgt.entity.BgtAlapId;
import com.example.bms.bdgt.service.BgtAlapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/alap")
public class BgtAlapController {

    private final BgtAlapService bgtAlapService;

    public BgtAlapController(BgtAlapService bgtAlapService) {
        this.bgtAlapService = bgtAlapService;
    }

    @GetMapping
    public ResponseEntity<List<BgtAlapDto>> getAlapList(@RequestParam String bsnsYy, @RequestParam String teamCd) {
        List<BgtAlapDto> alapList = bgtAlapService.getAlapList(bsnsYy, teamCd);
        return new ResponseEntity<>(alapList, HttpStatus.OK);
    }

    @GetMapping("/{bsnsYy}/{alapNo}")
    public ResponseEntity<BgtAlapDto> getAlapDetail(@PathVariable String bsnsYy, @PathVariable Integer alapNo) {
        BgtAlapId bgtAlapId = new BgtAlapId(bsnsYy, alapNo);
        BgtAlapDto alapDto = bgtAlapService.getAlapDetail(bgtAlapId);
        return new ResponseEntity<>(alapDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BgtAlapDto> registerAlap(@RequestBody BgtAlapDto bgtAlapDto) {
        BgtAlapDto createdBgtAlap = bgtAlapService.registerAlap(bgtAlapDto);
        return new ResponseEntity<>(createdBgtAlap, HttpStatus.CREATED);
    }

    @PutMapping("/{bsnsYy}/{alapNo}")
    public ResponseEntity<BgtAlapDto> updateAlap(
            @PathVariable String bsnsYy,
            @PathVariable int alapNo,
            @RequestBody BgtAlapDto updatedBgtAlapDto) {
        BgtAlapId bgtAlapId = new BgtAlapId(bsnsYy, alapNo);
        BgtAlapDto updatedAlap = bgtAlapService.updateAlap(bgtAlapId, updatedBgtAlapDto);
        return new ResponseEntity<>(updatedAlap, HttpStatus.OK);
    }

    @DeleteMapping("/{bsnsYy}/{alapNo}")
    public ResponseEntity<Void> deleteAlap(
            @PathVariable String bsnsYy,
            @PathVariable int alapNo) {
        BgtAlapId bgtAlapId = new BgtAlapId(bsnsYy, alapNo);
        bgtAlapService.deleteAlap(bgtAlapId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //추가배정 실행은 관리자만 할 수 있으므로 추가 필요.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/execute/{bsnsYy}/{alapNo}")
    public ResponseEntity<Void> executeBgtAlap(
            @PathVariable String bsnsYy,
            @PathVariable int alapNo) {
        bgtAlapService.executeBgtAlap(new BgtAlapId(bsnsYy, alapNo));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approval/{bsnsYy}/{alapNo}")
    public ResponseEntity<Void> approvalBgtAlap(
            @PathVariable String bsnsYy,
            @PathVariable int alapNo) {

        bgtAlapService.approvalBgtAlap(new BgtAlapId(bsnsYy, alapNo));
        return ResponseEntity.ok().build();
    }

}

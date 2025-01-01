package com.example.bms.prfr.controller;

import com.example.bms.prfr.entity.BgtCbng;
import com.example.bms.prfr.entity.BgtCbngId;
import com.example.bms.prfr.service.BgtCbngService;
import com.example.bms.prfr.vo.BgtCbngVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bgt/cbng")
public class BgtCbngController {
    private final BgtCbngService bgtCbngService;

    public BgtCbngController(BgtCbngService bgtCbngService) {
        this.bgtCbngService = bgtCbngService;
    }

    @GetMapping("/{teamCd}")
    public ResponseEntity<List<BgtCbngVo>> getByTeamCrd (@PathVariable String teamCd) {
        List<BgtCbngVo> result = bgtCbngService.getBgtCbngListByTeamCrd(teamCd);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{tstmYmd}/{tstmNo}")
    public ResponseEntity<BgtCbng> getById(@PathVariable String tstmYmd, @PathVariable Integer tstmNo) {
        BgtCbngId bgtCbngId = BgtCbngId.builder().tstmYmd(tstmYmd).tstmNo(tstmNo).build();
        Optional<BgtCbng> result = bgtCbngService.getById(bgtCbngId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{tstmYmd}/{tstmNo}/updateAmounts")
    public ResponseEntity<Void> updateExecutionAmounts(@PathVariable String tstmYmd, @PathVariable Integer tstmNo,
                                                       @RequestParam BigDecimal prfrAmt, @RequestParam BigDecimal prfrCcAmt) {
        BgtCbngId bgtCbngId = BgtCbngId.builder().tstmYmd(tstmYmd).tstmNo(tstmNo).build();
        bgtCbngService.updateExecutionAmounts(bgtCbngId, prfrAmt, prfrCcAmt);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{tstmYmd}/{tstmNo}/markAsDeleted")
    public ResponseEntity<Void> markAsDeleted(@PathVariable String tstmYmd, @PathVariable Integer tstmNo,
                                              @RequestParam String delRsnCon, @RequestParam String statCd) {
        BgtCbngId bgtCbngId = BgtCbngId.builder().tstmYmd(tstmYmd).tstmNo(tstmNo).build();
        bgtCbngService.markAsDeleted(bgtCbngId, delRsnCon, statCd);
        return ResponseEntity.ok().build();
    }
}

package com.example.bms.prfr.controller;

import com.example.bms.prfr.dto.BgtTcrdDto;
import com.example.bms.prfr.entity.BgtTcrdId;
import com.example.bms.prfr.service.BgtTcrdService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/tcrd")
public class BgtTcrdController {
    private final BgtTcrdService bgtTcrdService;

    public BgtTcrdController(BgtTcrdService bgtTcrdService) {
        this.bgtTcrdService = bgtTcrdService;
    }

    @GetMapping
    public List<BgtTcrdDto> getAllBgtTcrd() {
        return bgtTcrdService.findAllBgtTcrd();
    }

    @GetMapping("/{crdNo}/{teamCrdSrn}")
    public BgtTcrdDto getBgtTcrdById(@PathVariable String crdNo, @PathVariable Integer teamCdrSrn) {
        return bgtTcrdService.findBgtTcrdById(new BgtTcrdId(crdNo, teamCdrSrn));
    }

    @PostMapping
    public BgtTcrdDto createBgtTcrd(@RequestBody BgtTcrdDto dto) {
        return bgtTcrdService.createBgtTcrd(dto);
    }

    @PutMapping("/{crdNo}/{teamCrdSrn}")
    public BgtTcrdDto updateBgtTcrd(@PathVariable String crdNo, @PathVariable Integer teamCdrSrn, @RequestBody BgtTcrdDto bgtTcrdDto) {
        return bgtTcrdService.updateBgtTcrd(new BgtTcrdId(crdNo, teamCdrSrn), bgtTcrdDto);
    }

    @DeleteMapping("/{crdNo}/{teamCrdSrn}")
    public void deleteBgtTcrdById(@PathVariable String crdNo, @PathVariable Integer teamCdrSrn) {
        bgtTcrdService.deleteBgtTcrdById(new BgtTcrdId(crdNo, teamCdrSrn));
    }
}

package com.example.bms.bdgt.controller;

import com.example.bms.bdgt.entity.BgtBdgt;
import com.example.bms.bdgt.service.BgtBdgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bgt/bdgt")
public class BgtBdgtController {

    private final BgtBdgtService bgtBdgtService;

    public BgtBdgtController(BgtBdgtService bgtBdgtService) {
        this.bgtBdgtService = bgtBdgtService;
    }

    @GetMapping
    public ResponseEntity<List<BgtBdgt>> getBgtBdgtList(@RequestParam String bsnSyy, @RequestParam String qtrDcd) {
        List<BgtBdgt> result = bgtBdgtService.getBgtBdgtList(bsnSyy, qtrDcd);
        return ResponseEntity.ok(result);
    }


    @PostMapping
    public ResponseEntity<Void> addBgtBdgtList(@RequestBody List<BgtBdgt> bgtBdgtList) {
        bgtBdgtService.addBgtBdgtList(bgtBdgtList);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    public ResponseEntity<Void> updateBgtBdgtList(@RequestBody List<BgtBdgt> bgtBdgtList) {
        bgtBdgtService.updateBgtBdgtList(bgtBdgtList);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{bsnsYy}/{qtrDcd}")
    public ResponseEntity<Void> deleteBgtBdgt(@PathVariable String bsnsYy, @PathVariable String qtrDcd) {
        bgtBdgtService.deleteBgtBdgtList(bsnsYy, qtrDcd);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/execute")
    public ResponseEntity<Void> executeBgtBdgtList(@RequestBody List<BgtBdgt> bgtBdgtList) {
        bgtBdgtService.executeBgtBdgtList(bgtBdgtList);
        return ResponseEntity.ok().build();
    }
}

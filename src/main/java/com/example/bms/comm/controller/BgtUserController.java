package com.example.bms.comm.controller;

import com.example.bms.comm.dto.BgtUserDto;
import com.example.bms.comm.entity.BgtUser;
import com.example.bms.comm.service.BgtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class BgtUserController {
    private final BgtUserService bgtUserService;

    public BgtUserController(BgtUserService bgtUserService) {
        this.bgtUserService = bgtUserService;
    }

    @GetMapping
    public List<BgtUserDto> getAllUsers() {
        return bgtUserService.getAllUsers();
    }

    @GetMapping("/{userNo}")
    public BgtUserDto getUserById(@PathVariable String userNo) {
        return bgtUserService.getUserById(userNo);
    }

    @PostMapping
    public void createUser(@RequestBody BgtUser user) {
        bgtUserService.createUser(user);
    }

    @PutMapping("/{userNo}")
    public void updateUser(@PathVariable String userNo, @RequestBody BgtUserDto dto) {
        bgtUserService.updateUser(userNo, dto);
    }

    @DeleteMapping("/{userNo}")
    public void deleteUser(@PathVariable String userNo) {
        bgtUserService.deleteUser(userNo);
    }
}

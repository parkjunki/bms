package com.example.bms.comm.service;

import com.example.bms.comm.dto.BgtUserDto;
import com.example.bms.comm.entity.BgtUser;
import com.example.bms.comm.repository.BgtUserRepository;
import com.example.bms.glob.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BgtUserService {
    private final BgtUserRepository bgtUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BgtUserService(BgtUserRepository bgtUserRepository) {
        this.bgtUserRepository = bgtUserRepository;
    }

    public List<BgtUserDto> getAllUsers() {
        List<BgtUser> users = bgtUserRepository.findAll();
        return users.stream()
                .map(user -> BgtUserDto.builder()
                        .userNo(user.getUserNo())
                        .userNm(user.getUserNm())
                        .nickNm(user.getNickNm())
                        .teamCd(user.getTeamCd())
                        .authRole(user.getAuthRole())
                        .fsDt(user.getFsDt())
                        .lsDt(user.getLsDt())
                        .build())
                .toList();
    }

    public BgtUserDto getUserById(String userNo) {
        BgtUser user = bgtUserRepository.findById(userNo)
                .orElseThrow(() -> new CustomNotFoundException("사용자를 찾을 수 없습니다."));
        return BgtUserDto.builder()
                .userNo(user.getUserNo())
                .userNm(user.getUserNm())
                .nickNm(user.getNickNm())
                .teamCd(user.getTeamCd())
                .authRole(user.getAuthRole())
                .fsDt(user.getFsDt())
                .lsDt(user.getLsDt())
                .build();
    }

    public void createUser(BgtUser user) {
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        user.setFsDt(LocalDateTime.now());
        user.setLsDt(LocalDateTime.now());
        bgtUserRepository.save(user);
    }

    public void updateUser(String userNo, BgtUserDto dto) {
        BgtUser user = bgtUserRepository.findById(userNo)
                .orElseThrow(() -> new CustomNotFoundException("사용자를 찾을 수 없습니다."));
        user.setUserNm(dto.getUserNm());
        user.setNickNm(dto.getNickNm());
        user.setTeamCd(dto.getTeamCd());
        user.setAuthRole(dto.getAuthRole());
        user.setLsDt(LocalDateTime.now());
        bgtUserRepository.save(user);
    }

    public void deleteUser(String userNo) {
        bgtUserRepository.deleteById(userNo);
    }

}

package com.example.bms.comm;

import com.example.bms.comm.dto.BgtUserDto;
import com.example.bms.comm.entity.BgtUser;
import com.example.bms.comm.repository.BgtUserRepository;
import com.example.bms.comm.service.BgtUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BgtUserServiceTest {
    @Mock
    private BgtUserRepository bgtUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BgtUserService bgtUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 등록")
    void createUserTest() {
        BgtUser user = BgtUser.builder()
                .userNo("82062")
                .pwd("password123")
                .userNm("박준기")
                .nickNm("Tester")
                .teamCd("0001")
                .authRole("ADMIN")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        bgtUserService.createUser(user);

        verify(bgtUserRepository).save(user);
        assertNotNull(user.getPwd());
        //System.out.println("Encrypted Password: " + user.getPwd());
    }

    @Test
    @DisplayName("사용자 상세 조회")
    void getAllUsersTest() {
        List<BgtUser> users = List.of(
                BgtUser.builder()
                        .userNo("82062")
                        .userNm("박준기")
                        .nickNm("Tester")
                        .teamCd("0001")
                        .authRole("ADMIN")
                        .build(),
                BgtUser.builder()
                        .userNo("82021")
                        .userNm("홍길동")
                        .nickNm("Janie")
                        .teamCd("0002")
                        .authRole("USER")
                        .build()
        );

        when(bgtUserRepository.findAll()).thenReturn(users);

        List<BgtUserDto> result = bgtUserService.getAllUsers();

        assertEquals(2, result.size());
        verify(bgtUserRepository).findAll();
    }

    @Test
    @DisplayName("사용자정보 수정")
    void updateUserTest() {
        String userNo = "82062";
        BgtUser existingUser = BgtUser.builder()
                .userNo(userNo)
                .userNm("박준기")
                .nickNm("Tester")
                .teamCd("0001")
                .authRole("ADMIN")
                .build();

        BgtUserDto updateDto = BgtUserDto.builder()
                .userNo(userNo)
                .userNm("박준수")
                .nickNm("Debuger")
                .teamCd("0002")
                .authRole("USER")
                .build();

        when(bgtUserRepository.findById(userNo)).thenReturn(Optional.of(existingUser));

        bgtUserService.updateUser(userNo, updateDto);

        verify(bgtUserRepository).save(existingUser);
        assertEquals("박준수", existingUser.getUserNm());
        assertEquals("Debuger", existingUser.getNickNm());
        assertEquals("0002", existingUser.getTeamCd());
        assertEquals("USER", existingUser.getAuthRole());
    }

    @Test
    @DisplayName("사용자정보 삭제")
    void deleteUserTest() {
        String userNo = "82062";

        bgtUserService.deleteUser(userNo);

        verify(bgtUserRepository).deleteById(userNo);
    }
}

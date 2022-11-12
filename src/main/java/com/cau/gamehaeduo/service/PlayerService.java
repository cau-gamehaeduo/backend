package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerRequestDTO;
import com.cau.gamehaeduo.domain.player.PlayerResponseDTO;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.PlayerRepository;
import com.cau.gamehaeduo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlayerResponseDTO registerPlayer(PlayerRequestDTO requestDto){
        userRepository.registerPlayer(requestDto.getUserIdx());
        List<UserEntity> userList = userRepository.selectByUserId(requestDto.getUserIdx());
        UserEntity user = userList.get(0);
        PlayerEntity playerEntity = PlayerRequestDTO.of(requestDto, user);
        playerRepository.save(playerEntity);
        return new PlayerResponseDTO(true,
                "플레이어가 등록되었습니다.");
    }
}

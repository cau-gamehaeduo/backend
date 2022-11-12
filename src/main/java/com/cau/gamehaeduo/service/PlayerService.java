package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerRequestDTO;
import com.cau.gamehaeduo.domain.player.PlayerResponseDTO;
import com.cau.gamehaeduo.repository.PlayerRepository;
import com.cau.gamehaeduo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlayerResponseDTO registerPlayer(PlayerRequestDTO requestDto){
        userRepository.registerPlayer(requestDto.getUserIndex());
        PlayerEntity playerEntity = PlayerRequestDTO.of(requestDto);
        playerRepository.save(playerEntity);
        return new PlayerResponseDTO(true,
                "플레이어가 등록되었습니다.");
    }
}

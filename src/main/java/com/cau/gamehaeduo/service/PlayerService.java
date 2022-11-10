package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerRequestDTO;
import com.cau.gamehaeduo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public void registerPlayer(PlayerRequestDTO requestDto) {
        PlayerEntity playerEntity = PlayerRequestDTO.of(requestDto);
        playerRepository.save(playerEntity);
    }
}

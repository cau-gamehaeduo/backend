package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.Player;
import com.cau.gamehaeduo.domain.dto.PlayerRequestDto;
import com.cau.gamehaeduo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public void registerPlayer(PlayerRequestDto requestDto) {
        Player player = PlayerRequestDto.of(requestDto);
        playerRepository.save(player);
    }
}

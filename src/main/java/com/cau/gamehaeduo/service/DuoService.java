package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DuoService {
    private final PlayerRepository playerRepository;

    public List<PlayerProfileResponseDTO> getAllPlayer(final Pageable pageable) {
        Page<PlayerEntity> players = playerRepository.findAll(pageable);  // 10개
        List<PlayerProfileResponseDTO> playerProfiles = new ArrayList<>();
        for(PlayerEntity player : players) {
            playerProfiles.add(new PlayerProfileResponseDTO(player.getUser(), player));
        }
        return playerProfiles;
    }
}

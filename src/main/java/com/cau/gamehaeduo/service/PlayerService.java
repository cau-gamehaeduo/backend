package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.domain.player.PlayerRequestDTO;
import com.cau.gamehaeduo.domain.player.PlayerResponseDTO;
import com.cau.gamehaeduo.domain.player.ProfileRequestDTO;
import com.cau.gamehaeduo.domain.player.ProfileResponseDTO;
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
    public PlayerResponseDTO registerPlayer(PlayerRequestDTO requestDto) {
        userRepository.registerPlayer(requestDto.getUserIdx());
        int userIndex = requestDto.getUserIdx();
        UserEntity user = getUserEntity(userIndex);
        PlayerEntity playerEntity = PlayerRequestDTO.of(requestDto, user);
        playerRepository.save(playerEntity);
        return new PlayerResponseDTO(true,
                "플레이어가 등록되었습니다.");
    }

    // userIdx로 UserEntity 객체 가져오는 함수
    private UserEntity getUserEntity(int userIdx) {
        List<UserEntity> userList = userRepository.selectByUserId(userIdx);
        UserEntity user = userList.get(0);
        return user;
    }

    public ProfileResponseDTO getPlayerProfile(ProfileRequestDTO profileRequestDTO) {
        int userIndex = profileRequestDTO.getUserIdx();
        UserEntity user = getUserEntity(userIndex);

        // Player 등록 안 한 경우
        if (!isPlayer(userIndex)) {
            return new ProfileResponseDTO(
                    false,
                    user.getTop(),
                    user.getJungle(),
                    user.getMid(),
                    user.getAd(),
                    user.getSupporter()
            );
        }

        PlayerEntity player = playerRepository.findById(userIndex);
        return new PlayerProfileResponseDTO(
                true,
                user.getNickname(),
                player.getTier(),
                user.getTop(),
                user.getJungle(),
                user.getMid(),
                user.getAd(),
                user.getSupporter(),
                player.getPlayStyle(),
                player.getIntroduction(),
                user.getRating()
        );

    }

    public boolean isPlayer(int userIndex) {
        return playerRepository.existsById(userIndex);
    }
}

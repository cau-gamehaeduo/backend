package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponseStatus;
import com.cau.gamehaeduo.domain.player.*;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.PlayerRepository;
import com.cau.gamehaeduo.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.SIGNUP_EMPTY_USER_PROFILE;
import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.SIGNUP_INVALID_USER_PROFILE_PHOTO;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final S3UploaderService s3UploaderService;

    @Transactional
    public PlayerResponseDTO registerPlayer(MultipartFile mFile, PlayerRequestDTO requestDto) throws BaseException {
        String profilePhotoUrl;
        // 사용자 프로필 url 생성
        if (mFile.isEmpty()) {
            throw new BaseException(SIGNUP_EMPTY_USER_PROFILE);
        } else {
            try {
                profilePhotoUrl = s3UploaderService.upload(mFile);
            } catch (IOException e) {
                throw new BaseException(SIGNUP_INVALID_USER_PROFILE_PHOTO);
            }
        }

        userRepository.registerPlayer(requestDto.getUserIdx(), profilePhotoUrl);

        int userIndex = requestDto.getUserIdx();
        UserEntity user = getUserEntity(userIndex);

        PlayerEntity playerEntity = PlayerRequestDTO.of(requestDto, user);

        playerRepository.save(playerEntity);
        return new PlayerResponseDTO(true,
                "플레이어가 등록되었습니다.",
                profilePhotoUrl);
    }

    // userIdx로 UserEntity 객체 가져오는 함수
    private UserEntity getUserEntity(int userIdx) {
        List<UserEntity> userList = userRepository.selectByUserId(userIdx);
        UserEntity user = userList.get(0);
        return user;
    }

    public ProfileResponseDTO getUserPlayerProfile(int userIndex) {
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
        return new PlayerProfileResponseDTO(user, player);
    }

    public boolean isPlayer(int userIndex) {
        return playerRepository.existsById(userIndex);
    }

    public PlayerProfileResponseDTO getOtherPlayerProfile(int playerIdx) throws BaseException {
        UserEntity otherUser = getUserEntity(playerIdx);
        if (otherUser.getIsPlayer().equals("N")) {
            throw new BaseException(BaseResponseStatus.PRIVATE_PLAYER_PROFILE);
        }
        PlayerEntity player = playerRepository.findById(playerIdx);
        return new PlayerProfileResponseDTO(otherUser, player);
    }

    public ProfileResponseDTO getPlayerProfile(int userIdx, Integer otherIdx) throws BaseException {
        if (otherIdx == null) {
            return getUserPlayerProfile(userIdx);
        }
        return getOtherPlayerProfile(otherIdx);
    }

    //최근 등록한 플레이어 불러오기
    //(홈에서 세로 스크롤 호출시 사용)
    public PlayerListDTO getRecentRegisteredPlayers(final Pageable pageable) {
        Page<PlayerEntity> players = playerRepository.findAllOrderBy_DateDesc(pageable);  // 10개
        List<HomePartnerDTO> playerProfiles = new ArrayList<>();
        for (PlayerEntity player : players) {
            playerProfiles.add(new HomePartnerDTO(
                    player.getId(), player.getPrice(),
                    player.getUser().getNickname(),
                    player.getUser().getProfilePhotoUrl(),
                    player.getTier(), player.getUser().getRating()));
        }
        return new PlayerListDTO(playerProfiles);
    }

    //평점 상위 10명 불러오기
    public PlayerListDTO getHighRatingPlayers() {
        List<PlayerEntity> players = playerRepository.findTop10ByStatusEqualsOrderByUserRatingDesc("A");
        List<HomePartnerDTO> playerProfiles = new ArrayList<>();

        for (PlayerEntity player : players) {
            playerProfiles.add(new HomePartnerDTO(
                    player.getId(), player.getPrice(),
                    player.getUser().getNickname(),
                    player.getUser().getProfilePhotoUrl(),
                    player.getTier(), player.getUser().getRating()));
        }
        return new PlayerListDTO(playerProfiles);
    }

    @Transactional
    public void changePlayerState(int userId, boolean status) {
        PlayerEntity player = playerRepository.findById(userId);
        player.setStatus(status ? "A" : "I");
        playerRepository.save(player);
    }
}

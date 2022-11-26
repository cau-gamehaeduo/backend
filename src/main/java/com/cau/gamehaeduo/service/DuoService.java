package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponseStatus;
import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.duo.DuoRequestDTO;
import com.cau.gamehaeduo.domain.duo.DuoRequestResDTO;
import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.DuoRepository;
import com.cau.gamehaeduo.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.List;

import com.cau.gamehaeduo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class DuoService {
    private final PlayerRepository playerRepository;
    private final DuoRepository duoRepository;
    private final UserRepository userRepository;

    public List<PlayerProfileResponseDTO> getAllPlayer(int userIdx, final Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("registeredAt").descending());
        Page<PlayerEntity> players = playerRepository.findByStatusEquals("A", sortedPageable);  // 10개
        List<PlayerProfileResponseDTO> playerProfiles = new ArrayList<>();
        for(PlayerEntity player : players) {
            if(userIdx == player.getId()) continue;
            playerProfiles.add(new PlayerProfileResponseDTO(player.getUser(), player));
        }
        return playerProfiles;
    }

    //듀오 요청하기 (포인트 증가 및 감소)
    public DuoRequestResDTO requestDuo(DuoRequestDTO duoRequestDTO) throws BaseException {
        UserEntity user = getUserEntity(duoRequestDTO.getUserIdx());
        PlayerEntity player = playerRepository.findById(duoRequestDTO.getPlayerIdx());


        DuoEntity duoEntity = new DuoEntity(
                user,
                player.getUser(),
                "responseWaiting",
                duoRequestDTO.getUserIdx()
        );
        int duoIdx = duoRepository.save(duoEntity).getDuoId();

        int price = player.getPrice();


        if(user.getPoint() < price) {
            throw new BaseException(BaseResponseStatus.NOT_ENOUGH_POINT);
        }

        userRepository.updatePoint(user.getPoint()-price, user.getUserIdx() ) ;
        userRepository.updatePoint(player.getUser().getPoint() + price, player.getUser().getUserIdx());


        return new DuoRequestResDTO(duoIdx, duoRequestDTO.getUserIdx(), user.getPoint(),duoEntity.getStatus());
    }



    // userIdx로 UserEntity 객체 가져오는 함수
    private UserEntity getUserEntity(int userIdx) {
        List<UserEntity> userList = userRepository.selectByUserId(userIdx);
        UserEntity user = userList.get(0);
        return user;
    }
}

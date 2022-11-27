package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponseStatus;
import com.cau.gamehaeduo.domain.duo.AcceptDuoRequestDTO;
import com.cau.gamehaeduo.domain.duo.AcceptDuoResDTO;
import com.cau.gamehaeduo.domain.duo.CancelDuoRequestDTO;
import com.cau.gamehaeduo.domain.duo.CancelDuoResDTO;
import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.duo.DuoNumResDTO;
import com.cau.gamehaeduo.domain.duo.DuoRequestDTO;
import com.cau.gamehaeduo.domain.duo.DuoRequestResDTO;
import com.cau.gamehaeduo.domain.duo.FinishDuoRequestDTO;
import com.cau.gamehaeduo.domain.duo.FinishDuoResDTO;
import com.cau.gamehaeduo.domain.duo.DuoInfoResponseDTO;
import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.DuoRepository;
import com.cau.gamehaeduo.repository.PlayerRepository;
import com.cau.gamehaeduo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Log4j2
public class DuoService {
    private final PlayerRepository playerRepository;
    private final DuoRepository duoRepository;
    private final UserRepository userRepository;

    public List<PlayerProfileResponseDTO> getAllPlayer(int userIdx, final Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("registeredAt").descending());
        Page<PlayerEntity> players = playerRepository.findByStatusEquals("A", sortedPageable);  // 10개
        List<PlayerProfileResponseDTO> playerProfiles = new ArrayList<>();
        for (PlayerEntity player : players) {
            if (userIdx == player.getId()) {
                continue;
            }
            if (player.getStatus().equals("I")) {
                continue;
            }
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
                player.getUser(), "WAITING",
                player.getPrice()
        );
        int duoIdx = duoRepository.save(duoEntity).getDuoId();

        int price = player.getPrice();

        if (user.getPoint() < price) {
            throw new BaseException(BaseResponseStatus.NOT_ENOUGH_POINT);
        }

        //신청 유저의 포인트 감소
        int pointAfter = user.getPoint() - price;
        userRepository.updatePoint(pointAfter, user.getUserIdx());

        return new DuoRequestResDTO(duoIdx, duoRequestDTO.getUserIdx(), pointAfter, duoEntity.getStatus());
    }

    //듀오 신청 갯수 가져오기
    public DuoNumResDTO getDuoNum(int userIdx) {

        UserEntity user = getUserEntity(userIdx);
        int requestedDuoNum = duoRepository.countByRequestedUserId(user);
        int requestNum = duoRepository.countByRequestUserId(user);

        return new DuoNumResDTO(requestedDuoNum, requestNum);
    }


    //듀오 완료하기
    public FinishDuoResDTO finishDuo(FinishDuoRequestDTO finishDuoRequestDTO) throws BaseException {
        //듀오 완료 API 호출한 사람이 요청받은 사람인지 검증
        int duoIdx = finishDuoRequestDTO.getDuoIdx();

        DuoEntity duoEntity = duoRepository.findByDuoId(duoIdx);

        if (duoEntity.getRequestedUserId().getUserIdx() != finishDuoRequestDTO.getUserIdx()) {
            throw new BaseException(BaseResponseStatus.NOT_REQUESTED_PLAYER);
        }

        //현재 듀오 상태가 진행중인 상태인지 검증
        if (!duoEntity.getStatus().equals("PROCEEDING")) {
            throw new BaseException(BaseResponseStatus.DUO_NOT_PROCEEDING);
        }

        duoRepository.updateDuoStatus("FINISH", duoIdx);

        PlayerEntity player = playerRepository.findById(duoEntity.getRequestedUserId().getUserIdx());
        int duoPrice = duoEntity.getPrice();
        int updatePoint = player.getUser().getPoint() + duoPrice;

        userRepository.updatePoint(updatePoint, player.getUser().getUserIdx());

        return new FinishDuoResDTO(finishDuoRequestDTO.getDuoIdx(), "FINISH", updatePoint);
    }

    //듀오 수락하기
    public AcceptDuoResDTO acceptDuo(AcceptDuoRequestDTO acceptDuoRequestDTO) throws BaseException {
        //듀오 수락 API 호출한 사람이 요청받은 사람인지 검증
        int duoIdx = acceptDuoRequestDTO.getDuoIdx();

        DuoEntity duoEntity = duoRepository.findByDuoId(duoIdx);

        //듀오 요청받은 사람이 아니면 에러
        if (duoEntity.getRequestedUserId().getUserIdx() != acceptDuoRequestDTO.getUserIdx()) {
            throw new BaseException(BaseResponseStatus.DUO_ACCEPT_NOT_REQUESTED_USER);
        }

        //현재 듀오 상태가 수락 대기중인지 확인
        if (!duoEntity.getStatus().equals("WAITING")) {
            throw new BaseException(BaseResponseStatus.DUO_NOT_WAITING);
        }
        duoRepository.updateDuoStatus("PROCEEDING", duoIdx);

        return new AcceptDuoResDTO(acceptDuoRequestDTO.getDuoIdx(), "PROCEEDING");
    }


    @Transactional
    //듀오 취소하기
    public CancelDuoResDTO cancelDuo(CancelDuoRequestDTO cancelDuoRequestDTO) throws BaseException {

        int duoIdx = cancelDuoRequestDTO.getDuoIdx();

        DuoEntity duoEntity = duoRepository.findByDuoId(duoIdx);

        //듀오 취소 요청한 사람이 해당 듀오의 일원인지 확인
        if (duoEntity.getRequestedUserId().getUserIdx() != cancelDuoRequestDTO.getUserIdx()
                && duoEntity.getRequestUserId().getUserIdx() != cancelDuoRequestDTO.getUserIdx()) {
            throw new BaseException(BaseResponseStatus.DUO_NOT_PARTICIPATE_USER);
        }

        String duoStatus = duoEntity.getStatus();
        //이미 완료된 상태인지 검증
        if (duoStatus.equals("FINISH")) {
            throw new BaseException(BaseResponseStatus.DUO_ALREADY_COMPLETE);
        } else if (duoStatus.equals("CANCEL")) {
            throw new BaseException(BaseResponseStatus.DUO_ALREADY_CANCEL);
        }

        //취소 요청한 사람이 듀오 신청한사람인지 신청받은 사람인지 확인 후 포인트 돌려주기
        int requestUserIdx = duoEntity.getRequestUserId().getUserIdx();
        int updatePrice = duoEntity.getPrice() + duoEntity.getRequestUserId().getPoint();

        userRepository.updatePoint(updatePrice, requestUserIdx);

        duoRepository.updateDuoStatus("CANCEL", duoIdx);
        UserEntity userEntity = getUserEntity(cancelDuoRequestDTO.getUserIdx());

        return new CancelDuoResDTO(duoIdx, "CANCEL", userEntity.getPoint());
    }


    // userIdx로 UserEntity 객체 가져오는 함수
    private UserEntity getUserEntity(int userIdx) {
        UserEntity user = userRepository.selectByUserId(userIdx);
        return user;
    }

    public List<DuoInfoResponseDTO> getRequestDuo(int userId) {
        UserEntity user = userRepository.selectByUserId(userId);
        List<DuoEntity> requestDuos = duoRepository.findByRequestUserId(user);
        return getDuoInfoResponseDTOS(requestDuos);
    }

    public List<DuoInfoResponseDTO> getRequestedDuo(int userId) {
        UserEntity user = userRepository.selectByUserId(userId);
        List<DuoEntity> requestDuos = duoRepository.findByRequestedUserId(user);
        return getDuoInfoResponseDTOS(requestDuos);
    }

    private List<DuoInfoResponseDTO> getDuoInfoResponseDTOS(List<DuoEntity> requestDuos) {
        List<DuoInfoResponseDTO> responseDTOS = new ArrayList<>();
        for (DuoEntity duo : requestDuos) {
            UserEntity duoUser = userRepository.selectByUserId(duo.getDuoId());
            PlayerEntity duoPlayer = playerRepository.findById(duo.getDuoId());
            responseDTOS.add(new DuoInfoResponseDTO(duo, duoUser, duoPlayer));
        }
        return responseDTOS;
    }
}

package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.domain.user.UserLoginInfo;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserRepository {

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    public int createUser(UserEntity userEntity) {
        // User 테이블에 데이터 추가
        String createUserQuery = "insert into User(nickname, profile_photo_url, top, jungle, mid, ad, supporter, kakao_id) values(?,?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                userEntity.getNickname(),
                userEntity.getProfilePhotoUrl(),
                userEntity.getTop(),
                userEntity.getJungle(),
                userEntity.getMid(),
                userEntity.getAd(),
                userEntity.getSupporter(),
                userEntity.getKakaoIdx()
        };

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        // userIdx 반환
        String lastInsertUserIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertUserIdxQuery, int.class);
    }

    public int checkNickname(String nickname) {
        String checkNicknameQuery = "select exists(select nickname from User where nickname=?)";
        String  checkNicknameParam = nickname;
        return this.jdbcTemplate.queryForObject(
                checkNicknameQuery,
                int.class,
                checkNicknameParam);
    }

    public int checkKakaoMember(long kakaoIdx) {
        String checkKakaoMemberQuery = "select exists(select kakao_id from User where kakao_id=?)";
        long  checkKakaoMemberParam = kakaoIdx;
        return this.jdbcTemplate.queryForObject(
                checkKakaoMemberQuery,
                int.class,
                checkKakaoMemberParam);
    }


    public UserLoginInfo getUserLoginInfo(long kakaoIdx){
        String userLoginInfoQuery =
                "select u.user_id, u.nickname, u.profile_photo_url, u.status, u.is_player\n" +
                        "from User u \n" +
                        "where u.kakao_id = ?";


        try {
            return this.jdbcTemplate.queryForObject(userLoginInfoQuery,
                    (rs, row) -> new UserLoginInfo(
                            rs.getInt("user_id"),
                            rs.getString("nickname"),
                            rs.getString("profile_photo_url"),
                            rs.getString("status"),
                            rs.getString("is_player")
                    ),
                    kakaoIdx);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<UserEntity> selectByUserId(int userIdx) {
        return jdbcTemplate.query("select * from User where user_id = ?",
                (rs, row) -> new UserEntity(
                        rs.getInt("user_id"),
                        rs.getString("nickname"),
                        rs.getString("profile_photo_url"),
                        rs.getString("is_player"),
                        rs.getFloat("rating"),
                        rs.getString("created_at"),
                        rs.getString("status"),
                        rs.getInt("top"),
                        rs.getInt("jungle"),
                        rs.getInt("mid"),
                        rs.getInt("ad"),
                        rs.getInt("supporter"),
                        rs.getLong("kakao_id")
                ),
                userIdx
                );

    }

    //User 의 Player 상태 Y로 변경
    public void registerPlayer(int userIndex, String profilePhotoUrl) {
        String userRegisterPlayerQuery = "update User set is_player = ?, profile_photo_url = ? where user_id = ?";
        Object[] params = new Object[]{
                "Y",profilePhotoUrl,userIndex
        };

        this.jdbcTemplate.update(userRegisterPlayerQuery,params);
    }

    public int updatePlayerState(boolean status, int userId) {
        String changedState;
        if(status) {
            changedState = "Active";
        } else {
            changedState = "Inactive";
        }
        String updatePlayerStateQuery = "update User set status = ? where user_id = ?";
        return this.jdbcTemplate.update(updatePlayerStateQuery, changedState, userId);
    }
}

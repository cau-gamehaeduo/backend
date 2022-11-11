package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;

@Log4j2
@Repository
public class UserRepository {

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    public void createUser(UserEntity userEntity) {
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
//        String lastInsertUserIdxQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInsertUserIdxQuery, int.class);
    }

    public int checkNickname(String nickname) {
        String checkNicknameQuery = "select exists(select nickname from User where nickname=?)";
        String  checkNicknameParam = nickname;
        return this.jdbcTemplate.queryForObject(
                checkNicknameQuery,
                int.class,
                checkNicknameParam);
    }
}

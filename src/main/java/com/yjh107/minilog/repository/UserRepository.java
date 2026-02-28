package com.yjh107.minilog.repository;

import com.yjh107.minilog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    User 엔티티와 관련된 데이터베이스 연산을 담당하는 인터페이스
    JpaRepository를 상속하여 기본적인 CRUD 메서드를 별도의 구현 없이 바로 사용
    findByUsername() 메서드를 추가하여 사용자 이름(username)으로 User 엔티티 조회
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

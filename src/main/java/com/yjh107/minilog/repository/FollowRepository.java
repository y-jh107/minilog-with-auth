package com.yjh107.minilog.repository;

import com.yjh107.minilog.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
    Follow 엔티티와 관련된 데이터베이스 연산 담당
    팔로우 관계를 기반으로 특정 사용자가 팔로우하는 사용자 목록을 조회하거나(findByFollowerId)
    팔로우 관계의 존재 여부를 확인하는 메서드 제공(findByFollowerIdAndFolloweeId)
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}

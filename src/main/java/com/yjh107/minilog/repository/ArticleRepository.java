package com.yjh107.minilog.repository;

import com.yjh107.minilog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    Article 엔티티와 관련된 데이터베이스 연산을 담당
    findAllByAuthorId 파생 쿼리 메서드 추가(특정 작성자가 작성한 모든 게시글 조회)
    findAllByFollowerId JPQL 메서드 추가(팔로우 관계를 기반으로 팔로워가 작성한 게시글을 최신 순으로 조회)
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByAuthorId(Long authorId);

    @Query(
            "SELECT a FROM Article a JOIN a.author u JOIN Follow f"
            + " ON u.id = f.followee.id WHERE"
            + " f.follower.id = :authorId ORDER BY a.createdAt DESC")
    List<Article> findAllByFollowerId(Long authorId);
}

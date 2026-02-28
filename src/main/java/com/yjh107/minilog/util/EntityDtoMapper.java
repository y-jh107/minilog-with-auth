package com.yjh107.minilog.util;

import com.yjh107.minilog.dto.ArticleResponseDto;
import com.yjh107.minilog.dto.FollowResponseDto;
import com.yjh107.minilog.dto.UserResponseDto;
import com.yjh107.minilog.entity.Article;
import com.yjh107.minilog.entity.Follow;
import com.yjh107.minilog.entity.User;

// 엔티티와 DTO 사이의 변환을 담당하는 매퍼
public class EntityDtoMapper {
    public static ArticleResponseDto toDto(Article article) {
        return ArticleResponseDto.builder()
                .articleId(article.getId())
                .content(article.getContent())
                .authorId(article.getAuthor().getId())
                .authorName(article.getAuthor().getUsername())
                .createdAt(article.getCreatedAt())
                .build();
    }

    public static FollowResponseDto toDto(Follow follow) {
        return FollowResponseDto.builder()
                .followerId(follow.getFollower().getId())
                .followeeId(follow.getFollowee().getId())
                .build();
    }

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder().id(user.getId()).username(user.getUsername()).build();
    }

    public static Follow toEntity(Long followerId, Long followeeId) {
        return Follow.builder()
                .follower(User.builder().id(followerId).build())
                .followee(User.builder().id(followeeId).build())
                .build();
    }
}

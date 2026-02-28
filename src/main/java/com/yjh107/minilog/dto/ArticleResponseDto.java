package com.yjh107.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

/*
    게시글 작성, 수정, 조회에 대한 응답에 사용되는 DTO
    게시글 ID, 내용, 작성자 정보, 작성 시간 필드를 포함
 */
@Data
@Builder
public class ArticleResponseDto {
    @NonNull private Long articleId;
    @NonNull private String content;
    @NonNull private Long authorId;
    @NonNull private String authorName;
    @NonNull private LocalDateTime createdAt;
}

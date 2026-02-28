package com.yjh107.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
    게시글 작성 및 수정 요청을 위한 DTO
    게시글의 내용과 작성자의 ID 필드를 포함
 */
@Data
@Builder
public class ArticleRequestDto {
    @NonNull private String content;
    @NonNull private Long authorId;
}

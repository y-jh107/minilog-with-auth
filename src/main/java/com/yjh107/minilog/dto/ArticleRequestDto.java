package com.yjh107.minilog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @NonNull
    private String content;

    @Deprecated(since = "2.0", forRemoval = true)
    @Schema(
            description = "작성자 ID (이 필드는 더 이상 사용되지 않습니다.)",
            example = "0",
            required = true,
            deprecated = true
    )
    private Long authorId;
}

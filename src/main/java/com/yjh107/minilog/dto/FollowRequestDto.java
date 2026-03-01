package com.yjh107.minilog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

/*
    팔로우 요청을 위한 DTO
    팔로우하는 사람과 팔로우 당하는 사람의 ID 필드를 포함
 */
@Data
public class FollowRequestDto {
    @Deprecated(since = "2.0", forRemoval = true)
    @Schema(
            description = "팔로워 ID (이 필드는 더 이상 사용되지 않습니다.)",
            example = "0",
            required = true,
            deprecated = true
    )
    private Long followerId;
    
    @NonNull
    private Long followeeId;
}

package com.yjh107.minilog.dto;

import lombok.Data;
import lombok.NonNull;

/*
    팔로우 요청을 위한 DTO
    팔로우하는 사람과 팔로우 당하는 사람의 ID 필드를 포함
 */
@Data
public class FollowRequestDto {
    @NonNull private Long followerId;
    @NonNull private Long followeeId;
}

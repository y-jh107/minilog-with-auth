package com.yjh107.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
    팔로우 관계 생성 및 조회 시 응답 객체로 사용
    팔로워와 팔로이 ID 필드를 포함
 */
@Data
@Builder
public class FollowResponseDto {
    @NonNull private Long followerId;
    @NonNull private Long followeeId;
}

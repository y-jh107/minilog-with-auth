package com.yjh107.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
    등록/수정/조회에 대한 응답을 위한 DTO
    사용자 ID와 사용자 이름 필드를 포함하며, 서비스 계층에서 컨트롤러에 반환할 때 사용
 */
@Data
@Builder
public class UserResponseDto {
    @NonNull private Long id;
    @NonNull private String username;
}

package com.yjh107.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
    사용자 등록 및 수정 요청을 위한 DTO
    사용자 이름과 비밀번호 필드를 포함하고 있으며, 컨트롤러를 통해 입력된 데이터를 서비스 계층으로 전달하는 데 사용
 */
@Data
@Builder
public class UserRequestDto {
    @NonNull private String username;
    @NonNull private String password;
}

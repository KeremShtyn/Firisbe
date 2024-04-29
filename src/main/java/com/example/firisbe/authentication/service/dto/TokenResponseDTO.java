package com.example.firisbe.authentication.service.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenResponseDTO {

    private String accessToken;

    private LocalDateTime expireTime;

    private UserDataDTO userData;

}

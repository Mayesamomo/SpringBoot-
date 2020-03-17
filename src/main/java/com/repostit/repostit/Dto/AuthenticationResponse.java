package com.repostit.repostit.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
}

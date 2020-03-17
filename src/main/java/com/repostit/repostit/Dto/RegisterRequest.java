package com.repostit.repostit.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullname;
    private String username;
    private String email;
    private String password;
}

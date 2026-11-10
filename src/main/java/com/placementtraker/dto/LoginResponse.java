package com.placementtraker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private final String studentId;
    private final String name;
    private final String email;
    private final String role;
}
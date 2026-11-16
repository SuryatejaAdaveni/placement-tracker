
package com.placementtraker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponse {

    private final String studentId;
    private final String name;
    private final String email;
    private final String role;
    private final String token;
}

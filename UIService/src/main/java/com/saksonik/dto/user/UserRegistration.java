package com.saksonik.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRegistration {
    String username;
    String role;
    String email;
    String firstName;
    String lastName;
    String patronymic;
}

package org.example.DTO;

import org.example.Models.User;
import org.example.Models.UserAuthToken;

public record UserCreateReturn(
        UserAuthToken Authorization,
        User CreatedUser
) {}

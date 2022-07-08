package org.example.Models;

import java.util.UUID;

public record UserAuthToken(
        UUID publicKey,
        UUID privateKey
) {}

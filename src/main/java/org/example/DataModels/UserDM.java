package org.example.DataModels;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record UserDM(@NotNull UUID userId,
                     @NotNull UUID privateKey,
                     @NotNull String name
) {}
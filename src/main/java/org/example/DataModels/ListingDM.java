package org.example.DataModels;

import org.example.Enums.ListingStage;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ListingDM(
    @NotNull UUID listingId,
    @NotNull UUID userId,
    String title,
    String description,
    ListingStage stage,
    Integer category
) {}

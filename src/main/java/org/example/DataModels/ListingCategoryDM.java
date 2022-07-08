package org.example.DataModels;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ListingCategoryDM(
        @NotNull Integer listingCategoryId,
        @NotNull String name
) {}

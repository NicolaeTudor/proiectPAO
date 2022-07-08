package org.example.Models.Auction;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record Bid(
        @NotNull UUID ownerId,
        int bidValue
) {}

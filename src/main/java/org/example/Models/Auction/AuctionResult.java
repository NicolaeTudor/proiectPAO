package org.example.Models.Auction;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record AuctionResult(
        @NotNull UUID listingId,
        boolean wasSold,
        UUID winnerId,
        int settledValue
) {}

package org.example.Models;

import org.example.Enums.ListingStage;

import java.util.UUID;

public record Listing(
        UUID listingId,
        UUID ownerId,
        String title,
        String description,
        ListingStage stage,
        Integer category
) {}

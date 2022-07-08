package org.example.Models.Auction;

import org.example.Models.Listing;
import org.jetbrains.annotations.NotNull;

public class FirstPriceAuction extends Auction{
    public FirstPriceAuction(Listing listing, Integer minimumStartingBid, Integer priceFloor) {
        super(listing, minimumStartingBid, priceFloor, new Bid(listing.ownerId(), priceFloor));
    }

    @Override
    public void addBid(@NotNull Bid bid) {
        if(this.isNotValidBid(bid)) return;

        if(bid.bidValue() > _winningBid.bidValue()) _winningBid = bid;
    }
}

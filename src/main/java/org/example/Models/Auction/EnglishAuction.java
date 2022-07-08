package org.example.Models.Auction;

import org.example.Models.Listing;
import org.jetbrains.annotations.NotNull;

public class EnglishAuction extends Auction{
    private final Integer minimumRaise;
    public EnglishAuction(Listing listing, Integer minimumStartingBid, Integer priceFloor, Integer minimumRaise) {
        super(listing, minimumStartingBid, priceFloor, new Bid(listing.ownerId(), priceFloor - minimumRaise));
        this.minimumRaise = minimumRaise;
    }

    @Override
    public void addBid(@NotNull Bid bid) {
        if(this.isNotValidBid(bid)) return;

        _winningBid = bid;
    }

    @Override
    public boolean isNotValidBid(Bid bid) {
        if(super.isNotValidBid(bid)) return true;

        return bid.bidValue() < this._winningBid.bidValue() + minimumRaise;
    }
}

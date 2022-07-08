package org.example.Models.Auction;

import org.example.Models.Listing;
import org.jetbrains.annotations.NotNull;

public abstract class Auction {
    final Listing _listing;
    final Integer _minimumStartingBid;
    final Integer _priceFloor;
    Bid _winningBid = null;
    public Auction(Listing listing, Integer minimumStartingBid, Integer priceFloor, Bid winningBid) {
        _listing = listing;
        this._minimumStartingBid = minimumStartingBid;
        this._priceFloor = priceFloor;
        this._winningBid = winningBid;
    }

    public abstract void addBid(@NotNull Bid bid);
    public AuctionResult getAuctionResult() {
        var wasSold = this.wasSold();
        return new AuctionResult(_listing.listingId(), wasSold, _winningBid.ownerId(), _winningBid.bidValue());
    }
    boolean isNotValidBid(Bid bid) {
        return bid.bidValue() < _minimumStartingBid || bid.ownerId() == this._listing.ownerId();
    }

    boolean wasSold() {
        return _winningBid.ownerId() != _listing.ownerId();
    }
}

package org.example.Services;

import org.example.DTO._Wrap;
import org.example.DataModels.ListingDM;
import org.example.Enums.ListingStage;
import org.example.Models.Auction.*;
import org.example.Models.Listing;
import org.example.Models.UserAuthToken;
import org.example.Repositories.ListingRepository;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AuctionService {
    private static volatile AuctionService instance;
    private final Map<UUID, Auction> _auctionList;
    private final ListingRepository _listingRepository;

    public AuctionService() {
        _listingRepository = new ListingRepository();
        _auctionList = new HashMap<>();
    }

    public static AuctionService getInstance() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate
        // instances as a result.
        //
        // It may seem that having the `result` variable here is completely
        // pointless. There is, however, a very important caveat when
        // implementing double-checked locking in Java, which is solved by
        // introducing this local variable.
        //
        // You can read more info DCL issues in Java here:
        // https://refactoring.guru/java-dcl-issue
        AuctionService result = instance;
        if (result != null) {
            return result;
        }
        synchronized(AuctionService.class) {
            if (instance == null) instance = new AuctionService();
            return instance;
        }
    }

    private boolean isValidForAuction(@NotNull UserAuthToken userToken, @NotNull UUID listingId, _Wrap<Listing> w_listing) {
        var _userService = UserService.getInstance();
        var _listingService = ListingService.getInstance();
        var isValidAuth = _userService.checkAuthToken(userToken);

        if(!isValidAuth) return true;

        var listing = _listingService.get(listingId);
        w_listing.set( _listingService.get(listingId));

        if(listing.ownerId() != userToken.publicKey() || listing.stage() != ListingStage.Draft) return true;

        return false;
    }

    private void openAuction(@NotNull Auction auction, @NotNull UUID listingId) {
        _listingRepository.Update(
                listingId,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(ListingStage.Published)
        );
    }

    public UUID createEnglishAuction(@NotNull UserAuthToken userToken, @NotNull UUID listingId, int minimumStartingBid, int priceFloor, int minimumRaise) {
        _Wrap<Listing> w_listing = new _Wrap<>(null);
        if (isValidForAuction(userToken, listingId, w_listing)) return null;
        var listing = w_listing.get();

        Auction auction  = new EnglishAuction(listing, minimumStartingBid, priceFloor, minimumRaise);

        var auctionKey = UUID.randomUUID();
        _auctionList.put(auctionKey, auction);
        openAuction(auction, listingId);
        return auctionKey;
    }

    public UUID createFirstPriceAuction(@NotNull UserAuthToken userToken, @NotNull UUID listingId, int minimumStartingBid, int priceFloor) {
        _Wrap<Listing> w_listing = new _Wrap<>(null);
        if (isValidForAuction(userToken, listingId, w_listing)) return null;
        var listing = w_listing.get();

        Auction auction  = new FirstPriceAuction(listing, minimumStartingBid, priceFloor);

        var auctionKey = UUID.randomUUID();
        _auctionList.put(auctionKey, auction);
        openAuction(auction, listingId);
        return auctionKey;
    }

    public void makeBid(@NotNull UUID auctionId, @NotNull UserAuthToken userToken, int bidValue) {
        var _userService = UserService.getInstance();
        var isValidAuth = _userService.checkAuthToken(userToken);

        if(!isValidAuth)    return;

        var auction = _auctionList.get(auctionId);

        if(auction == null) return;

        var bid = new Bid(userToken.publicKey(), bidValue);
        auction.addBid(bid);
    }

    public AuctionResult closeAuction(@NotNull UUID auctionId, @NotNull UserAuthToken userToken) {
        var _userService = UserService.getInstance();
        var isValidAuth = _userService.checkAuthToken(userToken);

        if(!isValidAuth)    return null;

        var auction = _auctionList.remove(auctionId);

        if(auction == null) return null;

        var auctionResult = auction.getAuctionResult();
        _listingRepository.Update(
                auctionResult.listingId(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(auctionResult.wasSold() ? ListingStage.Closed : ListingStage.Draft));

        return auctionResult;
    }
}

package org.example;

import org.example.Services.AuctionService;
import org.example.Services.ListingService;
import org.example.Services.UserService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var _userService = UserService.getInstance();
        var _listingService = ListingService.getInstance();
        var _auctionService = AuctionService.getInstance();

        var auctioneerReturn = _userService.create("Alex");
        var loserReturn = _userService.create("Bob");
        var winnerReturn = _userService.create("Cindy");

        var auctioneer = _userService.get(auctioneerReturn.CreatedUser().get_publicKey());
        var loser = _userService.get(auctioneerReturn.CreatedUser().get_publicKey());
        var winner = _userService.get(auctioneerReturn.CreatedUser().get_publicKey());

        System.out.println("auctioneer = " + auctioneer.get_username());

        var createdCategory = _listingService.createCategory("Antiquities");
        var getCategory = _listingService.getCategory(createdCategory.listingCategoryId());

        System.out.println("category name = " + getCategory.name());

        var listing = _listingService.create(auctioneerReturn.Authorization(), "big vase", "a very big vase", getCategory.listingCategoryId());
        System.out.println("listing title = " + listing.title());

        var listingFPrice = _listingService.create(auctioneerReturn.Authorization(), "bigger vase", "a most big vase", getCategory.listingCategoryId());
        System.out.println("listing title = " + listingFPrice.title());

        var engAuctionId = _auctionService.createEnglishAuction(auctioneerReturn.Authorization(), listing.listingId(),10, 21, 5 );
        var fPriceAuctionId = _auctionService.createFirstPriceAuction(auctioneerReturn.Authorization(), listing.listingId(),20, 31);
        _auctionService.makeBid(engAuctionId, loserReturn.Authorization(), 14);
        _auctionService.makeBid(engAuctionId, winnerReturn.Authorization(), 27);
        _auctionService.makeBid(fPriceAuctionId, loserReturn.Authorization(), 81);
        _auctionService.makeBid(fPriceAuctionId, loserReturn.Authorization(), 54);
        var resultEng = _auctionService.closeAuction(engAuctionId, auctioneerReturn.Authorization());
        var resultFPrice = _auctionService.closeAuction(fPriceAuctionId, auctioneerReturn.Authorization());

        System.out.println("winning bid value eng = " + resultEng.settledValue());
        System.out.println("winning bid value f price = " + resultFPrice.settledValue());
    }
}